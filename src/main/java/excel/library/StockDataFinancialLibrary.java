/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package excel.library;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.bo.StockDataInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.StockSheetConstants;

public class StockDataFinancialLibrary {

	public StockDataFinancialLibrary() {

	}

	public static void writeStockData(DataService dataService, StockDataInfoBO stockInfo, Row row) {

		Cell annualDividendPercentageCell = row
				.createCell(StockSheetConstants.DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData());
		annualDividendPercentageCell.setCellValue(stockInfo.getAnnualDividendPercent());

		Cell averageDividendPercentCell = row
				.createCell(StockSheetConstants.AVERAGE_DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData());
		averageDividendPercentCell.setCellValue(stockInfo.getAverageDividendPercent());

		Cell currentPERatioCell = row.createCell(StockSheetConstants.PE_RATIO_COLUMN.getStockSheetData());
		currentPERatioCell.setCellValue(stockInfo.getPERatio());

		Cell averagePERatioCell = row.createCell(StockSheetConstants.AVERAGE_PE_RATIO_COLUMN.getStockSheetData());
		averagePERatioCell.setCellValue(stockInfo.getAveragePERatio());

		Cell currentPBRatioCell = row.createCell(StockSheetConstants.PB_RATIO_COLUMN.getStockSheetData());
		currentPBRatioCell.setCellValue(stockInfo.getPBRatio());

		Cell averagePBRatioCell = row.createCell(StockSheetConstants.AVERAGE_PB_RATIO_COLUMN.getStockSheetData());
		averagePBRatioCell.setCellValue(stockInfo.getAveragePBRatio());

		Cell forwardPERatioCell = row.createCell(StockSheetConstants.FORWARD_PE_RATIO_COLUMN.getStockSheetData());
		forwardPERatioCell.setCellValue(stockInfo.getForwardPERatio());

		Cell pegRatioCell = row.createCell(StockSheetConstants.PEG_RATIO_COLUMN.getStockSheetData());
		pegRatioCell.setCellValue(stockInfo.getPegRatio());
	}

	public static StockDataInfoBO createErrorStockDataInfoBO(String ticker) {
		StockDataInfoBO stockInfo = new StockDataInfoBO(0, ticker);
		stockInfo.setAnnualDividendPercent("ERROR");
		stockInfo.setAverageDividendPercent("ERROR");
		stockInfo.setAveragePBRatio("ERROR");
		stockInfo.setAveragePERatio("ERROR");
		stockInfo.setForwardPERatio("ERROR");
		stockInfo.setIndustry("ERROR");
		stockInfo.setPBRatio("ERROR");
		stockInfo.setPegRatio("ERROR");
		stockInfo.setPERatio("ERROR");
		stockInfo.setSector("ERROR");
		stockInfo.setStockName("ERROR");
		return stockInfo;
	}

}
