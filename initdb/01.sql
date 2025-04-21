DROP SCHEMA IF EXISTS biblioteca CASCADE;
CREATE SCHEMA biblioteca;
SET search_path TO biblioteca;

CREATE TYPE estado_libro AS ENUM ('activo', 'inactivo');
CREATE TYPE estado_ejemplar AS ENUM ('disponible', 'prestado', 'baja');


-- Create Libro (Book) table
CREATE TABLE Libro (
    libro_id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    estado estado_libro NOT NULL,
    editorial VARCHAR(255) NOT NULL,
    edicion VARCHAR(255) NOT NULL
);

-- Create Ejemplar (Copy) table
CREATE TABLE Ejemplar (
    ejemplar_id SERIAL PRIMARY KEY,
    estado estado_ejemplar NOT NULL,
    libro_id INTEGER NOT NULL REFERENCES Libro(libro_id)
);

-- Create Usuario (User) table
CREATE TABLE Usuario (
    matricula SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    penalizacion DATE DEFAULT '2020-04-01'
);

-- Create Préstamo (Loan) table
CREATE TABLE Prestamo (
    prestamo_id SERIAL PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    devuelto BOOLEAN DEFAULT FALSE,
    matricula INTEGER NOT NULL REFERENCES Usuario(matricula),
    ejemplar_id INTEGER NOT NULL REFERENCES Ejemplar(ejemplar_id)
);