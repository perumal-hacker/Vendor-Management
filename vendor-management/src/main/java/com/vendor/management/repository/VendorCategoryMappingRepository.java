package com.vendor.management.repository;

import com.vendor.management.entity.VendorCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing VendorCategoryMapping entities.
 *
 * Provides database operations for mapping vendors to categories,
 * including custom queries for deletion and existence checks.
 */
@Repository
public interface VendorCategoryMappingRepository extends JpaRepository<VendorCategoryMapping, Integer> {

    /**
     * Deletes all category mappings associated with a specific vendor.
     *
     * @param vendorId unique identifier of the vendor
     */
    @Modifying
    @Query("DELETE FROM VendorCategoryMapping vcm WHERE vcm.vendor.vendorId = :vendorId")
    void deleteByVendorId(@Param("vendorId") Integer vendorId);

    /**
     * Checks whether a mapping exists between a vendor and a category.
     *
     * @param vendorId   unique identifier of the vendor
     * @param categoryId unique identifier of the category
     * @return true if mapping exists, otherwise false
     */
    boolean existsByVendorVendorIdAndCategoryCategoryId(Integer vendorId, Integer categoryId);
}