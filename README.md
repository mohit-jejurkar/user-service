🔐 User Service – JWT Authentication

A stateless authentication service built with Spring Boot 3 and Spring Security 6 for user registration and JWT-based login.

This project is designed as an Auth Provider, responsible only for identity creation, authentication, and token issuance.

🚀 Features

User registration with BCrypt password hashing

Login endpoint that authenticates users and issues JWT

Stateless authentication (no server-side sessions)

Custom UserDetailsService backed by database

Clean SecurityFilterChain configuration (no deprecated APIs)

Centralized global exception handling

🔐 API Endpoints
Create User
POST api/create-users

Login
POST api/auth/login


Returns:

{ "token": "<JWT_TOKEN>" }

🧠 Design Notes

New users are assigned the USER role by default

JWT is issued by this service and intended to be validated by downstream services

Authentication is handled by Spring Security, not manual credential checks

The project intentionally avoids overengineering (OAuth, sessions, refresh tokens)

🛠️ Tech Stack

Java 17 • Spring Boot 3 • Spring Security 6 • JWT • JPA • Gradle • docker  • CICD

📂 Structure
controller | service | dao | security | config | ExceptionUtils

▶️ Run
./gradlew bootRun

🔮 Next Steps

JWT validation endpoint for other services

Refresh token support

Role-based authorization
