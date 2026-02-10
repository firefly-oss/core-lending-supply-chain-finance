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
import com.firefly.core.lending.supplychainfinance.interfaces.enums.InvoiceStatusEnum;
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
 * Unified DTO for supply chain finance invoices.
 * Supports both factoring and confirming invoice workflows.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyChainFinanceInvoiceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Finance type is required")
    private FinanceTypeEnum financeType;

    @NotNull(message = "Agreement ID is required")
    @FilterableId
    private UUID agreementId;

    @NotNull(message = "Counterparty ID is required")
    @FilterableId
    private UUID counterpartyId;

    @NotBlank(message = "Invoice number is required")
    @Size(max = 100, message = "Invoice number cannot exceed 100 characters")
    private String invoiceNumber;

    @NotNull(message = "Invoice date is required")
    @ValidDate(message = "Invoice date must be a valid date")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required")
    @ValidDate(message = "Due date must be a valid date")
    private LocalDate dueDate;

    @NotNull(message = "Currency is required")
    private CurrencyCodeEnum currency;

    @NotNull(message = "Invoice amount is required")
    @Positive(message = "Invoice amount must be positive")
    @ValidAmount(message = "Invoice amount must be a valid amount")
    private BigDecimal invoiceAmount;

    @NotNull(message = "Status is required")
    private InvoiceStatusEnum status;

    @PositiveOrZero(message = "Financed amount must be zero or positive")
    @ValidAmount(message = "Financed amount must be a valid amount")
    private BigDecimal financedAmount;

    @ValidDate(message = "Financed date must be a valid date")
    private LocalDate financedDate;

    @ValidDate(message = "Paid date must be a valid date")
    private LocalDate paidDate;

    @Size(max = 2000, message = "Verification notes cannot exceed 2000 characters")
    private String verificationNotes;

    @Size(max = 500, message = "Rejection reason cannot exceed 500 characters")
    private String rejectionReason;

    @Size(max = 100, message = "Purchase order number cannot exceed 100 characters")
    private String purchaseOrderNumber;

    @Size(max = 100, message = "Delivery note number cannot exceed 100 characters")
    private String deliveryNoteNumber;

    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;
}
