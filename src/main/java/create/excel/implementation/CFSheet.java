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

import create.excel.bo.CFInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.CFSheetConstants;
import create.excel.enums.CommonSheetConstants;
import excel.library.CFFinancialLibrary;

public class CFSheet extends FinancialSheet {

	public CFSheet(Workbook excelFile, int year, DataService dataService, boolean isQuarterly) {
		super(excelFile, year, dataService);
		this.setUpCFHeaderRow(this.getFirstRow());
		this.createCashFlowColumns(isQuarterly);
		this.createLastTickerColumn(CFSheetConstants.NUM_COLUMNS.getCfData() - 1);
		this.setUpCFHeaderRow(this.getLastRow());
		this.autoSizeAllColumns(CFSheetConstants.NUM_COLUMNS.getCfData());
	}

	private void createCashFlowColumns(boolean isQuarterly) {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {
			CFInfoBO cfInfo = null;
			if (!isQuarterly) {
				cfInfo = this.getDataService().getTickerToCFInfo().get(company).get(Math.abs(this.getYear()));
			} else {
				cfInfo = this.getDataService().getQuarterlyTickerToCFInfo().get(company).get(Math.abs(this.getYear()));
			}
			Row row = this.getTickerToRow().get(company);
			CFFinancialLibrary.writeCFInfo(cfInfo, row);

		}
	}

	private void setUpCFHeaderRow(Row headerRow) {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < CFSheetConstants.NUM_COLUMNS.getCfData(); i++) {
			Cell cell = headerRow.createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn()).setCellValue("Stock");
		firstRowCells.get(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).setCellValue("Ticker");
		firstRowCells.get(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn()).setCellValue("Sector");
		firstRowCells.get(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn()).setCellValue("Industry");
		firstRowCells.get(CFSheetConstants.CF_DATE.getCfData()).setCellValue("Date");
		firstRowCells.get(CFSheetConstants.CURRENCY_TYPE.getCfData()).setCellValue("Currency Type");
		firstRowCells.get(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_COLUMN.getCfData())
				.setCellValue("Net cash provided by operating activities");
		firstRowCells.get(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_GROWTH_COLUMN.getCfData())
				.setCellValue("Net cash rovided by operating activities growth");
		firstRowCells.get(CFSheetConstants.NET_CASH_FOR_INVESTING_COLUMN.getCfData())
				.setCellValue("Net cash used for investing activities");
		firstRowCells.get(CFSheetConstants.NET_CASH_FOR_FINANCING_COLUMN.getCfData())
				.setCellValue("Net cash provided by (used for) financing activities");
		firstRowCells.get(CFSheetConstants.CAPITAL_EXPENDITURE_COLUMN.getCfData()).setCellValue("Capital Expenditures");
		firstRowCells.get(CFSheetConstants.FREE_CASH_FLOW_COLUMN.getCfData()).setCellValue("Free Cash Flow");
		firstRowCells.get(CFSheetConstants.FREE_CASH_FLOW_GROWTH_COLUMN.getCfData())
				.setCellValue("Free Cash Flow Growth");
		firstRowCells.get(CFSheetConstants.NUM_COLUMNS.getCfData() - 1).setCellValue("Ticker");
	}

}
