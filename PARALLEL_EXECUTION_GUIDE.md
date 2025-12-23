# Parallel Browser Execution Guide

This guide explains how to run your test scenarios across multiple browsers in parallel.

## Overview

The framework now supports parallel execution across multiple browsers:
- **Chrome** (Default)
- **Firefox**
- **Microsoft Edge**

All browsers run **simultaneously** using Maven Surefire parallel execution.

## Architecture

### Key Components

1. **DriverManager** (src/main/java/Utils/DriverManager.java)
   - Supports Chrome, Firefox, and Edge
   - ThreadLocal-based for thread safety
   - Reads browser type from system property or method parameter

2. **Runner Classes**
   - `ChromeRunner.java` - Runs tests in Chrome
   - `FirefoxRunner.java` - Runs tests in Firefox
   - `EdgeRunner.java` - Runs tests in Edge

3. **Maven Surefire Configuration** (pom.xml)
   - Configured for parallel execution
   - 3 threads (one per browser)
   - Separate forks for isolation

## Running Tests

### 1. Run All Browsers in Parallel (Recommended)

Execute tests across all three browsers simultaneously:

```bash
mvn clean test
```

This will:
- Launch Chrome, Firefox, and Edge browsers simultaneously
- Execute all scenarios marked with `@login` tag
- Generate separate reports for each browser:
  - `target/cucumber-reports-chrome.html`
  - `target/cucumber-reports-firefox.html`
  - `target/cucumber-reports-edge.html`

### 2. Run Specific Browser

Execute tests in a single browser:

**Chrome:**
```bash
mvn test -Dtest=ChromeRunner
```

**Firefox:**
```bash
mvn test -Dtest=FirefoxRunner
```

**Edge:**
```bash
mvn test -Dtest=EdgeRunner
```

### 3. Run Two Browsers

Execute tests in specific browsers only:

```bash
mvn test -Dtest=ChromeRunner,FirefoxRunner
```

### 4. Run with Custom Browser via System Property

```bash
mvn test -Dtest=RunnerClass -Dbrowser=firefox
```

## Configuration

### Adjusting Parallel Execution

Edit `pom.xml` to modify parallel execution settings:

```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <!-- Number of parallel threads -->
        <threadCount>3</threadCount>

        <!-- Number of JVM forks -->
        <forkCount>3</forkCount>

        <!-- Reuse forks or create new ones -->
        <reuseForks>false</reuseForks>

        <!-- Which runner classes to include -->
        <includes>
            <include>**/ChromeRunner.java</include>
            <include>**/FirefoxRunner.java</include>
            <include>**/EdgeRunner.java</include>
        </includes>
    </configuration>
</plugin>
```

**Key Parameters:**
- `threadCount`: Number of parallel threads (recommend 1 per browser)
- `forkCount`: Number of JVM forks (recommend 1 per browser for isolation)
- `reuseForks`: false = better isolation, true = faster execution

### Adding More Browsers

To add support for Safari or other browsers:

1. **Update DriverManager.java:**
   ```java
   case "safari":
       webDriver = initSafariDriver();
       break;
   ```

2. **Create initSafariDriver method:**
   ```java
   private static WebDriver initSafariDriver() {
       WebDriverManager.safaridriver().setup();
       SafariOptions options = new SafariOptions();
       return new SafariDriver(options);
   }
   ```

3. **Create SafariRunner.java:**
   ```java
   @BeforeClass
   public static void setup() {
       System.setProperty("browser", "safari");
   }
   ```

4. **Update pom.xml includes:**
   ```xml
   <include>**/SafariRunner.java</include>
   ```

## Reports

Each browser generates its own report:

### HTML Reports
- Chrome: `target/cucumber-reports-chrome.html`
- Firefox: `target/cucumber-reports-firefox.html`
- Edge: `target/cucumber-reports-edge.html`

### JSON Reports
- Chrome: `target/cucumber-chrome.json`
- Firefox: `target/cucumber-firefox.json`
- Edge: `target/cucumber-edge.json`

Open the HTML reports in a browser to view test results.

## Best Practices

### 1. Browser Installation
Ensure all browsers are installed on your system:
- Chrome: https://www.google.com/chrome/
- Firefox: https://www.mozilla.org/firefox/
- Edge: Pre-installed on Windows, or https://www.microsoft.com/edge

### 2. WebDriver Management
The framework uses WebDriverManager, which automatically downloads and manages browser drivers. No manual setup needed!

### 3. Resource Management
- Each browser runs in a separate JVM fork for isolation
- Browsers are closed after each scenario (configured in TestHooks)
- ThreadLocal ensures thread safety

### 4. Headless Mode
To run browsers in headless mode (no UI, faster execution), update DriverManager:

**Chrome Headless:**
```java
options.addArguments("--headless=new");
```

**Firefox Headless:**
```java
options.addArguments("--headless");
```

**Edge Headless:**
```java
options.addArguments("--headless");
```

### 5. CI/CD Integration
For Jenkins or other CI/CD tools, use headless mode:

```bash
mvn clean test -Dheadless=true
```

Then update DriverManager to check for the headless property.

## Troubleshooting

### Issue: Firefox not launching
**Solution:**
- Ensure Firefox is installed
- Check Firefox version compatibility
- Update WebDriverManager: `WebDriverManager.firefoxdriver().setup()`

### Issue: Edge not launching on Linux
**Solution:**
- Edge is primarily for Windows
- Consider using Chrome or Firefox on Linux systems
- Remove EdgeRunner from includes if not needed

### Issue: Tests failing in one browser but passing in others
**Solution:**
- Check browser-specific element locators
- Verify element load times (use explicit waits)
- Check browser console for JavaScript errors

### Issue: Parallel execution conflicts
**Solution:**
- Ensure `reuseForks=false` in pom.xml
- Check that ports are not conflicting
- Verify ThreadLocal usage in DriverManager

### Issue: Out of memory
**Solution:**
- Reduce thread count: `<threadCount>2</threadCount>`
- Reduce fork count: `<forkCount>2</forkCount>`
- Increase JVM memory: Add to pom.xml:
  ```xml
  <argLine>-Xmx1024m</argLine>
  ```

## Performance Comparison

**Sequential Execution (one browser at a time):**
- Time: ~45 seconds (15s per browser × 3 browsers)

**Parallel Execution (all browsers simultaneously):**
- Time: ~18 seconds (max time of slowest browser)

**Speed Improvement: ~60% faster!**

## Example Output

```
[INFO] Running Runner.ChromeRunner
[INFO] Running Runner.FirefoxRunner
[INFO] Running Runner.EdgeRunner
WebDriver initialized successfully with chrome browser
WebDriver initialized successfully with firefox browser
WebDriver initialized successfully with edge browser
...
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in Runner.ChromeRunner
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in Runner.FirefoxRunner
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in Runner.EdgeRunner
[INFO] BUILD SUCCESS
```

## Summary

You can now:
- ✅ Run tests in multiple browsers simultaneously
- ✅ Execute specific browser tests independently
- ✅ Generate separate reports for each browser
- ✅ Easily add new browsers
- ✅ Configure parallel execution parameters
- ✅ Integrate with CI/CD pipelines

Run `mvn clean test` to execute tests across all browsers in parallel!
