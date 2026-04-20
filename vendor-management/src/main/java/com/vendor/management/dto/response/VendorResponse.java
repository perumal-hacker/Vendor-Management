package com.vendor.management.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vendor.management.enums.VendorStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO representing a vendor.
 *
 * Contains vendor details along with associated categories.
 * This is returned to clients when fetching vendor information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorResponse {

    /**
     * Unique identifier of the vendor.
     */
    private Integer vendorId;

    /**
     * Name of the business.
     */
    private String businessName;

    /**
     * Name of the business owner.
     */
    private String ownerName;

    /**
     * Email address of the vendor.
     */
    private String email;

    /**
     * Contact phone number of the vendor.
     */
    private String phone;

    /**
     * Current status of the vendor.
     *
     * Example values: ACTIVE, INACTIVE, PENDING, SUSPENDED.
     */
    private VendorStatus status;

    /**
     * Timestamp when the vendor was created.
     */
    private LocalDateTime createdAt;

    /**
     * List of categories associated with the vendor.
     */
    private List<VendorCategoryResponse> categories;
}