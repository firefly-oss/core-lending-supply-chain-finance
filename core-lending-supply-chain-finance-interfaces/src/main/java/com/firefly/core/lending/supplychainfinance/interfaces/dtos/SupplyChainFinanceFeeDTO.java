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
import com.firefly.core.lending.supplychainfinance.interfaces.enums.CurrencyCodeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FeeTypeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for fees in supply chain finance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyChainFinanceFeeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotNull(message = "Agreement ID is required")
    @FilterableId
    private UUID agreementId;

    @NotNull(message = "Fee type is required")
    private FeeTypeEnum feeType;

    @NotNull(message = "Currency is required")
    private CurrencyCodeEnum currency;

    @PositiveOrZero(message = "Fixed amount must be zero or positive")
    @ValidAmount(message = "Fixed amount must be a valid amount")
    private BigDecimal fixedAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Percentage rate must be zero or positive")
    @DecimalMax(value = "100.0", inclusive = true, message = "Percentage rate cannot exceed 100")
    private BigDecimal percentageRate;

    @PositiveOrZero(message = "Minimum amount must be zero or positive")
    @ValidAmount(message = "Minimum amount must be a valid amount")
    private BigDecimal minimumAmount;

    @PositiveOrZero(message = "Maximum amount must be zero or positive")
    @ValidAmount(message = "Maximum amount must be a valid amount")
    private BigDecimal maximumAmount;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
