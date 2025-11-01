package com.firefly.core.lending.supplychainfinance.interfaces.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Status for supply chain finance agreements (both factoring and confirming).
 */
@Getter
@RequiredArgsConstructor
public enum AgreementStatusEnum {
    
    DRAFT("DRAFT", "Draft"),
    PENDING_APPROVAL("PENDING_APPROVAL", "Pending Approval"),
    ACTIVE("ACTIVE", "Active"),
    SUSPENDED("SUSPENDED", "Suspended"),
    TERMINATED("TERMINATED", "Terminated"),
    CLOSED("CLOSED", "Closed");

    @JsonValue
    private final String code;
    private final String description;

    @JsonCreator
    public static AgreementStatusEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (AgreementStatusEnum status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid agreement status: " + code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
