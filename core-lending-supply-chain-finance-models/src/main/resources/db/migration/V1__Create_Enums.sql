-- Supply Chain Finance Enums
-- This unified schema supports both Factoring and Confirming

CREATE TYPE finance_type AS ENUM (
    'FACTORING',    -- Invoice factoring
    'CONFIRMING'    -- Reverse factoring (confirming)
);

CREATE TYPE agreement_status AS ENUM (
    'ACTIVE',
    'CLOSED',
    'SUSPENDED',
    'TERMINATED'
);

CREATE TYPE invoice_status AS ENUM (
    'ASSIGNED',     -- Factoring: invoice assigned
    'APPROVED',     -- Factoring: invoice approved
    'REJECTED',     -- Factoring: invoice rejected
    'CONFIRMED',    -- Confirming: invoice confirmed by buyer
    'EARLY_PAID',   -- Confirming: early payment made
    'SETTLED',      -- Common: final settlement
    'CANCELED',     -- Common: canceled
    'DISPUTED',     -- Common: disputed
    'DEFAULTED'     -- Common: defaulted
);

CREATE TYPE currency_code AS ENUM (
    'EUR',
    'USD',
    'GBP',
    'CHF'
);

CREATE TYPE fee_type AS ENUM (
    'DISCOUNT_FEE',
    'ADMIN_FEE',
    'COLLECTION_FEE',
    'SERVICE_FEE',
    'LATE_FEE',
    'EARLY_PAYMENT_FEE'
);
