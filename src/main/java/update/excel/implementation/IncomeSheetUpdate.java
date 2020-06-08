package update.excel.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import calculation.excel.implementation.ISSheetCalculation;
import create.excel.bo.ISInfoBO;
import create.excel.bo.PairBO;
import create.excel.data.service.DataService;
import create.excel.enums.ISSheetConstants;
import excel.library.CommonFinancialLibrary;
import excel.library.ISFinancialLibrary;

public class IncomeSheetUpdate extends SheetUpdate {

	public IncomeSheetUpdate(Integer year, Sheet isSheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService dataService) {
		super(year, isSheet, tickersToRemove, tickersToAdd, dataService);
		this.shiftRowAndAddTickers();
	}

	public void shiftRowAndAddTickers() {
		List<String> tickersToAdd = this.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			ISInfoBO isInfo = this.getDataService().getTickerToISInfo().get(tickerToAdd).get(Math.abs(this.getYear()));
			PairBO<String, Integer> tickerToRowNum = this.shiftRowForTicker(tickerToAdd);
			Row newTickerRow = this.getSheet().createRow(tickerToRowNum.getValue());
			this.getAddedTickersToRow().put(tickerToAdd, newTickerRow);
			CommonFinancialLibrary.updateBasicStockInfo(this.getDataService(), tickerToAdd, newTickerRow,
					ISSheetConstants.NUM_COLUMNS.getIsData());

			ISFinancialLibrary.writeISInfo(isInfo, newTickerRow);

//			Cell revenueCell = newTickerRow.createCell(ISSheetConstants.REVENUE_COLUMN.getIsData());
//			revenueCell.setCellValue(isInfo.getRevenue());
//
//			Cell costOfRevenueCell = newTickerRow.createCell(ISSheetConstants.COST_OF_REVENUE_COLUMN.getIsData());
//			costOfRevenueCell.setCellValue(isInfo.getCostOfRevenue());
//
//			Cell grossProfitCell = newTickerRow.createCell(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData());
//			grossProfitCell.setCellValue(isInfo.getGrossProfit());
//
//			Cell netIncomeCell = newTickerRow.createCell(ISSheetConstants.NET_INCOME_COLUMN.getIsData());
//			netIncomeCell.setCellValue(isInfo.getNetIncome());
//
//			Cell dateCell = newTickerRow.createCell(ISSheetConstants.IS_DATE.getIsData());
//			dateCell.setCellValue(isInfo.getIsDate());
//
//			Cell currencyTypeCell = newTickerRow.createCell(ISSheetConstants.CURRENCY_TYPE.getIsData());
//			currencyTypeCell.setCellValue(isInfo.getCurrencyType());

			this.styleAddedRow(newTickerRow);

		}
	}

	public void caluclateNewISTickerData() {
		Map<String, Row> addedTickersToRow = this.getAddedTickersToRow();
		Iterator<Entry<String, Row>> iterator = addedTickersToRow.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Row> entry = iterator.next();
			Row newTickerRow = entry.getValue();
			ISSheetCalculation isCalculation = new ISSheetCalculation(this.getYear());
			isCalculation.writeToIncomeStatementRow(newTickerRow);
			this.styleAddedRow(newTickerRow);
		}
	}
}
