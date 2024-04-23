CREATE TABLE IF NOT EXISTS volunteer_data.volunteers(
    id UUID CONSTRAINT volunteer_id_pk PRIMARY KEY,
    description VARCHAR,
    help_counter INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    user_id UUID NOT NULL,
    CONSTRAINT uk_volunteer_user_id UNIQUE(user_id),
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user_data.users(id) ON DELETE CASCADE
)
