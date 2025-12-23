package Hooks;

import Utils.TestDataProvider;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import java.util.List;
import java.util.Map;

public class TestHooks {

    private static List<Map<String, String>> testData;

    @Before(order = 0)
    public void loadTestData(Scenario scenario) {

    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("Finished Scenario: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());

        // Close browser after each scenario
        try {
            Utils.DriverManager.quitDriver();
            System.out.println("Browser closed successfully");
        } catch (Exception e) {
            System.out.println("No browser session to close");
        }
    }

    public static List<Map<String, String>> getTestData() {
        return testData;
    }
}
