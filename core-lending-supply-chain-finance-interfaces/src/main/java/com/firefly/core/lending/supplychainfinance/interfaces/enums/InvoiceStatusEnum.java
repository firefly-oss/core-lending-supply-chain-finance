package com.firefly.core.lending.supplychainfinance.interfaces.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Status for supply chain finance invoices.
 * Supports both factoring and confirming workflows.
 */
@Getter
@RequiredArgsConstructor
public enum InvoiceStatusEnum {
    
    REGISTERED("REGISTERED", "Registered"),
    PENDING_VERIFICATION("PENDING_VERIFICATION", "Pending Verification"),
    VERIFIED("VERIFIED", "Verified"),
    APPROVED("APPROVED", "Approved"),
    REJECTED("REJECTED", "Rejected"),
    FINANCED("FINANCED", "Financed"),
    CONFIRMED("CONFIRMED", "Confirmed"),
    PAID("PAID", "Paid"),
    SETTLED("SETTLED", "Settled"),
    CANCELLED("CANCELLED", "Cancelled");

    @JsonValue
    private final String code;
    private final String description;

    @JsonCreator
    public static InvoiceStatusEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (InvoiceStatusEnum status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid invoice status: " + code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
