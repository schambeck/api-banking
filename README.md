# API Banking
[![build](https://github.com/schambeck/outbox-pattern/actions/workflows/maven.yml/badge.svg)](https://github.com/schambeck/api-banking/actions/workflows/maven.yml)

> Banking Service application.

<img src="app.png" alt="API Banking">

## Tech Stack

- Java 17
- Spring Boot
- Spring Security: OAuth2, SSO, Keycloak
- PostgreSQL, Flyway
- Swagger
- JUnit, Mockito, Testcontainers, JaCoCo

## Running project

### Start infra (PostgreSQL)
To start the PostgreSQL database, follow these steps:
```bash
$ make compose-up
```

### 🚀 Build artifact
To build the application, follow these steps:
```bash
$ make dist
```

### ☕ Run backend
To run the application, follow these steps:
```bash
$ make run
```
