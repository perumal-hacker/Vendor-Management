package com.vendor.management.service;

import com.vendor.management.dto.request.VendorPayoutCreateRequest;
import com.vendor.management.dto.response.VendorPayoutResponse;
import com.vendor.management.enums.PayoutStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service interface for managing vendor payouts.
 *
 * Defines operations for creating, retrieving, updating,
 * and deleting payouts, as well as calculating total payouts.
 */
public interface VendorPayoutService {

    /**
     * Creates a new payout.
     *
     * @param request payout creation request
     * @return created payout response
     */
    VendorPayoutResponse createPayout(VendorPayoutCreateRequest request);

    /**
     * Retrieves a payout by its ID.
     *
     * @param id payout ID
     * @return payout response
     */
    VendorPayoutResponse getPayoutById(Integer id);

    /**
     * Retrieves payouts for a specific vendor with pagination.
     *
     * @param vendorId vendor ID
     * @param pageable pagination configuration
     * @return paginated list of payouts
     */
    Page<VendorPayoutResponse> getPayoutsByVendorId(Integer vendorId, Pageable pageable);

    /**
     * Retrieves all payouts with pagination.
     *
     * @param pageable pagination configuration
     * @return paginated list of payouts
     */
    Page<VendorPayoutResponse> getAllPayouts(Pageable pageable);

    /**
     * Updates the status of a payout.
     *
     * @param payoutId payout ID
     * @param status   payout status (true = completed, false = pending)
     * @return updated payout response
     */
    VendorPayoutResponse updatePayoutStatus(Integer payoutId, PayoutStatus status);;

    /**
     * Calculates the total paid amount for a vendor.
     *
     * @param vendorId vendor ID
     * @return total payout amount
     */
    BigDecimal getTotalPaidAmountByVendor(Integer vendorId);

    /**
     * Deletes a payout by its ID.
     *
     * @param id payout ID
     */
    void deletePayout(Integer id);
}