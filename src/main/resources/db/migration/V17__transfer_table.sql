CREATE TABLE IF NOT EXISTS general_data.transfer(
    id UUID CONSTRAINT address_id_pk PRIMARY KEY,
    status SMALLINT NOT NULL,
    volunteer_id UUID,
    inventory_item_id UUID,
    group_id UUID,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transfer_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteers(id) ON DELETE CASCADE,
    CONSTRAINT fk_transfer_inventory_item_id FOREIGN KEY(inventory_item_id) REFERENCES volunteer_data.inventory_items(id) ON DELETE CASCADE,
    CONSTRAINT fk_transfer_group_id FOREIGN KEY(group_id) REFERENCES volunteer_data.groups(id) ON DELETE CASCADE,
)
