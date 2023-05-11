--liquibase formatted sql

--changeset kovalchuk-d:create_table_workloads rollbackSplitStatements:true
--comment: Создание таблицы с рабочими пространствами
CREATE TABLE WORKLOADS
(
    ID   VARCHAR(36) PRIMARY KEY,
    NAME VARCHAR(128),
    STATUS VARCHAR(18),
    AUTHOR_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

COMMENT ON TABLE WORKLOADS IS 'Рабочие пространства';
COMMENT ON COLUMN WORKLOADS.ID IS 'Идентификатор рабочего пространства';
COMMENT ON COLUMN WORKLOADS.NAME IS 'Наименование рабочего пространства';
COMMENT ON COLUMN WORKLOADS.STATUS IS 'Статус рабочего пространства';
COMMENT ON COLUMN WORKLOADS.AUTHOR_ID IS 'Идентификатор автора рабочего пространства';
COMMENT ON COLUMN WORKLOADS.CREATED_AT IS 'Дата создания записи рабочего пространства';
COMMENT ON COLUMN WORKLOADS.UPDATED_AT IS 'Дата обновления записи рабочего пространства';

--rollback DROP TABLE WORKLOADS;