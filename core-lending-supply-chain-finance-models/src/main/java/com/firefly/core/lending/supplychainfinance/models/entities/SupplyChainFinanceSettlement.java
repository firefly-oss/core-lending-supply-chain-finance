package com.firefly.core.lending.supplychainfinance.models.entities;

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
 * Entity for final settlement of invoices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supply_chain_finance_settlement")
public class SupplyChainFinanceSettlement {

    @Id
    @Column("id")
    private UUID id;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("invoice_id")
    private UUID invoiceId;

    @Column("settlement_date")
    private LocalDate settlementDate;

    @Column("currency")
    private CurrencyCodeEnum currency;

    @Column("settlement_amount")
    private BigDecimal settlementAmount;

    @Column("advanced_amount")
    private BigDecimal advancedAmount;

    @Column("interest_amount")
    private BigDecimal interestAmount;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("balance_due")
    private BigDecimal balanceDue;

    @Column("payment_reference")
    private String paymentReference;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
