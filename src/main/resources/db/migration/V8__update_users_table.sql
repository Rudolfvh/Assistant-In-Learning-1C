
-- Меняем тип колонки avatar на BYTEA
ALTER TABLE users
ALTER COLUMN avatar TYPE BYTEA USING decode(avatar, 'base64');

