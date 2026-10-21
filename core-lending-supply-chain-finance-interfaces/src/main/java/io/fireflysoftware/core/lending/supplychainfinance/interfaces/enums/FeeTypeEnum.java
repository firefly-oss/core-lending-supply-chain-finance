/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Types of fees applicable to supply chain finance products.
 */
@Getter
@RequiredArgsConstructor
public enum FeeTypeEnum {
    
    DISCOUNT_FEE("DISCOUNT_FEE", "Discount Fee"),
    SERVICE_FEE("SERVICE_FEE", "Service Fee"),
    ADMINISTRATION_FEE("ADMINISTRATION_FEE", "Administration Fee"),
    VERIFICATION_FEE("VERIFICATION_FEE", "Verification Fee"),
    EARLY_PAYMENT_FEE("EARLY_PAYMENT_FEE", "Early Payment Fee"),
    COLLECTION_FEE("COLLECTION_FEE", "Collection Fee"),
    LATE_PAYMENT_FEE("LATE_PAYMENT_FEE", "Late Payment Fee"),
    OTHER("OTHER", "Other");

    @JsonValue
    private final String code;
    private final String description;

    @JsonCreator
    public static FeeTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (FeeTypeEnum type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid fee type: " + code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
