package update.excel.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import calculation.excel.implementation.StockSheetCalculation;
import create.excel.bo.PairBO;
import create.excel.bo.StockDataInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.StockSheetConstants;
import excel.library.CommonFinancialLibrary;
import excel.library.StockDataFinancialLibrary;

public class StockSheetUpdate extends SheetUpdate {

	public StockSheetUpdate(Integer year, Sheet stockSheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService dataService) {
		super(year, stockSheet, tickersToRemove, tickersToAdd, dataService);
		this.shiftRowAndAddTickers();
	}

	public void shiftRowAndAddTickers() {
		List<String> tickersToAdd = this.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			StockDataInfoBO stockInfo = this.getDataService().getTickerToStockDataInfo().get(tickerToAdd);

			PairBO<String, Integer> tickerToRowNum = this.shiftRowForTicker(tickerToAdd);
			Row newTickerRow = this.getSheet().createRow(tickerToRowNum.getValue());
			this.getAddedTickersToRow().put(tickerToAdd, newTickerRow);
			CommonFinancialLibrary.updateBasicStockInfo(this.getDataService(), tickerToAdd, newTickerRow,
					StockSheetConstants.NUM_COLUMNS.getStockSheetData());

			StockDataFinancialLibrary.writeStockData(this.getDataService(), stockInfo, newTickerRow);

//			Cell currentPriceCell = newTickerRow.createCell(StockSheetConstants.PRICE_COLUMN.getStockSheetData());
//			currentPriceCell.setCellValue(this.getDataService().getTickerToStockPrice().get(tickerToAdd));
//
//			Cell changePercentageCell = newTickerRow
//					.createCell(StockSheetConstants.CHANGE_PERCENTAGE_COLUMN.getStockSheetData());
//			changePercentageCell.setCellValue(this.getDataService().getTickerToChangePercentage().get(tickerToAdd));
//
//			Cell marketCapCell = newTickerRow
//					.createCell(StockSheetConstants.MARKET_CAPITALIZATION_COLUMN.getStockSheetData());
//			marketCapCell.setCellValue(CommonFinancialLibrary.checkAndConvertToMorningStarNumber(
//					this.getDataService().getTickerToMarketCapitalization().get(tickerToAdd)));
//
//			Cell betaCell = newTickerRow.createCell(StockSheetConstants.BETA_COLUMN.getStockSheetData());
//			betaCell.setCellValue(this.getDataService().getTickerToBeta().get(tickerToAdd));
//
//			Cell annualDividendPercentageCell = newTickerRow
//					.createCell(StockSheetConstants.DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData());
//			annualDividendPercentageCell.setCellValue(stockInfo.getAnnualDividendPercent());
//
//			Cell averageDividendPercentCell = newTickerRow
//					.createCell(StockSheetConstants.AVERAGE_DIVIDEND_ANNUAL_PERCENTAGE_COLUMN.getStockSheetData());
//			averageDividendPercentCell.setCellValue(stockInfo.getAverageDividendPercent());
//
//			Cell currentPERatioCell = newTickerRow.createCell(StockSheetConstants.PE_RATIO_COLUMN.getStockSheetData());
//			currentPERatioCell.setCellValue(stockInfo.getPERatio());
//
//			Cell averagePERatioCell = newTickerRow
//					.createCell(StockSheetConstants.AVERAGE_PE_RATIO_COLUMN.getStockSheetData());
//			averagePERatioCell.setCellValue(stockInfo.getAveragePERatio());
//
//			Cell currentPBRatioCell = newTickerRow.createCell(StockSheetConstants.PB_RATIO_COLUMN.getStockSheetData());
//			currentPBRatioCell.setCellValue(stockInfo.getPBRatio());
//
//			Cell averagePBRatioCell = newTickerRow
//					.createCell(StockSheetConstants.AVERAGE_PB_RATIO_COLUMN.getStockSheetData());
//			averagePBRatioCell.setCellValue(stockInfo.getAveragePBRatio());
//
//			Cell forwardPERatioCell = newTickerRow
//					.createCell(StockSheetConstants.FORWARD_PE_RATIO_COLUMN.getStockSheetData());
//			forwardPERatioCell.setCellValue(stockInfo.getForwardPERatio());
//
//			Cell pegRatioCell = newTickerRow.createCell(StockSheetConstants.PEG_RATIO_COLUMN.getStockSheetData());
//			pegRatioCell.setCellValue(stockInfo.getPegRatio());

		}
	}

	public void caluclateNewStockTickerData() {
		Map<String, Row> addedTickersToRow = this.getAddedTickersToRow();
		Iterator<Entry<String, Row>> iterator = addedTickersToRow.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Row> entry = iterator.next();
			Row newTickerRow = entry.getValue();
			StockSheetCalculation stockCalculation = new StockSheetCalculation(this.getYear());
			stockCalculation.writeToStockSheetRow(newTickerRow);
			this.styleAddedRow(newTickerRow);
		}
	}
}
