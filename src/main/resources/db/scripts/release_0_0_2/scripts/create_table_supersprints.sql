--liquibase formatted sql

--changeset klepikov-vs:create_table_supersprint rollbackSplitStatements:true
--comment: Создание таблицы суперспринтов
create table SUPERSPRINTS
(
    ID   varchar(36) primary key,
    NAME VARCHAR(128),
    START_AT TIMESTAMP,
    END_AT TIMESTAMP,
    AUTHOR_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
    );



COMMENT ON TABLE SUPERSPRINTS IS 'Суперспринты';
COMMENT ON COLUMN SUPERSPRINTS.ID IS 'Идентификатор суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.NAME IS 'Наименование суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.START_AT IS 'Начало суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.END_AT IS 'Конец суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.AUTHOR_ID IS 'Автор айди суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.CREATED_AT IS 'Дата создания суперспринта';
COMMENT ON COLUMN SUPERSPRINTS.UPDATED_AT IS 'Дата обновления суперспринта';

--rollback DROP TABLE SUPERSPRINTS;