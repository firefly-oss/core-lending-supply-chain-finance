-- Supply Chain Finance Tables
-- UNIFIED schema with finance_type discriminator
-- Supports both FACTORING and CONFIRMING in single tables

-- Main agreement table (factoring OR confirming)
CREATE TABLE supply_chain_finance_agreement (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    finance_type finance_type NOT NULL,
    agreement_number VARCHAR(100) NOT NULL UNIQUE,
    loan_servicing_case_id UUID NOT NULL,  -- FK to LoanServicingCase in loan-servicing microservice
    status agreement_status NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    currency currency_code NOT NULL,
    credit_limit DECIMAL(19,4),
    available_limit DECIMAL(19,4),
    outstanding_amount DECIMAL(19,4) DEFAULT 0,
    
    -- Factoring-specific fields (nullable for confirming)
    recourse BOOLEAN,
    notification_required BOOLEAN,
    advance_rate DECIMAL(5,2),
    
    -- Confirming-specific fields (nullable for factoring)
    supplier_early_payment_option BOOLEAN,
    standard_payment_term_days INTEGER,
    early_payment_discount_days INTEGER,
    
    -- Common fields
    interest_rate DECIMAL(5,2),
    notes TEXT,
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Counterparty table (debtor in factoring, supplier in confirming)
CREATE TABLE counterparty (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agreement_id UUID NOT NULL 
        REFERENCES supply_chain_finance_agreement(id) ON DELETE CASCADE,
    finance_type finance_type NOT NULL,
    counterparty_name VARCHAR(255) NOT NULL,
    tax_id VARCHAR(50),
    registration_number VARCHAR(50),
    address TEXT,
    contact_person VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(50),
    approved BOOLEAN DEFAULT FALSE,
    approved_limit DECIMAL(19,4),
    current_exposure DECIMAL(19,4) DEFAULT 0,
    credit_rating VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Invoice table (factoring OR confirming)
CREATE TABLE supply_chain_finance_invoice (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    finance_type finance_type NOT NULL,
    agreement_id UUID NOT NULL 
        REFERENCES supply_chain_finance_agreement(id) ON DELETE CASCADE,
    counterparty_id UUID NOT NULL 
        REFERENCES counterparty(id) ON DELETE CASCADE,
    invoice_number VARCHAR(100) NOT NULL,
    invoice_date DATE NOT NULL,
    due_date DATE NOT NULL,
    currency currency_code NOT NULL,
    invoice_amount DECIMAL(19,4) NOT NULL,
    status invoice_status NOT NULL,
    financed_amount DECIMAL(19,4),
    financed_date DATE,
    paid_date DATE,
    verification_notes TEXT,
    rejection_reason TEXT,
    purchase_order_number VARCHAR(100),
    delivery_note_number VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_invoice_number UNIQUE(finance_type, invoice_number)
);

-- Advance table (factoring advance OR confirming early payment)
CREATE TABLE supply_chain_finance_advance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    finance_type finance_type NOT NULL,
    invoice_id UUID NOT NULL 
        REFERENCES supply_chain_finance_invoice(id) ON DELETE CASCADE,
    advance_date DATE NOT NULL,
    currency currency_code NOT NULL,
    advance_amount DECIMAL(19,4) NOT NULL,
    discount_amount DECIMAL(19,4),
    fee_amount DECIMAL(19,4),
    net_amount DECIMAL(19,4),
    payment_reference VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Settlement table (final settlement for both types)
CREATE TABLE supply_chain_finance_settlement (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    finance_type finance_type NOT NULL,
    invoice_id UUID NOT NULL 
        REFERENCES supply_chain_finance_invoice(id) ON DELETE CASCADE,
    settlement_date DATE NOT NULL,
    currency currency_code NOT NULL,
    settlement_amount DECIMAL(19,4) NOT NULL,
    advanced_amount DECIMAL(19,4),
    interest_amount DECIMAL(19,4),
    fee_amount DECIMAL(19,4),
    balance_due DECIMAL(19,4),
    payment_reference VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Fee table (fee structures for both types)
CREATE TABLE supply_chain_finance_fee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    finance_type finance_type NOT NULL,
    agreement_id UUID NOT NULL 
        REFERENCES supply_chain_finance_agreement(id) ON DELETE CASCADE,
    fee_type fee_type NOT NULL,
    currency currency_code NOT NULL,
    fixed_amount DECIMAL(19,4),
    percentage_rate DECIMAL(5,2),
    minimum_amount DECIMAL(19,4),
    maximum_amount DECIMAL(19,4),
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
