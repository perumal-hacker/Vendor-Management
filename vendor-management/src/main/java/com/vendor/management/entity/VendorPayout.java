package com.vendor.management.entity;

import com.vendor.management.enums.PayoutStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a vendor payout.
 *
 * Maps to the "vendor_payouts" table and stores payout-related
 * information such as amount, status, and payout date.
 */
@Entity
@Table(name = "vendor_payouts", indexes = {
        @Index(name = "idx_payout_vendor", columnList = "vendor_id"),
        @Index(name = "idx_payout_date", columnList = "payout_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorPayout {

    /**
     * Unique identifier of the payout.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payout_id")
    private Integer payoutId;

    /**
     * Associated vendor for the payout.
     *
     * Represents a many-to-one relationship with Vendor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * Amount paid to the vendor.
     *
     * Stored with precision up to 10 digits and 2 decimal places.
     */
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * Timestamp when the payout was created.
     *
     * Automatically populated by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "payout_date")
    private LocalDateTime payoutDate;

    /**
     * Status of the payout.
     *
     * Default value is {@link PayoutStatus#PENDING}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private PayoutStatus status = PayoutStatus.PENDING;
}