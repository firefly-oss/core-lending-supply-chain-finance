package com.firefly.core.lending.supplychainfinance.interfaces.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ISO 4217 currency codes supported by supply chain finance.
 */
@Getter
@RequiredArgsConstructor
public enum CurrencyCodeEnum {
    
    EUR("EUR", "Euro"),
    USD("USD", "US Dollar"),
    GBP("GBP", "British Pound"),
    CHF("CHF", "Swiss Franc"),
    JPY("JPY", "Japanese Yen"),
    CNY("CNY", "Chinese Yuan");

    @JsonValue
    private final String code;
    private final String description;

    @JsonCreator
    public static CurrencyCodeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (CurrencyCodeEnum currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid currency code: " + code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
