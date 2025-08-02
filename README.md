# Customer Management System

A Spring Boot microservice for managing customer records with full CRUD operations, built with modern Java technologies and comprehensive BDD testing.

## 🚀 Features

- **Complete Customer Management**: Create, read, update, and delete customer records
- **RESTful API**: Clean REST endpoints with proper HTTP status codes
- **Data Validation**: Comprehensive input validation with custom constraints
- **Duplicate Prevention**: Email uniqueness validation with custom constraint
- **H2 Database**: Embedded database for development and testing
- **BDD Testing**: Comprehensive Cucumber test suite with 20+ scenarios
- **Security Hardened**: Actuator endpoints restricted, vulnerability-free dependencies
- **Modern Stack**: Spring Boot 3.3.7, Java 17, Jakarta EE

## 🛡️ Security & Vulnerability Status

✅ **68 vulnerabilities resolved** through recent security upgrade:
- Upgraded from Spring Boot 2.7.6 to 3.3.7
- Updated H2 Database to version 2.2.220
- Updated Spring Kafka to version 3.3.0
- Migrated from javax.* to jakarta.* namespace
- Restricted actuator endpoint exposure

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/amitbhagra/customerv1.git
cd customerv1
```

### 2. Build the Application
```bash
mvn clean compile
```

### 3. Run Tests
```bash
# Run all tests including Cucumber BDD tests
mvn test

# Run only Cucumber tests
mvn test -Dtest=CucumberTestRunner
```

### 4. Start the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Customer Endpoints

#### Create Customer
```http
POST /customers
Content-Type: application/json

{
  "customerName": "John Doe",
  "email": "john.doe@example.com",
  "mobile": "1234567890"
}
```

#### Get All Customers
```http
GET /customers
```

#### Get Customer by ID
```http
GET /customers/{id}
```

#### Update Customer
```http
PUT /customers/{id}
Content-Type: application/json

{
  "customerName": "John Smith",
  "email": "john.smith@example.com",
  "mobile": "0987654321"
}
```

#### Delete Customer
```http
DELETE /customers/{id}
```

### Response Codes
- `200 OK` - Successful retrieval/update
- `201 Created` - Customer created successfully
- `204 No Content` - Successful deletion or empty result
- `400 Bad Request` - Validation errors
- `404 Not Found` - Customer not found
- `500 Internal Server Error` - Server error

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/amit/customer/
│   │   ├── CustomerApplication.java          # Main application class
│   │   ├── config/
│   │   │   └── CustomerConfiguration.java   # Application configuration
│   │   ├── domain/
│   │   │   ├── BaseEntity.java              # Base entity with audit fields
│   │   │   └── Customer.java                # Customer entity
│   │   ├── exceptions/
│   │   │   ├── CustomerExceptionHandler.java # Global exception handler
│   │   │   └── constraints/                 # Custom validation constraints
│   │   ├── mapstruct/mappers/
│   │   │   └── CustomerMapper.java          # Entity-DTO mapping
│   │   ├── repository/
│   │   │   └── CustomerRepository.java      # Data access layer
│   │   ├── service/
│   │   │   ├── CustomerService.java         # Service interface
│   │   │   └── impl/CustomerServiceImpl.java # Service implementation
│   │   └── web/
│   │       ├── controllers/
│   │       │   └── CustomerController.java  # REST controller
│   │       └── model/
│   │           └── CustomerDto.java         # Data transfer object
│   └── resources/
│       └── application.properties           # Application configuration
└── test/
    ├── java/com/amit/customer/
    │   ├── CustomerApplicationTests.java    # Integration tests
    │   └── cucumber/                        # BDD test suite
    └── resources/
        └── features/
            └── customer.feature             # Cucumber scenarios
```

## 🧪 Testing

### Test Coverage
The application includes comprehensive testing with **20 Cucumber BDD scenarios** covering:

- ✅ Customer CRUD operations
- ✅ Input validation and error handling
- ✅ Duplicate email prevention
- ✅ Entity equality and hash operations
- ✅ REST API response structure
- ✅ Database connectivity and transactions

### Running Tests

```bash
# All tests
mvn test

# Cucumber tests only
mvn test -Dtest=CucumberTestRunner

# With coverage report
mvn test jacoco:report
```

### Test Scenarios
- Create customer with valid/invalid data
- Retrieve customers (all, by ID, non-existent)
- Update existing/non-existent customers
- Delete existing/non-existent customers
- Validation error handling
- Duplicate email prevention

## 🔧 Configuration

### Database Configuration
```properties
# H2 Database (development)
spring.datasource.url=jdbc:h2:file:./data/customer
spring.datasource.username=admin
spring.datasource.password=admin
spring.h2.console.enabled=true
```

### Actuator Endpoints (Security Hardened)
```properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=when-authorized
```

### JPA Configuration
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

## 🛠️ Technology Stack

### Core Technologies
- **Spring Boot 3.3.7** - Application framework
- **Java 17** - Programming language
- **Maven** - Build tool
- **H2 Database 2.2.220** - Embedded database

### Key Dependencies
- **Spring Data JPA** - Data persistence
- **Spring Web** - REST API
- **Spring Validation** - Input validation
- **Spring Kafka 3.3.0** - Message streaming
- **MapStruct 1.6.3** - Object mapping
- **Lombok** - Code generation

### Testing Framework
- **JUnit 5** - Unit testing
- **Cucumber 7.20.1** - BDD testing
- **Spring Boot Test** - Integration testing
- **JaCoCo** - Code coverage

## 🔍 Monitoring & Management

### H2 Console
Access the H2 database console at: `http://localhost:8080/h2-ui`
- **JDBC URL**: `jdbc:h2:file:./data/customer`
- **Username**: `admin`
- **Password**: `admin`

### Actuator Endpoints
- **Health**: `http://localhost:8080/actuator/health`
- **Info**: `http://localhost:8080/actuator/info`
- **Metrics**: `http://localhost:8080/actuator/metrics`

## 🚀 Deployment

### Building for Production
```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/customer-0.0.1-SNAPSHOT.jar
```

### Docker Support
```dockerfile
FROM openjdk:17-jre-slim
COPY target/customer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔄 Recent Updates

### Version 3.3.7 (Latest)
- ✅ **Security**: Resolved 68 vulnerabilities
- ✅ **Framework**: Upgraded to Spring Boot 3.3.7
- ✅ **Java**: Updated to Java 17
- ✅ **Dependencies**: Updated H2, Spring Kafka, MapStruct, Cucumber
- ✅ **Migration**: javax.* → jakarta.* namespace migration
- ✅ **Testing**: All 20 Cucumber tests passing

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow existing code style and patterns
- Add tests for new features
- Ensure all Cucumber tests pass
- Update documentation as needed

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or support, please contact:
- **Author**: Amit Bhagra
- **GitHub**: [@amitbhagra](https://github.com/amitbhagra)
- **Repository**: [customerv1](https://github.com/amitbhagra/customerv1)

## 🔗 Related Links

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Cucumber Documentation](https://cucumber.io/docs)
- [H2 Database Documentation](http://www.h2database.com/html/main.html)
- [MapStruct Documentation](https://mapstruct.org/)
