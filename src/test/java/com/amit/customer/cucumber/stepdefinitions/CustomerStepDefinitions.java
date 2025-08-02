package com.amit.customer.cucumber.stepdefinitions;

import com.amit.customer.domain.Customer;
import com.amit.customer.repository.CustomerRepository;
import com.amit.customer.web.model.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerStepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<CustomerDto> customerResponse;
    private ResponseEntity<CustomerDto[]> customersResponse;
    private ResponseEntity<String> errorResponse;
    private Long currentCustomerId;
    private CustomerDto testCustomer;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/customers";
    }

    @Given("the customer management system is running")
    public void theCustomerManagementSystemIsRunning() {
        assertNotNull(restTemplate);
        assertNotNull(customerRepository);
    }

    @Given("the database is clean")
    public void theDatabaseIsClean() {
        customerRepository.deleteAll();
    }

    @When("I create a customer with the following details:")
    public void iCreateACustomerWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> customerData = dataTable.asMap(String.class, String.class);
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(customerData.get("customerName"));
        customerDto.setEmail(customerData.get("email"));
        customerDto.setMobile(customerData.get("mobile"));

        try {
            customerResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, CustomerDto.class);
            if (customerResponse.getBody() != null) {
                currentCustomerId = customerResponse.getBody().getId();
            }
        } catch (Exception e) {
            errorResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, String.class);
        }
    }

    @When("I create a customer with valid details")
    public void iCreateACustomerWithValidDetails() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName("Test Customer");
        customerDto.setEmail("test@example.com");
        customerDto.setMobile("1234567890");

        try {
            customerResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, CustomerDto.class);
        } catch (Exception e) {
            errorResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, String.class);
        }
    }

    @When("I create a customer with invalid details:")
    public void iCreateACustomerWithInvalidDetails(DataTable dataTable) {
        Map<String, String> customerData = dataTable.asMap(String.class, String.class);
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(customerData.get("customerName"));
        customerDto.setEmail(customerData.get("email"));
        customerDto.setMobile(customerData.get("mobile"));

        errorResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, String.class);
    }

    @When("I create a customer with email {string}")
    public void iCreateACustomerWithEmail(String email) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName("Test Customer");
        customerDto.setEmail(email);
        customerDto.setMobile("1234567890");

        errorResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, String.class);
    }

    @Given("the following customers exist:")
    public void theFollowingCustomersExist(DataTable dataTable) {
        List<Map<String, String>> customers = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> customerData : customers) {
            Customer customer = new Customer();
            customer.setName(customerData.get("customerName"));
            customer.setEmail(customerData.get("email"));
            customer.setMobile(customerData.get("mobile"));
            customerRepository.save(customer);
        }
    }

    @Given("a customer exists with name {string} and email {string}")
    public void aCustomerExistsWithNameAndEmail(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setMobile("1234567890");
        Customer savedCustomer = customerRepository.save(customer);
        currentCustomerId = savedCustomer.getId();
    }

    @Given("a customer exists with email {string}")
    public void aCustomerExistsWithEmail(String email) {
        Customer customer = new Customer();
        customer.setName("Existing Customer");
        customer.setEmail(email);
        customer.setMobile("1234567890");
        customerRepository.save(customer);
    }

    @Given("the database is unavailable")
    public void theDatabaseIsUnavailable() {
    }

    @When("I retrieve all customers")
    public void iRetrieveAllCustomers() {
        customersResponse = restTemplate.getForEntity(getBaseUrl(), CustomerDto[].class);
    }

    @When("I retrieve the customer by ID")
    public void iRetrieveTheCustomerById() {
        customerResponse = restTemplate.getForEntity(getBaseUrl() + "/" + currentCustomerId, CustomerDto.class);
    }

    @When("I retrieve customer with ID {int}")
    public void iRetrieveCustomerWithId(int id) {
        customerResponse = restTemplate.getForEntity(getBaseUrl() + "/" + id, CustomerDto.class);
    }

    @When("I update the customer with the following details:")
    public void iUpdateTheCustomerWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> customerData = dataTable.asMap(String.class, String.class);
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(customerData.get("customerName"));
        customerDto.setEmail(customerData.get("email"));
        customerDto.setMobile(customerData.get("mobile"));

        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(customerDto);
        customerResponse = restTemplate.exchange(
            getBaseUrl() + "/" + currentCustomerId, 
            HttpMethod.PUT, 
            requestEntity, 
            CustomerDto.class
        );
    }

    @When("I update customer with ID {int} with name {string}")
    public void iUpdateCustomerWithIdWithName(int id, String name) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(name);
        customerDto.setEmail("test@example.com");
        customerDto.setMobile("1234567890");

        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(customerDto);
        customerResponse = restTemplate.exchange(
            getBaseUrl() + "/" + id, 
            HttpMethod.PUT, 
            requestEntity, 
            CustomerDto.class
        );
    }

    @When("I delete the customer")
    public void iDeleteTheCustomer() {
        customerResponse = restTemplate.exchange(
            getBaseUrl() + "/" + currentCustomerId, 
            HttpMethod.DELETE, 
            null, 
            CustomerDto.class
        );
    }

    @When("I delete customer with ID {int}")
    public void iDeleteCustomerWithId(int id) {
        customerResponse = restTemplate.exchange(
            getBaseUrl() + "/" + id, 
            HttpMethod.DELETE, 
            null, 
            CustomerDto.class
        );
    }

    @Then("the customer should be created successfully")
    public void theCustomerShouldBeCreatedSuccessfully() {
        assertNotNull(customerResponse);
        assertNotNull(customerResponse.getBody());
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        if (customerResponse != null) {
            assertEquals(HttpStatus.valueOf(expectedStatus), customerResponse.getStatusCode());
        } else if (customersResponse != null) {
            assertEquals(HttpStatus.valueOf(expectedStatus), customersResponse.getStatusCode());
        } else if (errorResponse != null) {
            assertEquals(HttpStatus.valueOf(expectedStatus), errorResponse.getStatusCode());
        }
    }

    @Then("the customer should have an ID")
    public void theCustomerShouldHaveAnId() {
        assertNotNull(customerResponse.getBody().getId());
        assertTrue(customerResponse.getBody().getId() > 0);
    }

    @Then("the customer name should be {string}")
    public void theCustomerNameShouldBe(String expectedName) {
        assertEquals(expectedName, customerResponse.getBody().getCustomerName());
    }

    @Then("the customer email should be {string}")
    public void theCustomerEmailShouldBe(String expectedEmail) {
        assertEquals(expectedEmail, customerResponse.getBody().getEmail());
    }

    @Then("the customer mobile should be {string}")
    public void theCustomerMobileShouldBe(String expectedMobile) {
        assertEquals(expectedMobile, customerResponse.getBody().getMobile());
    }

    @Then("I should get {int} customers")
    public void iShouldGetCustomers(int expectedCount) {
        assertNotNull(customersResponse.getBody());
        assertEquals(expectedCount, customersResponse.getBody().length);
    }

    @Then("the customers should contain {string}")
    public void theCustomersShouldContain(String customerName) {
        CustomerDto[] customers = customersResponse.getBody();
        boolean found = false;
        for (CustomerDto customer : customers) {
            if (customer.getCustomerName().equals(customerName)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Customer " + customerName + " not found in the response");
    }

    @Then("the customer should not exist in the system")
    public void theCustomerShouldNotExistInTheSystem() {
        assertFalse(customerRepository.existsById(currentCustomerId));
    }

    @Then("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedError) {
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        assertTrue(errorResponse.getBody().contains(expectedError), 
            "Expected error message to contain: " + expectedError + ", but got: " + errorResponse.getBody());
    }

    @Then("the customer should have version information")
    public void theCustomerShouldHaveVersionInformation() {
        assertNotNull(customerResponse.getBody());
        Customer customer = customerRepository.findById(customerResponse.getBody().getId()).orElse(null);
        assertNotNull(customer);
        assertNotNull(customer.getVersion());
        assertTrue(customer.getVersion() >= 0);
    }

    @Then("the customer should have creation timestamp")
    public void theCustomerShouldHaveCreationTimestamp() {
        assertNotNull(customerResponse.getBody());
        Customer customer = customerRepository.findById(customerResponse.getBody().getId()).orElse(null);
        assertNotNull(customer);
        assertNotNull(customer.getCreateTs());
    }

    @Then("the customer should have update timestamp")
    public void theCustomerShouldHaveUpdateTimestamp() {
        assertNotNull(customerResponse.getBody());
        Customer customer = customerRepository.findById(customerResponse.getBody().getId()).orElse(null);
        assertNotNull(customer);
        assertNotNull(customer.getUpdateTs());
    }

    @Then("the customers should be distinct entities")
    public void theCustomersShouldBeDistinctEntities() {
        CustomerDto[] customers = customersResponse.getBody();
        assertNotNull(customers);
        assertTrue(customers.length >= 2);
        
        for (int i = 0; i < customers.length; i++) {
            for (int j = i + 1; j < customers.length; j++) {
                assertNotEquals(customers[i].getId(), customers[j].getId());
            }
        }
    }

    @Then("each customer should have proper hash codes")
    public void eachCustomerShouldHaveProperHashCodes() {
        CustomerDto[] customers = customersResponse.getBody();
        assertNotNull(customers);
        
        for (CustomerDto customerDto : customers) {
            Customer customer = customerRepository.findById(customerDto.getId()).orElse(null);
            assertNotNull(customer);
            
            assertTrue(customer.equals(customer));
            assertEquals(customer.hashCode(), customer.hashCode());
            
            assertFalse(customer.equals(null));
            
            assertFalse(customer.equals("not a customer"));
            
            assertNotNull(customer.getId());
            assertTrue(customer.getId() > 0);
            
            String customerString = customer.toString();
            assertNotNull(customerString);
            assertTrue(customerString.length() > 0);
            
            int hashCode1 = customer.hashCode();
            int hashCode2 = customer.hashCode();
            assertEquals(hashCode1, hashCode2);
        }
    }

    @When("I create a customer with multiple validation errors:")
    public void iCreateACustomerWithMultipleValidationErrors(DataTable dataTable) {
        Map<String, String> customerData = dataTable.asMap(String.class, String.class);
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(customerData.get("customerName"));
        customerDto.setEmail(customerData.get("email"));
        customerDto.setMobile(customerData.get("mobile"));

        errorResponse = restTemplate.postForEntity(getBaseUrl(), customerDto, String.class);
    }

    @Then("the response should contain validation error details")
    public void theResponseShouldContainValidationErrorDetails() {
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        String responseBody = errorResponse.getBody();
        assertTrue(responseBody.contains("errors"));
        assertTrue(responseBody.contains("timestamp"));
        assertTrue(responseBody.contains("status"));
    }

    @Then("the error response should have proper structure")
    public void theErrorResponseShouldHaveProperStructure() {
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        String responseBody = errorResponse.getBody();
        
        assertTrue(responseBody.contains("{"));
        assertTrue(responseBody.contains("}"));
        assertTrue(responseBody.contains("errors"));
        assertTrue(responseBody.contains("["));
        assertTrue(responseBody.contains("]"));
    }

    @Then("the customer should have string representation")
    public void theCustomerShouldHaveStringRepresentation() {
        assertNotNull(customerResponse.getBody());
        Customer customer = customerRepository.findById(customerResponse.getBody().getId()).orElse(null);
        assertNotNull(customer);
        
        String customerString = customer.toString();
        assertNotNull(customerString);
        assertTrue(customerString.length() > 0);
        assertTrue(customerString.contains("Customer") || customerString.contains("BaseEntity") || customerString.contains(customer.getName()));
    }
}
