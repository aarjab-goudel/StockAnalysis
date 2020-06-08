package update.excel.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import calculation.excel.implementation.RatioSheetCalculation;
import create.excel.bo.ISInfoBO;
import create.excel.bo.PairBO;
import create.excel.bo.RatioInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.RatioSheetConstants;
import excel.library.CommonFinancialLibrary;
import excel.library.RatioFinancialLibrary;

public class RatioSheetUpdate extends SheetUpdate {

	public RatioSheetUpdate(Integer year, Sheet ratioSheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService wrapper) {
		super(year, ratioSheet, tickersToRemove, tickersToAdd, wrapper);
		this.shiftRowAndAddTickers();
	}

	public void shiftRowAndAddTickers() {
		List<String> tickersToAdd = this.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			RatioInfoBO ratioInfo = this.getDataService().getTickerToRatioInfo().get(tickerToAdd)
					.get(Math.abs(this.getYear()));
			ISInfoBO isInfo = this.getDataService().getTickerToISInfo().get(tickerToAdd).get(Math.abs(this.getYear()));
			PairBO<String, Integer> tickerToRowNum = this.shiftRowForTicker(tickerToAdd);
			Row newTickerRow = this.getSheet().createRow(tickerToRowNum.getValue());
			this.getAddedTickersToRow().put(tickerToAdd, newTickerRow);
			CommonFinancialLibrary.updateBasicStockInfo(this.getDataService(), tickerToAdd, newTickerRow,
					RatioSheetConstants.NUM_COLUMNS.getRatioData());

			RatioFinancialLibrary.writeRatioInfo(isInfo, ratioInfo, newTickerRow);

//			Cell earningsPerShareCell = newTickerRow
//					.createCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData());
//			earningsPerShareCell.setCellValue(isInfo.getEps());
//
//			Cell currentRatioCell = newTickerRow.createCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData());
//			currentRatioCell.setCellValue(ratioInfo.getCurrentRatio());
//
//			Cell debtEquityRatioCell = newTickerRow
//					.createCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData());
//			debtEquityRatioCell.setCellValue(ratioInfo.getDebtToEquityRatio());
//
//			Cell dateCell = newTickerRow.createCell(RatioSheetConstants.RATIO_DATE.getRatioData());
//			dateCell.setCellValue(ratioDate);

			this.styleAddedRow(newTickerRow);

		}

	}

	public void caluclateNewRatiosTickerData() {
		Map<String, Row> addedTickersToRow = this.getAddedTickersToRow();
		Iterator<Entry<String, Row>> iterator = addedTickersToRow.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Row> entry = iterator.next();
			Row newTickerRow = entry.getValue();
			RatioSheetCalculation ratioCalculation = new RatioSheetCalculation(this.getYear());
			ratioCalculation.writeToRatioSheetRow(newTickerRow);
			this.styleAddedRow(newTickerRow);
		}
	}
}
