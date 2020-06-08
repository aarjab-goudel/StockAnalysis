package create.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.data.service.SaveExcel;
import create.excel.implementation.CreateExcelFile;

public class CreateExcelFileTest {
	private static CreateExcelFile excelFile;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateExcelFile();
	}

	@Test
	public void testSavePath() throws IOException, JSONException {
		assertEquals("C:\\Users\\mythg\\Desktop\\StockData2.xlsx", SaveExcel.getSavePath());
	}

	@Test
	public void testNumSheets() {
		int numExcelSheets = excelFile.getExcelFile().getNumberOfSheets();
		assertEquals("Number of sheets is not 21!", numExcelSheets, (int) (CreateExcelFile.getNumSheets()));
	}

}
