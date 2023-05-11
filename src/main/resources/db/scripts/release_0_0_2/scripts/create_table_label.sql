--liquibase formatted sql

--changeset klepikov-vs:create_table_labels rollbackSplitStatements:true
--comment: Создание таблицы меток задачи
CREATE TABLE LABELS
(
    ID   VARCHAR(36) PRIMARY KEY,
    TEXT VARCHAR(64),
    AUTHOR_ID VARCHAR(36),
    TASK_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

COMMENT ON TABLE LABELS IS 'Баги';
COMMENT ON COLUMN LABELS.ID IS 'Идентификатор метки задачи';
COMMENT ON COLUMN LABELS.TEXT IS 'Наименование метки задачи';
COMMENT ON COLUMN LABELS.AUTHOR_ID IS 'Идентификатор автора метки задачи';
COMMENT ON COLUMN LABELS.TASK_ID IS 'Идентификатор задачи';
COMMENT ON COLUMN LABELS.CREATED_AT IS 'Дата создания записи';
COMMENT ON COLUMN LABELS.UPDATED_AT IS 'Дата обновления записи';

--rollback DROP TABLE labels;