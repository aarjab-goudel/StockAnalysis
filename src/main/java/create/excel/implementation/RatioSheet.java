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
import create.excel.bo.RatioInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.RatioSheetConstants;
import excel.library.RatioFinancialLibrary;

public class RatioSheet extends FinancialSheet {

	public RatioSheet(Workbook excelFile, int sheetNum, DataService dataService) {
		super(excelFile, sheetNum, dataService);
		this.setUpFirstRow();
		this.createRatioColumns();
		this.createLastTickerColumn(RatioSheetConstants.NUM_COLUMNS.getRatioData() - 1);
		// Should be last method to call before finishing setting up Ratio Sheet
		this.autoSizeAllColumns(RatioSheetConstants.NUM_COLUMNS.getRatioData());
	}

	private void createRatioColumns() {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {

			RatioInfoBO ratioInfo = this.getDataService().getTickerToRatioInfo().get(company)
					.get(Math.abs(this.getYear()));

			ISInfoBO isInfo = this.getDataService().getTickerToISInfo().get(company).get(Math.abs(this.getYear()));

			Row row = this.getTickerToRow().get(company);

			RatioFinancialLibrary.writeRatioInfo(isInfo, ratioInfo, row);

//			String ratioDate = ratioInfo.getRatioDate();
//			Row row = this.getTickerToRow().get(company);
//
//			Cell earningsPerShareCell = row.createCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData());
//			earningsPerShareCell.setCellValue(isInfo.getEps());
//
//			Cell currentRatioCell = row.createCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData());
//			currentRatioCell.setCellValue(ratioInfo.getCurrentRatio());
//
//			Cell debtEquityRatioCell = row.createCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData());
//			debtEquityRatioCell.setCellValue(ratioInfo.getDebtToEquityRatio());
//
//			Cell dateCell = row.createCell(RatioSheetConstants.RATIO_DATE.getRatioData());
//			dateCell.setCellValue(ratioDate);

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
		for (int i = 0; i < RatioSheetConstants.NUM_COLUMNS.getRatioData(); i++) {
			Cell cell = this.getFirstRow().createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn()).setCellValue("Stock");
		firstRowCells.get(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).setCellValue("Ticker");
		firstRowCells.get(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn()).setCellValue("Sector");
		firstRowCells.get(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn()).setCellValue("Industry");
		firstRowCells.get(RatioSheetConstants.RATIO_DATE.getRatioData()).setCellValue("Date");
		firstRowCells.get(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData())
				.setCellValue("Earnings Per Share");
		firstRowCells.get(RatioSheetConstants.EARNINGS_PER_SHARE_GROWTH_COLUMN.getRatioData())
				.setCellValue("EPS Growth");
		firstRowCells.get(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData()).setCellValue("Current Ratio");
		firstRowCells.get(RatioSheetConstants.CURRENT_RATIO_GROWTH.getRatioData()).setCellValue("Current Ratio Growth");
		firstRowCells.get(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData())
				.setCellValue("Debt to Equity Ratio");
		firstRowCells.get(RatioSheetConstants.DEBT_EQUITY_RATIO_GROWTH_COLUMN.getRatioData())
				.setCellValue("Debt to Equity Ratio Growth");
		firstRowCells.get(RatioSheetConstants.NUM_COLUMNS.getRatioData() - 1).setCellValue("Ticker");

	}

}
