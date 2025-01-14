# Insurance System Dashboard API

The Insurance System Dashboard API is a Spring Boot application that provides data management and analysis capabilities for insurance claims.

This project offers a robust backend system for an insurance dashboard, allowing users to add data from Excel files, read and analyze claim information, and manage user identities. It utilizes Spring Boot 3.3.5 and integrates with Redis for caching and PostgreSQL for data persistence.

## Repository Structure

- `src/main/java/api/dashboard/insurance/system/`: Root package for the application
  - `config/`: Configuration classes for Redis and Spring Security
  - `controller/`: REST controllers for handling HTTP requests
  - `exception/`: Custom exception classes
  - `model/`: Data models, entities, and enums
  - `repository/`: JPA repositories
  - `service/`: Business logic services
  - `usecase/`: Use case classes for orchestrating business operations
  - `utils/`: Utility classes
- `src/test/`: Test classes
- `pom.xml`: Maven project configuration file

Key Files:
- `SystemApplication.java`: Main entry point for the application
- `SpringRedisConfig.java`: Redis configuration
- `SpringSecurityConfig.java`: Security configuration
- `AddDataController.java`: Controller for adding data
- `ReadFromExcelController.java`: Controller for reading Excel data
- `JwtUtil.java`: JWT utility for token generation and validation

## Usage Instructions

### Installation

Prerequisites:
- Java 17 or later
- Maven 3.6 or later
- Redis server
- PostgreSQL database

Steps:
1. Clone the repository
2. Navigate to the project root directory
3. Run `mvn clean install` to build the project and download dependencies

### Configuration

1. Configure the database connection in `application.properties`:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. Set up Redis configuration in `application.properties`:
   ```
   spring.redis.host=localhost
   spring.redis.port=6379
   ```

3. Configure JWT secret key in `application.properties`:
   ```
   security.jwt.secret-key=your_secret_key
   ```

### Running the Application

To start the application, run:

```
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### API Endpoints

1. Add Data:
   - POST `/api/v1/add`
   - Request body:
     ```json
     {
       "username": "user123",
       "fileLocation": "/path/to/excel/file.xlsx",
       "sheet": 0
     }
     ```
   - Response:
     ```json
     {
       "accessToken": "jwt_token_here"
     }
     ```

2. Read from Excel:
   - GET `/api/v1/read`
   - Headers:
     - Authorization: Bearer {jwt_token}
   - Query Parameters:
     - month: {month_name}
   - Response:
     ```json
     {
       "totalOverThan100M": 10,
       "totalOverThan50M": 20,
       "totalOverThan25M": 30,
       "totalLessThan25M": 40,
       "totalUnknown": 5
     }
     ```

### Testing & Quality

To run tests:

```
mvn test
```

### Troubleshooting

1. Issue: Unable to connect to Redis
   - Error message: "Connection refused: connect"
   - Solution: 
     1. Ensure Redis server is running
     2. Check Redis connection settings in `application.properties`
     3. Verify firewall settings allow connection to Redis port

2. Issue: JWT token validation fails
   - Error message: "Invalid JWT token"
   - Solution:
     1. Check if the correct secret key is set in `application.properties`
     2. Ensure the token hasn't expired
     3. Verify the token is being sent in the correct format in the Authorization header

### Debugging

To enable debug logging:

1. Add the following to `application.properties`:
   ```
   logging.level.api.dashboard.insurance.system=DEBUG
   ```
2. Restart the application
3. Check the console or log files for detailed debug information

Log files are typically located in the `logs/` directory in the project root.

## Data Flow

The Insurance System Dashboard API processes data through the following flow:

1. Client sends a request to add data (POST `/api/v1/add`)
2. `AddDataController` receives the request and passes it to `AddDataUseCase`
3. `AddDataService` reads the Excel file, categorizes data, and stores it in Redis cache
4. A JWT token is generated and returned to the client

For reading data:

1. Client sends a request to read data (GET `/api/v1/read`) with a JWT token
2. `ReadFromExcelController` receives the request and passes it to `ReadFromExcelUseCase`
3. `JwtUtil` validates the token and extracts the username
4. `ReadFromExcelService` retrieves data from Redis cache based on the month parameter
5. Aggregated data is returned to the client

```
Client -> Controller -> UseCase -> Service -> Redis/Database
   ^                                               |
   |                                               |
   +-----------------------------------------------+
```

Note: The system uses Redis for caching frequently accessed data to improve performance.

## Infrastructure

The application uses the following key infrastructure components:

Redis:
- Type: Cache
- Purpose: Stores categorized insurance claim data for quick retrieval

PostgreSQL:
- Type: Database
- Purpose: Persists user identity information and other long-term data

Spring Security:
- Type: Security framework
- Purpose: Configures CORS, CSRF protection, and session management

JWT:
- Type: Authentication mechanism
- Purpose: Generates and validates tokens for secure API access