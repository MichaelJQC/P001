USE master;
GO

DROP DATABASE IF EXISTS SistemaVentas;
GO

CREATE DATABASE SistemaVentas;
GO

USE SistemaVentas;
GO

SET DATEFORMAT dmy;
GO

CREATE TABLE person
(
    person_id         INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    first_name        VARCHAR(50),
    last_name         VARCHAR(50),
    phone_number      VARCHAR(9),
    date_of_birth     DATE,
    type_document     CHAR(3) CHECK (type_document IN ('DNI', 'CE')),
    number_document   VARCHAR(20) UNIQUE,
    gender            CHAR(1) CHECK (gender IN ('M', 'F')),
    email             VARCHAR(100),
    registration_date DATE,
    active            CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I'))
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
    manufacture_date DATE,
    category_id      INT,
    stock_quantity   INT,
    active           CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);


-- INSERCIÒN DE DATOS
INSERT INTO person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email,
                    registration_date)
VALUES ('Juan', 'Pérez', '123456789', '1990-05-15', 'DNI', '12345678', 'M', 'juan.perez@gmail.com', '2023-01-01'),
       ('Ana', 'Gómez', '987654321', '1988-08-20', 'CE', '87654321', 'F', 'ana.gomez@gmail.com', '2023-01-02'),
       ('Roberto', 'Sánchez', '555123456', '1995-02-10', 'DNI', '98765432', 'M', 'roberto.sanchez@gmail.com',
        '2023-01-03'),
       ('Alicia', 'López', '111222333', '1992-11-30', 'CE', '56789012', 'F', 'alicia.lopez@gmail.com', '2023-01-04'),
       ('Carlos', 'Muñoz', '777888999', '1985-07-05', 'DNI', '34567890', 'M', 'carlos.munoz@gmail.com', '2023-01-05');

INSERT INTO category (category_name)
VALUES ('Electrónicos'),
       ('Ropa'),
       ('Hogar y Jardín'),
       ('Libros'),
       ('Deportes');

INSERT INTO product (product_name, price, manufacture_date, category_id, stock_quantity)
VALUES ('Teléfono inteligente', 599.99, '2022-12-01', 1, 100),
       ('Camiseta', 19.99, '2022-11-15', 2, 200),
       ('Cortadora de césped', 199.99, '2022-10-05', 3, 50),
       ('Libro: Introducción a SQL', 29.99, '2023-01-10', 4, 30),
       ('Zapatillas para correr', 79.99, '2022-09-20', 5, 150);



SELECT * FROM person;
SELECT * FROM category;
SELECT * FROM product;



