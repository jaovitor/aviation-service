# Aviation Service

## Overview
This project is a Spring Boot application that provides services for retrieving airport details using an external aviation API. It includes retry mechanisms, error handling, and metrics collection.

---

## Setup and Run Instructions

### Prerequisites
- Java 17 or higher
- Gradle 7.x or higher

### Steps to Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. Build the project:
   ```bash
   ./gradlew clean build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. URLs:
   ```bash
   http://localhost/swagger-ui/index.html - Swagger UI for API documentation and testing.
   http://localhost/actuator - Spring Boot Actuator endpoints for health and metrics.
   http://localhost/actuator/metrics/airport.get - Metrics for get airport endpoint.
   http://localhost/actuator/health - Health check endpoint.
    ```
   
---

## Instructions for Running Tests

1. Run all tests:
   ```bash
   ./gradlew test
   ```

2. View the test report:
   After running the tests, the report will be available at `build/reports/tests/test/index.html`.

---

## Notes on Assumptions, Architecture Decisions, and Error Handling

### Assumptions
- The external aviation API is reliable and provides valid data for valid airport codes.
- The retry mechanism is sufficient to handle transient errors from the external API.
- Metrics collection is required for monitoring API calls and performance.

### Architecture Decisions
- **Spring Boot**: Chosen for its simplicity and integration with Spring ecosystem features like retry, metrics, and dependency injection.
- **Retry Mechanism**: Implemented using Spring Retry to handle transient errors from the external API.
- **Metrics**: Integrated with Micrometer to collect and expose metrics for monitoring.
- **Error Handling**: Custom exceptions are used to handle specific error scenarios, such as `AviationApiException` for API errors and `GeneralException` for domain-specific errors.

### Error Handling
- Invalid or missing airport codes result in a `404 NOT FOUND` response.
- Transient API errors are retried based on the configuration in `application.properties`.
- All exceptions are logged for debugging and monitoring purposes.

---

## Details on AI-Generated Code
Some parts of the code, including unit tests and boilerplate code, were generated using AI tools. These include:
- Unit tests.
- Mocking and stubbing logic in test cases.
- This README.

All AI-generated code was reviewed and modified to ensure correctness, readability, and alignment with project requirements.