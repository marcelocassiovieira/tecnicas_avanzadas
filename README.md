# TP Final

**Alumno:** Marcelo Vieira

## Descripción del proyecto

Sistema de gestión de turnos para una veterinaria, desarrollado en Java con Spring Boot
siguiendo el patrón MVC (Controller–Service–Repository) y una base de datos MySQL con
tres tablas relacionadas: Dueños, Mascotas y Turnos. Expone una API REST con operaciones
CRUD completas (GET, POST, PUT, DELETE) para cada entidad.

Cumple las tres condiciones de promoción del TP:
1. Utiliza una **API REST**.
2. Utiliza el patrón **MVC** (Model = entidades JPA, Controller = `@RestController`,
   View = la representación JSON generada por Spring; con una capa `Service` intermedia
   para la lógica de negocio).
3. Utiliza **tres tablas relacionadas**: `duenios` → `mascotas` (N:1) → `turnos` (1:N).

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.3.4 (Spring Web, Spring Data JPA, Spring Validation)
- MySQL
- Maven

## Estructura del repositorio

- `src/` → código fuente del proyecto (Maven, Spring Boot)
- `dbf/` → script SQL con la estructura de la base de datos (`estructura.sql`)
- `dist/` → ejecutable `.jar` generado (opcional, con `mvn package`)

## Cómo levantar el proyecto

### 1. Base de datos

**Opción A - MySQL ya instalado:**

```bash
mysql -u root -p < dbf/estructura.sql
```

**Opción B - MySQL con Docker (no requiere instalar nada en el sistema):**

```bash
# Levantar un contenedor MySQL
docker run -d --name vet-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -p 3306:3306 \
  mysql:8.0

# Esperar unos segundos a que arranque, y cargar la estructura
docker exec -i vet-mysql mysql -uroot -proot < dbf/estructura.sql
```

Cualquiera de las dos opciones crea la base `veterinaria_db`, las tablas `duenios`,
`mascotas`, `turnos`, y carga algunos datos de ejemplo.

Para conectarse luego a la base por consola (por ejemplo, con Docker):

```bash
docker exec -it vet-mysql mysql -uroot -proot veterinaria_db
```

Para detener el contenedor (conserva los datos, se puede retomar con `docker start vet-mysql`):

```bash
docker stop vet-mysql
```

Para eliminarlo completamente (se pierden los datos cargados):

```bash
docker rm -f vet-mysql
```

### 2. Backend

1. Configurar usuario/contraseña de MySQL en `src/main/resources/application.properties`
   (si se usó la Opción B de Docker, el usuario es `root` y la contraseña `root`).
2. Ejecutar:

```bash
mvn spring-boot:run
```

3. La API queda disponible en `http://localhost:8080`.

## Endpoints disponibles

### Dueños — `/api/duenios`

| Verbo  | Path              | Descripción              |
|--------|-------------------|--------------------------|
| GET    | `/api/duenios`    | Listar todos los dueños  |
| GET    | `/api/duenios/{id}` | Obtener un dueño por id |
| POST   | `/api/duenios`    | Crear un nuevo dueño     |
| PUT    | `/api/duenios/{id}` | Actualizar un dueño    |
| DELETE | `/api/duenios/{id}` | Eliminar un dueño      |

### Mascotas — `/api/mascotas`

| Verbo  | Path                         | Descripción                              |
|--------|------------------------------|-------------------------------------------|
| GET    | `/api/mascotas`              | Listar todas las mascotas                |
| GET    | `/api/mascotas/{id}`         | Obtener una mascota por id                |
| GET    | `/api/mascotas/duenio/{duenioId}` | Listar mascotas de un dueño (relación N:1) |
| POST   | `/api/mascotas`              | Crear una mascota (requiere `duenio.id` válido) |
| PUT    | `/api/mascotas/{id}`         | Actualizar una mascota                    |
| DELETE | `/api/mascotas/{id}`         | Eliminar una mascota                      |

### Turnos — `/api/turnos`

| Verbo  | Path                            | Descripción                                 |
|--------|----------------------------------|----------------------------------------------|
| GET    | `/api/turnos`                   | Listar todos los turnos                      |
| GET    | `/api/turnos/{id}`               | Obtener un turno por id                      |
| GET    | `/api/turnos/mascota/{mascotaId}` | Listar turnos de una mascota (relación 1:N) |
| POST   | `/api/turnos`                   | Crear un turno (requiere `mascota.id` válido) |
| PUT    | `/api/turnos/{id}`               | Actualizar un turno                          |
| DELETE | `/api/turnos/{id}`               | Eliminar un turno                            |

## Cómo probar

Ejemplos con `curl`:

```bash
# Listar dueños
curl http://localhost:8080/api/duenios

# Crear un dueño
curl -X POST http://localhost:8080/api/duenios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellido":"López","dni":"40999888","telefono":"1155667788","email":"ana@mail.com"}'

# Crear una mascota asociada al dueño 1
curl -X POST http://localhost:8080/api/mascotas \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Rocky","especie":"Perro","raza":"Bulldog","fechaNacimiento":"2022-01-01","duenio":{"id":1}}'

# Crear un turno para la mascota 1
curl -X POST http://localhost:8080/api/turnos \
  -H "Content-Type: application/json" \
  -d '{"fechaHora":"2026-12-01T10:00:00","motivo":"Control","estado":"PENDIENTE","mascota":{"id":1}}'

# Actualizar un turno
curl -X PUT http://localhost:8080/api/turnos/1 \
  -H "Content-Type: application/json" \
  -d '{"fechaHora":"2026-12-01T10:00:00","motivo":"Control","estado":"CONFIRMADO","mascota":{"id":1}}'

# Eliminar un turno
curl -X DELETE http://localhost:8080/api/turnos/1
```

También se puede usar Postman importando los mismos requests.
