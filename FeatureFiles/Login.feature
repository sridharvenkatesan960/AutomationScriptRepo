Feature: Launch Naukri and search jobs
Scenario: Login to naukri

  @login
  Scenario: Login to Naukri Portal
    Given User enters the Url
    Then User Enters 'sridharsush@gmail.com' and 'Gmail@96002'
    And Click on Submit button
    Then Get the page title

	