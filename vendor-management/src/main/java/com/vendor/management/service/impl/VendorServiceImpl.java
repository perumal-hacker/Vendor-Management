package com.vendor.management.service.impl;

import com.vendor.management.dto.request.VendorCreateRequest;
import com.vendor.management.dto.request.VendorUpdateRequest;
import com.vendor.management.dto.response.VendorCategoryResponse;
import com.vendor.management.dto.response.VendorResponse;
import com.vendor.management.entity.Vendor;
import com.vendor.management.entity.VendorCategory;
import com.vendor.management.entity.VendorCategoryMapping;
import com.vendor.management.enums.VendorStatus;
import com.vendor.management.exception.DuplicateResourceException;
import com.vendor.management.exception.ResourceNotFoundException;
import com.vendor.management.repository.VendorCategoryMappingRepository;
import com.vendor.management.repository.VendorCategoryRepository;
import com.vendor.management.repository.VendorRepository;
import com.vendor.management.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing vendors.
 * Provides business logic for vendor creation, retrieval, update,
 * deletion, and category mapping operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorCategoryRepository categoryRepository;
    private final VendorCategoryMappingRepository mappingRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new vendor.
     *
     * @param request vendor creation request
     * @return created vendor response
     * @throws DuplicateResourceException if email already exists
     */
    @Override
    @Transactional
    public VendorResponse createVendor(VendorCreateRequest request) {
        log.info("Creating new vendor with email: {}", request.getEmail());

        if (vendorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Vendor with email " + request.getEmail() + " already exists");
        }

        Vendor vendor = Vendor.builder()
                .businessName(request.getBusinessName())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .status(VendorStatus.PENDING)
                .build();

        Vendor savedVendor = vendorRepository.save(vendor);

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            addCategoryMappings(savedVendor, request.getCategoryIds());
        }

        log.info("Vendor created successfully with ID: {}", savedVendor.getVendorId());
        return mapToResponse(savedVendor);
    }

    /**
     * Retrieves a vendor by ID.
     *
     * @param id vendor ID
     * @return vendor response
     */
    @Override
    public VendorResponse getVendorById(Integer id) {
        Vendor vendor = findVendorById(id);
        return mapToResponse(vendor);
    }

    /**
     * Retrieves a vendor along with associated categories.
     *
     * @param id vendor ID
     * @return vendor response with categories
     */
    @Override
    public VendorResponse getVendorByIdWithCategories(Integer id) {
        Vendor vendor = vendorRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + id));
        return mapToResponseWithCategories(vendor);
    }

    /**
     * Retrieves all vendors with pagination.
     */
    @Override
    public Page<VendorResponse> getAllVendors(Pageable pageable) {
        return vendorRepository.findAll(pageable).map(this::mapToResponse);
    }

    /**
     * Retrieves vendors filtered by status.
     */
    @Override
    public Page<VendorResponse> getVendorsByStatus(VendorStatus status, Pageable pageable) {
        return vendorRepository.findByStatus(status, pageable).map(this::mapToResponse);
    }

    /**
     * Searches vendors by business name or owner name.
     */
    @Override
    public Page<VendorResponse> searchVendors(String search, Pageable pageable) {
        return vendorRepository.searchVendors(search, pageable).map(this::mapToResponse);
    }

    /**
     * Updates an existing vendor.
     *
     * @param id      vendor ID
     * @param request update request
     * @return updated vendor response
     */
    @Override
    @Transactional
    public VendorResponse updateVendor(Integer id, VendorUpdateRequest request) {
        log.info("Updating vendor with ID: {}", id);

        Vendor vendor = findVendorById(id);

        if (request.getBusinessName() != null) {
            vendor.setBusinessName(request.getBusinessName());
        }
        if (request.getOwnerName() != null) {
            vendor.setOwnerName(request.getOwnerName());
        }
        if (request.getPhone() != null) {
            vendor.setPhone(request.getPhone());
        }
        if (request.getStatus() != null) {
            vendor.setStatus(request.getStatus());
        }

        if (request.getCategoryIds() != null) {
            updateCategoryMappings(vendor, request.getCategoryIds());
        }

        Vendor updatedVendor = vendorRepository.save(vendor);

        log.info("Vendor updated successfully with ID: {}", id);
        return mapToResponseWithCategories(updatedVendor);
    }

    /**
     * Deletes a vendor by ID.
     *
     * @param id vendor ID
     */
    @Override
    @Transactional
    public void deleteVendor(Integer id) {
        log.info("Deleting vendor with ID: {}", id);

        if (!vendorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + id);
        }

        vendorRepository.deleteById(id);
        log.info("Vendor deleted successfully with ID: {}", id);
    }

    /**
     * Finds vendor by ID or throws exception.
     */
    private Vendor findVendorById(Integer id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + id));
    }

    /**
     * Adds category mappings for a vendor.
     */
    private void addCategoryMappings(Vendor vendor, List<Integer> categoryIds) {
        for (Integer categoryId : categoryIds) {
            VendorCategory category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

            VendorCategoryMapping mapping = VendorCategoryMapping.builder()
                    .vendor(vendor)
                    .category(category)
                    .build();

            mappingRepository.save(mapping);
        }
    }

    /**
     * Updates category mappings for a vendor.
     */

    private void updateCategoryMappings(Vendor vendor, List<Integer> categoryIds) {
        mappingRepository.deleteByVendorId(vendor.getVendorId());
        vendor.getCategoryMappings().clear();

        if (!categoryIds.isEmpty()) {
            addCategoryMappings(vendor, categoryIds);
        }
    }

    /**
     * Maps Vendor entity to response DTO.
     */
    private VendorResponse mapToResponse(Vendor vendor) {
        return VendorResponse.builder()
                .vendorId(vendor.getVendorId())
                .businessName(vendor.getBusinessName())
                .ownerName(vendor.getOwnerName())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .status(vendor.getStatus())
                .createdAt(vendor.getCreatedAt())
                .build();
    }

    /**
     * Maps Vendor entity to response DTO including categories.
     */
    private VendorResponse mapToResponseWithCategories(Vendor vendor) {
        List<VendorCategoryResponse> categories =
                vendor.getCategoryMappings() != null
                        ? vendor.getCategoryMappings().stream()
                          .map(mapping -> VendorCategoryResponse.builder()
                                          .categoryId(mapping.getCategory().getCategoryId())
                                          .categoryName(mapping.getCategory().getCategoryName())
                                          .build())
                          .collect(Collectors.toList())
                        : Collections.emptyList();

        return VendorResponse.builder()
                .vendorId(vendor.getVendorId())
                .businessName(vendor.getBusinessName())
                .ownerName(vendor.getOwnerName())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .status(vendor.getStatus())
                .createdAt(vendor.getCreatedAt())
                .categories(categories)
                .build();
    }
}