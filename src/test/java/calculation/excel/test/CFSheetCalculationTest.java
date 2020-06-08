package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.CFSheetCalculation;
import create.excel.implementation.CreateExcelFile;

public class CFSheetCalculationTest {

	private static CFSheetCalculation cfSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateExcelFile excelFile = new CreateExcelFile();
		cfSheetCalculation = new CFSheetCalculation(0, excelFile.getCFSheet());
	}

	@Test
	public void testSetUpNetCashByOperatingActivitiesGrowthFormula() {
		String colOneNetCashByOperatingActivitesGrowthFormula = "(H1-CF!H1)/(CF!H1)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column one!",
				colOneNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 1, 0));

		String colThirteenNetCashByOperatingActivitesGrowthFormula = "(H13-CFOne!H13)/(CFOne!H13)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column thirteen!",
				colThirteenNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 13, -1));

		String colHundredTwentyNetCashByOperatingActivitesGrowthFormula = "(H120-CFTwo!H120)/(CFTwo!H120)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column one hundred and twenty!",
				colHundredTwentyNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 120, -2));

		String colThirtyFourNetCashByOperatingActivitesGrowthFormula = "(H34-CFThree!H34)/(CFThree!H34)";
		assertEquals("The NetCashByOperatingActivitesGrowthFormula is wrong for column thirty four!",
				colThirtyFourNetCashByOperatingActivitesGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 34, -3));

		String colTwoThousandAndTwoHundredAndFiftyFourNetCashByOperatingActivitesGrowthFormula = "(H2254-CFFour!H2254)/(CFFour!H2254)";
		assertEquals(
				"The NetCashByOperatingActivitesGrowthFormula is wrong for column two thousand and two hundred and fifty four!",
				colTwoThousandAndTwoHundredAndFiftyFourNetCashByOperatingActivitesGrowthFormula, cfSheetCalculation
						.setUpGrowthFormula(CFSheetCalculation.getNetCashByOperatingLetter(), "CF", 2254, -4));

		String colNineFreeCashFlowGrowthFormula = "(F9-CFOne!F9)/(CFOne!F9)";
		assertEquals("The free cash flow growth formula is wrong for column nine!", colNineFreeCashFlowGrowthFormula,
				cfSheetCalculation.setUpGrowthFormula(CFSheetCalculation.getFreeCashFlowLetter(), "CF", 9, -1));

	}

}
