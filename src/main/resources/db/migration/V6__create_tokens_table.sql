CREATE TABLE IF NOT EXISTS user_data.tokens(
    id UUID CONSTRAINT token_id_pk PRIMARY KEY,
    refresh_token VARCHAR NOT NULL,
    ip_address VARCHAR(15) NOT NULL,
    expires_at TIMESTAMP(6) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT uk_ip_address UNIQUE(ip_address),
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user_data.users(id)
);