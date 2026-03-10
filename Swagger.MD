# Adding Swagger (OpenAPI) Support to the Project

This guide outlines the steps to integrate Swagger (OpenAPI) documentation into your Spring Boot project using `springdoc-openapi`.

## 1. Choose a Swagger Integration Library
- Use [`springdoc-openapi`](https://springdoc.org/) for seamless integration with Spring Boot.

## 2. Add Dependency
- Add the following dependency to your `pom.xml`:
  ```xml
  <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.7.0</version> <!-- Check for the latest version -->
  </dependency>
  ```

## 3. (Optional) Configure Swagger
- You can customize API info in `application.properties`:
  ```properties
  springdoc.api-docs.path=/v3/api-docs
  springdoc.swagger-ui.path=/swagger-ui.html
  springdoc.swagger-ui.title=Customer API Documentation
  springdoc.swagger-ui.version=1.0.0
  ```

## 4. Verify Endpoints
- After starting the application, access:
  - Swagger UI: `http://localhost:8080/swagger-ui.html` or `/swagger-ui/index.html`
  - OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 5. (Optional) Enhance Documentation
- Use annotations in your controllers and DTOs:
  - `@Operation`, `@Parameter`, `@Schema`, etc.

## 6. (Optional) Secure Swagger Endpoints
- Restrict Swagger UI access in production if needed.

---

**Summary Table**

| Step | Action                        | Details/Example                       |
|------|-------------------------------|---------------------------------------|
| 1    | Choose library                | springdoc-openapi                     |
| 2    | Add dependency                | Add to pom.xml                        |
| 3    | Configure (optional)          | application.properties                |
| 4    | Verify endpoints              | /swagger-ui.html, /v3/api-docs        |
| 5    | Customize docs (optional)     | Use Swagger/OpenAPI annotations       |
| 6    | Secure UI (optional)          | Restrict Swagger UI in production     |

> **Note:** No code changes have been made yet. This is a planning guide only.

