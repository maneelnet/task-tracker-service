--liquibase formatted sql

-- changeset DocUser:1:createIndexChangeType
CREATE INDEX  epics_name_idx  ON  task_tracker.epics(name  DESC);
