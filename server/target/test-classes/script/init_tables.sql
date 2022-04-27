DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS role;

CREATE TABLE role (
    role_id IDENTITY,
    name NVARCHAR(50) UNIQUE
);

CREATE TABLE usr (
    usr_id IDENTITY,
    role_id BIGINT NOT NULL,
    username VARCHAR(70) UNIQUE,
    password VARCHAR(70),
    email VARCHAR(70) UNIQUE,
    firstname VARCHAR(70),
    lastname VARCHAR(70),
    birthday DATE,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);