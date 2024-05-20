CREATE SCHEMA IF NOT EXISTS general_data;

CREATE TABLE IF NOT EXISTS general_data.addresses(
    id UUID CONSTRAINT address_id_pk PRIMARY KEY,
    address VARCHAR(256),
    coordinates_longitude REAL NOT NULL,
    coordinates_latitude REAL NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
