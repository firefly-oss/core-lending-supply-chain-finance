package com.firefly.core.lending.supplychainfinance.models.entities;

import com.firefly.core.lending.supplychainfinance.interfaces.enums.CurrencyCodeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FeeTypeEnum;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
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
    private CurrencyCodeEnum currency;

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
