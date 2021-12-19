-- Для uni- и bi- directional таблицы те же

CREATE TABLE details_o2o (
    id int NOT NULL AUTO_INCREMENT,
    city varchar(15) DEFAULT NULL,
    phone_number varchar(25) DEFAULT NULL,
    email varchar(30) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE employees_o2o (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(15) NOT NULL,
    surname varchar(25) NOT NULL,
    department varchar(20) DEFAULT NULL,
    salary int DEFAULT NULL,
    detail_id int DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (detail_id) REFERENCES details_o2o(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);