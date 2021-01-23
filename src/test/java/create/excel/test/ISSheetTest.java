/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
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
import create.excel.enums.ISSheetConstants;
import create.excel.implementation.CreateAnnualExcelFile;
import create.excel.implementation.ISSheet;

public class ISSheetTest {

	private static CreateAnnualExcelFile excelFile;
	private static ISSheet incomeStatementSheet;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateAnnualExcelFile();
		incomeStatementSheet = excelFile.getISSheet();
	}

	@Test
	public void testISSheetColumns() {
		Row firstRow = incomeStatementSheet.getFirstRow();

		Cell zeroColumn = firstRow.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		assertEquals("First column is not stocks! ", zeroColumn.getStringCellValue(), "Stock");

		Cell secondColumn = firstRow.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		assertEquals("Third column is not ticker!", secondColumn.getStringCellValue(), "Ticker");

		Cell fourthColumn = firstRow.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		assertEquals("Fourth column is Sector!", fourthColumn.getStringCellValue(), "Sector");

		Cell fifthColumn = firstRow.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		assertEquals("Fifth column is not Industry!", fifthColumn.getStringCellValue(), "Industry");

		Cell sixthColumn = firstRow.getCell(ISSheetConstants.REVENUE_COLUMN.getIsData());
		assertEquals("Sixth column is not revenue!", sixthColumn.getStringCellValue(), "Revenue");

		Cell seventhColumn = firstRow.getCell(ISSheetConstants.REVENUE_GROWTH_COLUMN.getIsData());
		assertEquals("Seventh column is not revenue growth!", seventhColumn.getStringCellValue(), "Revenue Growth");

		Cell eighthColumn = firstRow.getCell(ISSheetConstants.COST_OF_REVENUE_COLUMN.getIsData());
		assertEquals("Eighth column is not Cost of revenue!", eighthColumn.getStringCellValue(), "Cost of revenue");

		Cell ninthColumn = firstRow.getCell(ISSheetConstants.COST_OF_REVENUE_GROWTH_COLUMN.getIsData());
		assertEquals("Ninth column is not Cost of Revenue Growth!", ninthColumn.getStringCellValue(),
				"Cost of Revenue Growth");

		Cell tenthColumn = firstRow.getCell(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData());
		assertEquals("Tenth column is not Gross profit!", tenthColumn.getStringCellValue(), "Gross profit");

		Cell eleventhColumn = firstRow.getCell(ISSheetConstants.GROSS_PROFIT_GROWTH_COLUMN.getIsData());
		assertEquals("Eleventh column is not Gross Profit Growth!", eleventhColumn.getStringCellValue(),
				"Gross Profit Growth");

		Cell twlevthColumn = firstRow.getCell(ISSheetConstants.NET_INCOME_COLUMN.getIsData());
		assertEquals("Twlevth column is not Net income!", twlevthColumn.getStringCellValue(), "Net income");

		Cell thirteenthColumn = firstRow.getCell(ISSheetConstants.NET_INCOME_GOWTH_COLUMN.getIsData());
		assertEquals("Thirteenth column is not Net Income Growth!", thirteenthColumn.getStringCellValue(),
				"Net Income Growth");

	}

}
