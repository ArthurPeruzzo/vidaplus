INSERT INTO `users`
(email, password)
VALUES('admin@email.com', '$2a$10$cnZH1oMATrChB6o6JE3g0OcFCbQdr62fDy5uJkt0QGXysMzGtlEuy');

INSERT INTO `users_roles`
(user_id, role_id)
VALUES(LAST_INSERT_ID(), (SELECT r.id FROM `roles` r WHERE r.name = 'ROLE_ADMINISTRATOR'));

INSERT INTO `administrator`
(name, last_name, user_id)
VALUES('Administrador', 'Default', LAST_INSERT_ID());