CREATE TABLE IF NOT EXISTS volunteer_data.items(
    id UUID CONSTRAINT item_id_pk PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(512),
    amount INTEGER NOT NULL,
    item_measurement SMALLINT NOT NULL,
    item_type SMALLINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);