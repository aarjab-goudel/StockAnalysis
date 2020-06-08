package update.excel.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import calculation.excel.implementation.CFSheetCalculation;
import create.excel.bo.CFInfoBO;
import create.excel.bo.PairBO;
import create.excel.data.service.DataService;
import create.excel.enums.CFSheetConstants;
import excel.library.CFFinancialLibrary;
import excel.library.CommonFinancialLibrary;

public class CashFlowUpdate extends SheetUpdate {

	public CashFlowUpdate(Integer year, Sheet cfSheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService dataService) {
		super(year, cfSheet, tickersToRemove, tickersToAdd, dataService);
		this.shiftRowAndAddTickers();
	}

	public void shiftRowAndAddTickers() {
		List<String> tickersToAdd = this.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			CFInfoBO cfInfo = this.getDataService().getTickerToCFInfo().get(tickerToAdd).get(Math.abs(this.getYear()));
			PairBO<String, Integer> tickerToRowNum = this.shiftRowForTicker(tickerToAdd);
			Row newTickerRow = this.getSheet().createRow(tickerToRowNum.getValue());
			this.getAddedTickersToRow().put(tickerToAdd, newTickerRow);
			CommonFinancialLibrary.updateBasicStockInfo(getDataService(), tickerToAdd, newTickerRow,
					CFSheetConstants.NUM_COLUMNS.getCfData());

			CFFinancialLibrary.writeCFInfo(cfInfo, newTickerRow);

//			Cell netCashByOperatingCell = newTickerRow
//					.createCell(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_COLUMN.getCfData());
//			netCashByOperatingCell.setCellValue(cfInfo.getNetCashByOperatingActivites());
//
//			Cell netCashForFinancingCell = newTickerRow
//					.createCell(CFSheetConstants.NET_CASH_FOR_FINANCING_COLUMN.getCfData());
//			netCashForFinancingCell.setCellValue(cfInfo.getNetCashForFinancingActivities());
//
//			Cell netCashForInvestingCell = newTickerRow
//					.createCell(CFSheetConstants.NET_CASH_FOR_INVESTING_COLUMN.getCfData());
//			netCashForInvestingCell.setCellValue(cfInfo.getNetCashForInvestingActivities());
//
//			Cell capitalExpenditureCell = newTickerRow
//					.createCell(CFSheetConstants.CAPITAL_EXPENDITURE_COLUMN.getCfData());
//			capitalExpenditureCell.setCellValue(cfInfo.getCapitalExpenditure());
//
//			Cell dateCell = newTickerRow.createCell(CFSheetConstants.CF_DATE.getCfData());
//			dateCell.setCellValue(cfInfo.getCfDate());
//
//			Cell freeCashFlowCell = newTickerRow.createCell(CFSheetConstants.FREE_CASH_FLOW_COLUMN.getCfData());
//			freeCashFlowCell.setCellValue(cfInfo.getFreeCashFlow());
//
//			Cell currencyTypeCell = newTickerRow.createCell(CFSheetConstants.CURRENCY_TYPE.getCfData());
//			currencyTypeCell.setCellValue(cfInfo.getCurrencyType());

			this.styleAddedRow(newTickerRow);

		}

	}

	public void caluclateNewCFTickerData() {
		Map<String, Row> addedTickersToRow = this.getAddedTickersToRow();
		Iterator<Entry<String, Row>> iterator = addedTickersToRow.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Row> entry = iterator.next();
			Row newTickerRow = entry.getValue();
			CFSheetCalculation cfCalculation = new CFSheetCalculation(this.getYear());
			cfCalculation.writeToCashFlowRow(newTickerRow);
			this.styleAddedRow(newTickerRow);
		}
	}

}
