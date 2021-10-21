INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('adrian', '$2a$10$63bx8DESX/.pFSegkZzrwOQP0ztcORO4Uo8K2cOJiBMSIEshfasBm', true, 'Adrian', 'Gonzalez', 'adrian@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$S5s.EIkMdOIHjWd3NS9jbO/2QNFsi0rhco2LlkeHiBaOW14Lu2iHO', true, 'Jhon', 'Doe', 'jhon@gmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);