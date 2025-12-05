package StepDefenitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import Actions.loginActions;

public class LoginStepDefenition {

    loginActions loginAction;

    public LoginStepDefenition() {
        loginAction = new loginActions();
    }

    @Given("User enters the Url")
    public void user_enters_the_url() {
        loginAction.launchBrowser();
        loginAction.navigateToUrl("https://www.naukri.com/nlogin/login");
    }

    @Then("User Enters {string} and {string}")
    public void user_enters_username_and_password(String email, String password) {
        loginAction.enterUsername(email);
        loginAction.enterPassword(password);
    }

    @And("Click on Submit button")
    public void click_on_submit_button() {
        loginAction.clickSubmitButton();
    }

    @Then("Get the page title")
    public void get_the_page_title() {
        loginAction.getPageTitle();
    }
}
