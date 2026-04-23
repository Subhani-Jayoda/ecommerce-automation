# 🛒 E-Commerce Test Automation Framework

**Selenium + Java + TestNG + Page Object Model (POM) + Extent Reports**

> 🔗 Test site: https://www.saucedemo.com (QA demo e-commerce platform)

---

## 📌 Overview

This project is a **UI Test Automation Framework** designed to validate core functionalities of an e-commerce application. It automates real-world user workflows such as login, product selection, cart operations, and checkout.

The framework is built using **industry-standard design patterns** like Page Object Model (POM) and includes reporting, logging, and reusable utilities for scalability and maintainability.

---

## 🚀 Key Features

* ✅ End-to-end UI test automation
* ✅ Page Object Model (POM) design pattern
* ✅ Test execution with TestNG
* ✅ Extent Reports with screenshots
* ✅ Centralized configuration management
* ✅ Reusable utility classes
* ✅ Thread-safe WebDriver (parallel execution ready)

---

## 🛠️ Tech Stack

* **Java**
* **Selenium WebDriver**
* **TestNG**
* **Maven**
* **Extent Reports**
* **Log4j2**
* **WebDriverManager**
* **Git & GitHub**

---

## 📁 Project Structure

```
ecommerce-automation/
├── pom.xml
├── testng.xml
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   ├── tests/
│       │   ├── utils/
│       │   └── listeners/
│       └── resources/
├── reports/
└── test-output/
```

---

## ✅ Test Coverage

### 🔐 Login Module

* Valid login
* Invalid login scenarios
* Empty fields validation
* Locked user validation
* Logout functionality

### 🛍️ Product Module

* Product listing validation
* Sorting (A–Z, price low-high)
* Product details verification
* Add to cart

### 🛒 Cart Module

* Add/remove items
* Multiple item handling
* Cart badge validation
* Continue shopping

### 💳 Checkout Module

* Complete checkout flow
* Form validation errors
* Order summary validation
* Cancel checkout

### 🔄 End-to-End

* Full user journey: Login → Cart → Checkout → Confirmation

---

## ⚙️ Setup & Execution

### Prerequisites

* Java JDK 11+
* Maven 3.6+
* Chrome / Firefox / Edge

---

### 🔹 Run All Tests

```bash
mvn clean test
```

---

### 🔹 Run Specific Test

```bash
mvn clean test -Dtest=LoginTest
mvn clean test -Dtest=E2ETest
```

---

### 🔹 Headless Execution (CI/CD)

```bash
mvn clean test -Dheadless=true
```

---

## ⚙️ Configuration

Edit:

```
src/test/resources/config.properties
```

Example:

```properties
browser=chrome
headless=false
base.url=https://www.saucedemo.com
```

---

## 📊 Reports & Outputs

| Type        | Location                     |
| ----------- | ---------------------------- |
| HTML Report | `reports/`                   |
| Screenshots | `test-output/screenshots/`   |
| Logs        | `test-output/automation.log` |

📌 Open the Extent Report in a browser to view:

* Pass/Fail summary
* Step execution logs
* Failure screenshots
* Environment details

---

## 🏗️ Framework Design

### 🔹 Page Object Model (POM)

* Each page has a dedicated class
* Encapsulates locators and actions
* Improves maintainability and reusability

---

### 🔹 Driver Management

* Uses `ThreadLocal<WebDriver>`
* Supports parallel execution
* Avoids driver conflicts

---

### 🔹 Reporting & Listener

* Integrated with TestNG Listener
* Automatically captures:

  * Test status
  * Screenshots on failure
  * Logs in Extent Reports

---

## 🔧 Browser Configuration

Update in `config.properties`:

```properties
browser=firefox
```

Supports:

* Chrome
* Firefox
* Edge

---

## 🧪 Manual Test Mapping

All automated test cases map to manual test scenarios using IDs (e.g., TC_LOGIN_001), demonstrating both **manual + automation QA skills**.

---

## 🔥 Future Enhancements

* API Testing integration (REST Assured)
* CI/CD pipeline using GitHub Actions
* Data-driven testing (Excel/JSON)
* Cross-browser parallel execution
* Docker integration


