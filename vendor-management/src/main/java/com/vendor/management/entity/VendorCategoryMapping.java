package com.vendor.management.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the mapping between vendors and categories.
 *
 * This acts as a join table for the many-to-many relationship
 * between Vendor and VendorCategory. Each record links one vendor
 * to one category.
 */
@Entity
@Table(name = "vendor_category_mappings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"vendor_id", "category_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCategoryMapping {

    /**
     * Unique identifier of the mapping.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Associated vendor.
     *
     * Represents a many-to-one relationship with Vendor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * Associated category.
     *
     * Represents a many-to-one relationship with VendorCategory.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private VendorCategory category;
}