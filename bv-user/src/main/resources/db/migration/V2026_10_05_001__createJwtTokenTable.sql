CREATE TABLE bv_refresh_tokens(
    jti pg_catalog.uuid NOT NULL ,
    user_id pg_catalog.uuid REFERENCES bv_users(id) NOT NULL,
    token VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);