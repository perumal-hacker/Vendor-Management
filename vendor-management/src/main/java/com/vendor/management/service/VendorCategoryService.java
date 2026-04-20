package com.vendor.management.service;

import com.vendor.management.dto.response.VendorCategoryResponse;

import java.util.List;

/**
 * Service interface for managing vendor categories.
 *
 * Defines operations for creating, retrieving, updating,
 * and deleting vendor categories.
 */
public interface VendorCategoryService {

    /**
     * Creates a new vendor category.
     *
     * @param categoryName name of the category
     * @return created category response
     */
    VendorCategoryResponse createCategory(String categoryName);

    /**
     * Retrieves a category by its ID.
     *
     * @param id category ID
     * @return category response
     */
    VendorCategoryResponse getCategoryById(Integer id);

    /**
     * Retrieves all vendor categories.
     *
     * @return list of category responses
     */
    List<VendorCategoryResponse> getAllCategories();

    /**
     * Updates an existing category.
     *
     * @param id           category ID
     * @param categoryName updated category name
     * @return updated category response
     */
    VendorCategoryResponse updateCategory(Integer id, String categoryName);

    /**
     * Deletes a category by its ID.
     *
     * @param id category ID
     */
    void deleteCategory(Integer id);
}