CREATE TABLE bv_bids
(
    id         pg_catalog.uuid NOT NULL,
    room_id    pg_catalog.uuid NOT NULL REFERENCES bv_room (id),
    user_id    pg_catalog.uuid NOT NULL REFERENCES bv_users (id),
    amount     DECIMAL         NOT NULL,
    created_at TIMESTAMP       NOT NULL DEFAULT now(),
    updated_at TIMESTAMP       NOT NULL DEFAULT now(),
    status     VARCHAR         NOT NULL
);