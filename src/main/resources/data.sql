INSERT INTO users (id, first_name, last_name, email, username, password, role, created_at)
VALUES (1, 'Admin', 'User', 'example@email.com', 'admin', '$2a$10$T1FcdBk4jk.jXx8mgF6M6.HdHzvydXxCZUpqZStgQ9fT5bnL0SM.a', 'ADMIN', NOW());

INSERT INTO users (id, first_name, last_name, email, username, password, role, created_at)
VALUES (2, 'John', 'Doe', 'john@doe.com', 'johndoe', '$2a$10$uy8WsfymdTzXNd2aNmKjdOmeyscUxwAHz9yceRMHA/KLSlycaarzW', 'USER', NOW());

INSERT INTO users (id, first_name, last_name, email, username, password, role, created_at)
VALUES (3, 'Jane', 'Doe', 'jane@doe.com', 'janedoe', '$2a$10$uy8WsfymdTzXNd2aNmKjdOmeyscUxwAHz9yceRMHA/KLSlycaarzW', 'USER', NOW());