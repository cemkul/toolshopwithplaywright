# Toolshop Automation Framework

## Overview

This project is a hybrid UI + API automation framework built using:

* Java 17
* Playwright
* RestAssured
* JUnit 5
* Allure Reporting
* Maven

# Tech Stack

| Technology  | Purpose              |
| ----------- | -------------------- |
| Java 17     | Programming Language |
| Playwright  | UI Automation        |
| RestAssured | API Automation       |
| JUnit 5     | Test Framework       |
| Allure      | Reporting            |
| Maven       | Build Management     |

---

---

# UI Automation Features

* Page Object Model (POM)
* Reusable BasePage
* Playwright browser management
* Screenshot capture on pass/failure
* Allure step reporting
* Positive and negative test scenarios

---

# API Automation Features

* API client architecture
* Request DTOs
* Response DTOs
* Token reuse mechanism
* Authenticated API requests
* Custom API logging
* Request/response attachments in Allure

---

# Integration Testing

The framework supports API + UI integration scenarios.

Example:

```text
1. Get product from API
2. Search same product in UI
3. Validate product visibility
```

---

# Allure Reporting

The framework generates detailed Allure reports including:

* Test steps
* API request logs
* API response logs
* Screenshots
* Failure stack traces

## Generate Report

```bash
allure serve target/allure-results
```

---

# Running Tests


## Run Tests From CMD / Terminal

Open CMD or terminal inside the project root folder where `pom.xml` exists.

Example:

```bash
cd C:/Users/YourUser/IdeaProjects/toolshopwithplaywright
```

Then run:

```bash
mvn clean test
```

This command will:

* clean the previous build
* compile the project
* run UI, API, and integration tests
* generate Allure result files under `target/allure-results`

---

## Run Specific Test Class

```bash
mvn -Dtest=ProductApiTests test
```

```bash
mvn -Dtest=CheckoutTests test
```

```bash
mvn -Dtest=ProductApiUiIntegrationTests test
```

---

## Run Specific Test Method

```bash
mvn -Dtest=CheckoutTests#customerShouldCompleteCheckoutWithCashOnDelivery test
```

---

## Generate and Open Allure Report

After running tests, execute:

```bash
allure serve target/allure-results
```

This will generate and open the Allure report in the browser.

---

# Manual Test Scenarios

The following table represents the manual test definitions for the automated UI, API, and integration test coverage.

| Test ID | Test Type            | Module             | Test Case                                               | Description                                                                 | Preconditions                                 | Test Steps                                                                                                                                                                   | Expected Result                                                               | Automation Coverage          |
| ------- | -------------------- | ------------------ | ------------------------------------------------------- | --------------------------------------------------------------------------- | --------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------- | ---------------------------- |
| UI-001  | Positive UI          | Home               | Verify home page loads successfully                     | Validate that the Toolshop home page is opened successfully.                | Application is accessible.                    | 1. Open application URL. 2. Verify page title. 3. Verify search input is visible.                                                                                            | Home page should load and search input should be visible.                     | HomeTests                    |
| UI-002  | Positive UI          | Home/Search        | Verify user can search product                          | Validate that user can search for a product from the home page.             | Application is accessible.                    | 1. Open home page. 2. Enter product keyword. 3. Click search.                                                                                                                | Product results should be displayed.                                          | HomeTests                    |
| UI-003  | Positive UI          | Product            | Verify user can open product details                    | Validate that user can open a product detail page.                          | Products are available.                       | 1. Open home page. 2. Click first product. 3. Verify product detail page.                                                                                                    | Product detail page should open and product name should be visible.           | ProductTests                 |
| UI-004  | Positive UI          | Product            | Verify user can filter products by category             | Validate product filtering by category.                                     | Categories are available.                     | 1. Open home page. 2. Select first category filter.                                                                                                                          | Filtered product list should be displayed.                                    | ProductTests                 |
| UI-005  | Positive UI          | Product            | Verify user can filter products by brand                | Validate product filtering by brand.                                        | Brands are available.                         | 1. Open home page. 2. Select first brand filter.                                                                                                                             | Filtered product list should be displayed.                                    | ProductTests                 |
| UI-006  | Positive UI          | Product            | Verify user can sort products                           | Validate product sorting functionality.                                     | Products are available.                       | 1. Open home page. 2. Select sorting option.                                                                                                                                 | Product list should be sorted according to selected option.                   | ProductTests                 |
| UI-007  | Positive UI          | Cart               | Verify user can add product to cart                     | Validate that an available product can be added to cart.                    | Product must be in stock.                     | 1. Open available product. 2. Click add to cart. 3. Open cart.                                                                                                               | Product should be displayed in cart.                                          | CartTests                    |
| UI-008  | Positive UI          | Cart               | Verify user can update product quantity in cart         | Validate that product quantity can be updated from cart page.               | Product exists in cart.                       | 1. Add product to cart. 2. Open cart. 3. Update quantity.                                                                                                                    | Cart should reflect updated quantity.                                         | CartTests                    |
| UI-009  | Positive UI          | Cart               | Verify user can remove product from cart                | Validate that product can be removed from cart.                             | Product exists in cart.                       | 1. Add product to cart. 2. Open cart. 3. Remove product.                                                                                                                     | Product should be removed from cart.                                          | CartTests                    |
| UI-010  | Negative UI          | Authentication     | Verify user cannot login with invalid credentials       | Validate login error handling for invalid credentials.                      | Login page is accessible.                     | 1. Open login page. 2. Enter invalid email/password. 3. Click login.                                                                                                         | Error message should be displayed.                                            | AuthTests / NegativeTests    |
| UI-011  | Positive UI          | Authentication     | Verify user can navigate to register page               | Validate navigation from login page to register page.                       | Login page is accessible.                     | 1. Open login page. 2. Click register link.                                                                                                                                  | Register page should be opened.                                               | AuthTests                    |
| UI-012  | Positive UI          | Checkout           | Verify user can complete checkout successfully          | Validate full checkout flow for an existing customer.                       | User account exists and product is available. | 1. Add product to cart. 2. Proceed to checkout. 3. Login. 4. Proceed to address. 5. Fill billing address. 6. Proceed to payment. 7. Select payment method. 8. Confirm order. | Order should be completed successfully and success message should be visible. | CheckoutTests                |
| UI-013  | Negative UI          | Checkout           | Verify checkout cannot continue without billing address | Validate that checkout payment step is blocked if billing address is empty. | Product exists in cart and user is logged in. | 1. Add product to cart. 2. Login. 3. Proceed to billing step without filling address.                                                                                        | Proceed to payment button should remain disabled.                             | NegativeTests                |
| UI-014  | Negative UI          | Cart/Checkout      | Verify checkout is blocked with empty cart              | Validate that user cannot proceed with checkout when cart is empty.         | Cart is empty.                                | 1. Open cart. 2. Try to proceed to checkout.                                                                                                                                 | Checkout should be blocked or empty cart message should be displayed.         | NegativeTests                |
| API-001 | Positive API         | Product            | Verify products can be retrieved                        | Validate GET products endpoint.                                             | API is available.                             | 1. Send GET request to /products.                                                                                                                                            | Status code should be 200 and product data should be returned.                | ProductApiTests              |
| API-002 | Positive API         | Product            | Verify single product can be retrieved                  | Validate GET product by ID endpoint.                                        | Product ID exists or endpoint is available.   | 1. Send GET request to /products/{productId}.                                                                                                                                | API should return product data or valid not-found response.                   | ProductApiTests              |
| API-003 | Positive API         | Product/Search     | Verify products can be searched                         | Validate product search endpoint.                                           | API is available.                             | 1. Send search request with keyword.                                                                                                                                         | Status code should be 200 and matching data should be returned.               | ProductApiTests              |
| API-004 | Negative API         | Product            | Verify invalid product ID handling                      | Validate API behavior for invalid product ID.                               | API is available.                             | 1. Send GET request with invalid product ID.                                                                                                                                 | API should return 400 or 404.                                                 | ProductApiTests              |
| API-005 | Positive API         | Product DTO        | Verify product response can be deserialized             | Validate response mapping from JSON to DTO.                                 | API is available.                             | 1. Send GET products request. 2. Deserialize response into ProductResponse DTO.                                                                                              | Product DTO list should not be empty.                                         | ProductApiTests              |
| API-006 | Positive API         | Authentication     | Verify user can login via API                           | Validate login endpoint and token response.                                 | Valid credentials exist.                      | 1. Send POST request to login endpoint with email and password.                                                                                                              | Access token should be returned.                                              | AuthApiTests                 |
| API-007 | Positive API         | Authentication     | Verify token can be reused                              | Validate bearer token reuse in authenticated request.                       | User can login successfully.                  | 1. Login via API. 2. Save token. 3. Send authenticated API request using token.                                                                                              | Authenticated request should return successful response.                      | AuthenticatedProductApiTests |
| API-008 | Positive API         | Authenticated POST | Verify authenticated message can be sent                | Validate authenticated POST request using request and response DTOs.        | Valid token exists.                           | 1. Login via API. 2. Save token. 3. Send POST message request. 4. Deserialize response DTO.                                                                                  | Message response should be returned successfully.                             | AuthenticatedPostApiTests    |
| INT-001 | API + UI Integration | Product            | Verify product returned by API is searchable in UI      | Validate that product data from API is visible in UI.                       | API and UI are available.                     | 1. Get product name from API. 2. Search same product in UI.                                                                                                                  | Product should be displayed in UI search results.                             | ProductApiUiIntegrationTests |

---
# CI/CD Pipelines

![API Tests](https://github.com/cemkul/toolshopwithplaywright/actions/workflows/api-tests.yml/badge.svg)

![UI Tests](https://github.com/cemkul/toolshopwithplaywright/actions/workflows/ui-tests.yml/badge.svg)

![Integration Tests](https://github.com/cemkul/toolshopwithplaywright/actions/workflows/integration-tests.yml/badge.svg)

This project uses GitHub Actions for Continuous Integration and automated test execution.

## Implemented Workflows

### API Tests

* Executes REST API automation tests
* Uses Rest Assured + JUnit 5
* Runs on GitHub-hosted runners
* Generates Allure reports

### UI Tests

* Executes Playwright UI automation tests
* Runs on a Windows self-hosted runner
* Implemented to avoid cloud browser restrictions and Cloudflare-related issues
* Generates downloadable Allure HTML reports

### Integration Tests

* Executes API + UI combined validation scenarios
* Verifies product data consistency between backend and frontend
* Generates Allure reports as workflow artifacts

---

# Workflow Strategy

The framework separates execution pipelines for better maintainability and scalability.

| Workflow          | Purpose                        |
| ----------------- | ------------------------------ |
| API Tests         | Backend/API validation         |
| UI Tests          | Frontend browser automation    |
| Integration Tests | API + UI end-to-end validation |

Advantages:

* Independent execution
* Faster debugging
* Better CI stability
* Parallel execution support
* Cleaner test organization

---



