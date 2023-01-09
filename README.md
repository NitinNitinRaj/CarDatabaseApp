Car CRUD App
This is a CRUD (create, read, update, delete) app for managing a list of cars. It has a backend built with Spring Boot and a frontend built with React, and it uses a MariaDB database to store the data. It also includes Spring Security with JWT (JSON Web Token) authentication and uses Spring HATEOAS to provide links to related resources.

The backend includes a many-to-one relationship between cars and their owners, but this relationship is not currently implemented in the frontend.

Prerequisites
JDK 11 or higher
Maven 3.6 or higher
Node.js 12 or higher
MariaDB 10.5 or higher

Stack
Spring Boot 2.7.6
React 16
MariaDB 10.5
HATEOAS
JPA
Spring Data
Hibernate
Bean Validation

Getting Started
Server
To run the server, navigate to the server directory and run the following command:
mvn spring-boot:run
This will start the server on port 8080.

Client
To run the client, navigate to the client directory and run the following commands:

Copy code
npm install
npm start
This will start the client on port 3000.

Functionality
The app allows you to create, read, update, and delete cars in the list. It also has JWT authentication, so you will need to sign in to be able to access the list of cars.
