# 🛒 E-Commerce Test Automation Framework

**Selenium + Java + TestNG + Page Object Model (POM) + Extent Reports**

> Test site used: [SauceDemo](https://www.saucedemo.com) — a purpose-built QA demo store.

---

## 📁 Project Structure

```
ecommerce-automation/
├── pom.xml                            ← Maven dependencies
├── testng.xml                         ← TestNG suite configuration
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/                 ← Page Object Model classes
│       │   │   ├── BasePage.java
│       │   │   ├── LoginPage.java
│       │   │   ├── ProductsPage.java
│       │   │   ├── ProductDetailPage.java
│       │   │   ├── CartPage.java
│       │   │   └── CheckoutPage.java
│       │   ├── tests/                 ← TestNG test classes
│       │   │   ├── BaseTest.java
│       │   │   ├── LoginTest.java
│       │   │   ├── ProductTest.java
│       │   │   ├── CartTest.java
│       │   │   ├── CheckoutTest.java
│       │   │   └── E2ETest.java
│       │   ├── utils/                 ← Utility / Helper classes
│       │   │   ├── ConfigReader.java
│       │   │   ├── DriverManager.java
│       │   │   ├── ExtentReportManager.java
│       │   │   ├── ScreenshotUtils.java
│       │   │   └── WaitUtils.java
│       │   └── listeners/
│       │       └── TestListener.java  ← TestNG listener (reports + screenshots)
│       └── resources/
│           ├── config.properties      ← All configuration (browser, URL, credentials)
│           └── log4j2.xml             ← Logging configuration
├── reports/                           ← Extent HTML reports (generated)
└── test-output/
    └── screenshots/                   ← Failure screenshots (generated)
```

---

## ✅ Test Cases

| ID           | Module   | Description                                     |
|--------------|----------|-------------------------------------------------|
| TC_LOGIN_001 | Login    | Valid login with correct credentials            |
| TC_LOGIN_002 | Login    | Invalid login – wrong password                  |
| TC_LOGIN_003 | Login    | Login with empty username                       |
| TC_LOGIN_004 | Login    | Login with empty password                       |
| TC_LOGIN_005 | Login    | Locked out user gets error message              |
| TC_LOGIN_006 | Login    | Successful logout                               |
| TC_PROD_001  | Products | Products page loads with items                  |
| TC_PROD_002  | Products | Sort products A-Z                               |
| TC_PROD_003  | Products | Sort products by price low to high              |
| TC_PROD_004  | Products | Product detail page shows correct info          |
| TC_PROD_005  | Products | Add to cart from product detail page            |
| TC_CART_001  | Cart     | Add single product to cart                      |
| TC_CART_002  | Cart     | Add multiple products to cart                   |
| TC_CART_003  | Cart     | Remove product from cart                        |
| TC_CART_004  | Cart     | Cart badge count updates correctly              |
| TC_CART_005  | Cart     | Continue shopping returns to products page      |
| TC_CHK_001   | Checkout | Full end-to-end checkout flow                   |
| TC_CHK_002   | Checkout | Error when first name is missing                |
| TC_CHK_003   | Checkout | Error when last name is missing                 |
| TC_CHK_004   | Checkout | Error when postal code is missing               |
| TC_CHK_005   | Checkout | Order summary displayed on Step 2               |
| TC_CHK_006   | Checkout | Cancel checkout returns to cart                 |
| E2E_001      | E2E      | Full user journey: Login→Cart→Checkout→Confirm  |

---

## 🚀 Setup & Run

### Prerequisites
- Java JDK 11+
- Maven 3.6+
- Chrome/Firefox/Edge browser installed
- Internet connection (WebDriverManager downloads drivers automatically)

### 1. Clone / Unzip the project
```bash
cd ecommerce-automation
```

### 2. Configure test settings
Edit `src/test/resources/config.properties`:
```properties
browser=chrome        # chrome | firefox | edge
headless=false        # true for CI/CD pipelines
base.url=https://www.saucedemo.com
```

### 3. Run all tests via Maven
```bash
mvn clean test
```

### 4. Run a specific test class
```bash
mvn clean test -Dtest=LoginTest
mvn clean test -Dtest=E2ETest
mvn clean test -Dtest=CheckoutTest
```

### 5. Run in headless mode (CI/CD)
```bash
mvn clean test -Dheadless=true
```

---

## 📊 Reports & Screenshots

| Output              | Location                          |
|---------------------|-----------------------------------|
| Extent HTML Report  | `reports/TestReport_<timestamp>.html` |
| Failure Screenshots | `test-output/screenshots/`        |
| Log file            | `test-output/automation.log`      |

Open the HTML report in any browser after the run to see:
- ✅ Passed / ❌ Failed / ⚠️ Skipped counts
- Step-by-step logs per test
- Screenshots embedded directly in the report for failures
- System info (browser, OS, Java version)

---

## 🏗️ Framework Design

### Page Object Model (POM)
Each page of the application has its own Java class under `pages/`. Page classes:
- Use `@FindBy` annotations to declare locators
- Expose business-action methods (e.g. `login()`, `addProductToCart()`)
- Return the next page object where navigation occurs (fluent POM)
- Extend `BasePage` for shared helpers (click, type, getText, etc.)

### ThreadLocal WebDriver
`DriverManager` stores the WebDriver in a `ThreadLocal`, making the framework safe for **parallel test execution** without driver conflicts.

### Extent Reports + Listener
`TestListener` implements `ITestListener` and hooks into TestNG's lifecycle:
- `onTestStart` → creates a test node in the report
- `onTestSuccess` → marks green
- `onTestFailure` → marks red + captures screenshot + attaches to report
- `onFinish` → flushes the report to disk

---

## 🔧 Changing Browser

In `config.properties`:
```properties
browser=firefox
# or
browser=edge
```
No other code changes needed — WebDriverManager handles driver downloads automatically.

---

## 📝 Manual Test Cases

The test cases in `LoginTest`, `ProductTest`, `CartTest`, and `CheckoutTest` directly correspond to manually written test cases. For any manual QA exercise, use the TC IDs in the table above as your test case IDs.

---

*Built for QA Internship Portfolio — Selenium + Java + TestNG + POM + Extent Reports*
