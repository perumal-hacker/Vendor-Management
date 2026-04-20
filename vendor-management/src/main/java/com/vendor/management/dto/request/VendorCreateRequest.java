package com.vendor.management.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * Request DTO for creating a new vendor.
 *
 * Contains vendor details and validation rules to ensure
 * correct and complete input data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCreateRequest {

    /**
     * Name of the business.
     *
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Business name is required")
    @Size(max = 255, message = "Business name must not exceed 255 characters")
    private String businessName;

    /**
     * Name of the business owner.
     *
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Owner name is required")
    @Size(max = 255, message = "Owner name must not exceed 255 characters")
    private String ownerName;

    /**
     * Email address of the vendor.
     *
     * Must not be blank and should follow a valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Contact phone number of the vendor.
     *
     * Must match a valid phone number pattern (10–15 digits, optional '+' prefix).
     * This field is optional.
     */
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    /**
     * Password for vendor account.
     *
     * Must not be blank and should contain at least 8 characters.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    /**
     * List of category IDs associated with the vendor.
     *
     * Can be null or empty if no categories are assigned.
     */
    private List<Integer> categoryIds;
}