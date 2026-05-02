/*ALTER TABLE users
ALTER COLUMN id TYPE uuid
USING id::uuid*/

/*USERS*/
/*DO $$
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
       CREATE TYPE  user_role AS ENUM('ADMIN','MANAGER','EMPLOYEE');
    END IF;
END
$$;



CREATE TABLE IF NOT EXISTS users (
    id varchar(250) NOT NULL,
    email varchar(20) UNIQUE,
    password varchar(30) NOT NULL,
    full_name varchar(100) NOT NULL,
    role varchar(200) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    is_active boolean DEFAULT FALSE,
    
    PRIMARY KEY (id)
);
*/
/*PROJECTS*/
/*
CREATE TABLE IF NOT EXISTS projects(
    id varchar(250) NOT NULL,
    name varchar(20) NOT NULL,
    description varchar(150) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    user_id varchar(250),

    PRIMARY KEY(id),
    CONSTRAINT fk_project_user FOREIGN KEY (user_id) REFERENCES users(id)
);*/

/*TASKS*/
/*
DO $block$
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'task_status') THEN
       CREATE TYPE task_status AS ENUM('NEW','IN_PROGRESS','COMPLETED','BLOCKED','PENDING','CANCELLED');
    END IF;
END $block$;

DO $block$
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'task_priority') THEN
       CREATE TYPE  task_priority AS ENUM('LOW','MEDIUM','HIGH');
    END IF;
END $block$;*/
/*
 CREATE TYPE task_statuses AS ENUM('NEW','IN_PROGRESS','COMPLETED','BLOCKED','PENDING','CANCELLED');
 CREATE TYPE  task_priorities AS ENUM('LOW','MEDIUM','HIGH');

CREATE TABLE IF NOT EXISTS tasks(
id varchar(250) NOT NULL,
title varchar(50)  NOT NULL,
description varchar(300) NOT NULL,
status task_statuses NOT NULL,
priority task_priorities NOT NULL,
due_date date NOT NULL,
created_at timestamp DEFAULT CURRENT_TIMESTAMP,
updated_at timestamp NULL,
user_id varchar(250),
project_id varchar(250),

PRIMARY KEY(id),
CONSTRAINT  fk_task_user FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES projects(id)
);


/*COMMENTS*/

CREATE TABLE IF NOT EXISTS  comments(
    id varchar(250) NOT NULL,
    content varchar(300) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
task_id varchar(250),

    PRIMARY KEY(id),
    CONSTRAINT fk_comment_task FOREIGN KEY (task_id) REFERENCES tasks(id)
);
*/