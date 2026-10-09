CREATE TABLE bv_auction(
    id pg_catalog.uuid PRIMARY KEY NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR NOT NULL,
    status VARCHAR NOT NULL DEFAULT 'CREATED',
    start_price DECIMAL NOT NULL DEFAULT 0,
    finish_price DECIMAL,
    owner_id pg_catalog.uuid REFERENCES bv_users(id) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    duration BIGINT NOT NULL,
    count_of_participants INT NOT NULL DEFAULT 0
)