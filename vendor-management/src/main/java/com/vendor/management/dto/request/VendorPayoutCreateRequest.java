package com.vendor.management.dto.request;

import com.vendor.management.enums.PayoutStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Request DTO for creating a vendor payout.
 *
 * Contains payout details along with validation constraints
 * to ensure valid input data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorPayoutCreateRequest {

    /**
     * Unique identifier of the vendor receiving the payout.
     *
     * Must not be null.
     */
    @NotNull(message = "Vendor ID is required")
    private Integer vendorId;

    /**
     * Payout amount.
     *
     * Must be greater than 0 and follow a valid monetary format
     * with up to 8 digits in the integer part and 2 decimal places.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    /**
     * Status of the payout.
     *
     * Accepts only valid {@link PayoutStatus} enum values.
     * This field is optional and defaults to {@link PayoutStatus#PENDING} if not provided.
     */
    private PayoutStatus status;
}