CREATE TABLE IF NOT EXISTS volunteer_data.cars(
    id UUID CONSTRAINT car_id_pk PRIMARY KEY,
    number VARCHAR(16) NOT NULL,
    description VARCHAR(64),
    carrying_kg INTEGER NOT NULL,
    type SMALLINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    volunteer_id UUID NOT NULL,
    CONSTRAINT uk_car_number UNIQUE(number),
    CONSTRAINT fk_car_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteers(id) ON DELETE CASCADE
)