USE master;
GO

DROP DATABASE IF EXISTS SistemaVentas;
GO

CREATE DATABASE SistemaVentas
    COLLATE Latin1_General_CI_AS;

GO

USE SistemaVentas;
GO

SET DATEFORMAT dmy;
GO

-- Table: Province
-- Table: Department
CREATE TABLE Department
(
    department_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    department_name VARCHAR(50)
);

-- Table: Province
CREATE TABLE Province
(
    province_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    department_id INT FOREIGN KEY REFERENCES Department(department_id),
    province_name VARCHAR(50)
);

-- Table: District
CREATE TABLE District
(
    district_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    province_id INT FOREIGN KEY REFERENCES Province(province_id),
    district_name VARCHAR(50)
);

-- Table: Person
CREATE TABLE Person
(
    person_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(9),
    date_of_birth DATE,
    type_document CHAR(3) CHECK (type_document IN ('DNI', 'CE')),
    number_document VARCHAR(20) UNIQUE,
    gender CHAR(1) CHECK (gender IN ('M', 'F')),
    email VARCHAR(100),
    active CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
    department_id INT FOREIGN KEY REFERENCES Department(department_id),
    province_id INT FOREIGN KEY REFERENCES Province(province_id),
    district_id INT FOREIGN KEY REFERENCES District(district_id)
);



CREATE TABLE category
(
    category_id   INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    category_name VARCHAR(50),
    active        CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I'))
);

CREATE TABLE product
(
    product_id       INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    product_name     VARCHAR(50),
    price            DECIMAL(10, 2),
    code_product    VARCHAR(10) NOT NULL UNIQUE ,
    category_id      INT,
    stock_quantity   INT,
    active           CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);



INSERT INTO category (category_name)
VALUES ('Electrónicos'),
       ('Ropa'),
       ('Hogar y Jardín'),
       ('Libros'),
       ('Deportes');

INSERT INTO product (product_name, price, category_id, stock_quantity , code_product)
VALUES ('Teléfono inteligente', 599.99, 1, 100 , '8455845478'),
       ('Camiseta', 19.99, 2, 200 , '8455842278'),
       ('Cortadora de césped', 199.99,  3, 50 , '1155845478'),
       ('Libro: Introducción a SQL', 29.99,  4, 30, '3355845478'),
       ('Zapatillas para correr', 79.99,  5, 150 ,'4455845478');


-- Insertar datos en la tabla Department
INSERT INTO Department (department_name) VALUES ('Lima');
INSERT INTO Department (department_name) VALUES ('Arequipa');
INSERT INTO Department (department_name) VALUES ('Cusco');
INSERT INTO Department (department_name) VALUES ('La Libertad');
INSERT INTO Department (department_name) VALUES ('Piura');

-- Insertar datos en la tabla Province
INSERT INTO Province (department_id, province_name) VALUES (1, 'Lima');
INSERT INTO Province (department_id, province_name) VALUES (1, 'Cañete');
INSERT INTO Province (department_id, province_name) VALUES (2, 'Arequipa');
INSERT INTO Province (department_id, province_name) VALUES (2, 'Caylloma');
INSERT INTO Province (department_id, province_name) VALUES (3, 'Cusco');
INSERT INTO Province (department_id, province_name) VALUES (3, 'Quispicanchi');
INSERT INTO Province (department_id, province_name) VALUES (4, 'Trujillo');
INSERT INTO Province (department_id, province_name) VALUES (4, 'Ascope');
INSERT INTO Province (department_id, province_name) VALUES (5, 'Piura');
INSERT INTO Province (department_id, province_name) VALUES (5, 'Sullana');

-- Insertar datos en la tabla District
INSERT INTO District (province_id, district_name) VALUES (1, 'Lima');
INSERT INTO District (province_id, district_name) VALUES (1, 'Barranco');
INSERT INTO District (province_id, district_name) VALUES (1, 'Chorrillos');
INSERT INTO District (province_id, district_name) VALUES (2, 'San Vicente de Cañete');
INSERT INTO District (province_id, district_name) VALUES (2, 'Imperial');
INSERT INTO District (province_id, district_name) VALUES (2, 'San Luis');
INSERT INTO District (province_id, district_name) VALUES (3, 'Miraflores');
INSERT INTO District (province_id, district_name) VALUES (3, 'Chosica');
INSERT INTO District (province_id, district_name) VALUES (3, 'La Molina');
INSERT INTO District (province_id, district_name) VALUES (4, 'Yanahuara');
INSERT INTO District (province_id, district_name) VALUES (4, 'Arequipa');
INSERT INTO District (province_id, district_name) VALUES (4, 'Cayma');

-- Insertar datos en la tabla Person
INSERT INTO Person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, department_id, province_id, district_id)
VALUES ('Ana', 'Gomez', '987654321', '1985-05-15', 'DNI', '87654321', 'F', 'ana.gomez@example.com', 1, 1, 1);

INSERT INTO Person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, department_id, province_id, district_id)
VALUES ('Carlos', 'Martinez', '999888777', '1992-09-20', 'DNI', '76543210', 'M', 'carlos.martinez@example.com', 2, 3, 9);

INSERT INTO Person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, department_id, province_id, district_id)
VALUES ('Maria', 'Lopez', '111222333', '1988-03-08', 'CE', '98765432', 'F', 'maria.lopez@example.com', 3, 5, 11);

INSERT INTO Person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, department_id, province_id, district_id)
VALUES ('Juan', 'Hernandez', '444555666', '1995-07-12', 'DNI', '54321678', 'M', 'juan.hernandez@example.com', 4, 7, 7);

INSERT INTO Person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, department_id, province_id, district_id)
VALUES ('Luisa', 'Torres', '777888999', '1982-11-30', 'CE', '87677321', 'F', 'luisa.torres@example.com', 5, 10, 10);


SELECT * FROM person;
SELECT * FROM category;
SELECT * FROM product;

SELECT p.*, c.category_name as CATEGORY FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) ORDER BY p.product_id ASC


UPDATE PRODUCT SET active = 'A' WHERE product_id= 1;



SELECT
    p.person_id,
    p.first_name,
    p.last_name,
    p.phone_number,
    p.date_of_birth,
    p.type_document,
    p.number_document,
    p.gender,
    p.email,
    d.department_name,
    pr.province_name,
    di.district_name
FROM
    PERSON AS p
        INNER JOIN DEPARTMENT AS d ON p.department_id = d.department_id
        INNER JOIN PROVINCE AS pr ON p.province_id = pr.province_id
        INNER JOIN DISTRICT AS di ON p.district_id = di.district_id
WHERE
        p.active = 'A';


SELECT
    p.person_id,
    p.first_name,
    p.last_name,
    p.phone_number,
    p.date_of_birth,
    p.type_document,
    p.number_document,
    p.gender,
    p.email,
    d.department_name,
    pr.province_name,
    di.district_name
FROM
    PERSON AS p
        INNER JOIN DEPARTMENT AS d ON p.department_id = d.department_id
        INNER JOIN PROVINCE AS pr ON p.province_id = pr.province_id
        INNER JOIN DISTRICT AS di ON p.district_id = di.district_id
WHERE
        p.active = 'I';

SELECT * FROM PROVINCE order by province_id ASC

SELECT * FROM DISTRICT order by district_id ASC

SELECT * FROM DEPARTMENT order by department_id ASC

SELECT P.province_id, P.province_name, D.department_id, D.department_name FROM PROVINCE P  JOIN DEPARTMENT D ON P.department_id = D.department_id ORDER BY P.province_id ASC



SELECT P.province_id, P.province_name, DT.district_id, DT.district_name
FROM PROVINCE P
         JOIN DISTRICT DT ON DT.district_id = DT.district_id
ORDER BY P.province_id ASC;






