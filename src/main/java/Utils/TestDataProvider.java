package Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TestDataProvider {

    private ExcelReader excelReader;
    private CSVReader csvReader;
    private String dataFilePath;

    public TestDataProvider(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

   
    public List<Map<String, String>> loadTestDataFromExcel(String sheetName) throws IOException {
        excelReader = new ExcelReader(dataFilePath);
        excelReader.loadSheet(sheetName);
        List<Map<String, String>> data = excelReader.getAllData();
        excelReader.close();
        return data;
    }

    public List<Map<String, String>> loadTestDataFromCSV() throws IOException {
        csvReader = new CSVReader(dataFilePath);
        csvReader.loadCSV();
        return csvReader.getAllData();
    }

   
    public Map<String, String> getLoginCredentials(String testCaseId, String sheetName) throws IOException {
        List<Map<String, String>> allData;

        
        if (dataFilePath.toLowerCase().endsWith(".csv")) {
            allData = loadTestDataFromCSV();
        } else {
            allData = loadTestDataFromExcel(sheetName);
        }

        for (Map<String, String> row : allData) {
            if (row.get("TestCase").equals(testCaseId)) {
                return row;
            }
        }

        throw new IllegalArgumentException("Test case " + testCaseId + " not found in test data");
    }

   
    public Map<String, String> getLoginCredentialsFromCSV(String testCaseId) throws IOException {
        csvReader = new CSVReader(dataFilePath);
        csvReader.loadCSV();
        return csvReader.getRowByColumnValue("TestCase", testCaseId);
    }

   
    public static String getDefaultTestDataPath() {
        String projectPath = System.getProperty("user.dir");
        return projectPath + "/TestData/";
    }

    
    public static String getLoginDataFilePath() {
        return getDefaultTestDataPath() + "LoginData.xlsx";
    }

   
    public static String getLoginDataCSVPath() {
        return getDefaultTestDataPath() + "LoginData.csv";
    }
}
