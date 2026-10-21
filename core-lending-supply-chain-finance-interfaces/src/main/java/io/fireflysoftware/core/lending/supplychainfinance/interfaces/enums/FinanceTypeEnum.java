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
