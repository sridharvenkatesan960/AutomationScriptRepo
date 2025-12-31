package Actions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import webElements.loginPageObjects;
import Utils.commonUtils;
import Utils.DriverManager;
import Utils.ExcelUtils;

public class loginActions {

    public loginPageObjects loginPageObj;
    
    

    public WebDriver launchBrowser() {
        WebDriver driver = commonUtils.launchBrowser();
        loginPageObj = new loginPageObjects(driver);
        return driver;
    }

    public void navigateToUrl(String url, int rowNum) {
    	String loginUrl = ExcelUtils.getCellData("Sheet1",rowNum, url);
        commonUtils.navigateToUrl(loginUrl);
    }

    public void enterUsername(String username, int rowNum) {
    	String userName=ExcelUtils.getCellData("Sheet1", rowNum, username);
        WebElement usernameField = loginPageObj.getUsernameField();
        usernameField.clear();
        usernameField.sendKeys(userName);
    }

    public void enterPassword(String password, int rowNum) {
    	String pass = ExcelUtils.getCellData("Sheet1", rowNum, password);
        WebElement passwordField = loginPageObj.getPasswordField();
        passwordField.clear();
        passwordField.sendKeys(pass);
    }

    public void clickSubmitButton() {
        loginPageObj.getSubmitButton().click();
        try {
            // Wait longer for page to load after login
            Thread.sleep(8000);
            String currentUrl = getDriver().getCurrentUrl();
            System.out.println("Current URL after login: " + currentUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPageTitle() {
        return commonUtils.getPageTitle();
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }
    
    public void logout() {
    	loginPageObj.getProfileIcon().click();
    	loginPageObj.getLogoutOption().click();
    }
    
    public void clickLogin() {
    	loginPageObj.getLoginButton().click();
    }
    
    public void errorMessage() {
    	String ErrorMsg = loginPageObj.getInvalidErrorMsg().getText();
    	System.out.println(ErrorMsg);
    }
    
    public void clearFields() {
    	loginPageObj.getUsernameField().clear();
    	loginPageObj.getPasswordField().clear();
    }
    
    public void searchJobs(String keyword, String experience, String location) {
    	DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	loginPageObj.getSearchField().click();
    	String keyto = ExcelUtils.getCellData("Sheet1",1,keyword);
    	System.out.println(keyto);
    	String expe = ExcelUtils.getCellData("Sheet1",1,experience);
    	System.out.println(expe);
    	String loca = ExcelUtils.getCellData("Sheet1",1,location);
    	System.out.println(loca);    	
    	
    	loginPageObj.getSearchFieldkeyword().sendKeys(keyto);
    	
    	loginPageObj.getSearchFieldExperience().click();    	
    	WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    	List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='sa-dd-scrollexperienceDD']")));

    	for (WebElement webElement : list) {
			if(webElement.getText().equalsIgnoreCase(expe)) {
				webElement.click();
				break;
			}
		}

    	
    	loginPageObj.getSearchFieldLocation().sendKeys(loca);    	
    	loginPageObj.getSearchIcon().click();
    }
    
}
