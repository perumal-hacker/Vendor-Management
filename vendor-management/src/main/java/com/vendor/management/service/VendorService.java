package com.vendor.management.service;

import com.vendor.management.dto.request.VendorCreateRequest;
import com.vendor.management.dto.request.VendorUpdateRequest;
import com.vendor.management.dto.response.VendorResponse;
import com.vendor.management.enums.VendorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing vendors.
 *
 * Defines operations for creating, retrieving, updating,
 * deleting, and searching vendors, including category handling.
 */
public interface VendorService {

    /**
     * Creates a new vendor.
     *
     * @param request vendor creation request
     * @return created vendor response
     */
    VendorResponse createVendor(VendorCreateRequest request);

    /**
     * Retrieves a vendor by its ID.
     *
     * @param id vendor ID
     * @return vendor response
     */
    VendorResponse getVendorById(Integer id);

    /**
     * Retrieves a vendor along with associated categories.
     *
     * @param id vendor ID
     * @return vendor response including categories
     */
    VendorResponse getVendorByIdWithCategories(Integer id);

    /**
     * Retrieves all vendors with pagination.
     *
     * @param pageable pagination configuration
     * @return paginated list of vendors
     */
    Page<VendorResponse> getAllVendors(Pageable pageable);

    /**
     * Retrieves vendors filtered by status.
     *
     * @param status   vendor status (e.g., ACTIVE, INACTIVE)
     * @param pageable pagination configuration
     * @return paginated list of vendors
     */
    Page<VendorResponse> getVendorsByStatus(VendorStatus status, Pageable pageable);

    /**
     * Searches vendors by business name or owner name.
     *
     * @param search   search keyword
     * @param pageable pagination configuration
     * @return paginated list of matching vendors
     */
    Page<VendorResponse> searchVendors(String search, Pageable pageable);

    /**
     * Updates an existing vendor.
     *
     * @param id      vendor ID
     * @param request update request
     * @return updated vendor response
     */
    VendorResponse updateVendor(Integer id, VendorUpdateRequest request);

    /**
     * Deletes a vendor by its ID.
     *
     * @param id vendor ID
     */
    void deleteVendor(Integer id);
}