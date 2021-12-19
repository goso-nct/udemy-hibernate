CREATE TABLE details_o2o_uni (
    id int NOT NULL AUTO_INCREMENT,
    city varchar(15) DEFAULT NULL,
    phone_number varchar(25) DEFAULT NULL,
    email varchar(30) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE employees_o2o_uni (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(15) NOT NULL,
    surname varchar(25) NOT NULL,
    department varchar(20) DEFAULT NULL,
    salary int DEFAULT NULL,
    details_id int DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (details_id) REFERENCES details_o2o_uni(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);