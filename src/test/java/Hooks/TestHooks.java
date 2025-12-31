package Hooks;

import io.cucumber.java.Before;

import org.openqa.selenium.WebDriver;

import Utils.commonUtils;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import webElements.loginPageObjects;

public class TestHooks {
	
	public loginPageObjects loginPageObj;

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
    }
    
    public WebDriver launchBrowser() {
        WebDriver driver = commonUtils.launchBrowser();
        loginPageObj = new loginPageObjects(driver);
        return driver;
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("Finished Scenario: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());
    }
}
