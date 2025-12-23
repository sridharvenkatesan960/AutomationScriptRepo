package Runner;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Runner class for Edge browser
 * Executes tests in Microsoft Edge browser
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "FeatureFiles",
    glue = {"StepDefenitions", "Hooks"},
    plugin = {"pretty",
              "html:target/cucumber-reports-edge.html",
              "json:target/cucumber-edge.json"},
    monochrome = true,
    tags = "@login",
    dryRun = false
)
public class EdgeRunner {

    @BeforeClass
    public static void setup() {
        System.setProperty("browser", "edge");
        System.out.println("Running tests in Microsoft Edge browser");
    }
}
