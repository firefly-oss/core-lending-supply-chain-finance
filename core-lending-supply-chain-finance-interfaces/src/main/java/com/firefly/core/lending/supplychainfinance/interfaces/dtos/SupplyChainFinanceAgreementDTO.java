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
import com.firefly.core.lending.supplychainfinance.interfaces.enums.AgreementStatusEnum;
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
 * Unified DTO for supply chain finance agreements.
 * Supports both FACTORING and CONFIRMING types via financeType discriminator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyChainFinanceAgreementDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotBlank(message = "Agreement number is required")
    @Size(max = 100, message = "Agreement number cannot exceed 100 characters")
    private String agreementNumber;

    @NotNull(message = "Loan servicing case ID is required")
    @FilterableId
    private UUID loanServicingCaseId; // FK to LoanServicingCase in loan-servicing microservice

    @NotNull(message = "Status is required")
    private AgreementStatusEnum status;

    @NotNull(message = "Start date is required")
    @ValidDate(message = "Start date must be a valid date")
    private LocalDate startDate;

    @ValidDate(message = "End date must be a valid date")
    private LocalDate endDate;

    @NotNull(message = "Currency is required")
    private CurrencyCodeEnum currency;

    @PositiveOrZero(message = "Credit limit must be zero or positive")
    @ValidAmount(message = "Credit limit must be a valid amount")
    private BigDecimal creditLimit;

    @PositiveOrZero(message = "Available limit must be zero or positive")
    @ValidAmount(message = "Available limit must be a valid amount")
    private BigDecimal availableLimit;

    @PositiveOrZero(message = "Outstanding amount must be zero or positive")
    @ValidAmount(message = "Outstanding amount must be a valid amount")
    private BigDecimal outstandingAmount;

    // Factoring-specific fields (nullable)
    private Boolean recourse;
    private Boolean notificationRequired;

    @DecimalMin(value = "0.0", inclusive = true, message = "Advance rate must be zero or positive")
    @DecimalMax(value = "100.0", inclusive = true, message = "Advance rate cannot exceed 100")
    private BigDecimal advanceRate;

    // Confirming-specific fields (nullable)
    private Boolean supplierEarlyPaymentOption;

    @Positive(message = "Standard payment term days must be positive")
    private Integer standardPaymentTermDays;

    @Positive(message = "Early payment discount days must be positive")
    private Integer earlyPaymentDiscountDays;

    // Common fields
    @DecimalMin(value = "0.0", inclusive = true, message = "Interest rate must be zero or positive")
    @DecimalMax(value = "100.0", inclusive = true, message = "Interest rate cannot exceed 100")
    private BigDecimal interestRate;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;

    @FilterableId
    private UUID createdBy;

    @FilterableId
    private UUID lastModifiedBy;
}
