package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.RatioSheetCalculation;
import create.excel.implementation.CreateExcelFile;

public class RatioSheetCalculationTest {

	private static RatioSheetCalculation ratioSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateExcelFile excelFile = new CreateExcelFile();
		ratioSheetCalculation = new RatioSheetCalculation(0, excelFile.getRatioSheet());
	}

	@Test
	public void testRatioSheetGrowthFormulas() {
		String colThirteenEPSGrowthFormula = "(H13-RatiosOne!H13)/(RatiosOne!H13)";
		assertEquals("The EPSGrowthFormula is wrong for column thirteen!", colThirteenEPSGrowthFormula,
				ratioSheetCalculation.setUpGrowthFormula(RatioSheetCalculation.getEpsLetter(), "Ratios", 13, -1));

		String colHundredAndTwentyCurrentRatioGrowthFormula = "(J120-RatiosTwo!J120)/(RatiosTwo!J120)";
		assertEquals("The currentRatioGrowthFormula is wrong for column hundred and twenty!",
				colHundredAndTwentyCurrentRatioGrowthFormula, ratioSheetCalculation
						.setUpGrowthFormula(RatioSheetCalculation.getCurrentRatioLetter(), "Ratios", 120, -2));

		String colTwoThousandAndToHundredAndFiftyFourDebtEquityGrowthFormula = "(L2254-RatiosThree!L2254)/(RatiosThree!L2254)";
		assertEquals("The debtEquityGrowthFormula is wrong for column two thousand two hundred and fifty four!",
				colTwoThousandAndToHundredAndFiftyFourDebtEquityGrowthFormula, ratioSheetCalculation
						.setUpGrowthFormula(RatioSheetCalculation.getDebtEquityRatioLetter(), "Ratios", 2254, -3));

	}

}
