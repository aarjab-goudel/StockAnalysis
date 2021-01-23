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

import create.excel.bo.StockDataInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.StockSheetConstants;
import excel.library.StockDataFinancialLibrary;

public class StockSheet extends FinancialSheet {

	public StockSheet(Workbook excelFile, int year, DataService dataService) {
		super(excelFile, year, dataService);
		this.setUpStockSheetHeaderRow(this.getFirstRow());
		this.createStockSheetColumns();
		this.createLastTickerColumn(StockSheetConstants.NUM_COLUMNS.getStockSheetData() - 1);
		this.setUpStockSheetHeaderRow(this.getLastRow());
		this.autoSizeAllColumns(StockSheetConstants.NUM_COLUMNS.getStockSheetData());
	}

	public void setUpStockSheetHeaderRow(Row headerRow) {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < StockSheetConstants.NUM_COLUMNS.getStockSheetData(); i++) {
			Cell cell = headerRow.createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn()).setCellValue("Stock");
		firstRowCells.get(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).setCellValue("Ticker");
		firstRowCells.get(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn()).setCellValue("Sector");
		firstRowCells.get(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn()).setCellValue("Industry");
		firstRowCells.get(StockSheetConstants.DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData())
				.setCellValue("Annual Dividend Percent Yield");
		firstRowCells.get(StockSheetConstants.AVERAGE_DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData())
				.setCellValue("Average 5 Year Dividend Yield");
		firstRowCells.get(StockSheetConstants.PB_RATIO_COLUMN.getStockSheetData()).setCellValue("Current P/B Ratio");
		firstRowCells.get(StockSheetConstants.AVERAGE_PB_RATIO_COLUMN.getStockSheetData())
				.setCellValue("Average 5 Year P/B ratio");
		firstRowCells.get(StockSheetConstants.AVERAGE_PE_RATIO_COLUMN.getStockSheetData())
				.setCellValue("Average 5 Year P/E ratio");
		firstRowCells.get(StockSheetConstants.PE_RATIO_COLUMN.getStockSheetData()).setCellValue("Current P/E ratio");
		firstRowCells.get(StockSheetConstants.FORWARD_PE_RATIO_COLUMN.getStockSheetData())
				.setCellValue("Forward P/E Ratio");
		firstRowCells.get(StockSheetConstants.PEG_RATIO_COLUMN.getStockSheetData()).setCellValue("PEG Ratio");
		firstRowCells.get(StockSheetConstants.AVERAGE_CURRENT_RATIO.getStockSheetData())
				.setCellValue("Average Current Ratio");
		firstRowCells.get(StockSheetConstants.AVERAGE_DEBT_EQUITY_RATIO.getStockSheetData())
				.setCellValue("Average Debt to Equity Ratio");
		firstRowCells.get(StockSheetConstants.NUM_COLUMNS.getStockSheetData() - 1).setCellValue("Ticker");
	}

	public void createStockSheetColumns() {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (String company : companies) {
			StockDataInfoBO stockInfo = this.getDataService().getTickerToStockDataInfo().get(company);
			Row row = this.getTickerToRow().get(company);

			StockDataFinancialLibrary.writeStockData(this.getDataService(), stockInfo, row);

		}
	}

}
