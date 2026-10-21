-- Confirming is the Spanish market name for reverse factoring (payables finance).
-- The stored product kind is the English name. Existing rows keep their meaning.

ALTER TYPE finance_type RENAME VALUE 'CONFIRMING' TO 'REVERSE_FACTORING';
