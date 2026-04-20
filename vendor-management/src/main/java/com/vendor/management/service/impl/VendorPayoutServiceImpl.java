package com.vendor.management.service.impl;

import com.vendor.management.dto.request.VendorPayoutCreateRequest;
import com.vendor.management.dto.response.VendorPayoutResponse;
import com.vendor.management.entity.Vendor;
import com.vendor.management.entity.VendorPayout;
import com.vendor.management.enums.PayoutStatus;
import com.vendor.management.exception.ResourceNotFoundException;
import com.vendor.management.repository.VendorPayoutRepository;
import com.vendor.management.repository.VendorRepository;
import com.vendor.management.service.VendorPayoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service implementation for managing vendor payouts.
 *
 * Provides business logic for creating, retrieving, updating,
 * and deleting vendor payouts, along with aggregation operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VendorPayoutServiceImpl implements VendorPayoutService {

    private final VendorPayoutRepository payoutRepository;
    private final VendorRepository vendorRepository;

    /**
     * Creates a new payout for a vendor.
     *
     * @param request payout creation request containing vendor ID, amount, and status
     * @return created payout response
     * @throws ResourceNotFoundException if vendor does not exist
     */
    @Override
    @Transactional
    public VendorPayoutResponse createPayout(VendorPayoutCreateRequest request) {
        log.info("Creating payout for vendor ID: {}", request.getVendorId());

        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + request.getVendorId()));

        VendorPayout payout = VendorPayout.builder()
                .vendor(vendor)
                .amount(request.getAmount())
                .status(request.getStatus() != null ? request.getStatus() : PayoutStatus.PENDING)
                .build();

        VendorPayout savedPayout = payoutRepository.save(payout);
        log.info("Payout created with ID: {}", savedPayout.getPayoutId());
        return mapToResponse(savedPayout);
    }

    /**
     * Retrieves a payout by its ID.
     *
     * @param id payout ID
     * @return payout response
     * @throws ResourceNotFoundException if payout is not found
     */
    @Override
    public VendorPayoutResponse getPayoutById(Integer id) {
        VendorPayout payout = payoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payout not found with ID: " + id));
        return mapToResponse(payout);
    }

    /**
     * Retrieves payouts for a specific vendor with pagination.
     *
     * @param vendorId vendor ID
     * @param pageable pagination configuration
     * @return paginated list of payout responses
     * @throws ResourceNotFoundException if vendor is not found
     */
    @Override
    public Page<VendorPayoutResponse> getPayoutsByVendorId(Integer vendorId, Pageable pageable) {
        if (!vendorRepository.existsById(vendorId)) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + vendorId);
        }
        return payoutRepository.findByVendorVendorId(vendorId, pageable)
                .map(this::mapToResponse);
    }

    /**
     * Retrieves all payouts with pagination.
     *
     * @param pageable pagination configuration
     * @return paginated list of payout responses
     */
    @Override
    public Page<VendorPayoutResponse> getAllPayouts(Pageable pageable) {
        return payoutRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    /**
     * Updates the status of a payout.
     *
     * @param payoutId payout ID
     * @param status   new status value (true = completed, false = pending)
     * @return updated payout response
     * @throws ResourceNotFoundException if payout is not found
     */
    @Override
    @Transactional
    public VendorPayoutResponse updatePayoutStatus(Integer payoutId, PayoutStatus status) {
        VendorPayout payout = payoutRepository.findById(payoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Payout not found with ID: " + payoutId));

        payout.setStatus(status);
        return mapToResponse(payoutRepository.save(payout));
    }

    /**
     * Calculates the total paid amount for a vendor.
     *
     * @param vendorId vendor ID
     * @return total payout amount, or zero if no payouts exist
     * @throws ResourceNotFoundException if vendor is not found
     */
    @Override
    public BigDecimal getTotalPaidAmountByVendor(Integer vendorId) {
        if (!vendorRepository.existsById(vendorId)) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + vendorId);
        }
        BigDecimal total = payoutRepository.getTotalPaidAmount(vendorId);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Deletes a payout by its ID.
     *
     * @param id payout ID
     * @throws ResourceNotFoundException if payout is not found
     */
    @Override
    @Transactional
    public void deletePayout(Integer id) {
        log.info("Deleting payout with ID: {}", id);

        if (!payoutRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payout not found with ID: " + id);
        }

        payoutRepository.deleteById(id);
        log.info("Payout deleted with ID: {}", id);
    }

    /**
     * Maps VendorPayout entity to VendorPayoutResponse DTO.
     *
     * @param payout VendorPayout entity
     * @return mapped response DTO
     */
    private VendorPayoutResponse mapToResponse(VendorPayout payout) {
        return VendorPayoutResponse.builder()
                .payoutId(payout.getPayoutId())
                .vendorId(payout.getVendor().getVendorId())
                .vendorBusinessName(payout.getVendor().getBusinessName())
                .amount(payout.getAmount())
                .payoutDate(payout.getPayoutDate())
                .status(payout.getStatus())
                .build();
    }
}