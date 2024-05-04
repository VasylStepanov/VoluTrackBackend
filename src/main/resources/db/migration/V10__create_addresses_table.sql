CREATE SCHEMA IF NOT EXISTS general_data;

CREATE TABLE IF NOT EXISTS general_data.addresses(
    id UUID CONSTRAINT address_id_pk PRIMARY KEY,
    region VARCHAR(32) NOT NULL,
    settlement VARCHAR(64) NOT NULL,
    location VARCHAR(128) NOT NULL,
    coordinates_latitude REAL,
    coordinates_longitude REAL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
)
