--liquibase formatted sql

--changeset klepikov-vs:create_table_sprint rollbackSplitStatements:true
--comment: Создание таблицы спринтов
create table SPRINTS
(
    ID   varchar(36) primary key,
    NAME VARCHAR(128),
    START_AT TIMESTAMP,
    END_AT TIMESTAMP,
    AUTHOR_ID VARCHAR(36),
    SUPERSPRINT_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
    );



comment on table SPRINTS is 'Спринты';
comment on column SPRINTS.ID is 'Идентификатор спринта';
comment on column SPRINTS.NAME is 'Наименование спринта';
comment on column SPRINTS.START_AT is 'Начало спринта';
comment on column SPRINTS.END_AT is 'Конец спринта';
comment on column SPRINTS.AUTHOR_ID is 'Автор айди спринта';
comment on column SPRINTS.SUPERSPRINT_ID is 'Суперспринт айди спринта';
comment on column SPRINTS.CREATED_AT is 'Дата создания спринта';
comment on column SPRINTS.UPDATED_AT is 'Дата обновления спринта';

--rollback DROP TABLE SPRINTS;