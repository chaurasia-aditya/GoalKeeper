# GoalKeeper

This project is a robust To-Do List application built with Spring Boot. It provides RESTful APIs for managing tasks, categories, and subtasks.

## Features

- Create, read, update, and delete tasks
- Organize tasks into categories
- Set task priorities (High, Medium, Low)
- Create subtasks for more detailed task management (1 level of subtasks supported)
- RESTful APIs for easy integration with front-end applications

## Tech Stack

- Java 17
- Spring Boot 
- Spring Data JPA
- H2 Database (can be easily switched to any other database)
- Maven for dependency management

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/chaurasia-aditya/GoalKeeper.git
   ```

2. Navigate to the project directory:
   ```
   cd GoalKeeper
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start running at `http://localhost:8080`.

## API Endpoints

- `GET /api/tasks`: Get all tasks
- `POST /api/tasks`: Create a new task
- `GET /api/tasks/{id}`: Get a specific task
- `PUT /api/tasks/{id}`: Update a task
- `DELETE /api/tasks/{id}`: Delete a task

Similar endpoints exist for categories.

## Postman Collection

A Postman collection has been provided `Goalkeeper.postman_collection.json` for API testing purposes. Import this collection into Postman to quickly test all available endpoints.

## Future Work

- [ ] Create a front-end (Thymeleaf) to consume the API
- [ ] Switch to MySQL database
- [ ] Dockerize for easy execution
- [ ] Implement pagination and search functionality for tasks
- [ ] Add support for task due dates
- [ ] Implement user separation

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.