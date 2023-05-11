--liquibase formatted sql

--changeset kalyashov-ga:create_table_deliveries rollbackSplitStatements:true
--comment: Создание таблицы поставок
CREATE TABLE DELIVERIES
(
    ID   VARCHAR(36) PRIMARY KEY,
    NAME VARCHAR(128),
    VERSION VARCHAR(12),
    RELEASE_ID VARCHAR(36) REFERENCES RELEASES(ID),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
);

COMMENT ON TABLE DELIVERIES IS 'Поставки';
COMMENT ON COLUMN DELIVERIES.ID IS 'Идентификатор';
COMMENT ON COLUMN DELIVERIES.NAME IS 'Наименование';
COMMENT ON COLUMN DELIVERIES.VERSION IS 'Версия';

--rollback DROP TABLE DELIVERIES;