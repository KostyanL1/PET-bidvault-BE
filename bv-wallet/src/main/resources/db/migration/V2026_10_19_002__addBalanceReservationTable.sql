CREATE TABLE bv_balance_reservations(
    id pg_catalog.uuid NOT NULL PRIMARY KEY,
    wallet_id pg_catalog.uuid REFERENCES bv_wallet(id),
    auction_id pg_catalog.uuid REFERENCES bv_auction(id),
    amount BIGINT NOT NULL DEFAULT 0,
    created_ay TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);