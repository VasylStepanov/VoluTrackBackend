CREATE TABLE IF NOT EXISTS user_data.roles(
    id INTEGER CONSTRAINT role_id_pk PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    CONSTRAINT uk_name UNIQUE(name)
);

INSERT INTO user_data.roles(id, name) VALUES(1, 'ROLE_USER'), (2, 'ROLE_RECIPIENT'), (3, 'ROLE_ADMIN'), (4, 'ROLE_MODERATOR');