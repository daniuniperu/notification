# Student REST API — Spring Boot + MongoDB

A RESTful CRUD API built with Spring Boot and MongoDB Atlas. Manages student records with automatic age calculation from date of birth.

## Tech Stack

- **Java 17**
- **Spring Boot 4.1.0**
- **Spring Data MongoDB**
- **MongoDB Atlas** (cloud database)
- **Lombok** (boilerplate code generation)
- **Jackson** (JSON serialization)

## Project Structure

```
src/main/java/com/backend/notifications/
├── controller/
│   └── StudentController.java   # REST endpoints
├── service/
│   └── StudentService.java      # Business logic
├── repository/
│   └── StudentRepository.java   # MongoDB data access
├── model/
│   └── Student.java             # Student entity
└── NotificationsApplication.java
```

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MongoDB Atlas account (or local MongoDB instance)

### Setup

1. Clone the repository:
```bash
git clone https://github.com/daniuniperu/notification.git
cd notification
```

2. Copy the example properties file and add your MongoDB credentials:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Edit `application.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://USERNAME:PASSWORD@cluster.mongodb.net/DATABASE_NAME?appName=AppName
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.

## API Endpoints

| Method | URL | Description | Body |
|--------|-----|-------------|------|
| `POST` | `/api/add` | Create a student | JSON |
| `GET` | `/api/findAllStudents` | Get all students | — |
| `PUT` | `/api/students/{id}` | Update a student by ID | JSON |
| `DELETE` | `/api/delete/{id}` | Delete a student by ID | — |

### Request Body Example

```json
{
  "name": "Daniel",
  "dob": "2000-05-15"
}
```

### Response Example

```json
{
  "saveStudentResponse": {
    "name": "Daniel",
    "dob": "2000-05-15",
    "age": 26,
    "id": "64abc123def456789"
  }
}
```

## Key Design Decisions

- **`@Transient` on `age`** — Age is never stored in the database. It is dynamically calculated from `dob` using `Period.between()` every time it is accessed, keeping the data always accurate.
- **Lombok `@Data` + `@AllArgsConstructor`** — Eliminates boilerplate getters, setters, and constructors.
- **Constructor injection with `@Autowired`** — Preferred over field injection for immutability and testability.
- **`application.properties` excluded from Git** — Credentials are never committed. Use `application.properties.example` as a template.

## Architecture

```
HTTP Request → Controller → Service → Repository → MongoDB Atlas
```

Each layer has a single responsibility:
- **Controller** — Receives HTTP requests, returns responses
- **Service** — Contains business logic and validation
- **Repository** — Handles all database operations
