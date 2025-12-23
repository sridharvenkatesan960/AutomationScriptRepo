package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVReader utility to read data from CSV files
 */
public class CSVReader {

    private String filePath;
    private List<String> headers;
    private List<Map<String, String>> data;

    /**
     * Constructor to initialize the CSV file path
     * @param filePath Path to the CSV file
     */
    public CSVReader(String filePath) {
        this.filePath = filePath;
        this.data = new ArrayList<>();
    }

    /**
     * Load and parse the CSV file
     * @throws IOException If file cannot be read
     */
    public void loadCSV() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstLine) {
                    // First line is header
                    headers = new ArrayList<>();
                    for (String header : values) {
                        headers.add(header.trim());
                    }
                    isFirstLine = false;
                } else {
                    // Data rows
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < values.length && i < headers.size(); i++) {
                        rowData.put(headers.get(i), values[i].trim());
                    }
                    data.add(rowData);
                }
            }
        }
    }

    /**
     * Get all data from CSV
     * @return List of maps where each map represents a row
     */
    public List<Map<String, String>> getAllData() {
        return data;
    }

    /**
     * Get specific row by index
     * @param index Row index (0-based)
     * @return Map containing row data
     */
    public Map<String, String> getRowByIndex(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        }
        return new HashMap<>();
    }

    /**
     * Get row by specific column value
     * @param columnName Column name to search
     * @param value Value to match
     * @return Map containing row data, or empty map if not found
     */
    public Map<String, String> getRowByColumnValue(String columnName, String value) {
        for (Map<String, String> row : data) {
            if (row.containsKey(columnName) && row.get(columnName).equals(value)) {
                return row;
            }
        }
        return new HashMap<>();
    }

    /**
     * Get total number of data rows (excluding header)
     * @return Number of rows
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Get headers
     * @return List of header names
     */
    public List<String> getHeaders() {
        return headers;
    }
}
