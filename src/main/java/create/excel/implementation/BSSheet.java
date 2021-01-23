/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.bo.BSInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.BSSheetConstants;
import create.excel.enums.CommonSheetConstants;
import excel.library.BSFinancialLibrary;

public class BSSheet extends FinancialSheet {

	public BSSheet(Workbook excelFile, int sheetNum, DataService dataService, boolean isQuarterly) {
		super(excelFile, sheetNum, dataService);
		this.setUpBSHeaderRow(this.getFirstRow());
		this.createBalanceSheetColumns(isQuarterly);
		this.createLastTickerColumn(BSSheetConstants.NUM_COLUMNS.getBSData() - 1);
		this.setUpBSHeaderRow(this.getLastRow());
		this.autoSizeAllColumns(BSSheetConstants.NUM_COLUMNS.getBSData());
	}

	private void createBalanceSheetColumns(boolean isQuarterly) {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {
			BSInfoBO bsInfo = null;
			if (!isQuarterly) {
				bsInfo = this.getDataService().getTickerToBSInfo().get(company).get(Math.abs(this.getYear()));
			} else {
				bsInfo = this.getDataService().getQuarterlyTickerToBSInfo().get(company).get(Math.abs(this.getYear()));
			}
			Row row = this.getTickerToRow().get(company);

			BSFinancialLibrary.writeBSInfo(bsInfo, row);

		}
	}

	public void setUpBSHeaderRow(Row headerRow) {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < BSSheetConstants.NUM_COLUMNS.getBSData(); i++) {
			Cell cell = headerRow.createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn()).setCellValue("Stock");
		firstRowCells.get(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).setCellValue("Ticker");
		firstRowCells.get(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn()).setCellValue("Sector");
		firstRowCells.get(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn()).setCellValue("Industry");
		firstRowCells.get(BSSheetConstants.BS_DATE.getBSData()).setCellValue("Date");
		firstRowCells.get(BSSheetConstants.CURRENCY_TYPE.getBSData()).setCellValue("Currency Type");
		firstRowCells.get(BSSheetConstants.NET_EQUITY_COLUMN.getBSData()).setCellValue("Net Equity");
		firstRowCells.get(BSSheetConstants.NET_EQUITY_GROWTH_COLUMN.getBSData()).setCellValue("Net Equity Growth");
		firstRowCells.get(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData())
				.setCellValue("Total Shareholders Equity");
		firstRowCells.get(BSSheetConstants.SHARE_HOLDER_EQUITY_GROWTH_COLUMN.getBSData())
				.setCellValue("Total Shareholders Equity Growth");
		firstRowCells.get(BSSheetConstants.INTANGIBLE_ASSETS_COLUMN.getBSData())
				.setCellValue("Total intangible assets");
		firstRowCells.get(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData())
				.setCellValue("Cash and cash equivalents");
		firstRowCells.get(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData())
				.setCellValue("Short-term investments");
		firstRowCells.get(BSSheetConstants.RECEIVABLES_COLUMN.getBSData()).setCellValue("Receivables");
		firstRowCells.get(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData())
				.setCellValue("Long-term investments");
		firstRowCells.get(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData()).setCellValue("Short-term debt");
		firstRowCells.get(BSSheetConstants.ACCOUNTS_PAYABLE_COLUMN.getBSData()).setCellValue("Accounts payable");
		firstRowCells.get(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData()).setCellValue("Long-term debt");
		firstRowCells.get(BSSheetConstants.NUM_COLUMNS.getBSData() - 1).setCellValue("Ticker");

	}

}
