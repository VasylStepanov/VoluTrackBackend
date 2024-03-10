INSERT INTO user_data.users(id, firstname, lastname, email, password, role_id, is_locked, is_enabled, created_at, updated_at)
VALUES('085c36de-2f7d-4bfc-9150-2df11e2eaafd', 'TestName', 'TestLastName', 'testemail@gmail.com', '$2a$10$n4kZPDz6Ex8Kwjx4QXcgnugaI4KE3NpeZKd73XnfNMpdVQ/o5/nRG', 1, 'f', 't', '2024-03-10 00:46:56.777549', '2024-03-10 00:47:47.359138');

INSERT INTO volunteer_data.volunteer(id, created_at, updated_at, user_id)
VALUES('227bc203-9f0c-4d51-942c-04f5bd4d39e7', '2024-03-10 00:46:56.777549', '2024-03-10 00:47:47.359138', '085c36de-2f7d-4bfc-9150-2df11e2eaafd');