--liquibase formatted sql

-- changeset DocUser:1:createIndexChangeType
CREATE INDEX  supersprints_name_idx  ON  task_tracker.supersprints(name  DESC);