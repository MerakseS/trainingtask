CREATE TABLE IF NOT EXISTS project
(
    id          BIGINT IDENTITY PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS employee
(
    id         BIGINT IDENTITY PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    surname    VARCHAR(30) NOT NULL,
    patronymic VARCHAR(30),
    position   VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS task
(
    id         BIGINT IDENTITY PRIMARY KEY,
    status     VARCHAR(20) NOT NULL,
    name       VARCHAR(50) NOT NULL,
    project    BIGINT      NOT NULL,
    work_time  INT,
    start_date DATE,
    end_date   DATE,
    executor   BIGINT,

    FOREIGN KEY (project) REFERENCES project (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    FOREIGN KEY (executor) REFERENCES employee (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);