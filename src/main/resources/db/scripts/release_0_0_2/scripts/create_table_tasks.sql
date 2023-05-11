--liquibase formatted sql

--changeset chusov-vl:create_table_tasks rollbackSplitStatements:true
--comment: Создание таблицы задач

CREATE TABLE TASKS
(
    ID VARCHAR(36) PRIMARY KEY ,
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

COMMENT ON TABLE TASKS IS 'ЗАДАЧИ';
COMMENT ON COLUMN TASKS.ID IS 'Идентификатор';
COMMENT ON COLUMN TASKS.NAME IS 'Наименование';
COMMENT ON COLUMN TASKS.DESCRIPTION IS 'Описание';
COMMENT ON COLUMN TASKS.STATUS IS 'Статус';
COMMENT ON COLUMN TASKS.PRIORITY IS 'Приоритет ';
COMMENT ON COLUMN TASKS.HISTORY_ID IS 'Идентификатор истории';
COMMENT ON COLUMN TASKS.AUTHOR_ID IS 'Идентификатор автора';
COMMENT ON COLUMN TASKS.ASSIGNEE_ID IS 'Идентификатор исполнителя';
COMMENT ON COLUMN TASKS.CREATED_AT IS 'Дата создания записи';
COMMENT ON COLUMN TASKS.UPDATED_AT IS 'Дата обновления записи';



--rollback DROP TABLE TASKS;
