package com.firefly.core.lending.supplychainfinance.models.entities;

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
 * Unified entity for counterparties.
 * Represents debtors in factoring, suppliers in confirming.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("counterparty")
public class Counterparty {

    @Id
    @Column("id")
    private UUID id;

    @Column("agreement_id")
    private UUID agreementId;

    @Column("finance_type")
    private FinanceTypeEnum financeType;

    @Column("counterparty_name")
    private String counterpartyName;

    @Column("tax_id")
    private String taxId;

    @Column("registration_number")
    private String registrationNumber;

    @Column("address")
    private String address;

    @Column("contact_person")
    private String contactPerson;

    @Column("contact_email")
    private String contactEmail;

    @Column("contact_phone")
    private String contactPhone;

    @Column("approved")
    private Boolean approved;

    @Column("approved_limit")
    private BigDecimal approvedLimit;

    @Column("current_exposure")
    private BigDecimal currentExposure;

    @Column("credit_rating")
    private String creditRating;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
