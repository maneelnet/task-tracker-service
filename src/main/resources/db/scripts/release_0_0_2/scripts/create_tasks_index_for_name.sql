--liquibase formatted sql

-- changeset DocUser:1:createIndexChangeType
CREATE INDEX  tasks_name_idx  ON  task_tracker.tasks(name  DESC);