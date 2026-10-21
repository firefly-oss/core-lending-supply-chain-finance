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


package io.fireflysoftware.core.lending.supplychainfinance.models.entities;

import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.FeeTypeEnum;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity for fee structures.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supply_chain_finance_fee")
public class SupplyChainFinanceFee {

    @Id
    @Column("id")
    private UUID id;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("agreement_id")
    private UUID agreementId;

    @Column("fee_type")
    private FeeTypeEnum feeType;

    @Column("currency")
    private String currency;

    @Column("fixed_amount")
    private BigDecimal fixedAmount;

    @Column("percentage_rate")
    private BigDecimal percentageRate;

    @Column("minimum_amount")
    private BigDecimal minimumAmount;

    @Column("maximum_amount")
    private BigDecimal maximumAmount;

    @Column("description")
    private String description;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
