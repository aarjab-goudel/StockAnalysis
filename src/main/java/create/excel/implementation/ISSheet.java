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

import create.excel.bo.ISInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.ISSheetConstants;
import excel.library.ISFinancialLibrary;

public class ISSheet extends FinancialSheet {

	public ISSheet(Workbook excelFile, int sheetNum, DataService dataService, boolean isQuarterly) {
		super(excelFile, sheetNum, dataService);
		this.setUpISHeaderRow(this.getFirstRow());
		this.createIncomeStatementColumns(isQuarterly);
		this.createLastTickerColumn(ISSheetConstants.NUM_COLUMNS.getIsData() - 1);
		this.setUpISHeaderRow(this.getLastRow());
		this.autoSizeAllColumns(ISSheetConstants.NUM_COLUMNS.getIsData());
	}

	private void createIncomeStatementColumns(boolean isQuarterly) {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {
			ISInfoBO isInfo = null;
			if (!isQuarterly) {
				isInfo = this.getDataService().getTickerToISInfo().get(company).get(Math.abs(this.getYear()));
			} else {
				isInfo = this.getDataService().getQuarterlyTickerToISInfo().get(company).get(Math.abs(this.getYear()));
			}
			Row row = this.getTickerToRow().get(company);

			ISFinancialLibrary.writeISInfo(isInfo, row);

		}
	}

	public void setUpISHeaderRow(Row headerRow) {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < ISSheetConstants.NUM_COLUMNS.getIsData(); i++) {
			Cell cell = headerRow.createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn()).setCellValue("Stock");
		firstRowCells.get(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).setCellValue("Ticker");
		firstRowCells.get(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn()).setCellValue("Sector");
		firstRowCells.get(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn()).setCellValue("Industry");
		firstRowCells.get(ISSheetConstants.IS_DATE.getIsData()).setCellValue("Date");
		firstRowCells.get(ISSheetConstants.CURRENCY_TYPE.getIsData()).setCellValue("Currency Type");
		firstRowCells.get(ISSheetConstants.REVENUE_COLUMN.getIsData()).setCellValue("Revenue");
		firstRowCells.get(ISSheetConstants.REVENUE_GROWTH_COLUMN.getIsData()).setCellValue("Revenue Growth");
		firstRowCells.get(ISSheetConstants.COST_OF_REVENUE_COLUMN.getIsData()).setCellValue("Cost of revenue");
		firstRowCells.get(ISSheetConstants.COST_OF_REVENUE_GROWTH_COLUMN.getIsData())
				.setCellValue("Cost of Revenue Growth");
		firstRowCells.get(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData()).setCellValue("Gross profit");
		firstRowCells.get(ISSheetConstants.GROSS_PROFIT_GROWTH_COLUMN.getIsData()).setCellValue("Gross Profit Growth");
		firstRowCells.get(ISSheetConstants.NET_INCOME_COLUMN.getIsData()).setCellValue("Net income");
		firstRowCells.get(ISSheetConstants.NET_INCOME_GOWTH_COLUMN.getIsData()).setCellValue("Net Income Growth");
		firstRowCells.get(ISSheetConstants.EPS_COLUMN.getIsData()).setCellValue("Earnings Per Share");
		firstRowCells.get(ISSheetConstants.EPS_GROWTH_COLUMN.getIsData()).setCellValue("Earnings Per Share Growth");
		firstRowCells.get(ISSheetConstants.RESEARCH_AND_DEVELOPMENT_COLUMN.getIsData())
				.setCellValue("Research and Development");
		firstRowCells.get(ISSheetConstants.RESEARCH_AND_DEVELOPMENT_GROWTH_COLUMN.getIsData())
				.setCellValue("Research and Development Growth");
		firstRowCells.get(ISSheetConstants.NUM_COLUMNS.getIsData() - 1).setCellValue("Ticker");
	}

}
