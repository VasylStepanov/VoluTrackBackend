CREATE TABLE IF NOT EXISTS user_data.tokens(
    id UUID CONSTRAINT token_id_pk PRIMARY KEY,
    refresh_token VARCHAR NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user_data.users(id) ON DELETE CASCADE
);