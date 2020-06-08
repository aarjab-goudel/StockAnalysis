package update.excel.implementation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import calculation.excel.implementation.BSSheetCalculation;
import create.excel.bo.BSInfoBO;
import create.excel.bo.PairBO;
import create.excel.data.service.DataService;
import create.excel.enums.BSSheetConstants;
import excel.library.BSFinancialLibrary;
import excel.library.CommonFinancialLibrary;

public class BalanceSheetUpdate extends SheetUpdate {

	public BalanceSheetUpdate(Integer year, Sheet bsSheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService dataService) {
		super(year, bsSheet, tickersToRemove, tickersToAdd, dataService);
		this.shiftRowAndAddTickers();
	}

	public void shiftRowAndAddTickers() {
		List<String> tickersToAdd = this.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			BSInfoBO bsInfo = this.getDataService().getTickerToBSInfo().get(tickerToAdd).get(Math.abs(this.getYear()));
			PairBO<String, Integer> tickerToRowNum = this.shiftRowForTicker(tickerToAdd);
			Row newTickerRow = this.getSheet().createRow(tickerToRowNum.getValue());
			this.getAddedTickersToRow().put(tickerToAdd, newTickerRow);
			CommonFinancialLibrary.updateBasicStockInfo(this.getDataService(), tickerToAdd, newTickerRow,
					BSSheetConstants.NUM_COLUMNS.getBSData());

			BSFinancialLibrary.writeBSInfo(bsInfo, newTickerRow);

//			Cell shareHoldersEquityCell = newTickerRow
//					.createCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());
//			shareHoldersEquityCell.setCellValue(bsInfo.getShareHoldersEquity());
//
//			Cell intangibleAssetsCell = newTickerRow.createCell(BSSheetConstants.INTANGIBLE_ASSETS_COLUMN.getBSData());
//			intangibleAssetsCell.setCellValue(bsInfo.getTotalIntangibleAssets());
//
//			Cell cashAndCashEquivalentCell = newTickerRow
//					.createCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
//			cashAndCashEquivalentCell.setCellValue(bsInfo.getCashAndCashEquivalent());
//
//			Cell shortTermInvestingCell = newTickerRow
//					.createCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
//			shortTermInvestingCell.setCellValue(bsInfo.getShortTermInvestments());
//
//			Cell longTermInvestmentCell = newTickerRow
//					.createCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
//			longTermInvestmentCell.setCellValue(bsInfo.getLongTermInvestments());
//
//			Cell shortTermDebtCell = newTickerRow.createCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
//			shortTermDebtCell.setCellValue(bsInfo.getShortTermDebt());
//
//			Cell longTermDebtCell = newTickerRow.createCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());
//			longTermDebtCell.setCellValue(bsInfo.getLongTermDebt());
//
//			Cell recievablesCell = newTickerRow.createCell(BSSheetConstants.RECEIVABLES_COLUMN.getBSData());
//			recievablesCell.setCellValue(bsInfo.getReceivables());
//
//			Cell payableCell = newTickerRow.createCell(BSSheetConstants.ACCOUNTS_PAYABLE_COLUMN.getBSData());
//			payableCell.setCellValue(bsInfo.getAccountsPayable());
//
//			Cell dateCell = newTickerRow.createCell(BSSheetConstants.BS_DATE.getBSData());
//			dateCell.setCellValue(bsInfo.getBsDate());
//
//			Cell currencyTypeCell = newTickerRow.createCell(BSSheetConstants.CURRENCY_TYPE.getBSData());
//			currencyTypeCell.setCellValue(bsInfo.getCurrencyType());

		}
	}

	public void caluclateNewBSTickerData() {
		Map<String, Row> addedTickersToRow = this.getAddedTickersToRow();
		Iterator<Entry<String, Row>> iterator = addedTickersToRow.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Row> entry = iterator.next();
			Row newTickerRow = entry.getValue();
			BSSheetCalculation bsCalculation = new BSSheetCalculation(this.getYear());
			bsCalculation.writeToBalanceSheetRow(newTickerRow);
			this.styleAddedRow(newTickerRow);
		}
	}

}
