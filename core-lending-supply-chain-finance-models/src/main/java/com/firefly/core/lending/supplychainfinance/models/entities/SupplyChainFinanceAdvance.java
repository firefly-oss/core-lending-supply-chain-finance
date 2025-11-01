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
    private CurrencyCodeEnum currency;

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
