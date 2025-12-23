package StepDefenitions;

import Actions.loginActions;
import Utils.TestDataProvider;
import Utils.DriverManager;
import webElements.loginPageObjects;
import io.cucumber.java.en.Then;
import java.io.IOException;
import java.util.Map;

public class CSVDataDrivenLoginSteps {

    private TestDataProvider dataProvider;
    private loginPageObjects loginPageObj;

    public CSVDataDrivenLoginSteps() {
      
        dataProvider = new TestDataProvider(TestDataProvider.getLoginDataCSVPath());
    }

    
    @Then("User logs in using test case {string} from CSV file")
    public void userLogsInUsingTestCaseFromCSV(String testCaseId) {
        try {
        
            loginPageObj = new loginPageObjects(DriverManager.getDriver());

            Map<String, String> credentials = dataProvider.getLoginCredentialsFromCSV(testCaseId);
            String email = credentials.get("Email");
            String password = credentials.get("Password");

            System.out.println("Reading data from CSV file...");
            System.out.println("Test Case: " + testCaseId);
            System.out.println("Description: " + credentials.get("Description"));
            System.out.println("Email: " + email);

            loginPageObj.getUsernameField().sendKeys(email);
            loginPageObj.getPasswordField().sendKeys(password);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from CSV: " + e.getMessage());
        }
    }

    @Then("User logs in using row {int} from CSV file")
    public void userLogsInUsingRowFromCSV(int rowNumber) {
        try {
            
            loginPageObj = new loginPageObjects(DriverManager.getDriver());

            java.util.List<Map<String, String>> allData = dataProvider.loadTestDataFromCSV();

            if (rowNumber >= allData.size()) {
                throw new IllegalArgumentException("Row number " + rowNumber + " is out of bounds. CSV has " + allData.size() + " rows.");
            }

            Map<String, String> credentials = allData.get(rowNumber);
            String email = credentials.get("Email");
            String password = credentials.get("Password");

            System.out.println("Reading data from CSV file...");
            System.out.println("Row: " + rowNumber);
            System.out.println("Test Case: " + credentials.get("TestCase"));
            System.out.println("Description: " + credentials.get("Description"));
            System.out.println("Email: " + email);

            loginPageObj.getUsernameField().sendKeys(email);
            loginPageObj.getPasswordField().sendKeys(password);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from CSV: " + e.getMessage());
        }
    }
}
