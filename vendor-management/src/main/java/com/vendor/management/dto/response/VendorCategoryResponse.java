package com.vendor.management.dto.response;

import lombok.*;

/**
 * Response DTO representing a vendor category.
 *
 * Contains category details returned to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCategoryResponse {

    /**
     * Unique identifier of the category.
     */
    private Integer categoryId;

    /**
     * Name of the category.
     */
    private String categoryName;
}