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
import com.firefly.annotations.ValidAmount;
import com.firefly.annotations.ValidDate;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.CurrencyCodeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for final settlement of supply chain finance invoices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyChainFinanceSettlementDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotNull(message = "Invoice ID is required")
    @FilterableId
    private UUID invoiceId;

    @NotNull(message = "Settlement date is required")
    @ValidDate(message = "Settlement date must be a valid date")
    private LocalDate settlementDate;

    @NotNull(message = "Currency is required")
    private CurrencyCodeEnum currency;

    @NotNull(message = "Settlement amount is required")
    @Positive(message = "Settlement amount must be positive")
    @ValidAmount(message = "Settlement amount must be a valid amount")
    private BigDecimal settlementAmount;

    @PositiveOrZero(message = "Advanced amount must be zero or positive")
    @ValidAmount(message = "Advanced amount must be a valid amount")
    private BigDecimal advancedAmount;

    @PositiveOrZero(message = "Interest amount must be zero or positive")
    @ValidAmount(message = "Interest amount must be a valid amount")
    private BigDecimal interestAmount;

    @PositiveOrZero(message = "Fee amount must be zero or positive")
    @ValidAmount(message = "Fee amount must be a valid amount")
    private BigDecimal feeAmount;

    @ValidAmount(message = "Balance due must be a valid amount")
    private BigDecimal balanceDue;

    @Size(max = 100, message = "Payment reference cannot exceed 100 characters")
    private String paymentReference;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;
}
