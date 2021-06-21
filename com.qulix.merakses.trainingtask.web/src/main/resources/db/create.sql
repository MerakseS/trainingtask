create TABLE project
(
    p_id          BIGINT IDENTITY PRIMARY KEY,
    p_name        VARCHAR(30) NOT NULL,
    p_description VARCHAR(200)
);

CREATE TABLE employee
(
    e_id         BIGINT IDENTITY PRIMARY KEY,
    e_first_name VARCHAR(30) NOT NULL,
    e_surname    VARCHAR(30) NOT NULL,
    e_patronymic VARCHAR(30),
    e_position   VARCHAR(30) NOT NULL
);

CREATE TABLE task
(
    t_id         BIGINT IDENTITY PRIMARY KEY,
    t_status     VARCHAR(20) NOT NULL,
    t_name       VARCHAR(50) NOT NULL,
    t_project    BIGINT      NOT NULL,
    t_work_time  INT,
    t_start_date DATE,
    t_end_date   DATE,
    t_executor   BIGINT
);

ALTER TABLE task
    ADD CONSTRAINT FK_task_project
        FOREIGN KEY (t_project) REFERENCES project (p_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE task
    ADD CONSTRAINT FK_task_employee
        FOREIGN KEY (t_executor) REFERENCES employee (e_id)
            ON UPDATE CASCADE;