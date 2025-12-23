package StepDefenitions;

import Actions.loginActions;
import Utils.TestDataProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import java.io.IOException;
import java.util.Map;

public class DataDrivenLoginSteps {

    loginActions loginAction;
    private TestDataProvider dataProvider;

    public DataDrivenLoginSteps() {
        loginAction = new loginActions();
        dataProvider = new TestDataProvider(TestDataProvider.getDefaultTestDataPath() + "LoginData.xlsx");
    }

   
    @Then("User logs in using test case {string} from Excel sheet {string}")
    public void userLogsInUsingTestCaseFromExcel(String testCaseId, String sheetName) {
        try {
            Map<String, String> credentials = dataProvider.getLoginCredentials(testCaseId, sheetName);
            String email = credentials.get("Email");
            String password = credentials.get("Password");

            System.out.println("Logging in with Test Case: " + testCaseId);
            System.out.println("Description: " + credentials.get("Description"));

            loginAction.enterUsername(email);
            loginAction.enterPassword(password);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from Excel: " + e.getMessage());
        }
    }

    @Then("User logs in using row {int} from Excel sheet {string}")
    public void userLogsInUsingRowFromExcel(int rowNumber, String sheetName) {
        try {
            dataProvider = new TestDataProvider(TestDataProvider.getDefaultTestDataPath() + "LoginData.xlsx");
            java.util.List<Map<String, String>> allData = dataProvider.loadTestDataFromExcel(sheetName);

            if (rowNumber >= allData.size()) {
                throw new IllegalArgumentException("Row number " + rowNumber + " is out of bounds");
            }

            Map<String, String> credentials = allData.get(rowNumber);
            String email = credentials.get("Email");
            String password = credentials.get("Password");

            System.out.println("Logging in with data from row: " + rowNumber);
            System.out.println("Test Case: " + credentials.get("TestCase"));
            System.out.println("Description: " + credentials.get("Description"));

            loginAction.enterUsername(email);
            loginAction.enterPassword(password);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from Excel: " + e.getMessage());
        }
    }
}
