package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

/**
 * DriverManager class to manage WebDriver instance
 * Supports multiple browsers: Chrome, Firefox, Edge
 * ThreadLocal ensures thread-safety for parallel execution
 */
public class DriverManager {

    // ThreadLocal to support parallel execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> browserType = new ThreadLocal<>();

    private DriverManager() {
    }

    /**
     * Get WebDriver instance for current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    /**
     * Get WebDriver instance with specific browser
     * @param browser Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browser) {
        browserType.set(browser);
        return getDriver();
    }

    /**
     * Initialize WebDriver based on browser type
     */
    private static void initializeDriver() {
        String browser = getBrowserType();
        WebDriver webDriver = null;

        switch (browser.toLowerCase()) {
            case "firefox":
                webDriver = initFirefoxDriver();
                break;
            case "edge":
                webDriver = initEdgeDriver();
                break;
            case "chrome":
            default:
                webDriver = initChromeDriver();
                break;
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(webDriver);
        System.out.println("WebDriver initialized successfully with " + browser + " browser");
    }

    /**
     * Initialize Chrome Driver
     * @return ChromeDriver instance
     */
    private static WebDriver initChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        return new ChromeDriver(options);
    }

    /**
     * Initialize Firefox Driver
     * @return FirefoxDriver instance
     */
    private static WebDriver initFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return new FirefoxDriver(options);
    }

    /**
     * Initialize Edge Driver
     * @return EdgeDriver instance
     */
    private static WebDriver initEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        return new EdgeDriver(options);
    }

    /**
     * Get browser type from system property or thread local
     * Priority: ThreadLocal > System Property > Default (chrome)
     * @return Browser type
     */
    private static String getBrowserType() {
        if (browserType.get() != null) {
            return browserType.get();
        }

        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // Default browser
        }
        return browser;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("WebDriver quit successfully");
        }
    }

    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}
