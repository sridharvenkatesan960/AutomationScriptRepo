package Runner;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Runner class for Firefox browser
 * Executes tests in Firefox browser
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "FeatureFiles",
    glue = {"StepDefenitions", "Hooks"},
    plugin = {"pretty",
              "html:target/cucumber-reports-firefox.html",
              "json:target/cucumber-firefox.json"},
    monochrome = true,
    tags = "@login",
    dryRun = false
)
public class FirefoxRunner {

    @BeforeClass
    public static void setup() {
        System.setProperty("browser", "firefox");
        System.out.println("Running tests in Firefox browser");
    }
}
