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

import calculation.excel.implementation.StockSheetCalculation;
import create.excel.implementation.CreateAnnualExcelFile;

public class StockSheetCalculationTest {

	private static StockSheetCalculation stockSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateAnnualExcelFile excelFile = new CreateAnnualExcelFile();
		stockSheetCalculation = new StockSheetCalculation(0, excelFile.getStockDataSheet());
	}

	@Test
	public void testAverageRatiosFormulas() {

		String colThirteenAverageCurrentRatio = "(Ratios!H13+RatiosOne!H13+RatiosTwo!H13+RatiosThree!H13+RatiosFour!H13)/5";
		assertEquals("The average current ratio is wrong for column thirteen!", colThirteenAverageCurrentRatio,
				stockSheetCalculation.setUpAverageRatioFormula(StockSheetCalculation.getCurrentRatioLetter(), 13));

		String colTwoHundredAndFiftyFourAverageDebtEquityRatio = "(Ratios!J254+RatiosOne!J254+RatiosTwo!J254+RatiosThree!J254+RatiosFour!J254)/5";
		assertEquals("The average debt to equity ratio is wrong for column two hundred and fifty four!",
				colTwoHundredAndFiftyFourAverageDebtEquityRatio,
				stockSheetCalculation.setUpAverageRatioFormula(StockSheetCalculation.getDebtEquityRatioLetter(), 254));

	}

}
