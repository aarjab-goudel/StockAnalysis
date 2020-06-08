package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.BSSheetCalculation;
import create.excel.implementation.CreateExcelFile;

public class BSSheetCalculationTest {

	private static BSSheetCalculation bsSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateExcelFile excelFile = new CreateExcelFile();
		bsSheetCalculation = new BSSheetCalculation(0, excelFile.getBSSheet());
	}

	@Test
	public void testSetUpNetEquityFormula() {
		String colOneNetEquityFormula = "(H1+I1+J1+K1)-(L1+M1+N1)";
		assertEquals("The netEquityFormula is wrong for column one!", colOneNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(1));

		String colTwoNetEquityFormula = "(H2+I2+J2+K2)-(L2+M2+N2)";
		assertEquals("The netEquityFormula is wrong for column two!", colTwoNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(2));

		String colThirteenNetEquityFormula = "(H13+I13+J13+K13)-(L13+M13+N13)";
		assertEquals("The netEquityFormula is wrong for column thirteen!", colThirteenNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(13));
	}

	@Test
	public void testSetUpNetEquityGrowthFormula() {
		String colOneNetEquityGrowthFormula = "(F1-BS!F1)/(BS!F1)";
		assertEquals("The netEquityGrowthFormula is wrong for column one!", colOneNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 1, 0));

		String colThirteenNetEquityGrowthFormula = "(F13-BSOne!F13)/(BSOne!F13)";
		assertEquals("The netEquityGrowthFormula is wrong for column thirteen!", colThirteenNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 13, -1));

		String colHundredTwentyNetEquityGrowthFormula = "(F120-BSTwo!F120)/(BSTwo!F120)";
		assertEquals("The netEquityGrowthFormula is wrong for column one hundred and twenty!",
				colHundredTwentyNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 120, -2));

		String colThirtyFourNetEquityGrowthFormula = "(F34-BSThree!F34)/(BSThree!F34)";
		assertEquals("The netEquityGrowthFormula is wrong for column thirty four!", colThirtyFourNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 34, -3));

		String colTwoThousandAndTwoHundredAndFiftyFourNetEquityGrowthFormula = "(F2254-BSFour!F2254)/(BSFour!F2254)";
		assertEquals("The netEquityGrowthFormula is wrong for column two thousand and two hundred and fifty four!",
				colTwoThousandAndTwoHundredAndFiftyFourNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 2254, -4));
	}
}
