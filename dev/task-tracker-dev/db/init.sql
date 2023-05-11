DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE  rolname = 'task_tracker_db_admin') THEN
      CREATE USER task_tracker_db_admin NOCREATEDB CREATEROLE NOSUPERUSER PASSWORD 'task_tracker_db_admin';
END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS ( SELECT FROM pg_roles WHERE  rolname = 'task_tracker_db_client') THEN
      CREATE USER task_tracker_db_client NOCREATEDB NOCREATEROLE NOSUPERUSER PASSWORD 'task_tracker_db_client';
END IF;
END
$do$;

CREATE DATABASE task_tracker OWNER task_tracker_db_admin ENCODING 'UTF8' TEMPLATE='template0' CONNECTION LIMIT 1000;

\connect task_tracker
CREATE SCHEMA liquibase AUTHORIZATION task_tracker_db_admin;
CREATE SCHEMA task_tracker AUTHORIZATION task_tracker_db_admin;

ALTER DEFAULT PRIVILEGES FOR USER task_tracker_db_admin IN SCHEMA task_tracker GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO task_tracker_db_client;
ALTER DEFAULT PRIVILEGES FOR ROLE task_tracker_db_admin GRANT SELECT,INSERT,UPDATE,DELETE ON TABLES TO task_tracker_db_client;
ALTER DEFAULT PRIVILEGES FOR ROLE task_tracker_db_admin GRANT USAGE ON SCHEMAS TO task_tracker_db_client;
GRANT USAGE ON SCHEMA task_tracker TO task_tracker_db_client;

ALTER DATABASE task_tracker SET search_path TO task_tracker;
ALTER USER task_tracker_db_client SET search_path TO 'task_tracker';