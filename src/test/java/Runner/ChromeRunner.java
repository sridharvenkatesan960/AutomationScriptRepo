package Runner;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Runner class for Chrome browser
 * Executes tests in Chrome browser
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "FeatureFiles",
    glue = {"StepDefenitions", "Hooks"},
    plugin = {"pretty",
              "html:target/cucumber-reports-chrome.html",
              "json:target/cucumber-chrome.json"},
    monochrome = true,
    tags = "@login",
    dryRun = false
)
public class ChromeRunner {

    @BeforeClass
    public static void setup() {
        System.setProperty("browser", "chrome");
        System.out.println("Running tests in Chrome browser");
    }
}
