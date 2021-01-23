/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
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

import create.excel.enums.BSSheetConstants;
import create.excel.enums.CommonSheetConstants;
import create.excel.implementation.BSSheet;
import create.excel.implementation.CreateAnnualExcelFile;

public class BSSheetTest {

	private static CreateAnnualExcelFile excelFile;
	private static BSSheet balanceSheet;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateAnnualExcelFile();
		balanceSheet = excelFile.getBSSheet();
	}

	@Test
	public void testTickerColumn() {
		Map<String, Row> tickerToRow = balanceSheet.getTickerToRow();
		List<String> companies = balanceSheet.getDataService().getOrderedCompanies();
		assertEquals("The number of rows is incorrect for the number of companies!", tickerToRow.size(),
				companies.size());
		for (int i = 0; i < companies.size(); i++) {
			String company = companies.get(i);
			Row row = tickerToRow.get(company);
			Cell cell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			assertEquals("The row does not correspond with the correct company ", companies.get(i),
					cell.getStringCellValue());
		}
	}

	@Test
	public void testBSSheetColumns() {
		Row firstRow = excelFile.getBSSheet().getFirstRow();

		Cell firstColumn = firstRow.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		assertEquals("First column is not stocks! ", firstColumn.getStringCellValue(), "Stock");

		Cell thirdColumn = firstRow.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		assertEquals("Third column is not ticker!", thirdColumn.getStringCellValue(), "Ticker");

		Cell fourthColumn = firstRow.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		assertEquals("Fourth column is Sector!", fourthColumn.getStringCellValue(), "Sector");

		Cell fifthColumn = firstRow.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		assertEquals("Fifth column is not Industry!", fifthColumn.getStringCellValue(), "Industry");

		Cell sixthColumn = firstRow.getCell(6);
		assertEquals("Fourth column is not net equity!", sixthColumn.getStringCellValue(), "Net Equity");

		Cell seventhColumn = firstRow.getCell(7);
		assertEquals("Fifth column is not net equity growth!", seventhColumn.getStringCellValue(), "Net Equity Growth");

		Cell eighthColumn = firstRow.getCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
		assertEquals("Sixth column is not cash and cash equivalents!", eighthColumn.getStringCellValue(),
				"Cash and cash equivalents");

		Cell ninethColumn = firstRow.getCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
		assertEquals("Seventh column is not short term investments!", ninethColumn.getStringCellValue(),
				"Short-term investments");

		Cell tenthColumn = firstRow.getCell(BSSheetConstants.RECEIVABLES_COLUMN.getBSData());
		assertEquals("Eighth column is not receivables!", tenthColumn.getStringCellValue(), "Receivables");

		Cell eleventhColumn = firstRow.getCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
		assertEquals("Nineth column is not long term investments!", eleventhColumn.getStringCellValue(),
				"Long-term investments");

		Cell thirteenthColumn = firstRow.getCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
		assertEquals("Eleventh column is not short term debt!", thirteenthColumn.getStringCellValue(),
				"Short-term debt");

		Cell foureenthColumn = firstRow.getCell(BSSheetConstants.ACCOUNTS_PAYABLE_COLUMN.getBSData());
		assertEquals("Twelvth column is not accounts payable!", foureenthColumn.getStringCellValue(),
				"Accounts payable");

		Cell fifteenthColumn = firstRow.getCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());
		assertEquals("Thirteenth column is not long term debt!", fifteenthColumn.getStringCellValue(),
				"Long-term debt");

	}

//Still working on ticker #. Fixed a couple of issues and Im working on fixing one last issue where the script 
//is not pulling all the Versa devices. I have a meeting with Kamil later today to discuss about the issue and fix it
}
