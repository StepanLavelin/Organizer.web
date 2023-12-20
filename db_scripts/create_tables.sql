CREATE TABLE project.users(
    id         bigserial   NOT NULL,
    first_name varchar(30) NOT NULL,
    last_name  varchar(30) NOT NULL,
    role_user  varchar(10) NOT NULL
);

ALTER TABLE project.users
ALTER COLUMN id int;

CREATE TABLE project.tasks(
                              id bigserial NOT NULL,
                              performer bigserial,
                              name character(30) NOT NULL,
                              description text,
                              date_created timestamp NOT NULL,
                              deadline timestamp,
                              task_status varchar(100)
);

ALTER TABLE project.tasks
ALTER COLUMN task_status varchar(100)

CREATE TABLE project.user_tasks_map(
    user_id bigserial NOT NULL,
    task_id bigserial NOT NULL
    );

CREATE TABLE project.comments(
    id bigserial NOT NULL,
    user_writer_id bigserial NOT NULL,
    text character NOT NULL
    );

CREATE TABLE project.task_comments_map(
    task_id bigserial NOT NULL,
    comment_id bigserial NOT NULL
    );