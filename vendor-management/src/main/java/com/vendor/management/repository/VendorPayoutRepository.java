package com.vendor.management.repository;

import com.vendor.management.entity.VendorPayout;
import com.vendor.management.enums.PayoutStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing VendorPayout entities.
 *
 * Provides database operations for vendor payouts including
 * pagination, filtering, aggregation, and date-based queries.
 */
@Repository
public interface VendorPayoutRepository extends JpaRepository<VendorPayout, Integer> {

    /**
     * Retrieves payouts for a specific vendor with pagination.
     *
     * @param vendorId unique identifier of the vendor
     * @param pageable pagination configuration
     * @return paginated list of payouts
     */
    Page<VendorPayout> findByVendorVendorId(Integer vendorId, Pageable pageable);

    /**
     * Retrieves payouts for a vendor filtered by status.
     *
     * @param vendorId unique identifier of the vendor
     * @param status   payout status (e.g., PENDING, COMPLETED, FAILED)
     * @return list of payouts matching the criteria
     */
    List<VendorPayout> findByVendorVendorIdAndStatus(Integer vendorId, PayoutStatus status);

    /**
     * Calculates the total paid amount for a vendor.
     *
     * Only includes payouts with status = true (completed).
     *
     * @param vendorId unique identifier of the vendor
     * @return total payout amount, or null if no payouts exist
     */
    @Query("SELECT SUM(vp.amount) FROM VendorPayout vp WHERE vp.vendor.vendorId = :vendorId AND vp.status = com.vendor.management.enums.PayoutStatus.COMPLETED")
    BigDecimal getTotalPaidAmount(@Param("vendorId") Integer vendorId);

    /**
     * Retrieves payouts within a specified date range.
     *
     * @param startDate start date (inclusive)
     * @param endDate   end date (inclusive)
     * @return list of payouts within the given date range
     */
    @Query("SELECT vp FROM VendorPayout vp WHERE vp.payoutDate BETWEEN :startDate AND :endDate")
    List<VendorPayout> findPayoutsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}