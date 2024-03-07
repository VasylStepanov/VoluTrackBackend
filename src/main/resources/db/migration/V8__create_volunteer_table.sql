CREATE TABLE IF NOT EXISTS volunteer_data.volunteer(
    id UUID CONSTRAINT volunteer_id_pk PRIMARY KEY,
    help_counter INTEGER NOT NULL DEFAULT 0 ,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    user_id UUID NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user_data.users(id) ON DELETE CASCADE
)
