SET search_path TO biblioteca;

-- Insertar libros
INSERT INTO Libro (titulo, autor, isbn, estado, editorial, edicion) VALUES
('Cien años de soledad', 'Gabriel García Márquez', '9788437604947', 'activo', 'Alfaguara', '1ª ed. especial'),
('La sombra del viento', 'Carlos Ruiz Zafón', '9788408043640', 'activo', 'Planeta', 'Edición conmemorativa'),
('Don Quijote de la Mancha', 'Miguel de Cervantes', '9788467031565', 'activo', 'Real Academia Española', 'Edición IV Centenario'),
('La casa de los espíritus', 'Isabel Allende', '9788401337206', 'inactivo', 'Plaza & Janés', 'Edición definitiva');

-- Insertar ejemplares
INSERT INTO Ejemplar (estado, libro_id) VALUES
('disponible', 1),
('prestado', 1),
('disponible', 1),
('baja', 2),
('disponible', 2),
('disponible', 3),
('prestado', 3),
('disponible', 4);

-- Insertar usuarios
INSERT INTO Usuario (nombre, correo, fecha_nacimiento, penalizacion) VALUES
('María González Pérez', 'maria.gonzalez@example.com', '1995-08-15', NULL),
('Javier Martínez López', 'javier.martinez@example.com', '2000-03-22', '2023-12-01'),
('Sofía Rodríguez García', 'sofia.rodriguez@example.com', '1998-11-05', DEFAULT),
('Carlos Sánchez Fernández', 'carlos.sanchez@example.com', '2002-07-30', NULL);

-- Insertar préstamos
INSERT INTO Prestamo (fecha_inicio, fecha_devolucion, devuelto, matricula, ejemplar_id) VALUES
('2025-01-01', '2025-01-15', true, 1, 2),
('2025-04-10', '2025-04-24', false, 2, 3),
('2025-02-01', '2025-02-15', false, 3, 7),
('2025-02-05', '2025-02-19', true, 4, 5);