CREATE TABLE bv_users(
    id pg_catalog.uuid PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    username VARCHAR(25) UNIQUE NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);