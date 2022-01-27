package dataprovider;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelApiTest {

	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;
	String xlFilePath;

	public WriteExcelApiTest(String xlFilePath) throws Exception {
		this.xlFilePath = xlFilePath;
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);
		fis.close();
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String value) {
		try {
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			//System.out.println(sheet);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
//				System.out.println(row.getLastCellNum());
//				System.out.println(i);
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
//					System.out.println(row.getCell(i).getStringCellValue().trim());
//					System.out.println(colName.trim());
					col_Num = i;
				}
			}

		//	sheet.autoSizeColumn(col_Num);

			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(col_Num);
			if (cell == null)
				cell = row.createCell(col_Num);

			cell.setCellValue(value);
			
			fos = new FileOutputStream(xlFilePath);
			workbook.write(fos);
			fos.close();
			workbook=new XSSFWorkbook(new FileInputStream(xlFilePath));
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}
