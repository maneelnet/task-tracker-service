--liquibase formatted sql

-- changeset DocUser:1:createIndexChangeType
CREATE INDEX  sprints_name_idx  ON  task_tracker.sprints(name  DESC);