##  **User Service – JWT Authentication**

A stateless authentication service built with Spring Boot 3 and Spring Security 6 for user registration and JWT-based login.

This project is designed as an Auth Provider, responsible only for identity creation, authentication, and token issuance.

## **Features**
____________________________________________________________________________________________________________________________________________________________________________________________________________________________

User registration with BCrypt password hashing

Login endpoint that authenticates users and issues JWT

Stateless authentication (no server-side sessions)

Custom UserDetailsService backed by PostgreSQL

Clean SecurityFilterChain configuration (no deprecated APIs)

Centralized global exception handling

Fully exposed REST APIs

Dockerized application with CI pipeline support

## **-API Endpoints**
____________________________________________________________________________________________________________________________________________________________________________________________________________________________
Create User
POST api/create-users

Login
POST api/auth/login

Returns:

{ "token": "<JWT_TOKEN>" }

## **-Design Notes**
____________________________________________________________________________________________________________________________________________________________________________________________________________________________

New users are assigned the USER role by default

JWT is issued by this service and intended to be validated by downstream services

Authentication is handled by Spring Security, not manual credential checks

The project intentionally avoids overengineering (OAuth, sessions, refresh tokens)

## **-Database (PostgreSQL)**
____________________________________________________________________________________________________________________________________________________________________________________________________________________________

Uses PostgreSQL for persistent storage

**-Docker & CI/CD**

Application is Dockerized using a multi-stage Docker build

GitHub Actions CI pipeline:

Builds the project

Runs Gradle build

Builds Docker image on every push

Ready for deployment to container platforms

**- Tech Stack**
____________________________________________________________________________________________________________________________________________________________________________________________________________________________

Java 17 • Spring Boot 3 • Spring Security 6 • JWT • JPA • Gradle • docker  • CICD

**- Project Structure**
controller | service | dao | security | config | ExceptionUtils

