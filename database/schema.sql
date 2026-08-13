-- VetZone - Schema de la base de datos
-- Proyecto Final SC-403 - Grupo 1

CREATE DATABASE IF NOT EXISTS vetzonedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vetzonedb;


------------ Kimberline Barquero Sanchez ----------

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    rol_id BIGINT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON UPDATE CASCADE
);


------------ Maria Jose Araya Camacho ----------

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(150),
    direccion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    animal VARCHAR(50) NOT NULL,
    raza VARCHAR(100),
    fecha_nacimiento DATE,
    peso DECIMAL(5,2),
    cliente_id BIGINT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON UPDATE CASCADE
);


------------ Allan Fernandez Cruz ----------

CREATE TABLE IF NOT EXISTS citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    mascota_id BIGINT NOT NULL,
    veterinario_id BIGINT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON UPDATE CASCADE,
    FOREIGN KEY (veterinario_id) REFERENCES usuarios(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS consultas_medicas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diagnostico TEXT,
    tratamiento TEXT,
    observaciones TEXT,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cita_id BIGINT NOT NULL,
    FOREIGN KEY (cita_id) REFERENCES citas(id) ON UPDATE CASCADE
);


------------ Stephanie Chavarria Araya ----------

CREATE TABLE IF NOT EXISTS categorias_productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria_id BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES categorias_productos(id) ON UPDATE CASCADE
);


------ allan fernandez roles ----- 
USE vetzonedb;

INSERT INTO roles (nombre) VALUES
('ADMIN'),
('RECEPCIONISTA'),
('VETERINARIO');

INSERT INTO usuarios
(nombre, apellidos, correo, password, activo, rol_id)
VALUES
(
    'Carlos',
    'Mora',
    'carlos.mora@vetzone.com',
    '$2a$10$Zmz9U.zWU5dQYQjABXknDevVoqelGlpqWJIL/d0ItjZ.5nqhUexeO',
    TRUE,
    3
),
(
    'Andrea',
    'Solano',
    'andrea.solano@vetzone.com',
    '$2a$10$Zmz9U.zWU5dQYQjABXknDevVoqelGlpqWJIL/d0ItjZ.5nqhUexeO',
    TRUE,
    3
),
(
    'Daniel',
    'Vargas',
    'daniel.vargas@vetzone.com',
    '$2a$10$Zmz9U.zWU5dQYQjABXknDevVoqelGlpqWJIL/d0ItjZ.5nqhUexeO',
    TRUE,
    3
);

USE vetzonedb;

INSERT IGNORE INTO categorias_productos (nombre) VALUES
('Alimentos'),
('Medicamentos'),
('Higiene'),
('Accesorios'),
('Juguetes');