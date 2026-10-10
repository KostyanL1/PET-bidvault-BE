CREATE TABLE bv_room(
    id pg_catalog.uuid PRIMARY KEY NOT NULL,
    auction_id pg_catalog.uuid REFERENCES bv_auction(id) NOT NULL,
    status VARCHAR NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    opened_at TIMESTAMP,
    closed_at TIMESTAMP
);
