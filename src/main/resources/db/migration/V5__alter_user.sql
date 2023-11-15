ALTER TABLE user_data.users ADD COLUMN is_locked BOOLEAN NOT NULL;
ALTER TABLE user_data.users ADD COLUMN is_enabled BOOLEAN NOT NULL;
ALTER TABLE user_data.users ADD COLUMN role_id INTEGER NOT NULL;
ALTER TABLE user_data.users ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES user_data.roles(id);
ALTER TABLE user_data.users DROP COLUMN role;