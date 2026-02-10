/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.lending.supplychainfinance.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fireflyframework.annotations.ValidAmount;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import org.fireflyframework.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Unified DTO for counterparties (debtors in factoring, suppliers in confirming).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CounterpartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Agreement ID is required")
    @FilterableId
    private UUID agreementId;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotBlank(message = "Counterparty name is required")
    @Size(max = 255, message = "Counterparty name cannot exceed 255 characters")
    private String counterpartyName;

    @Size(max = 50, message = "Tax ID cannot exceed 50 characters")
    private String taxId;

    @Size(max = 50, message = "Registration number cannot exceed 50 characters")
    private String registrationNumber;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Size(max = 255, message = "Contact person cannot exceed 255 characters")
    private String contactPerson;

    @Email(message = "Contact email must be a valid email address")
    @Size(max = 255, message = "Contact email cannot exceed 255 characters")
    private String contactEmail;

    @Size(max = 50, message = "Contact phone cannot exceed 50 characters")
    private String contactPhone;

    private Boolean approved;

    @PositiveOrZero(message = "Approved limit must be zero or positive")
    @ValidAmount(message = "Approved limit must be a valid amount")
    private BigDecimal approvedLimit;

    @PositiveOrZero(message = "Current exposure must be zero or positive")
    @ValidAmount(message = "Current exposure must be a valid amount")
    private BigDecimal currentExposure;

    @Size(max = 50, message = "Credit rating cannot exceed 50 characters")
    private String creditRating;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;
}
