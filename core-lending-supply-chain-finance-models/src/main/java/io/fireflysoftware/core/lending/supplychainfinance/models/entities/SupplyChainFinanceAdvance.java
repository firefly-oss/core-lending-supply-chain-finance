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

import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity for advances/early payments.
 * In factoring: advance to supplier.
 * In confirming: early payment to supplier.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supply_chain_finance_advance")
public class SupplyChainFinanceAdvance {

    @Id
    @Column("id")
    private UUID id;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("invoice_id")
    private UUID invoiceId;

    @Column("advance_date")
    private LocalDate advanceDate;

    @Column("currency")
    private String currency;

    @Column("advance_amount")
    private BigDecimal advanceAmount;

    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("net_amount")
    private BigDecimal netAmount;

    @Column("payment_reference")
    private String paymentReference;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
