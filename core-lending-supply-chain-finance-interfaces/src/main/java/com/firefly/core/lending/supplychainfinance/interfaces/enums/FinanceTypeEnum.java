package com.firefly.core.lending.supplychainfinance.interfaces.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Finance type discriminator for supply chain finance products.
 * This enum determines whether a product is FACTORING or CONFIRMING.
 */
@Getter
@RequiredArgsConstructor
public enum FinanceTypeEnum {
    
    FACTORING("FACTORING", "Factoring"),
    CONFIRMING("CONFIRMING", "Confirming");

    @JsonValue
    private final String code;
    private final String description;

    @JsonCreator
    public static FinanceTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (FinanceTypeEnum type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid finance type: " + code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
