INSERT INTO users (id, first_name, last_name, email, username, password, role, created_at)
VALUES
    (1, 'Admin', 'User', 'example@email.com', 'admin', '$2a$10$T1FcdBk4jk.jXx8mgF6M6.HdHzvydXxCZUpqZStgQ9fT5bnL0SM.a', 'ADMIN', NOW()),
    (2, 'John', 'Doe', 'john@doe.com', 'johndoe', '$2a$10$uy8WsfymdTzXNd2aNmKjdOmeyscUxwAHz9yceRMHA/KLSlycaarzW', 'USER', NOW()),
    (3, 'Jane', 'Doe', 'jane@doe.com', 'janedoe', '$2a$10$uy8WsfymdTzXNd2aNmKjdOmeyscUxwAHz9yceRMHA/KLSlycaarzW', 'USER', NOW());

INSERT INTO tasks (id, title, description, priority, status, due_date, created_at, user_id)
VALUES
    (1, 'Setup Project Repo', 'Initialize GitHub repo with README', 'HIGH', 'TODO', '2026-03-20 12:00:00', NOW(), 2),
    (2, 'Write API Endpoints', 'Implement CRUD endpoints for tasks', 'MEDIUM', 'IN_PROGRESS', '2026-03-22 17:00:00', NOW(), 3),
    (3, 'Design UI Mockups', 'Create wireframes for the task manager', 'LOW', 'TODO', '2026-03-25 10:00:00', NOW(), 2),
    (4, 'Setup Database', 'Create PostgreSQL schema and tables', 'HIGH', 'DONE', '2026-03-18 15:00:00', NOW(), 3),
    (5, 'Email Notifications', 'Implement email reminders for tasks', 'MEDIUM', 'TODO', '2026-03-28 09:00:00', NOW(), 1);