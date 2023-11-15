CREATE TABLE IF NOT EXISTS user_data.roles(
    id INTEGER CONSTRAINT role_id_pk PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    CONSTRAINT uk_name UNIQUE(name)
);

INSERT INTO user_data.roles(id, name) VALUES(1, 'USER_V_I'), (2, 'USER_V_II'), (3, 'USER_V_III'), (4, 'USER_V_III_P'), (5, 'USER_V_IV'), (6, 'RECIPIENT'), (7, 'ADMIN'), (8, 'MODERATOR');