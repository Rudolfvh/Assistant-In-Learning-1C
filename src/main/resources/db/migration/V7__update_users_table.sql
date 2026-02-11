-- Добавляем поле avatar в таблицу users
ALTER TABLE users
    ADD COLUMN avatar VARCHAR(255);
