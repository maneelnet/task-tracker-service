--liquibase formatted sql

--changeset kovalchuk-d:create_table_scheduler_info rollbackSplitStatements:true
--comment: Создание таблицы планировщика отправки уведомлений
CREATE TABLE scheduler_info
(
    id BIGSERIAL PRIMARY KEY,
    lasttrigger_time TIMESTAMP
);

COMMENT ON TABLE scheduler_info IS 'Планировщик (scheduler) отправки уведомлений';
COMMENT ON COLUMN scheduler_info.id IS 'Идентификатор планировщика';
COMMENT ON COLUMN scheduler_info.lasttrigger_time IS 'Время последнего срабатывания планировщика';

--rollback DROP TABLE scheduler_info;