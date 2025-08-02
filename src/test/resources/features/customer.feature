Feature: Customer Management
  As a system administrator
  I want to manage customer records through REST API
  So that I can maintain customer information with full CRUD operations

  Background:
    Given the customer management system is running
    And the database is clean

  Scenario: Create a new customer successfully
    When I create a customer with the following details:
      | customerName | John Doe              |
      | email        | john.doe@example.com  |
      | mobile       | 1234567890            |
    Then the customer should be created successfully
    And the response status should be 201
    And the customer should have an ID
    And the customer name should be "John Doe"
    And the customer email should be "john.doe@example.com"
    And the customer mobile should be "1234567890"

  Scenario: Retrieve all customers
    Given the following customers exist:
      | customerName | email                 | mobile     |
      | Alice Smith  | alice@example.com     | 9876543210 |
      | Bob Johnson  | bob@example.com       | 5555555555 |
    When I retrieve all customers
    Then the response status should be 200
    And I should get 2 customers
    And the customers should contain "Alice Smith"
    And the customers should contain "Bob Johnson"

  Scenario: Retrieve customer by ID
    Given a customer exists with name "Jane Wilson" and email "jane@example.com"
    When I retrieve the customer by ID
    Then the response status should be 200
    And the customer name should be "Jane Wilson"
    And the customer email should be "jane@example.com"

  Scenario: Retrieve non-existent customer by ID
    When I retrieve customer with ID 999
    Then the response status should be 404

  Scenario: Update existing customer
    Given a customer exists with name "Mike Brown" and email "mike@example.com"
    When I update the customer with the following details:
      | customerName | Michael Brown         |
      | email        | michael@example.com   |
      | mobile       | 1111111111            |
    Then the response status should be 200
    And the customer name should be "Michael Brown"
    And the customer email should be "michael@example.com"
    And the customer mobile should be "1111111111"

  Scenario: Update non-existent customer
    When I update customer with ID 999 with name "Non Existent"
    Then the response status should be 404

  Scenario: Delete existing customer
    Given a customer exists with name "Sarah Davis" and email "sarah@example.com"
    When I delete the customer
    Then the response status should be 204
    And the customer should not exist in the system

  Scenario: Delete non-existent customer
    When I delete customer with ID 999
    Then the response status should be 500

  Scenario: Get all customers when none exist
    When I retrieve all customers
    Then the response status should be 204

  Scenario Outline: Create customer with validation errors
    When I create a customer with invalid details:
      | customerName | <customerName> |
      | email        | <email>        |
      | mobile       | <mobile>       |
    Then the response status should be 400
    And the error message should contain "<expectedError>"

    Examples:
      | customerName | email              | mobile     | expectedError                    |
      |              | valid@example.com  | 1234567890 | Customer Name must not be blank  |
      | Valid Name   |                    | 1234567890 | email must not be blank          |
      | Valid Name   | invalid-email      | 1234567890 | email should be well formed      |
      | Valid Name   | valid@example.com  | 123        | Mobile number should be a 10 digit number |
      | Valid Name   | valid@example.com  | abc1234567 | Mobile number should be a 10 digit number |

  Scenario: Create customer with duplicate email
    Given a customer exists with email "duplicate@example.com"
    When I create a customer with email "duplicate@example.com"
    Then the response status should be 400
    And the error message should contain "Duplicate email"

  Scenario: Create customer successfully when database is available
    When I create a customer with valid details
    Then the response status should be 201

  Scenario: Test BaseEntity methods through Customer operations
    Given a customer exists with name "Test Customer" and email "test@example.com"
    When I retrieve the customer by ID
    Then the customer should have an ID
    And the customer should have version information
    And the customer should have creation timestamp
    And the customer should have update timestamp

  Scenario: Test Customer entity equality and hash operations
    Given the following customers exist:
      | customerName | email                 | mobile     |
      | John Doe     | john@example.com      | 1234567890 |
      | Jane Smith   | jane@example.com      | 9876543210 |
    When I retrieve all customers
    Then the customers should be distinct entities
    And each customer should have proper hash codes

  Scenario: Test validation error response structure
    When I create a customer with multiple validation errors:
      | customerName |  |
      | email        | invalid-email |
      | mobile       | 123 |
    Then the response status should be 400
    And the response should contain validation error details
    And the error response should have proper structure

  Scenario: Test Customer toString representation
    Given a customer exists with name "ToString Test" and email "tostring@example.com"
    When I retrieve the customer by ID
    Then the customer should have string representation
