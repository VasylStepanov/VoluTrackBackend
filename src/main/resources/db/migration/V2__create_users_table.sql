CREATE TABLE IF NOT EXISTS user_data.users(
    id UUID CONSTRAINT user_id_pk PRIMARY KEY,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(32) NOT NULL,
    CONSTRAINT uk_email UNIQUE(email)
);