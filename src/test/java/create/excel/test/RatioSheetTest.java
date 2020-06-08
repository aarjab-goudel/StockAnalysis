package create.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.bo.RatioInfoBO;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.RatioSheetConstants;
import create.excel.implementation.CreateExcelFile;
import create.excel.implementation.RatioSheet;

public class RatioSheetTest {

	private static CreateExcelFile excelFile;
	private static RatioSheet ratioSheet;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateExcelFile();
		ratioSheet = excelFile.getRatioSheet();
	}

	@Test
	public void testRatioSheetColumns() {
		Row firstRow = excelFile.getRatioSheet().getFirstRow();

		Cell firstColumn = firstRow.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		assertEquals("First column is not stocks! ", firstColumn.getStringCellValue(), "Stock");

		Cell thirdColumn = firstRow.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		assertEquals("Third column is not ticker!", thirdColumn.getStringCellValue(), "Ticker");

		Cell fourthColumn = firstRow.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		assertEquals("Fourth column is Sector!", fourthColumn.getStringCellValue(), "Sector");

		Cell fifthColumn = firstRow.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		assertEquals("Fifth column is not Industry!", fifthColumn.getStringCellValue(), "Industry");

		Cell eighthColumn = firstRow.getCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData());
		assertEquals("Eighth column is not earnings per share!", eighthColumn.getStringCellValue(),
				"Earnings Per Share");

		Cell ninethColumn = firstRow.getCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData());
		assertEquals("Nineth column is not current ratio!", ninethColumn.getStringCellValue(), "Current Ratio");

		Cell tenthColumn = firstRow.getCell(RatioSheetConstants.CURRENT_RATIO_GROWTH.getRatioData());
		assertEquals("Tenth column is not current ratio growth!", tenthColumn.getStringCellValue(),
				"Current Ratio Growth");

		Cell eleventhColumn = firstRow.getCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData());
		assertEquals("Eleventh column is not debt to equity ratio!", eleventhColumn.getStringCellValue(),
				"Debt to Equity Ratio");

		Cell twelvthColumn = firstRow.getCell(RatioSheetConstants.DEBT_EQUITY_RATIO_GROWTH_COLUMN.getRatioData());
		assertEquals("Twelvth column is not debt to equity ratio growth!", twelvthColumn.getStringCellValue(),
				"Debt to Equity Ratio Growth");

	}

	@Test
	public void testRatioSheetValues() {
		List<String> companies = ratioSheet.getDataService().getOrderedCompanies();
		Map<String, Row> tickerToRow = ratioSheet.getTickerToRow();
		for (int i = 0; i < companies.size(); i++) {
			String ticker = companies.get(i);
			Row tickerRow = tickerToRow.get(ticker);
			RatioInfoBO tickerRatioInfo = excelFile.getDataService().getTickerToRatioInfo().get(ticker)
					.get(Math.abs(0));

			assertEquals("Earnings per share is wrong! ", tickerRatioInfo.getEarningsPerShare(), tickerRow
					.getCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData()).getStringCellValue());
			assertEquals("Current Ratio is wrong!", tickerRatioInfo.getCurrentRatio(),
					tickerRow.getCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData()).getStringCellValue());
			assertEquals("Debt to Equity Ratio is wrong!", tickerRatioInfo.getDebtToEquityRatio(), tickerRow
					.getCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData()).getStringCellValue());
		}
	}

}
