package Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "FeatureFiles",
    glue = {"StepDefenitions", "Hooks"},
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"},
    monochrome = true,
    tags = "@test",
    dryRun = false
)
public class RunnerClass {
	
}
