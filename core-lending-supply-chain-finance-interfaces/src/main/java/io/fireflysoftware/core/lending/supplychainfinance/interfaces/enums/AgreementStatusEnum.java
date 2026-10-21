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
