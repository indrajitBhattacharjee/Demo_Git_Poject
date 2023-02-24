package data;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelDataReader implements DataReader {
	public String[][] readData(String filename) {
		try {
			// Load the Excel file
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);

			// Determine the dimensions of the data
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
			int colCount = sheet.getRow(0).getLastCellNum();

			// Read the data into a 2D string array
			String[][] data = new String[rowCount][colCount];
			for (int i = 0; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);
					data[i][j] = cell.getStringCellValue();
				}
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

