/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.CFSheetCalculation;
import create.excel.implementation.CreateAnnualExcelFile;

public class CFSheetCalculationTest {

	private static CFSheetCalculation cfSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateAnnualExcelFile excelFile = new CreateAnnualExcelFile();
		cfSheetCalculation = new CFSheetCalculation(0, excelFile.getCFSheet());
	}

	@Test
	public void testSetUpNetCashByOperatingActivitiesGrowthFormula() {
		String colOneNetCashByOperatingActivitesGrowthFormula = "(I1-CF!I1)/(CF!I1)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column one!",
				colOneNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 1, 0));

		String colThirteenNetCashByOperatingActivitesGrowthFormula = "(I13-CFOne!I13)/(CFOne!I13)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column thirteen!",
				colThirteenNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 13, -1));

		String colHundredTwentyNetCashByOperatingActivitesGrowthFormula = "(I120-CFTwo!I120)/(CFTwo!I120)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column one hundred and twenty!",
				colHundredTwentyNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 120, -2));

		String colThirtyFourNetCashByOperatingActivitesGrowthFormula = "(I34-CFThree!I34)/(CFThree!I34)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column thirty four!",
				colThirtyFourNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 34, -3));

		String colTwoThousandAndTwoHundredAndFiftyFourNetCashByOperatingActivitesGrowthFormula = "(I2254-CFFour!I2254)/(CFFour!I2254)";
		assertEquals(
				"The NetCashByOperatingActivitesGrowthFormula is wrong for column two thousand and two hundred and fifty four!",
				colTwoThousandAndTwoHundredAndFiftyFourNetCashByOperatingActivitesGrowthFormula, cfSheetCalculation
						.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 2254, -4));

		String colNineFreeCashFlowGrowthFormula = "(G9-CFOne!G9)/(CFOne!G9)";
		assertEquals("The free cash flow growth formula is wrong for column nine!", colNineFreeCashFlowGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getFreeCashFlowLetter(), "CF", 9, -1));

	}

}
