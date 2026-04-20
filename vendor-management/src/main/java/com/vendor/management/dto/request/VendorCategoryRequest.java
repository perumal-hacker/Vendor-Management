package com.vendor.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Request DTO for creating or updating a vendor category.
 *
 * Contains validation rules for category input data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCategoryRequest {

    /**
     * Name of the vendor category.
     *
     * Must not be blank and should not exceed 100 characters.
     */
    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String categoryName;
}