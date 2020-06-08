package calculation.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import calculation.excel.implementation.StockSheetCalculation;
import create.excel.implementation.CreateExcelFile;

public class StockSheetCalculationTest {

	private static StockSheetCalculation stockSheetCalculation;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		CreateExcelFile excelFile = new CreateExcelFile();
		stockSheetCalculation = new StockSheetCalculation(0, excelFile.getStockDataSheet());
	}

	@Test
	public void testAverageRatiosFormulas() {

		String colThirteenAverageCurrentRatio = "(Ratios!I13+RatiosOne!I13+RatiosTwo!I13+RatiosThree!I13+RatiosFour!I13)/5";
		assertEquals("The average current ratio is wrong for column thirteen!", colThirteenAverageCurrentRatio,
				stockSheetCalculation.setUpAverageRatioFormula(StockSheetCalculation.getCurrentRatioLetter(), 13));

		String colTwoHundredAndFiftyFourAverageDebtEquityRatio = "(Ratios!K254+RatiosOne!K254+RatiosTwo!K254+RatiosThree!K254+RatiosFour!K254)/5";
		assertEquals("The average debt to equity ratio is wrong for column two hundred and fifty four!",
				colTwoHundredAndFiftyFourAverageDebtEquityRatio,
				stockSheetCalculation.setUpAverageRatioFormula(StockSheetCalculation.getDebtEquityRatioLetter(), 254));

	}

}
