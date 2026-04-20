package com.vendor.management.repository;

import com.vendor.management.entity.Vendor;
import com.vendor.management.enums.VendorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Vendor entities.
 *
 * Provides database operations for vendors including lookup,
 * filtering, search, and fetching related category data.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    /**
     * Finds a vendor by email.
     *
     * @param email vendor email address
     * @return Optional containing the vendor if found
     */
    Optional<Vendor> findByEmail(String email);

    /**
     * Checks if a vendor exists with the given email.
     *
     * @param email vendor email address
     * @return true if exists, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves vendors filtered by status with pagination.
     *
     * @param status   vendor status (e.g., ACTIVE, INACTIVE)
     * @param pageable pagination configuration
     * @return paginated list of vendors
     */
    Page<Vendor> findByStatus(VendorStatus status, Pageable pageable);

    /**
     * Retrieves a vendor along with its associated categories.
     *
     * Uses JOIN FETCH to eagerly load category mappings and avoid
     * lazy loading issues (N+1 problem).
     *
     * @param id unique identifier of the vendor
     * @return Optional containing vendor with categories if found
     */
    @Query("SELECT v FROM Vendor v LEFT JOIN FETCH v.categoryMappings cm " +
            "LEFT JOIN FETCH cm.category WHERE v.vendorId = :id")
    Optional<Vendor> findByIdWithCategories(@Param("id") Integer id);

    /**
     * Searches vendors by business name or owner name.
     *
     * Performs a case-insensitive search using LIKE operator.
     *
     * @param search   search keyword
     * @param pageable pagination configuration
     * @return paginated list of matching vendors
     */
    @Query("SELECT v FROM Vendor v WHERE LOWER(v.businessName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.ownerName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Vendor> searchVendors(@Param("search") String search, Pageable pageable);
}