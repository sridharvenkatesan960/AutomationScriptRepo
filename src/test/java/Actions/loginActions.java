package Actions;

import org.openqa.selenium.WebDriver;
import webElements.loginPageObjects;
import Utils.commonUtils;
import Utils.DriverManager;

public class loginActions {

    private loginPageObjects loginPageObj;

    public WebDriver launchBrowser() {
        WebDriver driver = commonUtils.launchBrowser();        
        loginPageObj = new loginPageObjects(driver);
        return driver;
    }

    public void navigateToUrl(String url) {
        commonUtils.navigateToUrl(url);
    }

    public void enterUsername(String username) {
        loginPageObj.getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        loginPageObj.getPasswordField().sendKeys(password);
    }

    public void clickSubmitButton() {
        loginPageObj.getSubmitButton().click();
    }

    public String getPageTitle() {
        return commonUtils.getPageTitle();
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
