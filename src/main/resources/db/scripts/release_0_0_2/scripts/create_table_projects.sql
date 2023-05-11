--liquibase formatted sql

--changeset sadovnick-aa:create_table_projects rollbackSplitStatements:true
--comment: Создание таблицы проектов

create table projects
(
    id          varchar primary key,
    name        varchar(128),
    description varchar(255),
    status      varchar(36),
    workload_id varchar(36),
    author_id   varchar(36),
    created_At   timestamp,
    updated_At   timestamp
);

comment on table projects is 'Проекты';
comment on column projects.id is 'Идентификатор';
comment on column projects.name is 'Название проекта';
comment on column projects.description is 'Описание проекта';
comment on column projects.status is 'Статус проекта';
comment on column projects.workload_id is 'Идентификатор рабочего пространства';
comment on column projects.author_id is 'Идентификатор автора';
comment on column projects.created_At is 'Дата и время создания проекта';
comment on column projects.updated_At is 'Дата и время обновления проекта'

--rollback drop table projects;