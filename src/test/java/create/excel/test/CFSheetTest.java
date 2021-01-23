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

import create.excel.enums.CFSheetConstants;
import create.excel.enums.CommonSheetConstants;
import create.excel.implementation.CFSheet;
import create.excel.implementation.CreateAnnualExcelFile;

public class CFSheetTest {

	private static CreateAnnualExcelFile excelFile;
	private static CFSheet cashFlowSheet;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		excelFile = new CreateAnnualExcelFile();
		cashFlowSheet = excelFile.getCFSheet();
	}

	@Test
	public void testCFSheetColumns() {
		Row firstRow = cashFlowSheet.getFirstRow();

		Cell firstColumn = firstRow.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		assertEquals("First column is not stocks! ", firstColumn.getStringCellValue(), "Stock");

		Cell thirdColumn = firstRow.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		assertEquals("Third column is not ticker!", thirdColumn.getStringCellValue(), "Ticker");

		Cell fourthColumn = firstRow.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		assertEquals("Fourth column is Sector!", fourthColumn.getStringCellValue(), "Sector");

		Cell fifthColumn = firstRow.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		assertEquals("Fifth column is not Industry!", fifthColumn.getStringCellValue(), "Industry");

		Cell sixthColumn = firstRow.getCell(CFSheetConstants.FREE_CASH_FLOW_COLUMN.getCfData());
		assertEquals("Sixth column is not Free Cash Flow!", sixthColumn.getStringCellValue(), "Free Cash Flow");

		Cell seventhColumn = firstRow.getCell(CFSheetConstants.FREE_CASH_FLOW_GROWTH_COLUMN.getCfData());
		assertEquals("Seventh column is not Free Cash Flow Growth!", seventhColumn.getStringCellValue(),
				"Free Cash Flow Growth");

		Cell eighthColumn = firstRow.getCell(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_COLUMN.getCfData());
		assertEquals("Eighth column is not Net cash provided by operating activities!",
				eighthColumn.getStringCellValue(), "Net cash provided by operating activities");

		Cell ninthColumn = firstRow.getCell(CFSheetConstants.NET_CASH_FOR_INVESTING_COLUMN.getCfData());
		assertEquals("Ninth column is not Net cash used for investing activities!", ninthColumn.getStringCellValue(),
				"Net cash used for investing activities");

		Cell tenthColumn = firstRow.getCell(CFSheetConstants.NET_CASH_FOR_FINANCING_COLUMN.getCfData());
		assertEquals("Tenth column is not Net cash provided by (used for) financing activities!",
				tenthColumn.getStringCellValue(), "Net cash provided by (used for) financing activities");

		Cell eleventhColumn = firstRow.getCell(CFSheetConstants.CAPITAL_EXPENDITURE_COLUMN.getCfData());
		assertEquals("Eleventh column is not Capital expenditure!", eleventhColumn.getStringCellValue(),
				"Capital Expenditures");
	}

}
