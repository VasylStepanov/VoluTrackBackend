CREATE TABLE IF NOT EXISTS volunteer_data.address(
    id UUID CONSTRAINT address_id_pk PRIMARY KEY,
    region VARCHAR(32) NOT NULL,
    settlement VARCHAR(64) NOT NULL,
    location VARCHAR(128) NOT NULL,
    coordinates_latitude REAL,
    coordinates_longitude REAL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    volunteer_id UUID NOT NULL,
    CONSTRAINT uk_address_volunteer_id UNIQUE(volunteer_id),
    CONSTRAINT fk_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteer(id) ON DELETE CASCADE
)
