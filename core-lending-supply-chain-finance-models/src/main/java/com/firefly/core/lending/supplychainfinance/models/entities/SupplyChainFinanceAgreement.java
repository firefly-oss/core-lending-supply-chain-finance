package com.firefly.core.lending.supplychainfinance.models.entities;

import com.firefly.core.lending.supplychainfinance.interfaces.enums.AgreementStatusEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.CurrencyCodeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
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
 * Unified entity for supply chain finance agreements.
 * Supports both FACTORING and CONFIRMING via financeType discriminator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supply_chain_finance_agreement")
public class SupplyChainFinanceAgreement {

    @Id
    @Column("id")
    private UUID id;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("agreement_number")
    private String agreementNumber;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId; // FK to LoanServicingCase in loan-servicing microservice

    @Column("status")
    private AgreementStatusEnum status;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("currency")
    private CurrencyCodeEnum currency;

    @Column("credit_limit")
    private BigDecimal creditLimit;

    @Column("available_limit")
    private BigDecimal availableLimit;

    @Column("outstanding_amount")
    private BigDecimal outstandingAmount;

    // Factoring-specific fields (nullable)
    @Column("recourse")
    private Boolean recourse;

    @Column("notification_required")
    private Boolean notificationRequired;

    @Column("advance_rate")
    private BigDecimal advanceRate;

    // Confirming-specific fields (nullable)
    @Column("supplier_early_payment_option")
    private Boolean supplierEarlyPaymentOption;

    @Column("standard_payment_term_days")
    private Integer standardPaymentTermDays;

    @Column("early_payment_discount_days")
    private Integer earlyPaymentDiscountDays;

    // Common fields
    @Column("interest_rate")
    private BigDecimal interestRate;

    @Column("notes")
    private String notes;

    @Column("created_by")
    private UUID createdBy;

    @Column("last_modified_by")
    private UUID lastModifiedBy;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
