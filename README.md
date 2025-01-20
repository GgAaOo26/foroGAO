# Foro GAO - API Rest

## Descripción

**Foro GAO** es una aplicación backend que simula un foro en línea donde los usuarios pueden crear, consultar, actualizar y eliminar tópicos. La API está construida con **Spring Boot** y **JWT** para autenticación, y utiliza **MySQL** como base de datos para la persistencia de los datos. El propósito de este proyecto es proporcionar una interfaz robusta para gestionar foros de discusión en una plataforma de aprendizaje.

---

## Funcionalidades

- **Autenticación JWT**: Proporciona seguridad mediante el uso de **JSON Web Tokens** para autenticar a los usuarios.
- **CRUD de Tópicos**:
  - Crear un nuevo tópico.
  - Obtener todos los tópicos.
  - Obtener un tópico por ID.
  - Actualizar un tópico existente.
  - Eliminar un tópico.
- **Persistencia de datos** en **MySQL**.

---

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security** con **JWT** (JSON Web Token)
- **MySQL** como base de datos
- **Maven** para la gestión de dependencias
- **IntelliJ IDEA** como IDE

---

## Requisitos previos

- **JDK 17** o superior.
- **MySQL** instalado y corriendo.
- **Maven** para la construcción del proyecto.

---

## Instalación

1. Clona este repositorio:

   ```bash
   git clone https://github.com/GgAaOo26/foroGAO.git

2. Configura tu base de datos en MySQL:
Crea una base de datos llamada forohub.

3. Configura el archivo application.properties con tus credenciales de base de datos:

properties
Copiar
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

4. Construye y ejecuta el proyecto:

bash
Copiar
mvn clean install
mvn spring-boot:run

5. La API estará disponible en http://localhost:8081.

# Endpoints

## Autenticación
POST /auth/login - Inicia sesión y obtiene el token JWT.

## Tópicos
POST /topicos - Crear un nuevo tópico.
GET /topicos - Obtener todos los tópicos.
GET /topicos/{id} - Obtener un tópico por ID.
PUT /topicos/{id} - Actualizar un tópico existente.
DELETE /topicos/{id} - Eliminar un tópico.

# Contribución
Las contribuciones son bienvenidas. Si deseas mejorar el proyecto, por favor realiza un fork, crea un pull request y describe los cambios realizados.
