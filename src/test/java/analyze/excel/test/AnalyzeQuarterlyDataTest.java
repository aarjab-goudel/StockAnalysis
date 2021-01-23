/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import analyze.excel.data.AnalyzeQuarterlyExcelFile;

public class AnalyzeQuarterlyDataTest {

	private static AnalyzeQuarterlyExcelFile analyzeExcel;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		analyzeExcel = new AnalyzeQuarterlyExcelFile();
	}

	@Test
	public void testAnalyzeExcel() {
		assertNotNull("AnalyzeExcel is null!", analyzeExcel);
	}

}
