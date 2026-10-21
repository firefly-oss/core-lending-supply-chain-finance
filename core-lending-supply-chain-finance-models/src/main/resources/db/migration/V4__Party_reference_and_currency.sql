-- A counterparty on an agreement is a party, not a second customer master.
-- Currency is any ISO 4217 code. Draft and pending agreement statuses can be stored.

ALTER TYPE agreement_status ADD VALUE IF NOT EXISTS 'DRAFT' BEFORE 'ACTIVE';
ALTER TYPE agreement_status ADD VALUE IF NOT EXISTS 'PENDING_APPROVAL' BEFORE 'ACTIVE';

ALTER TABLE counterparty ADD COLUMN IF NOT EXISTS party_id UUID;
ALTER TABLE counterparty ALTER COLUMN counterparty_name DROP NOT NULL;

COMMENT ON COLUMN counterparty.party_id IS 'Party in customer management. Name and tax fields on this row are optional labels, not the customer master.';

ALTER TABLE supply_chain_finance_agreement
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::text;
ALTER TABLE supply_chain_finance_invoice
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::text;
ALTER TABLE supply_chain_finance_advance
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::text;
ALTER TABLE supply_chain_finance_settlement
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::text;
ALTER TABLE supply_chain_finance_fee
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::text;

DROP TYPE IF EXISTS currency_code;
