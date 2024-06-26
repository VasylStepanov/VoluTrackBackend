CREATE SCHEMA IF NOT EXISTS item_data;

CREATE TABLE IF NOT EXISTS item_data.inventories(
    id UUID CONSTRAINT inventory_id_pk PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE volunteer_data.volunteers ADD COLUMN inventory_id UUID;
ALTER TABLE volunteer_data.volunteers ADD CONSTRAINT fk_volunteer_inventory_id FOREIGN KEY(inventory_id) REFERENCES item_data.inventories(id);

ALTER TABLE group_data.groups ADD COLUMN inventory_id UUID;
ALTER TABLE group_data.groups ADD CONSTRAINT fk_group_inventory_id FOREIGN KEY(inventory_id) REFERENCES item_data.inventories(id);

CREATE TABLE IF NOT EXISTS item_data.inventory_items(
    id UUID CONSTRAINT inventory_items_id_pk PRIMARY KEY,
    ready_to_send BOOLEAN DEFAULT TRUE,
    end_product BOOLEAN NOT NULL,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(512),
    item_type SMALLINT,
    amount INTEGER NOT NULL DEFAULT 0,
    weight REAL NOT NULL DEFAULT 0,
    inventory_id UUID NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_id FOREIGN KEY(inventory_id) REFERENCES item_data.inventories(id) ON DELETE CASCADE
);