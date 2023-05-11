--liquibase formatted sql

--changeset maneelnet:createIndexChangeType
--comment: Создание индекса для поля наименование в таблице багов
CREATE INDEX bugs_name_idx ON task_tracker.bugs(name DESC);