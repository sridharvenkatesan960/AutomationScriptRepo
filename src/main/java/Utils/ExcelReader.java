package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private String filePath;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public void loadSheet(String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in the Excel file");
        }
    }

    public List<Map<String, String>> getAllData() {
        List<Map<String, String>> data = new ArrayList<>();

        if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
            return data;
        }

        // Get headers from first row
        Row headerRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValueAsString(cell));
        }

        // Read data rows
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValueAsString(cell);
                    rowData.put(headers.get(j), cellValue);
                }
                data.add(rowData);
            }
        }

        return data;
    }

    /**
     * Read data from a specific row
     * @param rowNum Row number (0-based, excluding header)
     * @return Map with column headers as keys
     */
    public Map<String, String> getRowData(int rowNum) {
        Map<String, String> rowData = new HashMap<>();

        if (sheet == null) {
            return rowData;
        }

        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowNum + 1); // +1 because row 0 is header

        if (headerRow != null && dataRow != null) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String header = getCellValueAsString(headerRow.getCell(i));
                String value = getCellValueAsString(dataRow.getCell(i));
                rowData.put(header, value);
            }
        }

        return rowData;
    }

    /**
     * Get cell value as string regardless of cell type
     * @param cell Cell to read
     * @return String value of the cell
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Get the total number of rows (excluding header)
     * @return Number of data rows
     */
    public int getRowCount() {
        if (sheet == null) {
            return 0;
        }
        return sheet.getLastRowNum();
    }

    /**
     * Close the workbook
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
