# CRUD Microservice Project

This is a basic CRUD (Create, Read, Update, Delete) microservice project designed for learning purposes. The project demonstrates how to create and manage microservices with Spring Boot, and integrates with a local MySQL database.

## Project Overview

This project includes several microservices, each responsible for a specific domain. The microservices are connected via an API Gateway and utilize Eureka for service discovery.

### Microservices

- **ApiGateway**: Manages requests and routes them to appropriate microservices.
- **Posts**: Handles operations related to posts.
- **Users**: Manages user-related operations.
- **Comments**: Manages comments on posts.
- **Category**: Manages categories for posts.

### Technologies Used

- **Spring Boot**: Framework for building the microservices.
- **Spring Cloud**: Includes Eureka for service discovery and Config Server for centralized configuration.
- **MySQL**: Local database for persistence.
- **OpenFeign**: For inter-service communication.
- **Spring Security**: For securing endpoints and JWT authentication.

### Database

The microservices connect to a local MySQL database for data storage.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- MySQL 8 or higher

### Setup

1. **Clone the Repository**

    ```bash
    git clone https://github.com/PiyushDixit12/news_api_spring_boot_microservice.git
    ```

2. **Configure the Database**

    Update the `application.properties` or `application.yml` files in each microservice with the MySQL database connection details.

3. **Build and Run the Services**

    Navigate to each microservice directory and run:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

    Start the services in the following order:

    - Eureka Server
    - Config Server
    - ApiGateway
    - Posts
    - Users
    - Comments
    - Category

### API Documentation

Each microservice includes its own API documentation. You can view them by navigating to:

- `http://localhost:8081/swagger-ui/index.html` for users
- `http://localhost:8082/swagger-ui/index.html` for category
- `http://localhost:8083/swagger-ui/index.html` for posts
- `http://localhost:8084/swagger-ui/index.html` for comments


## Contributing

Feel free to submit issues or pull requests. Contributions are welcome!

## Contact

For any questions, please reach out to [dixitp034@gmail.com](mailto:dixitp034@gmail.com).


