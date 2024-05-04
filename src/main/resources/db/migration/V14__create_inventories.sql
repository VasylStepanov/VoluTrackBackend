CREATE TABLE IF NOT EXISTS volunteer_data.inventories(
    id UUID CONSTRAINT inventory_id_pk PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);

ALTER TABLE volunteer_data.volunteers ADD COLUMN inventory_id UUID;
ALTER TABLE volunteer_data.volunteers ADD CONSTRAINT fk_volunteer_inventory_id FOREIGN KEY(inventory_id) REFERENCES volunteer_data.inventories(id);

ALTER TABLE volunteer_data.groups ADD COLUMN inventory_id UUID;
ALTER TABLE volunteer_data.groups ADD CONSTRAINT fk_group_inventory_id FOREIGN KEY(inventory_id) REFERENCES volunteer_data.inventories(id);

CREATE TABLE IF NOT EXISTS volunteer_data.inventory_items(
    id UUID CONSTRAINT inventory_items_id_pk PRIMARY KEY,
    inventory_id UUID NOT NULL,
    item_id UUID NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    CONSTRAINT fk_inventory_id FOREIGN KEY(inventory_id) REFERENCES volunteer_data.inventories(id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_item_id FOREIGN KEY(item_id) REFERENCES volunteer_data.items(id) ON DELETE CASCADE
);