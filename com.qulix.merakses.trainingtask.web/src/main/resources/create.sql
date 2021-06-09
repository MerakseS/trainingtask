create table project
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
    e_patronymic VARCHAR(30)
);

CREATE TABLE task
(
    t_id         BIGINT IDENTITY PRIMARY KEY,
    t_status     VARCHAR(15) NOT NULL,
    t_name       VARCHAR(50) NOT NULL,
    t_project    BIGINT      NOT NULL,
    t_work_time  INT,
    t_start_date TIMESTAMP,
    t_end_date   TIMESTAMP,
    t_executor   BIGINT
);

ALTER TABLE task
    ADD FOREIGN KEY (t_project) REFERENCES project (p_id);

ALTER TABLE task
    ADD FOREIGN KEY (t_executor) REFERENCES employee (e_id);