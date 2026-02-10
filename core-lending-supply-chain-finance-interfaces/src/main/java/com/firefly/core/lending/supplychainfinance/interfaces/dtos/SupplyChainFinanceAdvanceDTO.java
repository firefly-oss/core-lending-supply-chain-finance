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
import org.fireflyframework.annotations.ValidDate;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.CurrencyCodeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import org.fireflyframework.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for advances/early payments in supply chain finance.
 * In factoring: advance to supplier.
 * In confirming: early payment to supplier.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyChainFinanceAdvanceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotNull(message = "Invoice ID is required")
    @FilterableId
    private UUID invoiceId;

    @NotNull(message = "Advance date is required")
    @ValidDate(message = "Advance date must be a valid date")
    private LocalDate advanceDate;

    @NotNull(message = "Currency is required")
    private CurrencyCodeEnum currency;

    @NotNull(message = "Advance amount is required")
    @Positive(message = "Advance amount must be positive")
    @ValidAmount(message = "Advance amount must be a valid amount")
    private BigDecimal advanceAmount;

    @PositiveOrZero(message = "Discount amount must be zero or positive")
    @ValidAmount(message = "Discount amount must be a valid amount")
    private BigDecimal discountAmount;

    @PositiveOrZero(message = "Fee amount must be zero or positive")
    @ValidAmount(message = "Fee amount must be a valid amount")
    private BigDecimal feeAmount;

    @PositiveOrZero(message = "Net amount must be zero or positive")
    @ValidAmount(message = "Net amount must be a valid amount")
    private BigDecimal netAmount;

    @Size(max = 100, message = "Payment reference cannot exceed 100 characters")
    private String paymentReference;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;
}
