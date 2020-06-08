package create.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.enums.CommonSheetConstants;
import create.excel.enums.StockSheetConstants;
import create.excel.implementation.CreateExcelFile;
import create.excel.implementation.StockSheet;

public class StockSheetTest {

	private static CreateExcelFile excelFile;
	private static StockSheet stockSheet;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateExcelFile();
		stockSheet = excelFile.getStockDataSheet();
	}

	@Test
	public void testStockSheetColumns() {
		Row firstRow = stockSheet.getFirstRow();

		Cell zeroColumn = firstRow.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		assertEquals("First column is not stocks! ", zeroColumn.getStringCellValue(), "Stock");

		Cell secondColumn = firstRow.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		assertEquals("Third column is not ticker!", secondColumn.getStringCellValue(), "Ticker");

		Cell fourthColumn = firstRow.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		assertEquals("Fourth column is Sector!", fourthColumn.getStringCellValue(), "Sector");

		Cell fifthColumn = firstRow.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		assertEquals("Fifth column is not Industry!", fifthColumn.getStringCellValue(), "Industry");

		Cell sixthColumn = firstRow.getCell(StockSheetConstants.BETA_COLUMN.getStockSheetData());
		assertEquals("Sixth column is not Beta!", sixthColumn.getStringCellValue(), "Beta");

		Cell eighthColumn = firstRow.getCell(StockSheetConstants.AVERAGE_PE_RATIO_COLUMN.getStockSheetData());
		assertEquals("Eighth column is not Average P/E Ratio!", eighthColumn.getStringCellValue(), "Average P/E ratio");

		Cell ninthColumn = firstRow.getCell(StockSheetConstants.AVERAGE_CURRENT_RATIO.getStockSheetData());
		assertEquals("Ninth column is not Average Current Ratio!", ninthColumn.getStringCellValue(),
				"Average Current Ratio");

		Cell tenthColumn = firstRow.getCell(StockSheetConstants.AVERAGE_DEBT_EQUITY_RATIO.getStockSheetData());
		assertEquals("Tenth column is not Average Debt to Equity Ratio!", tenthColumn.getStringCellValue(),
				"Average Debt to Equity Ratio");

	}

}
