package com.vendor.management.controller;

import com.vendor.management.dto.request.VendorPayoutCreateRequest;
import com.vendor.management.dto.response.ApiResponse;
import com.vendor.management.dto.response.VendorPayoutResponse;
import com.vendor.management.enums.PayoutStatus;
import com.vendor.management.service.VendorPayoutService;
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

import java.math.BigDecimal;

/**
 * REST controller for managing vendor payouts.
 *
 * Provides endpoints to create, retrieve, update, and delete payouts.
 * Also supports pagination and vendor-specific payout queries.
 */
@RestController
@RequestMapping("/api/v1/payouts")
@RequiredArgsConstructor
@Tag(name = "Vendor Payout", description = "Vendor payout management APIs")
public class VendorPayoutController {

    private final VendorPayoutService payoutService;

    /**
     * Creates a new payout for a vendor.
     *
     * @param request request object containing payout details
     * @return response containing created payout information
     */
    @PostMapping
    @Operation(summary = "Create a new payout")
    public ResponseEntity<ApiResponse<VendorPayoutResponse>> createPayout(
            @Valid @RequestBody VendorPayoutCreateRequest request) {

        VendorPayoutResponse response = payoutService.createPayout(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payout created successfully", response));
    }

    /**
     * Retrieves a payout by its ID.
     *
     * @param id unique identifier of the payout
     * @return response containing payout details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get payout by ID")
    public ResponseEntity<ApiResponse<VendorPayoutResponse>> getPayoutById(
            @PathVariable Integer id) {

        VendorPayoutResponse response = payoutService.getPayoutById(id);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves all payouts with pagination and sorting.
     *
     * @param pageable pagination and sorting configuration
     * @return paginated list of payouts
     */
    @GetMapping
    @Operation(summary = "Get all payouts with pagination")
    public ResponseEntity<ApiResponse<Page<VendorPayoutResponse>>> getAllPayouts(
            @PageableDefault(size = 10, sort = "payoutDate", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<VendorPayoutResponse> response = payoutService.getAllPayouts(pageable);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves payouts for a specific vendor.
     *
     * @param vendorId unique identifier of the vendor
     * @param pageable pagination configuration
     * @return paginated list of payouts for the vendor
     */
    @GetMapping("/vendor/{vendorId}")
    @Operation(summary = "Get payouts by vendor ID")
    public ResponseEntity<ApiResponse<Page<VendorPayoutResponse>>> getPayoutsByVendorId(
            @PathVariable Integer vendorId,
            @PageableDefault(size = 10, sort = "payoutDate", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<VendorPayoutResponse> response =
                payoutService.getPayoutsByVendorId(vendorId, pageable);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves the total paid amount for a specific vendor.
     *
     * @param vendorId unique identifier of the vendor
     * @return total payout amount as BigDecimal
     */
    @GetMapping("/vendor/{vendorId}/total")
    @Operation(summary = "Get total paid amount for a vendor")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalPaidAmount(
            @PathVariable Integer vendorId) {

        BigDecimal total = payoutService.getTotalPaidAmountByVendor(vendorId);

        return ResponseEntity.ok(ApiResponse.success(total));
    }

    /**
     * Updates the status of a payout.
     *
     * @param id     unique identifier of the payout
     * @param status new status value (true/false)
     * @return response containing updated payout information
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update payout status")
    public ResponseEntity<ApiResponse<VendorPayoutResponse>> updatePayoutStatus(
            @PathVariable Integer id,
            @RequestParam PayoutStatus status) {

        VendorPayoutResponse response =
                payoutService.updatePayoutStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Payout status updated successfully", response)
        );
    }

    /**
     * Deletes a payout by its ID.
     *
     * @param id unique identifier of the payout
     * @return response indicating successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payout")
    public ResponseEntity<ApiResponse<Void>> deletePayout(
            @PathVariable Integer id) {

        payoutService.deletePayout(id);

        return ResponseEntity.ok(
                ApiResponse.success("Payout deleted successfully", null)
        );
    }
}