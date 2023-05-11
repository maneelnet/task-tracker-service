--liquibase formatted sql

--changeset klepikov-vs:create_table_epics rollbackSplitStatements:true
--comment: Создание таблицы эпиков
create table EPICS
(
    ID   varchar(36) primary key,
    NAME VARCHAR(128),
    DESCRIPTION VARCHAR(256),
    STATUS varchar(18),
    PRIORITY varchar(18),
    PROJECT_ID VARCHAR(36),
    AUTHOR_ID VARCHAR(36),
    SUPERSPRINT_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
    );



COMMENT ON TABLE EPICS IS 'Эпики';
COMMENT ON COLUMN EPICS.ID IS 'Идентификатор эпика';
COMMENT ON COLUMN EPICS.NAME IS 'Наименование эпика';
COMMENT ON COLUMN EPICS.DESCRIPTION IS 'Описание эпика';
COMMENT ON COLUMN EPICS.STATUS IS 'Статус эпика';
COMMENT ON COLUMN EPICS.PRIORITY IS 'Приоритет эпика';
COMMENT ON COLUMN EPICS.PROJECT_ID IS 'Идентификатор проекта';
COMMENT ON COLUMN EPICS.AUTHOR_ID IS 'Идентификатор автора';
COMMENT ON COLUMN EPICS.SUPERSPRINT_ID IS 'Идентификатор суперспринта';
COMMENT ON COLUMN EPICS.CREATED_AT IS 'Дата создания эпика';
COMMENT ON COLUMN EPICS.UPDATED_AT IS 'Дата обновления эпика';

--rollback DROP TABLE EPICS;