CREATE TABLE IF NOT EXISTS user_data.confirmation_email(
    id UUID CONSTRAINT confirmation_id_pk PRIMARY KEY,
    confirmed BOOLEAN,
    token VARCHAR(36) NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    expiresAt TIMESTAMP NOT NULL
)