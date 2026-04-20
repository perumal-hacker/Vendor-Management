package com.vendor.management.dto.response;

import com.vendor.management.enums.PayoutStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO representing a vendor payout.
 *
 * Contains payout details returned to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorPayoutResponse {

    /**
     * Unique identifier of the payout.
     */
    private Integer payoutId;

    /**
     * Unique identifier of the vendor.
     */
    private Integer vendorId;

    /**
     * Business name of the vendor.
     */
    private String vendorBusinessName;

    /**
     * Amount paid to the vendor.
     */
    private BigDecimal amount;

    /**
     * Date and time when the payout was processed.
     */
    private LocalDateTime payoutDate;

    /**
     * Status of the payout.
     *
     * Typically indicates whether the payout is completed or pending.
     */
    private PayoutStatus status;
}