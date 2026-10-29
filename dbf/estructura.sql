-- TP Final - Tecnicas Avanzadas de Programacion
-- Base de datos: Veterinaria (Duenios, Mascotas, Turnos)

CREATE DATABASE IF NOT EXISTS veterinaria_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE veterinaria_db;

-- Tabla: duenios
CREATE TABLE duenios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(30),
    email VARCHAR(150),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabla: mascotas (N:1 con duenios)
CREATE TABLE mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(100),
    fecha_nacimiento DATE,
    duenio_id BIGINT NOT NULL,
    CONSTRAINT fk_mascota_duenio
        FOREIGN KEY (duenio_id) REFERENCES duenios(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla: turnos (N:1 con mascotas => 1:N desde mascota)
CREATE TABLE turnos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    mascota_id BIGINT NOT NULL,
    CONSTRAINT fk_turno_mascota
        FOREIGN KEY (mascota_id) REFERENCES mascotas(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Datos de ejemplo
INSERT INTO duenios (nombre, apellido, dni, telefono, email) VALUES
('Juan', 'Perez', '30111222', '1122334455', 'juan.perez@mail.com'),
('Maria', 'Gomez', '28555666', '1166778899', 'maria.gomez@mail.com');

INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, duenio_id) VALUES
('Firulais', 'Perro', 'Labrador', '2020-05-10', 1),
('Michi', 'Gato', 'Siames', '2021-02-15', 2);

INSERT INTO turnos (fecha_hora, motivo, estado, mascota_id) VALUES
('2026-11-05 10:00:00', 'Vacunacion anual', 'PENDIENTE', 1),
('2026-11-06 15:30:00', 'Control general', 'CONFIRMADO', 2);
