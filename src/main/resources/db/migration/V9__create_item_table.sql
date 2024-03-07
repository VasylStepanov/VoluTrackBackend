CREATE TABLE IF NOT EXISTS volunteer_data.items(
    id UUID CONSTRAINT item_id_pk PRIMARY KEY,
    name VARCHAR(63) NOT NULL,
    description VARCHAR(511),
    amount INTEGER NOT NULL,
    item_measurement SMALLINT NOT NULL,
    item_type SMALLINT NOT NULL,
    volunteer_id UUID NOT NULL,
    CONSTRAINT fk_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteer(id) ON DELETE CASCADE
);
