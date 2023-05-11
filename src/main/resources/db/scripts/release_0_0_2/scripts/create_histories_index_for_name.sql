--liquibase formatted sql

-- changeset krasilovaa:createIndexChangeType
CREATE INDEX  histories_name_idx  ON  task_tracker.histories(name  DESC);