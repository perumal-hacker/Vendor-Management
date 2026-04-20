package com.vendor.management.service.impl;

import com.vendor.management.dto.response.VendorCategoryResponse;
import com.vendor.management.entity.VendorCategory;
import com.vendor.management.exception.DuplicateResourceException;
import com.vendor.management.exception.ResourceNotFoundException;
import com.vendor.management.repository.VendorCategoryRepository;
import com.vendor.management.service.VendorCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing vendor categories.
 *
 * Provides business logic for creating, retrieving, updating,
 * and deleting vendor categories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VendorCategoryServiceImpl implements VendorCategoryService {

    private final VendorCategoryRepository categoryRepository;

    /**
     * Creates a new vendor category.
     *
     * @param categoryName name of the category
     * @return created category response
     * @throws DuplicateResourceException if category already exists
     */
    @Override
    @Transactional
    public VendorCategoryResponse createCategory(String categoryName) {
        log.info("Creating new category: {}", categoryName);

        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new DuplicateResourceException("Category with name '" + categoryName + "' already exists");
        }

        VendorCategory category = VendorCategory.builder()
                .categoryName(categoryName)
                .build();

        VendorCategory savedCategory = categoryRepository.save(category);
        log.info("Category created with ID: {}", savedCategory.getCategoryId());

        return mapToResponse(savedCategory);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id category ID
     * @return category response
     * @throws ResourceNotFoundException if category is not found
     */
    @Override
    public VendorCategoryResponse getCategoryById(Integer id) {
        VendorCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        return mapToResponse(category);
    }

    /**
     * Retrieves all vendor categories.
     *
     * @return list of category responses
     */
    @Override
    public List<VendorCategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing category.
     *
     * @param id           category ID
     * @param categoryName new category name
     * @return updated category response
     * @throws ResourceNotFoundException if category not found
     * @throws DuplicateResourceException if category name already exists
     */
    @Override
    @Transactional
    public VendorCategoryResponse updateCategory(Integer id, String categoryName) {
        log.info("Updating category ID: {} to name: {}", id, categoryName);

        VendorCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        if (categoryRepository.existsByCategoryName(categoryName) &&
                !category.getCategoryName().equals(categoryName)) {
            throw new DuplicateResourceException("Category with name '" + categoryName + "' already exists");
        }

        category.setCategoryName(categoryName);
        VendorCategory updatedCategory = categoryRepository.save(category);

        return mapToResponse(updatedCategory);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id category ID
     * @throws ResourceNotFoundException if category not found
     */
    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        log.info("Deleting category with ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }

        categoryRepository.deleteById(id);

        log.info("Category deleted with ID: {}", id);
    }

    /**
     * Maps VendorCategory entity to response DTO.
     *
     * @param category entity object
     * @return mapped response DTO
     */
    private VendorCategoryResponse mapToResponse(VendorCategory category) {
        return VendorCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}