--liquibase formatted sql

--changeset belyakov-nu:create_table_comments rollbackSplitStatements:true
--comment: Создание таблицы комментариев
CREATE TABLE COMMENTS
(
    ID   VARCHAR(36) PRIMARY KEY,
    TEXT VARCHAR(255),
    AUTHOR_ID VARCHAR(36),
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP,
    TASK_ID VARCHAR(36)
);

COMMENT ON TABLE COMMENTS IS 'Комментарии к задачам(Tasks)';
COMMENT ON COLUMN COMMENTS.ID IS 'Идентификатор';
COMMENT ON COLUMN COMMENTS.TEXT IS 'Текст комментария';
COMMENT ON COLUMN COMMENTS.AUTHOR_ID IS 'Идентификатор автора комментария';
COMMENT ON COLUMN COMMENTS.CREATED_AT IS 'Дата и время создания комментария';
COMMENT ON COLUMN COMMENTS.UPDATED_AT IS 'Дата и время редактирования комментария';
COMMENT ON COLUMN COMMENTS.TASK_ID IS 'Идентификатор задания(Task) к которому относится комментарий';

--rollback DROP TABLE COMMENTS;