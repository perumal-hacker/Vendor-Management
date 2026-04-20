package com.vendor.management.controller;

import com.vendor.management.dto.request.VendorCategoryRequest;
import com.vendor.management.dto.response.ApiResponse;
import com.vendor.management.dto.response.VendorCategoryResponse;
import com.vendor.management.service.VendorCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing vendor categories.
 *
 * Provides endpoints to create, retrieve, update, and delete vendor categories.
 * These APIs are exposed under "/api/v1/categories".
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Vendor Category", description = "Vendor category management APIs")
public class VendorCategoryController {

    private final VendorCategoryService categoryService;

    /**
     * Creates a new vendor category.
     *
     * @param request request object containing category details
     * @return response containing created category data
     */
    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<VendorCategoryResponse>> createCategory(
            @RequestBody @Valid VendorCategoryRequest request) {

        VendorCategoryResponse response =
                categoryService.createCategory(request.getCategoryName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully", response));
    }

    /**
     * Retrieves a vendor category by its ID.
     *
     * @param id unique identifier of the category
     * @return response containing category details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<VendorCategoryResponse>> getCategoryById(
            @PathVariable Integer id) {

        VendorCategoryResponse response = categoryService.getCategoryById(id);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Retrieves all vendor categories.
     *
     * @return response containing list of all categories
     */
    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<VendorCategoryResponse>>> getAllCategories() {

        List<VendorCategoryResponse> response = categoryService.getAllCategories();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Updates an existing vendor category.
     *
     * @param id      unique identifier of the category
     * @param request request object containing updated category details
     * @return response containing updated category data
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<ApiResponse<VendorCategoryResponse>> updateCategory(
            @PathVariable Integer id,
            @RequestBody @Valid VendorCategoryRequest request) {

        VendorCategoryResponse response =
                categoryService.updateCategory(id, request.getCategoryName());

        return ResponseEntity.ok(
                ApiResponse.success("Category updated successfully", response)
        );
    }

    /**
     * Deletes a vendor category by its ID.
     *
     * @param id unique identifier of the category
     * @return response indicating successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Integer id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success("Category deleted successfully", null)
        );
    }
}