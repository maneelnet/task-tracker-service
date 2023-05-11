--liquibase formatted sql

--changeset kalyashov-ga:create_table_notification_subscriptions rollbackSplitStatements:true
--comment: Создание таблицы подписчиков на уведомления
create table NOTIFICATION_SUBSCRIPTIONS
(
    ID   varchar(36) primary key,
    USER_ID VARCHAR(36),
    PROJECT_ID VARCHAR(36),
    AUTHOR_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP
    );



COMMENT ON TABLE NOTIFICATION_SUBSCRIPTIONS IS 'Подписчики на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.ID IS 'Идентификатор подписчика на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.USER_ID IS 'User ID подписчика на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.PROJECT_ID IS 'Project ID подписчика на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.AUTHOR_ID IS 'Author ID подписчика на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.CREATED_AT IS 'Дата создания подписчика на уведомления';
COMMENT ON COLUMN NOTIFICATION_SUBSCRIPTIONS.UPDATED_AT IS 'Дата обновления подписчика на уведомления';

--rollback DROP TABLE NOTIFICATION_SUBSCRIPTIONS;