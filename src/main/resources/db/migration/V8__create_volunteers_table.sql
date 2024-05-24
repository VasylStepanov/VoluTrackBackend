CREATE TABLE IF NOT EXISTS volunteer_data.volunteers(
    id UUID CONSTRAINT volunteer_id_pk PRIMARY KEY,
    phone_number VARCHAR(13),
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id UUID NOT NULL,
    CONSTRAINT uk_volunteer_user_id UNIQUE(user_id),
    CONSTRAINT uk_phone_number UNIQUE(phone_number),
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user_data.users(id) ON DELETE CASCADE
)
