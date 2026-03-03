# Lab 01 — Get User by ID

## Objective

Build a REST API endpoint that returns user information given a user ID,
following Spring Boot best practices.

## Requirements

1. Create a `User` model with fields: `id` (Long), `name` (String), `email` (String)
2. Create a `UserDTO` record or class to return from the API (do not expose the model directly)
3. Create a `UserService` **interface** and a `UserServiceImpl` class that implements it
4. Create a `UserController` with:
    - `@RequestMapping("/users")` on the class
    - `@GetMapping("/{id}")` on the method
5. Handle the "user not found" case with a `@ControllerAdvice` and a custom exception
6. Write at least one controller test using `@WebMvcTest` and `MockMvc`

## Expected structure

src/main/java/com/lab/
├── controller/
│   └── UserController.java
├── service/
│   ├── UserService.java        ← interface
│   └── UserServiceImpl.java    ← implementation with @Service
├── model/
│   └── User.java
├── dto/
│   └── UserDTO.java
└── exception/
├── UserNotFoundException.java
└── GlobalExceptionHandler.java

## Getting Started

1. Open this project in GitHub Codespaces
2. Implement your solution inside `src/main/java/`
3. Write your tests inside `src/test/java/`
4. Run `mvn test` to verify your work locally
5. Open a Pull Request to trigger the grader

## Resources

- [Spring Boot REST Tutorial](https://spring.io/guides/gs/rest-service/)
- [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- [Testing with @WebMvcTest](https://docs.spring.io/spring-boot/docs/current/reference/html/test-auto-configuration.html)
- [ControllerAdvice](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-advice.html)