# 🏥 Sistema de Gestión Hospitalaria

Sistema de gestión hospitalaria desarrollado en **Java**, utilizando arquitectura **MVC**, **JDBC** y **MySQL**. Permite administrar pacientes, médicos, camas y sus respectivas reservas y asignaciones mediante una interfaz por consola.

## 🛠️ Tecnologías utilizadas

- Java
- MySQL
- JDBC
- Maven
- Lombok
- Apache NetBeans

## ⚙️ Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/mwojtasikINSPT/gestion-hospital-sql.git
```

Abrir el proyecto en NetBeans o en un IDE compatible con Maven.

### 2. Configurar la base de datos

Iniciar MySQL y ejecutar el script ubicado en:

```text
script.sql
```

Este script crea la base de datos `bbdd`, sus tablas y los datos iniciales de prueba.

### 3. Configurar las credenciales

En la raíz del proyecto encontrarás el archivo:

```text
config.properties.example
```

Creá una copia y renombrala a:

```text
config.properties
```

Editá el archivo con tus credenciales locales de MySQL:

```properties
db.url=jdbc:mysql://localhost:3306/bbdd
db.user=TU_USUARIO
db.password=TU_CONTRASENA
```

> 🔒 **Importante:** `config.properties` está incluido en `.gitignore` para evitar subir credenciales personales al repositorio.

### 4. Ejecutar el proyecto

1. Abrir el proyecto en NetBeans.
2. Esperar a que Maven descargue las dependencias.
3. Verificar que MySQL esté iniciado.
4. Ejecutar la clase `Main.java`.
5. Utilizar el menú interactivo desde la consola.

## 📋 Funcionalidades

### 👤 Pacientes
- Registrar, buscar, listar y actualizar pacientes.
- Desactivar pacientes mediante baja lógica.

### 🩺 Médicos
- Registrar, buscar, listar y actualizar médicos.
- Gestionar especialidades.
- Desactivar médicos mediante baja lógica.

### 🛏️ Camas
- Registrar, buscar, listar y actualizar camas.
- Consultar disponibilidad.
- Desactivar camas libres.

### 📅 Reservas
- Asignar camas disponibles a pacientes.
- Consultar reservas activas e históricas.
- Cancelar reservas y liberar camas.

### 📋 Asignaciones médicas
- Asignar médicos a pacientes.
- Cambiar el médico asignado.
- Consultar asignaciones activas e históricas.
- Cancelar asignaciones.

## 🔎 Consultas disponibles

- Consultar un paciente y su cama asignada.
- Listar los pacientes atendidos por un médico.
- Consultar el médico asignado a un paciente.

## 🏗️ Arquitectura del proyecto

El sistema utiliza el patrón **MVC**, separando las responsabilidades en diferentes capas:

```text
src/main/java/prog2/parcial/
├── config/
├── controllers/
├── daos/
├── dtos/
├── exceptions/
├── models/
├── services/
├── utils/
├── views/
└── Main.java
```

- **Models:** representan las entidades del sistema.
- **DTOs:** transportan datos de consultas.
- **DAOs:** gestionan la persistencia mediante JDBC.
- **Services:** implementan las reglas de negocio.
- **Controllers:** conectan las vistas con los servicios.
- **Views:** gestionan la interacción por consola.
- **Utils:** contienen funcionalidades reutilizables.

## 🗃️ Base de datos

El sistema utiliza MySQL con las siguientes tablas:

| Tabla | Descripción |
|---|---|
| `pacientes` | Información de pacientes |
| `medicos` | Información de médicos |
| `camas` | Camas y disponibilidad |
| `reservas` | Reservas de camas |
| `asignaciones` | Relaciones entre médicos y pacientes |

Se utiliza **baja lógica** para conservar los registros históricos.

## 📌 Consideraciones

- Se requiere una instalación local de MySQL.
- Las credenciales deben configurarse antes de ejecutar el sistema.
- El proyecto utiliza Maven para administrar dependencias.
- Los datos iniciales son ficticios y están destinados a pruebas.

---

**Proyecto académico — Programación II | Java + MySQL**