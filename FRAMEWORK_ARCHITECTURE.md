# Framework Architecture & Explanation

## Table of Contents
1. [Overview](#overview)
2. [Architecture Diagram](#architecture-diagram)
3. [Directory Structure](#directory-structure)
4. [Core Components](#core-components)
5. [Data Flow](#data-flow)
6. [Execution Flow](#execution-flow)
7. [Key Features](#key-features)
8. [Design Patterns](#design-patterns)

---

## Overview

This is a **Behavior-Driven Development (BDD)** test automation framework built using:
- **Cucumber** - BDD framework for writing tests in natural language
- **Selenium WebDriver** - Browser automation
- **JUnit** - Test runner
- **Maven** - Build and dependency management
- **Apache POI** - Excel/CSV data handling
- **Page Object Model (POM)** - Design pattern for organizing code

### Framework Type
**Hybrid Framework** = BDD (Cucumber) + Data-Driven + Keyword-Driven + POM

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         TEST LAYER                               │
│  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐   │
│  │ Login.feature  │  │ Other.feature  │  │ More.feature   │   │
│  │ (Gherkin BDD)  │  │ (Test Scenarios)│  │ (Test Specs)   │   │
│  └────────┬───────┘  └────────┬───────┘  └────────┬───────┘   │
└───────────┼──────────────────┼──────────────────┼─────────────┘
            │                  │                  │
            └──────────────────┴──────────────────┘
                               │
┌──────────────────────────────┼──────────────────────────────────┐
│                    STEP DEFINITIONS LAYER                        │
│  ┌────────────────────────────┴──────────────────────────────┐ │
│  │  LoginStepDefenition.java                                  │ │
│  │  CSVDataDrivenLoginSteps.java                             │ │
│  │  DataDrivenLoginSteps.java                                │ │
│  │  (Maps Gherkin steps to Java methods)                     │ │
│  └────────────────────────────┬──────────────────────────────┘ │
└───────────────────────────────┼──────────────────────────────────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
┌───────────────────┼───────────┐  ┌───────┼──────────────────────┐
│         ACTIONS LAYER          │  │    HOOKS LAYER               │
│  ┌─────────────────────────┐  │  │  ┌────────────────────────┐ │
│  │  loginActions.java      │  │  │  │  TestHooks.java        │ │
│  │  (Business Logic)       │  │  │  │  @Before / @After      │ │
│  └──────────┬──────────────┘  │  │  │  (Setup/Teardown)      │ │
└─────────────┼──────────────────┘  │  └────────────────────────┘ │
              │                     └────────────────────────────────┘
              │
┌─────────────┼────────────────────────────────────────────────────┐
│      PAGE OBJECTS LAYER (POM)                                    │
│  ┌───────────┴──────────────┐                                   │
│  │  loginPageObjects.java   │                                   │
│  │  (Web Elements)          │                                   │
│  └───────────┬──────────────┘                                   │
└──────────────┼───────────────────────────────────────────────────┘
               │
┌──────────────┼───────────────────────────────────────────────────┐
│         UTILITIES LAYER                                          │
│  ┌────────────┴─────────────────────────────────────────────┐  │
│  │  DriverManager.java    - WebDriver management            │  │
│  │  commonUtils.java      - Common utilities                │  │
│  │  ExcelReader.java      - Read Excel files                │  │
│  │  CSVReader.java        - Read CSV files                  │  │
│  │  TestDataProvider.java - Data management                 │  │
│  └──────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────┘
                               │
┌──────────────────────────────┼──────────────────────────────────┐
│         RUNNER LAYER                                             │
│  ┌────────────────────────────┴──────────────────────────────┐ │
│  │  RunnerClass.java    - Default runner                     │ │
│  │  ChromeRunner.java   - Chrome browser runner              │ │
│  │  FirefoxRunner.java  - Firefox browser runner             │ │
│  │  EdgeRunner.java     - Edge browser runner                │ │
│  │  (JUnit + Cucumber configuration)                         │ │
│  └────────────────────────────────────────────────────────────┘ │
└───────────────────────────────────────────────────────────────────┘
                               │
┌──────────────────────────────┼──────────────────────────────────┐
│         DATA LAYER                                               │
│  ┌────────────────────────────┴──────────────────────────────┐ │
│  │  TestData/LoginData.csv   - Test data                     │ │
│  │  TestData/LoginData.xlsx  - Test data (Excel)             │ │
│  └────────────────────────────────────────────────────────────┘ │
└───────────────────────────────────────────────────────────────────┘
                               │
                               ▼
                     ┌──────────────────┐
                     │   BROWSERS       │
                     │  Chrome/Firefox/ │
                     │  Edge            │
                     └──────────────────┘
```

---

## Directory Structure

```
Naukri/
├── src/
│   ├── main/java/
│   │   ├── Utils/                          # Utility classes
│   │   │   ├── DriverManager.java          # WebDriver lifecycle management
│   │   │   ├── commonUtils.java            # Common reusable methods
│   │   │   ├── ExcelReader.java            # Excel file reading
│   │   │   ├── CSVReader.java              # CSV file reading
│   │   │   └── TestDataProvider.java       # Data access layer
│   │   │
│   │   └── webElements/                    # Page Object classes
│   │       └── loginPageObjects.java       # Login page web elements
│   │
│   └── test/java/
│       ├── Actions/                        # Business logic layer
│       │   └── loginActions.java           # Login-related actions
│       │
│       ├── StepDefenitions/                # Cucumber step definitions
│       │   ├── LoginStepDefenition.java    # Basic login steps
│       │   ├── CSVDataDrivenLoginSteps.java # CSV data-driven steps
│       │   └── DataDrivenLoginSteps.java   # Excel data-driven steps
│       │
│       ├── Hooks/                          # Setup/Teardown
│       │   └── TestHooks.java              # Before/After hooks
│       │
│       └── Runner/                         # Test runners
│           ├── RunnerClass.java            # Default runner
│           ├── ChromeRunner.java           # Chrome-specific runner
│           ├── FirefoxRunner.java          # Firefox-specific runner
│           └── EdgeRunner.java             # Edge-specific runner
│
├── FeatureFiles/                           # BDD feature files
│   ├── Login.feature                       # Login test scenarios
│   └── LoginWithExcelData.feature          # Excel-based scenarios
│
├── TestData/                               # Test data files
│   ├── LoginData.csv                       # CSV test data
│   ├── LoginData.xlsx                      # Excel test data
│   └── README_EXTERNAL_DATA.md             # Data guide
│
├── pom.xml                                 # Maven configuration
├── FRAMEWORK_ARCHITECTURE.md               # This document
└── PARALLEL_EXECUTION_GUIDE.md             # Parallel testing guide
```

---

## Core Components

### 1. **Feature Files** (FeatureFiles/*.feature)

**Purpose**: Define test scenarios in plain English (Gherkin language)

**Example**:
```gherkin
Feature: Launch Naukri and search jobs - Data from CSV File

  @login @csvData
  Scenario: Login to Naukri Portal with TC001 from CSV
    Given User enters the Url
    Then User logs in using test case "TC001" from CSV file
    And Click on Submit button
    Then Get the page title
```

**Key Points**:
- Written in Gherkin syntax (Given-When-Then)
- Business-readable format
- No technical implementation details
- Maps to step definitions

---

### 2. **Step Definitions** (src/test/java/StepDefenitions/)

**Purpose**: Connect Gherkin steps to Java code

**Example**:
```java
@Then("User logs in using test case {string} from CSV file")
public void userLogsInUsingTestCaseFromCSV(String testCaseId) {
    // Initialize page objects
    loginPageObj = new loginPageObjects(DriverManager.getDriver());

    // Read data from CSV
    Map<String, String> credentials = dataProvider.getLoginCredentialsFromCSV(testCaseId);

    // Perform actions
    loginPageObj.getUsernameField().sendKeys(credentials.get("Email"));
    loginPageObj.getPasswordField().sendKeys(credentials.get("Password"));
}
```

**Available Step Definition Classes**:
1. **LoginStepDefenition.java** - Basic login steps with hardcoded data
2. **CSVDataDrivenLoginSteps.java** - CSV file-based data
3. **DataDrivenLoginSteps.java** - Excel file-based data

---

### 3. **Actions Layer** (src/test/java/Actions/)

**Purpose**: Business logic and reusable action methods

**Example**:
```java
public class loginActions {
    private loginPageObjects loginPageObj;

    public WebDriver launchBrowser() {
        WebDriver driver = commonUtils.launchBrowser();
        loginPageObj = new loginPageObjects(driver);
        return driver;
    }

    public void enterUsername(String username) {
        loginPageObj.getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        loginPageObj.getPasswordField().sendKeys(password);
    }
}
```

**Why Actions Layer?**
- Separates business logic from step definitions
- Reusable across multiple step definitions
- Easier to maintain and update

---

### 4. **Page Objects** (src/main/java/webElements/)

**Purpose**: Store web element locators (Page Object Model pattern)

**Example**:
```java
public class loginPageObjects {
    private WebDriver driver;

    public loginPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameField() {
        return driver.findElement(By.id("usernameField"));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.id("passwordField"));
    }

    public WebElement getSubmitButton() {
        return driver.findElement(By.xpath("//button[@type='submit']"));
    }
}
```

**Benefits**:
- Centralized element locators
- Easy to update if UI changes
- Reusable across different tests

---

### 5. **DriverManager** (src/main/java/Utils/)

**Purpose**: Manage WebDriver lifecycle and browser initialization

**Key Features**:
```java
public class DriverManager {
    // ThreadLocal for parallel execution support
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> browserType = new ThreadLocal<>();

    // Get driver instance (creates if not exists)
    public static WebDriver getDriver() { ... }

    // Initialize browser (Chrome/Firefox/Edge)
    private static void initializeDriver() { ... }

    // Close and cleanup browser
    public static void quitDriver() { ... }
}
```

**Why ThreadLocal?**
- Ensures thread safety for parallel execution
- Each thread gets its own WebDriver instance
- No conflicts between parallel tests

---

### 6. **Data Management** (src/main/java/Utils/)

#### **CSVReader.java**
Reads CSV files and converts to Java objects

#### **ExcelReader.java**
Reads Excel files (.xlsx) and converts to Java objects

#### **TestDataProvider.java**
High-level API for accessing test data

**Example**:
```java
TestDataProvider provider = new TestDataProvider("TestData/LoginData.csv");
Map<String, String> data = provider.getLoginCredentialsFromCSV("TC001");
String email = data.get("Email");
String password = data.get("Password");
```

---

### 7. **Test Runners** (src/test/java/Runner/)

**Purpose**: Configure and execute Cucumber tests

**Example**:
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "FeatureFiles",              // Where feature files are
    glue = {"StepDefenitions", "Hooks"},    // Where step definitions are
    plugin = {"pretty", "html:target/cucumber-reports-chrome.html"},
    tags = "@login",                         // Which scenarios to run
    dryRun = false                          // Actually execute (not just validate)
)
public class ChromeRunner {
    @BeforeClass
    public static void setup() {
        System.setProperty("browser", "chrome");
    }
}
```

**Available Runners**:
- **RunnerClass.java** - Default runner (Chrome)
- **ChromeRunner.java** - Chrome browser
- **FirefoxRunner.java** - Firefox browser
- **EdgeRunner.java** - Edge browser

---

### 8. **Hooks** (src/test/java/Hooks/)

**Purpose**: Setup and teardown operations

**TestHooks.java**:
```java
public class TestHooks {
    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        // Close browser after each scenario
        DriverManager.quitDriver();
        System.out.println("Finished: " + scenario.getName());
    }
}
```

**Hook Execution Order**:
1. `@Before` hooks run before each scenario
2. Scenario steps execute
3. `@After` hooks run after each scenario (even if test fails)

---

## Data Flow

### Example: Login with CSV Data

```
1. TEST START
   ├─> Runner (ChromeRunner.java) starts execution
   └─> Sets browser = "chrome"

2. FEATURE FILE
   ├─> Reads Login.feature
   └─> Finds scenario with @login tag

3. HOOKS (@Before)
   ├─> TestHooks.beforeScenario()
   └─> Logs scenario name and tags

4. STEP: "Given User enters the Url"
   ├─> LoginStepDefenition.user_enters_the_url()
   ├─> loginActions.launchBrowser()
   ├─> commonUtils.launchBrowser()
   ├─> DriverManager.getDriver()
   │   ├─> Checks browserType
   │   ├─> Initializes Chrome
   │   └─> Returns WebDriver instance
   ├─> loginActions.navigateToUrl("https://naukri.com/nlogin/login")
   └─> Browser opens and navigates

5. STEP: "Then User logs in using test case 'TC001' from CSV file"
   ├─> CSVDataDrivenLoginSteps.userLogsInUsingTestCaseFromCSV("TC001")
   ├─> TestDataProvider.getLoginCredentialsFromCSV("TC001")
   ├─> CSVReader.loadCSV()
   ├─> Reads TestData/LoginData.csv
   ├─> Finds row with TestCase = "TC001"
   ├─> Returns: {Email: "sridharsush@gmail.com", Password: "Gmail@96002"}
   ├─> loginPageObjects.getUsernameField().sendKeys(email)
   └─> loginPageObjects.getPasswordField().sendKeys(password)

6. STEP: "And Click on Submit button"
   ├─> LoginStepDefenition.click_on_submit_button()
   ├─> loginActions.clickSubmitButton()
   └─> loginPageObjects.getSubmitButton().click()

7. STEP: "Then Get the page title"
   ├─> LoginStepDefenition.get_the_page_title()
   ├─> loginActions.getPageTitle()
   ├─> commonUtils.getPageTitle()
   └─> Returns page title string

8. HOOKS (@After)
   ├─> TestHooks.afterScenario()
   ├─> DriverManager.quitDriver()
   └─> Browser closes

9. REPORT GENERATION
   ├─> Cucumber generates HTML report
   ├─> Saved to: target/cucumber-reports-chrome.html
   └─> JSON report: target/cucumber-chrome.json

10. TEST END
```

---

## Execution Flow

### Sequential Execution
```bash
mvn test -Dtest=ChromeRunner
```

1. Maven compiles Java code
2. Surefire plugin finds ChromeRunner.java
3. JUnit runs ChromeRunner
4. Cucumber reads feature files
5. For each scenario:
   - Execute @Before hooks
   - Execute steps sequentially
   - Execute @After hooks
6. Generate reports

### Parallel Execution
```bash
mvn clean test
```

1. Maven compiles Java code
2. Surefire plugin finds all runners (Chrome, Firefox, Edge)
3. Creates 3 JVM forks (parallel processes)
4. Each fork runs independently:
   ```
   Fork 1: ChromeRunner  ├─> Feature Files ─> Steps ─> Chrome Browser
   Fork 2: FirefoxRunner ├─> Feature Files ─> Steps ─> Firefox Browser
   Fork 3: EdgeRunner    └─> Feature Files ─> Steps ─> Edge Browser
   ```
5. All forks run simultaneously
6. Each generates its own report
7. Maven aggregates results

**Maven Surefire Configuration** (pom.xml):
```xml
<configuration>
    <parallel>classes</parallel>          <!-- Run runner classes in parallel -->
    <threadCount>3</threadCount>          <!-- 3 parallel threads -->
    <forkCount>3</forkCount>              <!-- 3 separate JVM processes -->
    <reuseForks>false</reuseForks>        <!-- Don't reuse (better isolation) -->
</configuration>
```

---

## Key Features

### 1. **BDD (Behavior-Driven Development)**
- Write tests in plain English
- Collaboration between developers, testers, and business
- Living documentation

### 2. **Data-Driven Testing**
- External data sources (CSV/Excel)
- Easy to add new test cases
- No code changes needed for new data

### 3. **Cross-Browser Testing**
- Support for Chrome, Firefox, Edge
- Same tests run on all browsers
- Browser-specific configurations

### 4. **Parallel Execution**
- Multiple browsers simultaneously
- Faster test execution
- ThreadLocal for thread safety

### 5. **Page Object Model (POM)**
- Separation of concerns
- Easy maintenance
- Reusable components

### 6. **Modular Architecture**
- Layered design
- High cohesion, low coupling
- Easy to extend

### 7. **Report Generation**
- HTML reports (human-readable)
- JSON reports (machine-readable)
- Separate reports per browser

---

## Design Patterns

### 1. **Page Object Model (POM)**
**Where**: webElements/loginPageObjects.java

**Purpose**: Separate page structure from test logic

**Benefits**:
- Single place to update element locators
- Reusable across tests
- Easier maintenance

### 2. **Singleton Pattern**
**Where**: DriverManager.java

**Purpose**: Single WebDriver instance per thread

**Implementation**:
```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

public static WebDriver getDriver() {
    if (driver.get() == null) {
        initializeDriver();
    }
    return driver.get();
}
```

### 3. **Factory Pattern**
**Where**: DriverManager.java

**Purpose**: Create different browser instances

**Implementation**:
```java
switch (browser.toLowerCase()) {
    case "firefox": return initFirefoxDriver();
    case "edge": return initEdgeDriver();
    default: return initChromeDriver();
}
```

### 4. **Strategy Pattern**
**Where**: TestDataProvider.java

**Purpose**: Different strategies for reading data (CSV vs Excel)

**Implementation**:
```java
if (dataFilePath.endsWith(".csv")) {
    allData = loadTestDataFromCSV();
} else {
    allData = loadTestDataFromExcel(sheetName);
}
```

### 5. **Dependency Injection**
**Where**: Step Definitions

**Purpose**: Inject dependencies (page objects, data providers)

**Implementation**:
```java
public CSVDataDrivenLoginSteps() {
    dataProvider = new TestDataProvider(TestDataProvider.getLoginDataCSVPath());
}
```

---

## Framework Advantages

### ✅ **Maintainability**
- Clear separation of concerns
- Easy to locate and fix issues
- Modular design

### ✅ **Scalability**
- Easy to add new browsers
- Easy to add new test scenarios
- Easy to add new pages

### ✅ **Reusability**
- Common utilities
- Page objects used across tests
- Actions reused in multiple scenarios

### ✅ **Readability**
- BDD syntax (Gherkin)
- Clear naming conventions
- Well-documented code

### ✅ **Flexibility**
- Support for multiple data sources
- Support for multiple browsers
- Configurable execution

### ✅ **Performance**
- Parallel execution
- ThreadLocal for thread safety
- Independent test execution

---

## How to Extend the Framework

### Adding a New Page

1. **Create Page Object**:
   ```java
   // src/main/java/webElements/SearchPageObjects.java
   public class SearchPageObjects {
       private WebDriver driver;

       public WebElement getSearchBox() {
           return driver.findElement(By.id("searchBox"));
       }
   }
   ```

2. **Create Actions**:
   ```java
   // src/test/java/Actions/searchActions.java
   public class searchActions {
       private SearchPageObjects searchPageObj;

       public void performSearch(String keyword) {
           searchPageObj.getSearchBox().sendKeys(keyword);
       }
   }
   ```

3. **Create Step Definitions**:
   ```java
   // src/test/java/StepDefenitions/SearchStepDefenition.java
   @When("User searches for {string}")
   public void userSearchesFor(String keyword) {
       searchAction.performSearch(keyword);
   }
   ```

4. **Create Feature File**:
   ```gherkin
   # FeatureFiles/Search.feature
   Scenario: Search for jobs
     When User searches for "Java Developer"
     Then User should see search results
   ```

### Adding a New Browser

1. **Update DriverManager.java**:
   ```java
   case "safari":
       webDriver = initSafariDriver();
       break;
   ```

2. **Create init method**:
   ```java
   private static WebDriver initSafariDriver() {
       WebDriverManager.safaridriver().setup();
       return new SafariDriver();
   }
   ```

3. **Create SafariRunner.java**:
   ```java
   @BeforeClass
   public static void setup() {
       System.setProperty("browser", "safari");
   }
   ```

---

## Summary

This is a **comprehensive, production-ready test automation framework** with:

- ✅ BDD approach (Cucumber + Gherkin)
- ✅ Data-driven testing (CSV/Excel)
- ✅ Cross-browser support (Chrome/Firefox/Edge)
- ✅ Parallel execution
- ✅ Page Object Model
- ✅ Modular architecture
- ✅ Comprehensive reporting
- ✅ Thread-safe design
- ✅ Easy to maintain and extend

**Perfect for**: Web application testing, regression testing, CI/CD integration, team collaboration.
