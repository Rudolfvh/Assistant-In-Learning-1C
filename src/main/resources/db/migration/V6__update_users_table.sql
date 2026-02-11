-- Добавляем новые колонки
ALTER TABLE users
    ADD COLUMN first_name VARCHAR(100) NOT NULL;

ALTER TABLE users
    ADD COLUMN last_name VARCHAR(100) NOT NULL;

ALTER TABLE users
    ADD COLUMN email VARCHAR(255) NOT NULL UNIQUE;

-- Ограничиваем длину username до 50 символов и делаем уникальным
ALTER TABLE users
ALTER COLUMN username TYPE VARCHAR(50);

ALTER TABLE users
    ADD CONSTRAINT unique_username UNIQUE (username);
