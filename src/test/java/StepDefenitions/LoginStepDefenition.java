package StepDefenitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import Actions.loginActions;

public class LoginStepDefenition {

    loginActions loginAction;

    public LoginStepDefenition() {
        loginAction = new loginActions();
    }

    @Given("User enters the {string} from xlsx file Row {int}")
    public void user_enters_the_url(String url, Integer rowNum) {
        loginAction.launchBrowser();
        loginAction.navigateToUrl(url, rowNum);
    }
    
    @Then("User logs in using {string} and {string} from xlsx file Row {int}")
    public void user_logs_in_using_test_case_and_from_xlsx_file_row(String username, String pwd, Integer rowNum) {
        loginAction.enterUsername(username, rowNum);
        loginAction.enterPassword(pwd, rowNum);
        loginAction.clickSubmitButton();       
        
    }
    
    @Then("Clear the username and password field")
    public void clear_the_username_and_password_field() {
    	loginAction.errorMessage();
        loginAction.clearFields();
    }
    
    @Then("search for jobs based on the {string} and {string} and {string}")
    public void search_for_jobs_based_on_the_details(String key,String exp, String location) {
    	loginAction.searchJobs(key, exp, location);
    }


}
