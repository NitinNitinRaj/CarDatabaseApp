Car CRUD App
This is a CRUD (create, read, update, delete) app for managing a list of cars. It has a backend built with Spring Boot and a frontend built with React. It also includes Spring Security with JWT (JSON Web Token) authentication and uses Spring HATEOAS to provide links to related resources.

The backend includes a many-to-one relationship between cars and their owners, but this relationship is not currently implemented in the frontend.

Prerequisites
Java 17
Maven
Node.js
Getting Started
Server
To run the server, navigate to the server directory and run the following command:

Copy code
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

Built With
Spring Boot
React
Spring Security
JWT
Spring HATEOAS
