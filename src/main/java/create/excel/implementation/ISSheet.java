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

	public ISSheet(Workbook excelFile, int sheetNum, DataService dataService) {
		super(excelFile, sheetNum, dataService);
		this.setUpFirstRow();
		this.createIncomeStatementColumns();
		this.createLastTickerColumn(ISSheetConstants.NUM_COLUMNS.getIsData() - 1);
		// Should be last method to call before finishing setting up Balance Sheet
		this.autoSizeAllColumns(ISSheetConstants.NUM_COLUMNS.getIsData());
	}

	private void createIncomeStatementColumns() {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {
			ISInfoBO isInfo = this.getDataService().getTickerToISInfo().get(company).get(Math.abs(this.getYear()));
			Row row = this.getTickerToRow().get(company);

			ISFinancialLibrary.writeISInfo(isInfo, row);

//			Cell revenueCell = row.createCell(ISSheetConstants.REVENUE_COLUMN.getIsData());
//			revenueCell.setCellValue(isInfo.getRevenue());
//
//			Cell costOfRevenueCell = row.createCell(ISSheetConstants.COST_OF_REVENUE_COLUMN.getIsData());
//			costOfRevenueCell.setCellValue(isInfo.getCostOfRevenue());
//
//			Cell grossProfitCell = row.createCell(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData());
//			grossProfitCell.setCellValue(isInfo.getGrossProfit());
//
//			Cell netIncomeCell = row.createCell(ISSheetConstants.NET_INCOME_COLUMN.getIsData());
//			netIncomeCell.setCellValue(isInfo.getNetIncome());
//
//			Cell dateCell = row.createCell(ISSheetConstants.IS_DATE.getIsData());
//			dateCell.setCellValue(isInfo.getIsDate());
//
//			Cell currencyTypeCell = row.createCell(ISSheetConstants.CURRENCY_TYPE.getIsData());
//			currencyTypeCell.setCellValue(isInfo.getCurrencyType());
		}
	}

	public void setUpFirstRow() {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < ISSheetConstants.NUM_COLUMNS.getIsData(); i++) {
			Cell cell = this.getFirstRow().createCell(i);
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
		firstRowCells.get(ISSheetConstants.NUM_COLUMNS.getIsData() - 1).setCellValue("Ticker");
	}

}
