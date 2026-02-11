-- Переименуем колонку content в theory
ALTER TABLE lessons
    RENAME COLUMN content TO theory;

-- Изменим тип колонки theory на TEXT (чтобы соответствовало JPA)
ALTER TABLE lessons
ALTER COLUMN theory TYPE TEXT;

-- Изменим тип колонки code_example на TEXT
ALTER TABLE lessons
ALTER COLUMN code_example TYPE TEXT;
