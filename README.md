# Pre-requisites

- Java 17
- Gradle 8
- MongoDB

# Metro Rail Ticket Booking System

> The Metro Rail Ticket Booking System is a personal project and web application designed to facilitate ticket booking for metro rail services. It provides a platform for users to register, book tickets, view booking history, and manage their account balance.

# Table of Contents

1. Introduction
2. Features
3. Technologies Used
4. Installation
5. Usage
6. API Documentation
7. Security
8. Contributing

# Introduction

> This is a personal project that built while learning Backend in java. This project is trying to mock Metro System and provide REST APIs. Using this APIs one can build a Frontend Application.

# Features

- **User Registration:** Users can create an account by providing their required details.
- **User Authentication:** Secure login system using JWT authentication.
- **Ticket Booking:** Users can book tickets for metro rail journeys.
- **Booking History:** Users can view their past booking history.
- **Account Management:** Users can manage their account details and balance.
- **Role-based Access Control:** Different levels of access for admins and regular users.
- **Database Management:** User data is saved using MongoDB.

# Technologies Used

- **Spring Boot:** Backend framework for developing RESTful APIs.
- **Spring Security:** Provides authentication and authorization functionalities.
- **Spring Data JPA:** For database interaction using Hibernate.
- **JWT (JSON Web Tokens):** Used for secure authentication.
- **Swagger/OpenAPI:** Documentation of RESTful APIs.
- **JUnit 5(Jupitor):** For unit and integration testing.
- **Lombok:** For auto generated getters/setters and constructors.
- **Mockito:** Mocking framework for testing.
- **MongoDB:** Non-Relational database management system.

# Installation/Setup

- **Clone the repository:** git clone <repository-url>
- **Navigate to the project directory:** cd metro_system.
- **Configure Dependencies:** Configure the dependencies.
- **Configure the environment:** Configure the Java and Gradle Version according to prerequisites.
- **Install MongoDB:** Install and configure mongoDB for using mongoRepository to save data on local.
- **Configure the application properties:** Update the database configurations in application.properties.
- **Build the project:** ./gradlew clean or (gradlew.bat clean for Windows)
- **Run the application:** java -jar target/metro_system.jar

# Usage Flow 
> 🔔 **Use Postman to access these apis**


- Access the application at http://localhost:8082 or set a new availabe port.
- Register a new account or log in if you already have an account.
- Navigate through the dashboard to book tickets, view booking history, and manage your account.
- Select Origin Station and Destination station to get the fare and book ticket.
- If balance is sufficient then ticket will be booked else Bad Request response will be sent and amount will be deducted.
- To cancel booking, hit the cancel api endpoint with Booking Id and refund will be done.
- To view booking history, Use booking history endpoint and can filter as well by Booking Status.
- Admin can view bookings of all users, bookings of one user, bookings by filters.
- To manage balance, use balance endpoint.

# API Documentation

> The API documentation is available using Swagger UI.
> Access the API documentation at http://localhost:8082/swagger-ui/index.html

# Security

- User Authentication: Users must register and log in to access the system.
- JWT Token: JSON Web Tokens are used for secure authentication.
- Role-based Access Control: Administrators have access to all features, while regular users have limited access.

# Contributing

Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

- Fork the repository.
- Create a new branch: git checkout -b feature-branch
- Make your changes and commit them: git commit -am 'Add new feature'
- Push to the branch: git push origin feature-branch
- Submit a pull request.

> ## Feel free to customize the README according to your project's specific details and requirements.
