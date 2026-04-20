package com.vendor.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a vendor category.
 *
 * Maps to the "vendor_categories" table and stores category details.
 * A category can be associated with multiple vendors through
 * VendorCategoryMapping.
 */
@Entity
@Table(name = "vendor_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorCategory {

    /**
     * Unique identifier of the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * Name of the category.
     *
     * Must be unique and not null.
     */
    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    /**
     * List of vendor-category mappings.
     *
     * Represents a one-to-many relationship with VendorCategoryMapping,
     * linking vendors to this category.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<VendorCategoryMapping> vendorMappings = new ArrayList<>();
}