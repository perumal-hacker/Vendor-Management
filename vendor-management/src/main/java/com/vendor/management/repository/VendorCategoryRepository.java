package com.vendor.management.repository;

import com.vendor.management.entity.VendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing VendorCategory entities.
 *
 * Provides database operations for vendor categories,
 * including lookup and existence checks by category name.
 */
@Repository
public interface VendorCategoryRepository extends JpaRepository<VendorCategory, Integer> {

    /**
     * Finds a vendor category by its name.
     *
     * @param categoryName name of the category
     * @return Optional containing the category if found, otherwise empty
     */
    Optional<VendorCategory> findByCategoryName(String categoryName);

    /**
     * Checks whether a category exists with the given name.
     *
     * @param categoryName name of the category
     * @return true if category exists, otherwise false
     */
    boolean existsByCategoryName(String categoryName);
}