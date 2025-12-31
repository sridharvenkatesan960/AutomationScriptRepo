Feature: Launch Naukri and search jobs - Data from xlsx File

Background: Launch Browser
Given User enters the "URL" from xlsx file Row 1


  @login @xlsxData @test
  Scenario: Login to Naukri Portal from xlsx credentials    
    Then User logs in using "UserName" and "Password" from xlsx file Row 1
    Then Clear the username and password field
    Then User logs in using "UserName" and "Password" from xlsx file Row 2
    
    
    
  @search @test
  Scenario: Search job
  	Then search for jobs based on the "keyWord" and "Exp" and "Location"
  	
    