Feature: Launch Naukri and search jobs - Data from CSV File

  @login @csvData
  Scenario: Login to Naukri Portal with TC001 from CSV
    Given User enters the Url
    Then User logs in using test case "TC001" from CSV file
    And Click on Submit button
    Then Get the page title

  @login @csvData
  Scenario: Login to Naukri Portal with TC002 from CSV
    Given User enters the Url
    Then User logs in using test case "TC002" from CSV file
    And Click on Submit button
    Then Get the page title
    
