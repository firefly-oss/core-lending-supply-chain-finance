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
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.InvoiceStatusEnum;
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
 * Unified entity for supply chain finance invoices.
 * Supports both factoring and confirming invoice workflows.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supply_chain_finance_invoice")
public class SupplyChainFinanceInvoice {

    @Id
    @Column("id")
    private UUID id;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("agreement_id")
    private UUID agreementId;

    @Column("counterparty_id")
    private UUID counterpartyId;

    @Column("invoice_number")
    private String invoiceNumber;

    @Column("invoice_date")
    private LocalDate invoiceDate;

    @Column("due_date")
    private LocalDate dueDate;

    @Column("currency")
    private String currency;

    @Column("invoice_amount")
    private BigDecimal invoiceAmount;

    @Column("status")
    private InvoiceStatusEnum status;

    @Column("financed_amount")
    private BigDecimal financedAmount;

    @Column("financed_date")
    private LocalDate financedDate;

    @Column("paid_date")
    private LocalDate paidDate;

    @Column("verification_notes")
    private String verificationNotes;

    @Column("rejection_reason")
    private String rejectionReason;

    @Column("purchase_order_number")
    private String purchaseOrderNumber;

    @Column("delivery_note_number")
    private String deliveryNoteNumber;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
