# External Test Data Configuration Guide

This guide explains how to use external data sources (Excel/CSV) for your Cucumber tests instead of hard-coded values.

## Overview

The framework now supports reading test data from external files, making it easy to maintain and update test data without modifying code.

## Approaches Available

### Approach 1: Scenario Outline with Examples (Recommended for Simple Cases)

**File**: `FeatureFiles/Login.feature`

This is the standard Cucumber approach where data is defined in the Examples table within the feature file.

```gherkin
Scenario Outline: Login to Naukri Portal with <TestCase>
  Given User enters the Url
  Then User Enters "<Email>" and "<Password>"
  And Click on Submit button
  Then Get the page title

  Examples:
    | TestCase | Email                   | Password     | Description              |
    | TC001    | sridharsush@gmail.com   | Gmail@96002  | Valid login credentials  |
    | TC002    | invalid@test.com        | WrongPass123 | Invalid credentials test |
```

**Pros**:
- Clean and readable
- Cucumber best practice
- Easy to understand test scenarios

**Cons**:
- Data is still in the feature file
- Need to update feature file for data changes

### Approach 2: Direct Excel Reading (Recommended for Large Datasets)

**File**: `FeatureFiles/LoginWithExcelData.feature`

This approach reads data directly from Excel files using custom step definitions.

```gherkin
Scenario: Login using specific test case from Excel
  Given User enters the Url
  Then User logs in using test case "TC001" from Excel sheet "LoginData"
  And Click on Submit button
  Then Get the page title
```

**Pros**:
- Data completely separated from code
- Easy to maintain large datasets
- Non-technical users can update test data
- Can use same data across multiple scenarios

**Cons**:
- Slightly more complex setup
- Requires Excel file management

## Setup Instructions

### Step 1: Prepare Your Test Data File

#### Option A: Use CSV File (Already Created)

The CSV file is already created at: `TestData/LoginData.csv`

```csv
TestCase,Email,Password,Description
TC001,sridharsush@gmail.com,Gmail@96002,Valid login credentials
TC002,invalid@test.com,WrongPass123,Invalid credentials test
```

#### Option B: Convert to Excel (Recommended)

1. Open `TestData/LoginData.csv` in Microsoft Excel or Google Sheets
2. Save it as `TestData/LoginData.xlsx`
3. Make sure the sheet name is "LoginData"

**Excel Structure**:
```
Column A: TestCase
Column B: Email
Column C: Password
Column D: Description
```

### Step 2: Choose Your Approach

#### For Approach 1 (Scenario Outline):
- Use the existing `FeatureFiles/Login.feature`
- Update the Examples table with your test data
- No additional setup needed

#### For Approach 2 (Excel Reading):
1. Ensure you have `LoginData.xlsx` in the `TestData` folder
2. Use `FeatureFiles/LoginWithExcelData.feature`
3. Run tests with tags: `@loginFromExcel` or `@loginFromExcelRow`

### Step 3: Update Test Data

#### For CSV/Excel Files:
1. Open `TestData/LoginData.xlsx`
2. Add/modify rows as needed
3. Keep the header row intact
4. Save the file

#### Adding New Test Cases:
```excel
TestCase | Email              | Password    | Description
---------|-------------------|-------------|------------------
TC003    | user3@test.com    | Pass@123    | Another test case
TC004    | user4@test.com    | Pass@456    | Yet another case
```

## Running Tests

### Run with Scenario Outline:
```bash
mvn test -Dcucumber.filter.tags="@login"
```

### Run with Excel Data:
```bash
# Run specific test case from Excel
mvn test -Dcucumber.filter.tags="@loginFromExcel"

# Run using row number
mvn test -Dcucumber.filter.tags="@loginFromExcelRow"
```

## Utility Classes

### ExcelReader.java
Located at: `src/main/java/Utils/ExcelReader.java`

**Purpose**: Reads Excel files and returns data as List of Maps

**Key Methods**:
- `loadSheet(String sheetName)`: Load a specific sheet
- `getAllData()`: Get all data from the sheet
- `getRowData(int rowNum)`: Get specific row data

**Example Usage**:
```java
ExcelReader reader = new ExcelReader("TestData/LoginData.xlsx");
reader.loadSheet("LoginData");
List<Map<String, String>> data = reader.getAllData();
reader.close();
```

### TestDataProvider.java
Located at: `src/main/java/Utils/TestDataProvider.java`

**Purpose**: High-level API for accessing test data

**Key Methods**:
- `loadTestDataFromExcel(String sheetName)`: Load all test data
- `getLoginCredentials(String testCaseId, String sheetName)`: Get specific test case data
- `getLoginDataFilePath()`: Get default Excel file path

**Example Usage**:
```java
TestDataProvider provider = new TestDataProvider(TestDataProvider.getLoginDataFilePath());
Map<String, String> creds = provider.getLoginCredentials("TC001", "LoginData");
String email = creds.get("Email");
String password = creds.get("Password");
```

### DataDrivenLoginSteps.java
Located at: `src/test/java/StepDefenitions/DataDrivenLoginSteps.java`

**Purpose**: Step definitions for Excel-based data-driven testing

**Available Steps**:
1. `Then User logs in using test case "TC001" from Excel sheet "LoginData"`
2. `Then User logs in using row 0 from Excel sheet "LoginData"`

## Best Practices

1. **File Organization**:
   - Keep all test data files in `TestData` folder
   - Use descriptive file names (e.g., `LoginData.xlsx`, `SearchData.xlsx`)
   - Maintain separate sheets for different test scenarios

2. **Data Structure**:
   - Always include a header row
   - Use consistent column names
   - Include a TestCase or ID column for reference
   - Add Description column for documentation

3. **Maintenance**:
   - Review test data regularly
   - Remove obsolete test cases
   - Document any special test data requirements
   - Version control your test data files

4. **Security**:
   - **DO NOT** commit real credentials to version control
   - Use dummy/test credentials only
   - Consider using environment variables for sensitive data

## Troubleshooting

### Issue: "Sheet not found" error
**Solution**: Ensure the sheet name in your Excel file matches the name used in code (default: "LoginData")

### Issue: "File not found" error
**Solution**:
- Check that `LoginData.xlsx` exists in `TestData` folder
- Verify the file path is correct
- Use absolute path for debugging: `new ExcelReader("C:/full/path/to/LoginData.xlsx")`

### Issue: CSV vs Excel
**Solution**: The current implementation uses `.xlsx` files. To use CSV:
1. Update `TestDataProvider.java` to use `getLoginDataCSVPath()` instead
2. Create a CSV reader utility similar to ExcelReader

### Issue: Empty data returned
**Solution**:
- Check that your Excel file has data rows (not just headers)
- Verify column names match exactly (case-sensitive)
- Ensure cells don't have extra spaces

## Extending for Other Scenarios

To add external data for other scenarios (e.g., Search, Profile):

1. Create a new data file: `TestData/SearchData.xlsx`
2. Create corresponding step definitions similar to `DataDrivenLoginSteps.java`
3. Create a feature file using the new steps
4. Update `TestDataProvider.java` with helper methods if needed

## Example: Adding Search Data

### Create Excel File:
`TestData/SearchData.xlsx`
```
Keyword     | Location  | Experience | ExpectedResults
------------|-----------|------------|----------------
Java        | Bangalore | 5          | 100
Python      | Mumbai    | 3          | 50
```

### Create Step Definition:
```java
@Then("User searches using test case {string} from Excel sheet {string}")
public void searchUsingTestCase(String testCaseId, String sheetName) {
    // Similar implementation to DataDrivenLoginSteps
}
```

### Create Feature:
```gherkin
Scenario: Search jobs using Excel data
  Given User enters the Url
  Then User searches using test case "TC001" from Excel sheet "SearchData"
```

## Summary

You now have two flexible approaches:
1. **Scenario Outline**: For readable, inline test data
2. **Excel Reading**: For large, maintainable external datasets

Choose based on your project needs and team preferences.
