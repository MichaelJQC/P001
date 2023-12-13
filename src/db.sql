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

CREATE TABLE role
(
    role_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE,
    active CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
);

-- Table: Person
CREATE TABLE person
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
    role_id INT,
    active CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
    FOREIGN KEY (role_id) REFERENCES role (role_id)
);



CREATE TABLE category
(
    category_id   INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    category_name VARCHAR(50),
    active        CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I'))
);


CREATE TABLE brand
(
    brand_id   INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    brand_name VARCHAR(50),
    active        CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I'))
);

CREATE TABLE product
(
    product_id       INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    product_name     VARCHAR(50),
    price            DECIMAL(10, 2),
    code_product    VARCHAR(10) NOT NULL UNIQUE ,
    category_id      INT,
    brand_id INT,
    stock_quantity   INT,
    active           CHAR(1) DEFAULT 'A' CHECK (active IN ('A', 'I')),
    FOREIGN KEY (category_id) REFERENCES category (category_id),
    FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
);

-- Crear la tabla de roles

-- Insertar tres roles
INSERT INTO role (role_name) VALUES ('Administrador');
INSERT INTO role (role_name) VALUES ('Moderador');
INSERT INTO role (role_name) VALUES ('Usuario');


-- Insertar datos en la tabla person
INSERT INTO person (first_name, last_name, phone_number, date_of_birth, type_document, number_document, gender, email, role_id, active)
VALUES
    ('Juan', 'Pérez', '123456789', '1990-05-15', 'DNI', '12345678', 'M', 'juan.perez@email.com', 1, 'A'),
    ('Ana', 'Gómez', '987654321', '1985-08-22', 'CE', '87654321', 'F', 'ana.gomez@email.com', 2, 'A'),
    ('Carlos', 'Martínez', '555123789', '1982-12-10', 'DNI', '98765432', 'M', 'carlos.martinez@email.com', 3, 'A'),
    ('Laura', 'Rodríguez', '333444555', '1995-03-05', 'CE', '76543210', 'F', 'laura.rodriguez@email.com', 1, 'A'),
    ('Pedro', 'García', '777888999', '1988-11-18', 'DNI', '23456789', 'M', 'pedro.garcia@email.com', 2, 'A'),
    ('María', 'López', '111222333', '1992-07-30', 'CE', '54321098', 'F', 'maria.lopez@email.com', 3, 'A'),
    ('Javier', 'Fernández', '999888777', '1980-04-12', 'DNI', '34567890', 'M', 'javier.fernandez@email.com', 1, 'A'),
    ('Sofía', 'Hernández', '666777888', '1987-09-25', 'CE', '67890123', 'F', 'sofia.hernandez@email.com', 2, 'A'),
    ('Eduardo', 'Ramírez', '444555666', '1998-01-08', 'DNI', '45678901', 'M', 'eduardo.ramirez@email.com', 3, 'A'),
    ('Lucía', 'Gutiérrez', '222333444', '1993-06-20', 'CE', '89012345', 'F', 'lucia.gutierrez@email.com', 1, 'A');

INSERT INTO category (category_name)
VALUES ('Electrónicos'),
       ('Ropa'),
       ('Hogar y Jardín'),
       ('Libros'),
       ('Deportes');
-- Agrega 10 marcas de productos a la tabla brand
INSERT INTO brand (brand_name, active)
VALUES
    ('Samsung', 'A'),
    ('Apple', 'A'),
    ('Nike', 'A'),
    ('Adidas', 'A'),
    ('Coca-Cola', 'A'),
    ('Toyota', 'A'),
    ('Sony', 'A'),
    ('Microsoft', 'A'),
    ('Puma', 'A'),
    ('Google', 'A');


-- Agrega 10 productos ficticios a la tabla product con nombres "reales"
INSERT INTO product (product_name, price, code_product, category_id, brand_id, stock_quantity, active)
VALUES
    ('Teléfono inteligente Galaxy S21', 899.99, 'SAMSUNG001', 1, 1, 50, 'A'),
    ('Portátil MacBook Air', 1299.99, 'APPLE001', 1, 2, 30, 'A'),
    ('Zapatillas Air Max 270', 119.99, 'NIKE001', 2, 3, 100, 'A'),
    ('Smart TV Bravia 4K', 799.99, 'SONY001', 1, 4, 20, 'A'),
    ('Coca-Cola Zero 2L', 1.99, 'COCA001', 3, 5, 200, 'A'),
    ('Robot Aspirador Roomba 960', 499.99, 'IROBOT001', 3, 6, 15, 'A'),
    ('Libro "Cien años de soledad"', 19.99, 'BOOK001', 4, 7, 80, 'A'),
    ('Balón de fútbol Tango', 29.99, 'ADIDAS001', 5, 8, 50, 'A'),
    ('Puma Cali Mujer', 69.99, 'PUMA001', 2, 9, 35, 'A'),
    ('Google Nest Hub', 99.99, 'GOOGLE001', 1, 10, 25, 'A');









SELECT * FROM person;
SELECT * FROM category;
SELECT * FROM product;



-- Crea un índice no agrupado en la tabla 'product' que incluye todas las columnas
CREATE NONCLUSTERED INDEX IX_Product_AllColumns ON product (product_id, product_name, price, code_product, category_id, brand_id, stock_quantity, active);




-- Actualización de registros en la tabla 'role'
UPDATE role SET role_name = 'Super Administrador' WHERE role_id = 1;

-- Eliminado lógico en la tabla 'role'
UPDATE role SET active = 'I' WHERE role_id = 2;

-- Listado de registros en la tabla 'role'
SELECT * FROM role;



-- Actualización de registros en la tabla 'role'
UPDATE role SET role_name = 'Super Administrador' WHERE role_id = 1;

-- Eliminado lógico en la tabla 'role'
UPDATE role SET active = 'I' WHERE role_id = 2;

-- Listado de registros en la tabla 'role'
SELECT * FROM role;


-- Consulta con predicado, cláusula WHERE, operadores lógicos, BETWEEN, LIKE e IN
SELECT * FROM person
WHERE gender = 'F' AND date_of_birth BETWEEN '1980-01-01' AND '2000-12-31'
  AND (first_name LIKE 'A%' OR last_name LIKE 'G%')
  AND role_id IN (2, 3);

-- Procedimientos Almacenados con IF, CASE y WHILE:


-- Ejemplo de procedimiento almacenado con IF
CREATE PROCEDURE UpdateProductPrice
    @productID INT,
    @newPrice DECIMAL(10, 2)
AS
BEGIN
    IF @newPrice > 0
        BEGIN
            UPDATE product SET price = @newPrice WHERE product_id = @productID;
        END
END;

-- Ejemplo de procedimiento almacenado con CASE
    CREATE PROCEDURE GetProductStatus
    @productID INT
    AS
    BEGIN
        SELECT
            CASE
                WHEN stock_quantity > 0 THEN 'En Stock'
                WHEN stock_quantity = 0 THEN 'Agotado'
                ELSE 'No Disponible'
                END AS ProductStatus
        FROM product
        WHERE product_id = @productID;
    END;

-- Ejemplo de procedimiento almacenado con WHILE
        CREATE PROCEDURE IncreaseStock
            @productID INT,
            @quantity INT
        AS
        BEGIN
            DECLARE @counter INT = 1;
            WHILE @counter <= @quantity
                BEGIN
                    UPDATE product SET stock_quantity = stock_quantity + 1 WHERE product_id = @productID;
                    SET @counter = @counter + 1;
                END
        END;



-- Ejemplo de MERGE para combinar y actualizar registros en 'product'
            MERGE INTO product AS target
            USING (VALUES (11, 'New Product', 59.99, 'NEW001', 1, 3, 10, 'A'))
                AS source (product_id, product_name, price, code_product, category_id, brand_id, stock_quantity, active)
            ON target.product_id = source.product_id
            WHEN MATCHED THEN
                UPDATE SET
                           target.product_name = source.product_name,
                           target.price = source.price,
                           target.code_product = source.code_product,
                           target.category_id = source.category_id,
                           target.brand_id = source.brand_id,
                           target.stock_quantity = source.stock_quantity,
                           target.active = source.active
            WHEN NOT MATCHED THEN
                INSERT (product_id, product_name, price, code_product, category_id, brand_id, stock_quantity, active)
                VALUES (source.product_id, source.product_name, source.price, source.code_product, source.category_id, source.brand_id, source.stock_quantity, source.active);



SELECT p.*, c.category_name as CATEGORY FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) ORDER BY p.product_id ASC;
UPDATE PRODUCT SET active = 'A' WHERE product_id= 1;
SELECT * FROM BRAND order by brand_id ASC
