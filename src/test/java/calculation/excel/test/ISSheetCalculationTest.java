package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.ISSheetCalculation;
import create.excel.implementation.CreateExcelFile;

public class ISSheetCalculationTest {

	private static ISSheetCalculation isSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateExcelFile excelFile = new CreateExcelFile();
		isSheetCalculation = new ISSheetCalculation(0, excelFile.getISSheet());
	}

	@Test
	public void testISSheetGrowthFormulas() {
		String colOneRevenueGrowthFormula = "(F1-IS!F1)/(IS!F1)";
		assertEquals("The RevenueGrowthFormula is wrong for column one!", colOneRevenueGrowthFormula,
				isSheetCalculation.setUpGrowthFormula(ISSheetCalculation.getRevenueLetter(), "IS", 1, 0));

		String colThirteenCostOfRevenueGrowthFormula = "(H13-ISOne!H13)/(ISOne!H13)";
		assertEquals("The CostOfRevenueGrowthFormula is wrong for column thirteen!",
				colThirteenCostOfRevenueGrowthFormula,
				isSheetCalculation.setUpGrowthFormula(ISSheetCalculation.getCostOfRevenueLetter(), "IS", 13, -1));

		String colHundredAndTwentyGrossProfitGrowthFormula = "(J123-ISTwo!J123)/(ISTwo!J123)";
		assertEquals("The GrossProfitGrowthFormula is wrong for column hundred and twenty three!",
				colHundredAndTwentyGrossProfitGrowthFormula,
				isSheetCalculation.setUpGrowthFormula(ISSheetCalculation.getGrossProfitLetter(), "IS", 123, -2));

		String colTwoThousandAndTwoHundredAndFiftyFourNetIncomeGrowthFormula = "(L2254-ISThree!L2254)/(ISThree!L2254)";
		assertEquals("The NetIncomeGrowthFormula is wrong for column two thousand and two hundred and fifty four!",
				colTwoThousandAndTwoHundredAndFiftyFourNetIncomeGrowthFormula,
				isSheetCalculation.setUpGrowthFormula(ISSheetCalculation.getNetIncomeLetter(), "IS", 2254, -3));

	}

}
