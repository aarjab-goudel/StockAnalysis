package excel.library;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import create.excel.bo.BSInfoBO;
import create.excel.enums.BSSheetConstants;
import create.excel.enums.CommonSheetConstants;

public class BSFinancialLibrary {

	public BSFinancialLibrary() {

	}

	public static Map<String, List<BSInfoBO>> readBSData(List<Sheet> bsSheets) {
		Map<String, List<BSInfoBO>> tickerToBSData = new HashMap<String, List<BSInfoBO>>();
		int yearCounter = 0;
		for (Sheet currentBSSheet : bsSheets) {
			Iterator<Row> rowIterator = currentBSSheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());

				Cell shareholdersEquity = row.getCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());

				Cell cashCell = row.getCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
				Cell shortTermInvestCell = row.getCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
				Cell longTermInvestCell = row.getCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
				Cell shortTermDebtCell = row.getCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
				Cell longTermDebtCell = row.getCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());

				BigDecimal cashValue = new BigDecimal(dataFormatter.formatCellValue(cashCell).replace(",", ""));
				BigDecimal shortTermInvestValue = new BigDecimal(
						dataFormatter.formatCellValue(shortTermInvestCell).replace(",", ""));
				BigDecimal longTermInvestValue = new BigDecimal(
						dataFormatter.formatCellValue(longTermInvestCell).replace(",", ""));

				BigDecimal shortTermDebtValue = new BigDecimal(
						dataFormatter.formatCellValue(shortTermDebtCell).replace(",", ""));
				BigDecimal longTermDebtValue = new BigDecimal(
						dataFormatter.formatCellValue(longTermDebtCell).replace(",", ""));

				BigDecimal assets = cashValue.add(shortTermInvestValue).add(longTermInvestValue);
				BigDecimal liabilities = shortTermDebtValue.add(longTermDebtValue);

				BigDecimal netEquityValue = assets.subtract(liabilities);

				String tickerValue = dataFormatter.formatCellValue(tickerCell);
				String shareholdersEquityValue = dataFormatter.formatCellValue(shareholdersEquity).replace(",", "");

				if (tickerToBSData.containsKey(tickerValue)) {
					List<BSInfoBO> bsInfoList = tickerToBSData.get(tickerValue);
					BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
					bsInfo.setShareHoldersEquity(shareholdersEquityValue);
					bsInfo.setNetEquity(netEquityValue.toString());
					bsInfoList.add(bsInfo);
				} else {
					List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();
					BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
					bsInfo.setShareHoldersEquity(shareholdersEquityValue);
					bsInfo.setNetEquity(netEquityValue.toString());
					bsInfoList.add(bsInfo);
					tickerToBSData.put(tickerValue, bsInfoList);
				}

				yearCounter--;
			}
		}

		calculateBSData(tickerToBSData);

		return tickerToBSData;
	}

	public static void calculateBSData(Map<String, List<BSInfoBO>> tickerToBSData) {

		Iterator<Entry<String, List<BSInfoBO>>> iterator = tickerToBSData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<BSInfoBO>> entry = iterator.next();
			List<BSInfoBO> bsInfoList = entry.getValue();

			BSInfoBO firstYearBSInfo = bsInfoList.get(0);
			BSInfoBO secondYearBSInfo = bsInfoList.get(1);
			BSInfoBO thirdYearBSInfo = bsInfoList.get(2);
			BSInfoBO fourthYearBSInfo = bsInfoList.get(3);
			BSInfoBO fifthYearBSInfo = bsInfoList.get(4);

			String firstYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getShareHoldersEquity(), secondYearBSInfo.getShareHoldersEquity());
			firstYearBSInfo.setShareHoldersEquityGrowth(firstYearShareholdersGrowthRate);
			String secondYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getShareHoldersEquity(), thirdYearBSInfo.getShareHoldersEquity());
			secondYearBSInfo.setShareHoldersEquityGrowth(secondYearShareholdersGrowthRate);
			String thirdYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getShareHoldersEquity(), fourthYearBSInfo.getShareHoldersEquity());
			thirdYearBSInfo.setShareHoldersEquityGrowth(thirdYearShareholdersGrowthRate);
			String fourthYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearBSInfo.getShareHoldersEquity(), fifthYearBSInfo.getShareHoldersEquity());
			fourthYearBSInfo.setShareHoldersEquityGrowth(fourthYearShareholdersGrowthRate);

			String firstYearNetEquityGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearBSInfo.getNetEquity(), secondYearBSInfo.getNetEquity());
			firstYearBSInfo.setNetEquityGrowth(firstYearNetEquityGrowthRate);
			String secondYearNetEquityGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearBSInfo.getNetEquity(), thirdYearBSInfo.getNetEquity());
			secondYearBSInfo.setNetEquityGrowth(secondYearNetEquityGrowthRate);
			String thirdYearNetEquityGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearBSInfo.getNetEquity(), fourthYearBSInfo.getNetEquity());
			thirdYearBSInfo.setNetEquityGrowth(thirdYearNetEquityGrowthRate);
			String fourthYearNetEquityGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(fourthYearBSInfo.getNetEquity(), fifthYearBSInfo.getNetEquity());
			fourthYearBSInfo.setNetEquityGrowth(fourthYearNetEquityGrowthRate);

		}

	}

	public static void writeBSInfo(BSInfoBO bsInfo, Row row) {
		Cell shareHoldersEquityCell = row.createCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());
		shareHoldersEquityCell.setCellValue(bsInfo.getShareHoldersEquity());

		Cell intangibleAssetsCell = row.createCell(BSSheetConstants.INTANGIBLE_ASSETS_COLUMN.getBSData());
		intangibleAssetsCell.setCellValue(bsInfo.getTotalIntangibleAssets());

		Cell cashAndCashEquivalentCell = row.createCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
		cashAndCashEquivalentCell.setCellValue(bsInfo.getCashAndCashEquivalent());

		Cell shortTermInvestingCell = row.createCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
		shortTermInvestingCell.setCellValue(bsInfo.getShortTermInvestments());

		Cell longTermInvestmentCell = row.createCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
		longTermInvestmentCell.setCellValue(bsInfo.getLongTermInvestments());

		Cell shortTermDebtCell = row.createCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
		shortTermDebtCell.setCellValue(bsInfo.getShortTermDebt());

		Cell longTermDebtCell = row.createCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());
		longTermDebtCell.setCellValue(bsInfo.getLongTermDebt());

		Cell recievablesCell = row.createCell(BSSheetConstants.RECEIVABLES_COLUMN.getBSData());
		recievablesCell.setCellValue(bsInfo.getReceivables());

		Cell payableCell = row.createCell(BSSheetConstants.ACCOUNTS_PAYABLE_COLUMN.getBSData());
		payableCell.setCellValue(bsInfo.getAccountsPayable());

		Cell dateCell = row.createCell(BSSheetConstants.BS_DATE.getBSData());
		dateCell.setCellValue(bsInfo.getBsDate());

		Cell currencyTypeCell = row.createCell(BSSheetConstants.CURRENCY_TYPE.getBSData());
		currencyTypeCell.setCellValue(bsInfo.getCurrencyType());
	}

}
