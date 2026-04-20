package com.vendor.management.entity;

import com.vendor.management.enums.VendorStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a vendor.
 *
 * Maps to the "vendors" table in the database and stores
 * vendor-related information such as business details,
 * contact information, status, and relationships with
 * categories and payouts.
 */
@Entity
@Table(name = "vendors", indexes = {
        @Index(name = "idx_vendor_email", columnList = "email"),
        @Index(name = "idx_vendor_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    /**
     * Unique identifier of the vendor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Integer vendorId;

    /**
     * Name of the vendor's business.
     */
    @Column(name = "business_name", nullable = false, length = 255)
    private String businessName;

    /**
     * Name of the business owner.
     */
    @Column(name = "owner_name", nullable = false, length = 255)
    private String ownerName;

    /**
     * Email address of the vendor.
     *
     * Must be unique across all vendors.
     */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Contact phone number of the vendor.
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Hashed password of the vendor.
     *
     * Stored securely using a password encoder (e.g., BCrypt).
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Current status of the vendor.
     *
     * Default value is {@link VendorStatus#PENDING}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    @Builder.Default
    private VendorStatus status = VendorStatus.PENDING;
    /**
     * Timestamp when the vendor was created.
     *
     * Automatically populated by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * List of category mappings associated with the vendor.
     *
     * Represents a one-to-many relationship with VendorCategoryMapping.
     */
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VendorCategoryMapping> categoryMappings = new ArrayList<>();

    /**
     * List of payouts associated with the vendor.
     *
     * Represents a one-to-many relationship with VendorPayout.
     */
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VendorPayout> payouts = new ArrayList<>();

    /**
     * Adds a category mapping to the vendor and maintains bidirectional consistency.
     *
     * @param mapping category mapping to add
     */
    public void addCategoryMapping(VendorCategoryMapping mapping) {
        categoryMappings.add(mapping);
        mapping.setVendor(this);
    }

    /**
     * Removes a category mapping from the vendor and updates the relationship.
     *
     * @param mapping category mapping to remove
     */
    public void removeCategoryMapping(VendorCategoryMapping mapping) {
        categoryMappings.remove(mapping);
        mapping.setVendor(null);
    }

    /**
     * Adds a payout to the vendor and maintains bidirectional consistency.
     *
     * @param payout payout to add
     */
    public void addPayout(VendorPayout payout) {
        payouts.add(payout);
        payout.setVendor(this);
    }
}