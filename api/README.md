# Spring Boot Boilerplate (RBAC + JWT + PostgreSQL)

A robust, production-ready Spring Boot boilerplate featuring Role-Based Access Control (RBAC), JWT Authentication, and a clean layered architecture. Designed to jumpstart your enterprise applications.

## 🚀 Features

- **Java 21 & Spring Boot 3.4.x**
- **Security**: JWT-based Authentication & Authorization.
- **RBAC**: Fine-grained permissions and roles (User, Role, Permission entities).
- **Database**: PostgreSQL integration with JPA/Hibernate.
- **Exception Handling**: Centralized `GlobalExceptionHandler` for consistent API responses.
- **Audit**: Automatic auditing (`createdAt`, `updatedAt`, `createdBy`, `deletedAt`).
- **Soft Delete**: Built-in support for soft deletes.
- **Documentation**: Swagger/OpenAPI 3 UI included.
- **Validation**: Request validation using Jakarta Validation.
- **DTO Pattern**: Clear separation between Entities and Data Transfer Objects.

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3
- **Build Tool**: Gradle
- **Language**: Java 21
- **Security**: Spring Security + JJWT
- **Database**: PostgreSQL
- **Persistence**: Spring Data JPA
- **API Docs**: SpringDoc OpenAPI

---

## 📋 Prerequisites

- JDK 21 or higher
- PostgreSQL
- IDE (IntelliJ IDEA recommended)

---

## ⚙️ Getting Started

### 1. Database Configuration
Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Security Configuration
Generate a secure Base64 secret key for JWT and update it in `application.properties`:

```properties
application.security.jwt.secret-key=YOUR_BASE64_SECRET_KEY
```

### 3. Build & Run
```bash
./gradlew bootRun
```

---

## 🏗️ Project Structure

- `com.example.springbootboilerplate`
  - `app.config`: Security, JWT, and general application configurations.
  - `app.controller`: REST Controllers.
  - `app.dto`: Request/Response Data Transfer Objects.
  - `app.exception`: Global exception handler and custom exceptions.
  - `app.models`: JPA Entities.
  - `app.repository`: Data access layers.
  - `app.service`: Business logic implementation.
  - `app.utils`: Common utility classes.

---

## 📦 How to Clone for a New Project

1. **Clone the repo**:
   ```bash
   git clone https://github.com/HengHoursan/springboot-boilerplate.git
   ```
2. **Rename the Project**:
   - In `settings.gradle`, change `rootProject.name`.
   - In `build.gradle`, update the `group` and `description`.
3. **Refactor Package** (Optional):
   - Use your IDE to rename the base package `com.example.springbootboilerplate` to your preferred name.

---

## 📄 License
This project is licensed under the MIT License.
