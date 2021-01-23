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

import calculation.excel.implementation.BSSheetCalculation;
import create.excel.implementation.CreateAnnualExcelFile;

public class BSSheetCalculationTest {

	private static BSSheetCalculation bsSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateAnnualExcelFile excelFile = new CreateAnnualExcelFile();
		bsSheetCalculation = new BSSheetCalculation(0, excelFile.getBSSheet());
	}

	@Test
	public void testSetUpNetEquityFormula() {
		String colOneNetEquityFormula = "(L1+M1+O1)-(P1+R1)";
		assertEquals("The netEquityFormula is wrong for column one!", colOneNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(1));

		String colTwoNetEquityFormula = "(L2+M2+O2)-(P2+R2)";
		assertEquals("The netEquityFormula is wrong for column two!", colTwoNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(2));

		String colThirteenNetEquityFormula = "(L13+M13+O13)-(P13+R13)";
		assertEquals("The netEquityFormula is wrong for column thirteen!", colThirteenNetEquityFormula,
				bsSheetCalculation.setUpNetEquityFormula(13));
	}

	@Test
	public void testSetUpNetEquityGrowthFormula() {
		String colOneNetEquityGrowthFormula = "(G1-BS!G1)/(BS!G1)";
		assertEquals("The netEquityGrowthFormula is wrong for column one!", colOneNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 1, 0));

		String colThirteenNetEquityGrowthFormula = "(G13-BSOne!G13)/(BSOne!G13)";
		assertEquals("The netEquityGrowthFormula is wrong for column thirteen!", colThirteenNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 13, -1));

		String colHundredTwentyNetEquityGrowthFormula = "(G120-BSTwo!G120)/(BSTwo!G120)";
		assertEquals("The netEquityGrowthFormula is wrong for column one hundred and twenty!",
				colHundredTwentyNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 120, -2));

		String colThirtyFourNetEquityGrowthFormula = "(G34-BSThree!G34)/(BSThree!G34)";
		assertEquals("The netEquityGrowthFormula is wrong for column thirty four!", colThirtyFourNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 34, -3));

		String colTwoThousandAndTwoHundredAndFiftyFourNetEquityGrowthFormula = "(G2254-BSFour!G2254)/(BSFour!G2254)";
		assertEquals("The netEquityGrowthFormula is wrong for column two thousand and two hundred and fifty four!",
				colTwoThousandAndTwoHundredAndFiftyFourNetEquityGrowthFormula,
				bsSheetCalculation.setUpGrowthFormula(BSSheetCalculation.getNetEquityLetter(), "BS", 2254, -4));
	}
}
