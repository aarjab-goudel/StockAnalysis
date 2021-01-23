/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.future.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.future.CreateFutureDataExcelFile;

public class CreateFutureExcelDataTest {
	private static CreateFutureDataExcelFile excelFile;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateFutureDataExcelFile();
	}

	@Test
	public void testNumSheets() {
		int numExcelSheets = excelFile.getExcelFile().getNumberOfSheets();
		assertEquals("Number of sheets is not 1!", numExcelSheets, 1);
	}

}
