--liquibase formatted sql

--changeset maneelnet:create_table_bugs rollbackSplitStatements:true
--comment: Создание таблицы багов
CREATE TABLE BUGS
(
    ID   VARCHAR(36) PRIMARY KEY,
    NAME VARCHAR(128),
    DESCRIPTION VARCHAR(256),
    STATUS VARCHAR(18),
    PRIORITY VARCHAR(18),
    HISTORY_ID VARCHAR(36),
    AUTHOR_ID VARCHAR(36),
    ASSIGNEE_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

COMMENT ON TABLE BUGS IS 'Баги';
COMMENT ON COLUMN BUGS.ID IS 'Идентификатор бага';
COMMENT ON COLUMN BUGS.NAME IS 'Наименование бага';
COMMENT ON COLUMN BUGS.DESCRIPTION IS 'Описание бага';
COMMENT ON COLUMN BUGS.STATUS IS 'Статус бага';
COMMENT ON COLUMN BUGS.PRIORITY IS 'Приоритет бага';
COMMENT ON COLUMN BUGS.HISTORY_ID IS 'Идентификатор истории';
COMMENT ON COLUMN BUGS.AUTHOR_ID IS 'Идентификатор автора';
COMMENT ON COLUMN BUGS.ASSIGNEE_ID IS 'Идентификатор исполнителя';
COMMENT ON COLUMN BUGS.CREATED_AT IS 'Дата создания записи';
COMMENT ON COLUMN BUGS.UPDATED_AT IS 'Дата обновления записи';

--rollback DROP TABLE BUGS;