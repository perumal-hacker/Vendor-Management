package com.vendor.management.dto.request;

import com.vendor.management.enums.VendorStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * Request DTO for updating an existing vendor.
 *
 * All fields are optional. Only the provided fields will be updated.
 * Validation constraints are applied where applicable.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorUpdateRequest {

    /**
     * Updated business name.
     *
     * Must not exceed 255 characters.
     */
    @Size(max = 255, message = "Business name must not exceed 255 characters")
    private String businessName;

    /**
     * Updated owner name.
     *
     * Must not exceed 255 characters.
     */
    @Size(max = 255, message = "Owner name must not exceed 255 characters")
    private String ownerName;

    /**
     * Updated phone number.
     *
     * Must match a valid phone number pattern (10–15 digits, optional '+' prefix).
     */
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    /**
     * Updated vendor status.
     *
     * Accepts only valid {@link VendorStatus} enum values.
     */
    private VendorStatus status;
    /**
     * Updated list of category IDs associated with the vendor.
     *
     * Can be null or empty if categories are not being modified.
     */
    private List<Integer> categoryIds;
}