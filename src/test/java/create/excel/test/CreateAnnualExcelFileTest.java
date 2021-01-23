/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.implementation.CreateAnnualExcelFile;

public class CreateAnnualExcelFileTest {
	private static CreateAnnualExcelFile excelFile;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateAnnualExcelFile();
	}

	@Test
	public void testNumSheets() {
		int numExcelSheets = excelFile.getExcelFile().getNumberOfSheets();
		assertEquals("Number of sheets is not 21!", numExcelSheets, (int) (CreateAnnualExcelFile.getNumSheets()));
	}

}
