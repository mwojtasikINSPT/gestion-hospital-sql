
CREATE DATABASE IF NOT EXISTS bbdd;
USE bbdd;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS asignaciones;
DROP TABLE IF EXISTS camas;
DROP TABLE IF EXISTS pacientes;
DROP TABLE IF EXISTS medicos;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE camas (
    codigo VARCHAR(10) PRIMARY KEY,
    piso INT NOT NULL,
    estado ENUM('LIBRE', 'OCUPADA') NOT NULL DEFAULT 'LIBRE',
    activo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_cama VARCHAR(10) NOT NULL,
    id_paciente INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    cama_activa VARCHAR(10)
        GENERATED ALWAYS AS (
            IF(activo = 1, codigo_cama, NULL)
        ) STORED,
    paciente_activo INT
        GENERATED ALWAYS AS (
            IF(activo = 1, id_paciente, NULL)
        ) STORED,
    UNIQUE KEY uq_reserva_cama (cama_activa),
    UNIQUE KEY uq_reserva_paciente (paciente_activo),
    CONSTRAINT fk_reserva_cama FOREIGN KEY (codigo_cama)
        REFERENCES camas(codigo),
    CONSTRAINT fk_reserva_paciente FOREIGN KEY (id_paciente)
        REFERENCES pacientes(id)
) ENGINE=InnoDB;

CREATE TABLE asignaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_medico INT NOT NULL,
    id_paciente INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    paciente_activo INT
        GENERATED ALWAYS AS (
            IF(activo = 1, id_paciente, NULL)
        ) STORED,
    UNIQUE KEY uq_asignacion_paciente (paciente_activo),
    CONSTRAINT fk_asignacion_medico FOREIGN KEY (id_medico)
        REFERENCES medicos(id),
    CONSTRAINT fk_asignacion_paciente FOREIGN KEY (id_paciente)
        REFERENCES pacientes(id)
) ENGINE=InnoDB;

INSERT INTO pacientes (dni, nombre, apellido) VALUES
('30000001', 'Santiago', 'Moreno Charpentier'),
('30000002', 'Gonzalo', 'Moreno Charpentier'),
('30000003', 'Sebastian', 'Seoane'),
('30000004', 'Diego', 'Lichtenstein'),
('30000005', 'Alejandro', 'Sergi'),
('30000006', 'Juliana', 'Gattas'),
('30000007', 'Mariana', 'Esposito'),
('30000008', 'Martina', 'Stoessel'),
('30000009', 'Adrian', 'Dargelos'),
('30000010', 'Diego', 'Tuñon'),
('30000011', 'Diego', 'Rodriguez'),
('30000012', 'Mariano', 'Roger'),
('30000013', 'Emilia', 'Mernes'),
('30000014', 'Maria', 'Becerra'),
('30000015', 'Nicki', 'Nicole'),
('30000016', 'Paulo', 'Londra'),
('30000017', 'Milo', 'J'),
('30000018', 'Tiago', 'PZK'),
('30000019', 'Valentin', 'Oliva'),
('30000020', 'Federico', 'Lauria');

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

INSERT INTO camas (codigo, piso) VALUES
('C001', 1), ('C002', 1), ('C003', 1),
('C004', 1), ('C005', 1), ('C006', 1),
('C007', 1), ('C008', 1), ('C009', 1),
('C010', 1),
('C011', 2), ('C012', 2), ('C013', 2),
('C014', 2), ('C015', 2), ('C016', 2),
('C017', 2), ('C018', 2), ('C019', 2),
('C020', 2),
('C021', 3), ('C022', 3), ('C023', 3),
('C024', 3), ('C025', 3), ('C026', 3),
('C027', 3), ('C028', 3), ('C029', 3),
('C030', 3);

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
    SELECT codigo_cama
    FROM reservas
    WHERE activo = TRUE
);

SELECT 'Pacientes' AS entidad, COUNT(*) AS total FROM pacientes
UNION ALL
SELECT 'Medicos', COUNT(*) FROM medicos
UNION ALL
SELECT 'Camas', COUNT(*) FROM camas
UNION ALL
SELECT 'Reservas', COUNT(*) FROM reservas
UNION ALL
SELECT 'Asignaciones', COUNT(*) FROM asignaciones;
