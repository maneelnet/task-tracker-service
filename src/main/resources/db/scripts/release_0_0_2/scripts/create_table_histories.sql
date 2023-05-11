--liquibase formatted sql

--changeset krasilovaa:create_table_histories rollbackSplitStatements:true
--comment: Создание таблицы историй
CREATE TABLE HISTORIES
(
    ID          VARCHAR(36) PRIMARY KEY,
    NAME        VARCHAR(128),
    DESCRIPTION VARCHAR(256),
    STATUS      VARCHAR(18),
    PRIORITY    VARCHAR(18),
    EPIC_ID     VARCHAR(36),
    AUTHOR_ID   VARCHAR(36),
    SPRINT_ID   VARCHAR(36),
    CREATED_AT  TIMESTAMP,
    UPDATED_AT  TIMESTAMP
);

COMMENT
ON TABLE HISTORIES IS 'Истории';
COMMENT
ON COLUMN HISTORIES.ID IS 'Идентификатор истории';
COMMENT
ON COLUMN HISTORIES.NAME IS 'Наименование истории';
COMMENT
ON COLUMN HISTORIES.DESCRIPTION IS 'Описание истории';
COMMENT
ON COLUMN HISTORIES.STATUS IS 'Статус истории';
COMMENT
ON COLUMN HISTORIES.PRIORITY IS 'Приоритет истории';
COMMENT
ON COLUMN HISTORIES.EPIC_ID IS 'Индентификатор эпика';
COMMENT
ON COLUMN HISTORIES.AUTHOR_ID IS 'Идентификатор автора';
COMMENT
ON COLUMN HISTORIES.SPRINT_ID IS 'Идентификатор спринта';
COMMENT
ON COLUMN HISTORIES.CREATED_AT IS 'Дата создания записи';
COMMENT
ON COLUMN HISTORIES.UPDATED_AT IS 'Дата обновления записи';

--rollback DROP TABLE HISTORIES;