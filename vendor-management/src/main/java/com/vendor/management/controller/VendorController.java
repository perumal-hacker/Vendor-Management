package com.vendor.management.controller;

import com.vendor.management.dto.request.VendorCreateRequest;
import com.vendor.management.dto.request.VendorUpdateRequest;
import com.vendor.management.dto.response.ApiResponse;
import com.vendor.management.dto.response.VendorResponse;
import com.vendor.management.enums.VendorStatus;
import com.vendor.management.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing vendors.
 *
 * Provides endpoints to create, retrieve, update, delete, and search vendors.
 * Supports pagination and filtering based on vendor status.
 */
@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
@Tag(name = "Vendor", description = "Vendor management APIs")
public class VendorController {

    private final VendorService vendorService;

    /**
     * Creates a new vendor.
     *
     * @param request request object containing vendor details
     * @return response containing created vendor information
     */
    @PostMapping
    @Operation(summary = "Create a new vendor")
    public ResponseEntity<ApiResponse<VendorResponse>> createVendor(
            @Valid @RequestBody VendorCreateRequest request) {

        VendorResponse response = vendorService.createVendor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Vendor created successfully", response));
    }

    /**
     * Retrieves a vendor by its ID along with associated categories.
     *
     * @param id unique identifier of the vendor
     * @return response containing vendor details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vendor by ID")
    public ResponseEntity<ApiResponse<VendorResponse>> getVendorById(@PathVariable Integer id) {

        VendorResponse response = vendorService.getVendorByIdWithCategories(id);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves all vendors with pagination and sorting.
     *
     * @param pageable pagination and sorting configuration
     * @return paginated list of vendors
     */
    @GetMapping
    @Operation(summary = "Get all vendors with pagination")
    public ResponseEntity<ApiResponse<Page<VendorResponse>>> getAllVendors(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<VendorResponse> response = vendorService.getAllVendors(pageable);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves vendors filtered by status.
     *
     * @param status   vendor status (e.g., ACTIVE, INACTIVE)
     * @param pageable pagination configuration
     * @return paginated list of vendors matching the status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get vendors by status")
    public ResponseEntity<ApiResponse<Page<VendorResponse>>> getVendorsByStatus(
            @PathVariable VendorStatus status,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<VendorResponse> response = vendorService.getVendorsByStatus(status, pageable);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Searches vendors by business name or owner name.
     *
     * @param query    search keyword
     * @param pageable pagination configuration
     * @return paginated list of matching vendors
     */
    @GetMapping("/search")
    @Operation(summary = "Search vendors by business name or owner name")
    public ResponseEntity<ApiResponse<Page<VendorResponse>>> searchVendors(
            @RequestParam String query,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<VendorResponse> response = vendorService.searchVendors(query, pageable);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Updates an existing vendor.
     *
     * @param id      unique identifier of the vendor
     * @param request request object containing updated vendor details
     * @return response containing updated vendor information
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update vendor")
    public ResponseEntity<ApiResponse<VendorResponse>> updateVendor(
            @PathVariable Integer id,
            @Valid @RequestBody VendorUpdateRequest request) {

        VendorResponse response = vendorService.updateVendor(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Vendor updated successfully", response)
        );
    }

    /**
     * Deletes a vendor by its ID.
     *
     * @param id unique identifier of the vendor
     * @return response indicating successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vendor")
    public ResponseEntity<ApiResponse<Void>> deleteVendor(@PathVariable Integer id) {

        vendorService.deleteVendor(id);

        return ResponseEntity.ok(
                ApiResponse.success("Vendor deleted successfully", null)
        );
    }
}