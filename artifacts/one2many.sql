CREATE TABLE departments_o2m (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(15),
    max_salary int,
    min_salary int,
    PRIMARY KEY (id)
);

CREATE TABLE employees_o2m (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(15) NOT NULL,
    surname varchar(25) NOT NULL,
    salary int,
    department_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES departments_o2m(id)
);