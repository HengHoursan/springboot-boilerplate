# Spring Boot & Vue/React/Next.js Boilerplate

Welcome to the **Spring Boot Boilerplate** repository. This project is designed as a professional, production-ready foundation for full-stack applications, featuring a clean separation between the backend (API) and frontend (UI).

## 📂 Project Structure

This repository is organized into two main modules:

- **[`api/`](./api)**: The backend service built with **Spring Boot 3.4**, Java 21, and PostgreSQL. It includes built-in JWT Authentication, RBAC (Role-Based Access Control), and a clean layered architecture.
- **[`ui/`](./ui)**: A placeholder for your frontend application (Vue.js, React, or Next.js).

---

## 🚀 Quick Start

### 1. Backend (API)
Navigate to the `api` directory and follow the instructions in its [README](./api/README.md).
```bash
cd api
# Update application.properties with your DB credentials
./gradlew bootRun
```

### 2. Frontend (UI)
Initialize your preferred frontend framework in the `ui` directory.
```bash
# Example: Create a new Vite project
cd ui
npm create vite@latest .
```

---

## 🛠️ How to Customize for Your New Project

When you clone this boilerplate for a new project, follow these steps to make it your own:

### 1. Rename the Project
- Update the `rootProject.name` in `api/settings.gradle`.
- Update the `description` and `group` in `api/build.gradle`.

### 2. Update Packages (Optional)
The default package is `com.example.springbootboilerplate`. You can use your IDE's refactoring tools (Shift+F6 in IntelliJ) to rename this to your project's specific domain (e.g., `com.mycompany.myproject`).

### 3. Database & Security
Ensure you change the following in `api/src/main/resources/application.properties`:
- **Database URL/Username/Password**
- **JWT Secret Key** (`application.security.jwt.secret-key`) — *Crucial for security!*

---

## 📄 License
This boilerplate is open-source and available under the [MIT License](./api/LICENSE). (If you haven't added a LICENSE file yet, it's recommended to do so).

---

**Developed with ❤️ by [HengHoursan](https://github.com/HengHoursan)**
