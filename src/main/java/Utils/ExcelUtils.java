package Utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelUtils {
	
	public static String getCellData(String sheetName, int colNum, String propertyName) {
		try {

			FileInputStream fis = new FileInputStream("TestData/InputData.xlsx");
			Workbook wbk = WorkbookFactory.create(fis);
			Sheet sheet = wbk.getSheet(sheetName);

			if (sheet == null) {
				System.out.println("ERROR: Sheet '" + sheetName + "' not found!");
				System.out.println("Available sheets:");
				for (int i = 0; i < wbk.getNumberOfSheets(); i++) {
					System.out.println("  - " + wbk.getSheetName(i));
				}
				wbk.close();
				throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in InputData.xlsx");
			}

			// Find the row where column 0 matches the property name
			int targetRow = -1;
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null && row.getCell(0) != null) {
					String cellValue = row.getCell(0).getStringCellValue();
					if (cellValue.equalsIgnoreCase(propertyName)) {
						targetRow = i;
						break;
					}
				}
			}

			if (targetRow == -1) {
				wbk.close();
				throw new IllegalArgumentException("Property '" + propertyName + "' not found in column 0 of Sheet1");
			}

			// Get the value from the specified column in that row
			Row dataRow = sheet.getRow(targetRow);
			if (dataRow.getCell(colNum) == null) {
				wbk.close();
				throw new IllegalArgumentException("No data found at row " + targetRow + ", column " + colNum);
			}

			String value = dataRow.getCell(colNum).getStringCellValue();

			wbk.close();
			return value;


		}catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

}
