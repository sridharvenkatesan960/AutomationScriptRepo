Feature: Login to Naukri using data from Excel file

  # Approach 1: Read data directly from Excel using test case ID
  @loginFromExcel
  Scenario: Login using specific test case from Excel
    Given User enters the Url
    Then User logs in using test case "TC001" from Excel sheet "LoginData"
    And Click on Submit button
    Then Get the page title

  # Approach 2: Read data using row number from Excel
  @loginFromExcelRow
  Scenario: Login using row number from Excel
    Given User enters the Url
    Then User logs in using row 0 from Excel sheet "LoginData"
    And Click on Submit button
    Then Get the page title

  # Note: To use these scenarios, you need to:
  # 1. Convert TestData/LoginData.csv to LoginData.xlsx
  # 2. Or update TestDataProvider to use CSV file path
