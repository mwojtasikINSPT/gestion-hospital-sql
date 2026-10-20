CREATE DATABASE IF NOT EXISTS bbdd;
USE bbdd;

CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

CREATE TABLE medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL
);

CREATE TABLE camas (
    codigo VARCHAR(10) PRIMARY KEY,
    piso INT NOT NULL,
    estado ENUM('LIBRE', 'OCUPADA') NOT NULL DEFAULT 'LIBRE'
);

INSERT INTO pacientes (dni, nombre, apellido) VALUES
('30000001', 'Charly', 'Garcia'),
('30000002', 'Luis Alberto', 'Spinetta'),
('30000003', 'Gustavo', 'Cerati'),
('30000004', 'Fito', 'Paez'),
('30000005', 'Andres', 'Calamaro'),
('30000006', 'Leon', 'Gieco'),
('30000007', 'Pappo', 'Napolitano'),
('30000008', 'Fabiana', 'Cantilo'),
('30000009', 'Celeste', 'Carballo'),
('30000010', 'Hilda', 'Lizarazu'),
('30000011', 'Pedro', 'Aznar'),
('30000012', 'David', 'Lebon'),
('30000013', 'Ricardo', 'Mollo'),
('30000014', 'Adrian', 'Dargelos'),
('30000015', 'Ciro', 'Martinez'),
('30000016', 'Indio', 'Solari'),
('30000017', 'Skay', 'Beilinson'),
('30000018', 'Miguel', 'Mateos'),
('30000019', 'Juan Carlos', 'Baglietto'),
('30000020', 'Javier', 'Calamaro');

INSERT INTO medicos (dni, nombre, apellido, especialidad) VALUES
('40000001', 'Lionel', 'Messi', 'Cardiologia'),
('40000002', 'Angel', 'Di Maria', 'Neurologia'),
('40000003', 'Emiliano', 'Martinez', 'Traumatologia'),
('40000004', 'Julian', 'Alvarez', 'Pediatria'),
('40000005', 'Lautaro', 'Martinez', 'Clinica Medica'),
('40000006', 'Rodrigo', 'De Paul', 'Dermatologia'),
('40000007', 'Enzo', 'Fernandez', 'Neumonologia'),
('40000008', 'Alexis', 'Mac Allister', 'Gastroenterologia'),
('40000009', 'Nicolas', 'Otamendi', 'Cirugia General'),
('40000010', 'Cristian', 'Romero', 'Urologia');

INSERT INTO camas (codigo, piso, estado) VALUES
('C001', 1, 'LIBRE'),
('C002', 1, 'LIBRE'),
('C003', 1, 'LIBRE'),
('C004', 1, 'LIBRE'),
('C005', 1, 'LIBRE'),
('C006', 1, 'LIBRE'),
('C007', 1, 'LIBRE'),
('C008', 1, 'LIBRE'),
('C009', 1, 'LIBRE'),
('C010', 1, 'LIBRE'),
('C011', 2, 'LIBRE'),
('C012', 2, 'LIBRE'),
('C013', 2, 'LIBRE'),
('C014', 2, 'LIBRE'),
('C015', 2, 'LIBRE'),
('C016', 2, 'LIBRE'),
('C017', 2, 'LIBRE'),
('C018', 2, 'LIBRE'),
('C019', 2, 'LIBRE'),
('C020', 2, 'LIBRE'),
('C021', 3, 'LIBRE'),
('C022', 3, 'LIBRE'),
('C023', 3, 'LIBRE'),
('C024', 3, 'LIBRE'),
('C025', 3, 'LIBRE'),
('C026', 3, 'LIBRE'),
('C027', 3, 'LIBRE'),
('C028', 3, 'LIBRE'),
('C029', 3, 'LIBRE'),
('C030', 3, 'LIBRE');


USE bbdd;

CREATE TABLE reservas (
    codigo_cama VARCHAR(10) PRIMARY KEY,
    id_paciente INT NOT NULL UNIQUE,
    FOREIGN KEY (codigo_cama) REFERENCES camas(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE asignaciones (
    id_medico INT NOT NULL,
    id_paciente INT NOT NULL UNIQUE,
    PRIMARY KEY (id_medico, id_paciente),
    FOREIGN KEY (id_medico) REFERENCES medicos(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

INSERT INTO reservas (codigo_cama, id_paciente) VALUES
('C001', 1),
('C002', 2),
('C003', 3),
('C004', 4),
('C005', 5),
('C011', 6),
('C012', 7),
('C013', 8),
('C021', 9),
('C022', 10);

INSERT INTO asignaciones (id_medico, id_paciente) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10);

UPDATE camas
SET estado = 'OCUPADA'
WHERE codigo IN (
    SELECT codigo_cama FROM reservas
);
