-- Indexes for Supply Chain Finance
-- Performance optimization for queries with finance_type filter

-- Agreement indexes
CREATE INDEX idx_scf_agreement_finance_type ON supply_chain_finance_agreement(finance_type);
CREATE INDEX idx_scf_agreement_loan_servicing_case ON supply_chain_finance_agreement(loan_servicing_case_id);
CREATE INDEX idx_scf_agreement_status ON supply_chain_finance_agreement(status);
CREATE INDEX idx_scf_agreement_number ON supply_chain_finance_agreement(agreement_number);
CREATE INDEX idx_scf_agreement_dates ON supply_chain_finance_agreement(start_date, end_date);

-- Counterparty indexes
CREATE INDEX idx_counterparty_agreement ON counterparty(agreement_id);
CREATE INDEX idx_counterparty_finance_type ON counterparty(finance_type);
CREATE INDEX idx_counterparty_name ON counterparty(counterparty_name);
CREATE INDEX idx_counterparty_approved ON counterparty(approved);

-- Invoice indexes
CREATE INDEX idx_invoice_agreement ON supply_chain_finance_invoice(agreement_id);
CREATE INDEX idx_invoice_counterparty ON supply_chain_finance_invoice(counterparty_id);
CREATE INDEX idx_invoice_status ON supply_chain_finance_invoice(status);
CREATE INDEX idx_invoice_finance_type ON supply_chain_finance_invoice(finance_type);
CREATE INDEX idx_invoice_number ON supply_chain_finance_invoice(invoice_number);
CREATE INDEX idx_invoice_dates ON supply_chain_finance_invoice(invoice_date, due_date);

-- Advance indexes
CREATE INDEX idx_advance_invoice ON supply_chain_finance_advance(invoice_id);
CREATE INDEX idx_advance_finance_type ON supply_chain_finance_advance(finance_type);
CREATE INDEX idx_advance_date ON supply_chain_finance_advance(advance_date);

-- Settlement indexes
CREATE INDEX idx_settlement_invoice ON supply_chain_finance_settlement(invoice_id);
CREATE INDEX idx_settlement_finance_type ON supply_chain_finance_settlement(finance_type);
CREATE INDEX idx_settlement_date ON supply_chain_finance_settlement(settlement_date);

-- Fee indexes
CREATE INDEX idx_fee_agreement ON supply_chain_finance_fee(agreement_id);
CREATE INDEX idx_fee_type ON supply_chain_finance_fee(fee_type);
CREATE INDEX idx_fee_finance_type ON supply_chain_finance_fee(finance_type);
