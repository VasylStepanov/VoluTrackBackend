CREATE TABLE IF NOT EXISTS item_data.requests(
    id UUID CONSTRAINT request_id_pk PRIMARY KEY,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);

ALTER TABLE volunteer_data.volunteers ADD COLUMN request_id UUID;
ALTER TABLE volunteer_data.volunteers ADD CONSTRAINT fk_volunteer_request_id FOREIGN KEY(request_id) REFERENCES item_data.requests(id);

ALTER TABLE group_data.groups ADD COLUMN request_id UUID;
ALTER TABLE group_data.groups ADD CONSTRAINT fk_group_request_id FOREIGN KEY(request_id) REFERENCES item_data.requests(id);

CREATE TABLE IF NOT EXISTS item_data.request_items(
    id UUID CONSTRAINT request_item_id_pk PRIMARY KEY,
    request_id UUID NOT NULL,
    item_id UUID NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    CONSTRAINT fk_request_id FOREIGN KEY(request_id) REFERENCES item_data.requests(id) ON DELETE CASCADE,
    CONSTRAINT fk_requested_item_id FOREIGN KEY(item_id) REFERENCES item_data.items(id) ON DELETE CASCADE
);