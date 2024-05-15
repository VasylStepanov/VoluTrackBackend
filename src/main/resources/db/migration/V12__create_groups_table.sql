CREATE SCHEMA IF NOT EXISTS group_data;

CREATE TABLE IF NOT EXISTS group_data.groups(
    id UUID CONSTRAINT group_id_pk PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(256),
    help_counter INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    volunteer_id UUID NOT NULL,
    address_id UUID,
    open_for_transfer BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uk_name UNIQUE(name),
    CONSTRAINT uk_address_id UNIQUE(address_id),
    CONSTRAINT fk_group_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteers(id) ON DELETE CASCADE,
    CONSTRAINT fk_group_address_id FOREIGN KEY(address_id) REFERENCES general_data.addresses(id)
);