CREATE TABLE bv_wallet(
    id pg_catalog.uuid PRIMARY KEY NOT NULL,
    user_id pg_catalog.uuid REFERENCES bv_users(id) NOT NULL,
    balance DECIMAL NOT NULL DEFAULT 0,
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    version BIGINT NOT NULL DEFAULT 1
);