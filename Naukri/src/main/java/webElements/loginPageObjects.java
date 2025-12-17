package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class loginPageObjects {

    private WebDriver driver;

    // Constructor
    public loginPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    // Web Element Locators
    private By usernameField = By.id("usernameField");
    private By passwordField = By.id("passwordField");
    private By submitButton = By.xpath("//button[@type='submit']");

    // Methods to return WebElements
    public WebElement getUsernameField() {
        return driver.findElement(usernameField);
    }

    public WebElement getPasswordField() {
        return driver.findElement(passwordField);
    }

    public WebElement getSubmitButton() {
        return driver.findElement(submitButton);
    }
}
