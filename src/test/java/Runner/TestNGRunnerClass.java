package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "FeatureFiles",
    glue = "StepDefenitions",
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"},
    monochrome = true,
    tags = "@login"
)
public class TestNGRunnerClass extends AbstractTestNGCucumberTests {

}
