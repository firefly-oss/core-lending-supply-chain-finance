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

/**
 * Product kind stored on a trade finance agreement.
 * FACTORING buys receivables. REVERSE_FACTORING pays suppliers after the buyer approves the payable.
 * Confirming is the Spanish market name for reverse factoring and is accepted on input as that kind.
 * JSON uses the enum name.
 */
public enum FinanceTypeEnum {

    FACTORING,
    REVERSE_FACTORING;

    @JsonCreator
    public static FinanceTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        if ("CONFIRMING".equalsIgnoreCase(code)) {
            return REVERSE_FACTORING;
        }
        for (FinanceTypeEnum type : values()) {
            if (type.name().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid finance type: " + code);
    }
}
