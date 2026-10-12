ALTER TABLE bv_refresh_tokens
    ADD COLUMN IF NOT EXISTS expired_at TIMESTAMP;

UPDATE bv_refresh_tokens
SET expired_at = created_at + INTERVAL '7 days'
WHERE expired_at IS NULL;

ALTER TABLE bv_refresh_tokens
    ALTER COLUMN expired_at SET NOT NULL;