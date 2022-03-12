CREATE DATABASE doc_validation;
USE doc_validation;

CREATE TABLE user (
    id          BIGINT          NOT NULL,
    username    VARCHAR(50)     NOT NULL,
    password    VARCHAR(1024)   NOT NULL,
    enabled     BOOLEAN         NOT NULL,
    PRIMARY KEY (id),
    UNIQUE      (username)
);

CREATE TABLE user_role (
    user_id                 BIGINT      NOT NULL,
    role                    VARCHAR(50) NOT NULL,
    PRIMARY KEY             (user_id, role),
    FOREIGN KEY fk_user_id  (user_id) REFERENCES user (id)
);
