CREATE TABLE IF NOT EXISTS volunteer_data.car(
    id UUID CONSTRAINT car_id_pk PRIMARY KEY,
    car_number VARCHAR(16) NOT NULL,
    carrying_kg INTEGER NOT NULL,
    car_type SMALLINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    volunteer_id UUID NOT NULL,
    CONSTRAINT uk_car_number UNIQUE(car_number),
    CONSTRAINT uk_car_volunteer_id UNIQUE(volunteer_id),
    CONSTRAINT fk_volunteer_id FOREIGN KEY(volunteer_id) REFERENCES volunteer_data.volunteer(id) ON DELETE CASCADE
)