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
    private By usernameField = By.xpath("//input[contains(@placeholder,'Email ID')]");
    private By passwordField = By.xpath("//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'password')]");
    private By submitButton = By.xpath("//button[@type='submit']");
    private By profileIcon = By.xpath("//div[@class='nI-gNb-drawer__icon-img-wrapper']/img");
    private By logOutOption = By.xpath("//div[@class='nI-gNb-de__list']//following::div[3]/a");
    private By loginButton = By.xpath("//a[@title='Jobseeker Login']");
    private By invalidCredUserNameErr = By.xpath("//div[@class='col s12 commonErrorMsg']");
    private By searchField = By.xpath("//span[text()='Search jobs here']");
    private By searchFieldKey = By.xpath("//input[@placeholder='Enter keyword / designation / companies']");
    private By searchFieldExp = By.xpath("//input[@placeholder='Select experience']");
    private By searchFieldLoc = By.xpath("//input[@placeholder='Enter location']");
    private By searchIcon = By.xpath("//span[text()='Search']");
    

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
    
    public WebElement getProfileIcon() {
    	return driver.findElement(profileIcon);
    }
    
    public WebElement getLogoutOption() {
    	return driver.findElement(logOutOption);
    }
    
    public WebElement getLoginButton() {
    	return driver.findElement(loginButton);
    }
    
    public WebElement getInvalidErrorMsg() {
    	return driver.findElement(invalidCredUserNameErr);
    }
    
    public WebElement getSearchField() {
    	return driver.findElement(searchField);
    }
    
    public WebElement getSearchFieldkeyword() {
    	return driver.findElement(searchFieldKey);
    }
    
    public WebElement getSearchFieldExperience() {
    	return driver.findElement(searchFieldExp);
    }
    
    public WebElement getSearchFieldLocation() {
    	return driver.findElement(searchFieldLoc);
    }
    
    public WebElement getSearchIcon() {
    	return driver.findElement(searchIcon);
    }
}
