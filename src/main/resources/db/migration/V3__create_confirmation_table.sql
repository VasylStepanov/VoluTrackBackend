CREATE TABLE IF NOT EXISTS user_data.confirmation_email(
    id UUID CONSTRAINT confirmation_id_pk PRIMARY KEY,
    confirmed BOOLEAN,
    token VARCHAR(36) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY(id) REFERENCES user_data.users(id) ON DELETE CASCADE
);