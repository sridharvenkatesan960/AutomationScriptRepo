# Automation Test Framework

A Selenium + Cucumber BDD test automation framework for Naukri portal testing, with Java practice programs.

## 📁 Project Structure

```
MyProjectGit/
├── Naukri/                          # Main test automation project
│   ├── src/
│   │   ├── main/java/
│   │   │   ├── Utils/               # Utility classes
│   │   │   │   ├── DriverManager.java
│   │   │   │   └── commonUtils.java
│   │   │   └── webElements/         # Page Object Models
│   │   │       └── loginPageObjects.java
│   │   └── test/java/
│   │       ├── Actions/             # Action classes
│   │       │   └── loginActions.java
│   │       ├── Runner/              # Test runners
│   │       │   └── RunnerClass.java
│   │       └── StepDefenitions/     # Cucumber step definitions
│   │           └── LoginStepDefenition.java
│   ├── FeatureFiles/                # BDD feature files
│   │   └── Login.feature
│   └── pom.xml                      # Maven dependencies
├── practicePrograms/                # Java practice code
│   └── src/
│       ├── StringPrograms.java      # String manipulation programs
│       └── integerPrograms.java     # Array/integer programs
├── Jenkinsfile                      # CI/CD pipeline configuration
├── JENKINS_SETUP.md                 # Jenkins setup guide
└── README.md                        # This file
```

## 🚀 Technologies Used

- **Java 17** - Programming language
- **Selenium WebDriver 4.35.0** - Browser automation
- **Cucumber 7.27.2** - BDD framework
- **Maven** - Build and dependency management
- **TestNG 7.11.0** - Testing framework
- **JUnit 5.11.0** - Unit testing
- **WebDriverManager 6.2.0** - Automatic driver management
- **Jenkins** - CI/CD automation

## ✨ Features

- ✅ Page Object Model (POM) design pattern
- ✅ Cucumber BDD with Gherkin syntax
- ✅ ThreadLocal WebDriver for parallel execution
- ✅ Automatic browser driver management
- ✅ Extent Reports integration
- ✅ Jenkins CI/CD pipeline support
- ✅ Parameterized test execution
- ✅ Multiple browser support (Chrome, Firefox, Edge)

## 📋 Prerequisites

- Java JDK 17 or higher
- Maven 3.6 or higher
- Chrome/Firefox browser installed
- Git

## 🛠️ Setup Instructions

### 1. Clone the Repository
```bash
git clone git@github.com:sridharvenkatesan960/AutomationScriptRepo.git
cd AutomationScriptRepo
```

### 2. Build the Project
```bash
cd Naukri
mvn clean install
```

### 3. Run Tests Locally
```bash
# Run all tests
mvn test

# Run specific Cucumber tags
mvn test -Dcucumber.filter.tags="@login"
```

## 🎯 Running Tests

### Command Line Execution
```bash
cd Naukri

# Run with default configuration
mvn clean test

# Run with specific tags
mvn test -Dcucumber.filter.tags="@login"

# Run with specific browser
mvn test -Dbrowser=firefox

# Run specific test runner
mvn test -Dtest=RunnerClass
```

### IDE Execution
1. Right-click on `RunnerClass.java`
2. Select **Run As → JUnit Test**

## 📊 Test Reports

After test execution, reports are generated in:
- **HTML Report:** `target/cucumber-reports.html`
- **JSON Report:** `target/cucumber.json`
- **JUnit XML:** `target/surefire-reports/`

## 🔄 CI/CD with Jenkins

### Quick Start
1. Follow the setup guide: [JENKINS_SETUP.md](JENKINS_SETUP.md)
2. Configure Jenkins with required plugins
3. Create pipeline job pointing to this repository
4. Run pipeline with parameters

### Pipeline Parameters
- **BROWSER:** chrome, firefox, edge
- **ENVIRONMENT:** dev, qa, staging, prod
- **TEST_TAGS:** @login, @smoke, @regression

## 📝 Practice Programs

The `practicePrograms` folder contains Java programs for:

### StringPrograms.java
- String reversal (multiple methods)
- Character count and occurrence
- Word-by-word reversal
- Pattern printing

### integerPrograms.java
- Array operations (sum, reverse, middle element)
- Fibonacci series
- Duplicate finding
- Even/odd separation
- Factorial calculation
- Array combining

## 🔒 Security Notes

- ❌ No hardcoded credentials in code
- ✅ Use environment variables or property files for sensitive data
- ✅ `.gitignore` configured to exclude sensitive files

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📧 Contact

- **Email:** sridharsush@gmail.com
- **GitHub:** [@sridharvenkatesan960](https://github.com/sridharvenkatesan960)

## 📄 License

This project is for educational and testing purposes.

---

**Happy Testing! 🚀**
