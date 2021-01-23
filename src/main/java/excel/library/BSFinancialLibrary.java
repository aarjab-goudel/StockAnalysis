/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
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
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!CommonFinancialLibrary.determineHeaderRow(row)) {
					Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());

					Cell shareholdersEquity = row.getCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());

					Cell cashCell = row.getCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
					Cell shortTermInvestCell = row.getCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
					Cell longTermInvestCell = row.getCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
					Cell shortTermDebtCell = row.getCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
					Cell longTermDebtCell = row.getCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());
					Cell bsDateCell = row.getCell(BSSheetConstants.BS_DATE.getBSData());

					try {

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

						String tickerValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell));
						String shareholdersEquityValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(shareholdersEquity).replace(",", ""));
						String bsDate = dataFormatter.formatCellValue(bsDateCell);

						if (tickerToBSData.containsKey(tickerValue)) {
							List<BSInfoBO> bsInfoList = tickerToBSData.get(tickerValue);
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(shareholdersEquityValue);
							bsInfo.setNetEquity(
									CommonFinancialLibrary.removeDecimalFromNumber(netEquityValue.toString()));

							bsInfo.setCashAndCashEquivalent(
									CommonFinancialLibrary.removeDecimalFromNumber(cashValue.toString()));

							bsInfo.setShortTermInvestments(
									CommonFinancialLibrary.removeDecimalFromNumber(shortTermInvestValue.toString()));
							bsInfo.setShortTermDebt(
									CommonFinancialLibrary.removeDecimalFromNumber(shortTermDebtValue.toString()));

							bsInfo.setLongTermInvestments(
									CommonFinancialLibrary.removeDecimalFromNumber(longTermInvestValue.toString()));
							bsInfo.setLongTermDebt(
									CommonFinancialLibrary.removeDecimalFromNumber(longTermDebtValue.toString()));

							bsInfo.setBsDate(bsDate);
							bsInfoList.add(bsInfo);
						} else {
							List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(shareholdersEquityValue);
							bsInfo.setNetEquity(
									CommonFinancialLibrary.removeDecimalFromNumber(netEquityValue.toString()));
							bsInfo.setCashAndCashEquivalent(
									CommonFinancialLibrary.removeDecimalFromNumber(cashValue.toString()));

							bsInfo.setShortTermInvestments(
									CommonFinancialLibrary.removeDecimalFromNumber(shortTermInvestValue.toString()));
							bsInfo.setShortTermDebt(
									CommonFinancialLibrary.removeDecimalFromNumber(shortTermDebtValue.toString()));

							bsInfo.setLongTermInvestments(
									CommonFinancialLibrary.removeDecimalFromNumber(longTermInvestValue.toString()));
							bsInfo.setLongTermDebt(
									CommonFinancialLibrary.removeDecimalFromNumber(longTermDebtValue.toString()));
							bsInfo.setBsDate(bsDate);
							bsInfoList.add(bsInfo);
							tickerToBSData.put(tickerValue, bsInfoList);
						}

					} catch (Exception e) {
						String tickerValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell));
						if (tickerToBSData.containsKey(tickerValue)) {
							List<BSInfoBO> bsInfoList = tickerToBSData.get(tickerValue);
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(CommonFinancialLibrary.errorMessage());
							bsInfo.setNetEquity(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setCashAndCashEquivalent(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setShortTermInvestments(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setShortTermDebt(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setLongTermInvestments(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setLongTermDebt(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setBsDate(CommonFinancialLibrary.errorMessage());
							bsInfoList.add(bsInfo);
						} else {
							List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(CommonFinancialLibrary.errorMessage());
							bsInfo.setNetEquity(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setCashAndCashEquivalent(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setShortTermInvestments(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setShortTermDebt(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setLongTermInvestments(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setLongTermDebt(CommonFinancialLibrary
									.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setBsDate(CommonFinancialLibrary.errorMessage());
							bsInfoList.add(bsInfo);
							tickerToBSData.put(tickerValue, bsInfoList);
						}

					}

				}

			}
			yearCounter--;
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

			String firstYearCashAndEquivalentsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getCashAndCashEquivalent(), secondYearBSInfo.getCashAndCashEquivalent());
			firstYearBSInfo.setCashAndCashEquivalentGrowth(firstYearCashAndEquivalentsGrowthRate);
			String secondYearCashAndEquivalentsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getCashAndCashEquivalent(), thirdYearBSInfo.getCashAndCashEquivalent());
			secondYearBSInfo.setCashAndCashEquivalentGrowth(secondYearCashAndEquivalentsGrowthRate);
			String thirdYearCashAndEquivalentsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getCashAndCashEquivalent(), fourthYearBSInfo.getCashAndCashEquivalent());
			thirdYearBSInfo.setCashAndCashEquivalentGrowth(thirdYearCashAndEquivalentsGrowthRate);
			String fourthYearCashAndEquivalentsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearBSInfo.getCashAndCashEquivalent(), fifthYearBSInfo.getCashAndCashEquivalent());
			fourthYearBSInfo.setCashAndCashEquivalentGrowth(fourthYearCashAndEquivalentsGrowthRate);

			String firstYearShortTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearBSInfo.getShortTermDebt(), secondYearBSInfo.getShortTermDebt());
			firstYearBSInfo.setShortTermDebtGrowth(firstYearShortTermDebtGrowthRate);
			String secondYearShortTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearBSInfo.getShortTermDebt(), thirdYearBSInfo.getShortTermDebt());
			secondYearBSInfo.setShortTermDebtGrowth(secondYearShortTermDebtGrowthRate);
			String thirdYearShortTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearBSInfo.getShortTermDebt(), fourthYearBSInfo.getShortTermDebt());
			thirdYearBSInfo.setShortTermDebtGrowth(thirdYearShortTermDebtGrowthRate);
			String fourthYearShortTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(fourthYearBSInfo.getShortTermDebt(), fifthYearBSInfo.getShortTermDebt());
			fourthYearBSInfo.setShortTermDebtGrowth(fourthYearShortTermDebtGrowthRate);

			String firstYearShortTermInvestmentsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getShortTermInvestments(), secondYearBSInfo.getShortTermInvestments());
			firstYearBSInfo.setShortTermInvestmentsGrowth(firstYearShortTermInvestmentsGrowthRate);
			String secondYearShortTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getShortTermInvestments(), thirdYearBSInfo.getShortTermInvestments());
			secondYearBSInfo.setShortTermInvestmentsGrowth(secondYearShortTermInvestmentGrowthRate);
			String thirdYearShortTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getShortTermInvestments(), fourthYearBSInfo.getShortTermInvestments());
			thirdYearBSInfo.setShortTermInvestmentsGrowth(thirdYearShortTermInvestmentGrowthRate);
			String fourthYearShortTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearBSInfo.getShortTermInvestments(), fifthYearBSInfo.getShortTermInvestments());
			fourthYearBSInfo.setShortTermInvestmentsGrowth(fourthYearShortTermInvestmentGrowthRate);

			String firstYearLongTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getLongTermInvestments(), secondYearBSInfo.getLongTermInvestments());
			firstYearBSInfo.setLongTermInvestmentsGrowth(firstYearLongTermInvestmentGrowthRate);
			String secondYearLongTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getLongTermInvestments(), thirdYearBSInfo.getLongTermInvestments());
			secondYearBSInfo.setLongTermInvestmentsGrowth(secondYearLongTermInvestmentGrowthRate);
			String thirdYearLongTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getLongTermInvestments(), fourthYearBSInfo.getLongTermInvestments());
			thirdYearBSInfo.setLongTermInvestmentsGrowth(thirdYearLongTermInvestmentGrowthRate);
			String fourthYearLongTermInvestmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearBSInfo.getLongTermInvestments(), fifthYearBSInfo.getLongTermInvestments());
			fourthYearBSInfo.setLongTermInvestmentsGrowth(fourthYearLongTermInvestmentGrowthRate);

			String firstYearLongTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearBSInfo.getLongTermDebt(), secondYearBSInfo.getLongTermDebt());
			firstYearBSInfo.setLongTermDebtGrowth(firstYearLongTermDebtGrowthRate);
			String secondYearLongTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearBSInfo.getLongTermDebt(), thirdYearBSInfo.getLongTermDebt());
			secondYearBSInfo.setLongTermDebtGrowth(secondYearLongTermDebtGrowthRate);
			String thirdYearLongTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearBSInfo.getLongTermDebt(), fourthYearBSInfo.getLongTermDebt());
			thirdYearBSInfo.setLongTermDebtGrowth(thirdYearLongTermDebtGrowthRate);
			String fourthYearLongTermDebtGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(fourthYearBSInfo.getLongTermDebt(), fifthYearBSInfo.getLongTermDebt());
			fourthYearBSInfo.setLongTermDebtGrowth(fourthYearLongTermDebtGrowthRate);

		}

	}

	public static void writeBSInfo(BSInfoBO bsInfo, Row row) {
		Cell shareHoldersEquityCell = row.createCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());
		shareHoldersEquityCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getShareHoldersEquity()));

		Cell intangibleAssetsCell = row.createCell(BSSheetConstants.INTANGIBLE_ASSETS_COLUMN.getBSData());
		intangibleAssetsCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getTotalIntangibleAssets()));

		Cell cashAndCashEquivalentCell = row.createCell(BSSheetConstants.CASH_AND_CASH_EQUIVALENT_COLUMN.getBSData());
		cashAndCashEquivalentCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getCashAndCashEquivalent()));

		Cell shortTermInvestingCell = row.createCell(BSSheetConstants.SHORT_TERM_INVESTMENTS_COLUMN.getBSData());
		shortTermInvestingCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getShortTermInvestments()));

		Cell longTermInvestmentCell = row.createCell(BSSheetConstants.LONG_TERM_INVESTMENTS_COLUMN.getBSData());
		longTermInvestmentCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getLongTermInvestments()));

		Cell shortTermDebtCell = row.createCell(BSSheetConstants.SHORT_TERM_DEBT_COLUMN.getBSData());
		shortTermDebtCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getShortTermDebt()));

		Cell longTermDebtCell = row.createCell(BSSheetConstants.LONG_TERM_DEBT_COLUMN.getBSData());
		longTermDebtCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getLongTermDebt()));

		Cell recievablesCell = row.createCell(BSSheetConstants.RECEIVABLES_COLUMN.getBSData());
		recievablesCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getReceivables()));

		Cell payableCell = row.createCell(BSSheetConstants.ACCOUNTS_PAYABLE_COLUMN.getBSData());
		payableCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getAccountsPayable()));

		Cell dateCell = row.createCell(BSSheetConstants.BS_DATE.getBSData());
		dateCell.setCellValue(bsInfo.getBsDate());

		Cell currencyTypeCell = row.createCell(BSSheetConstants.CURRENCY_TYPE.getBSData());
		currencyTypeCell.setCellValue(bsInfo.getCurrencyType());
	}

	public static BSInfoBO createErrorBSInfoBO(String ticker) {
		BSInfoBO bsInfo = new BSInfoBO(0, ticker);
		bsInfo.setAccountsPayable("ERROR");
		bsInfo.setBsDate("ERROR");
		bsInfo.setCashAndCashEquivalent("ERROR");
		bsInfo.setCurrencyType("ERROR");
		bsInfo.setLongTermDebt("ERROR");
		bsInfo.setLongTermInvestments("ERROR");
		bsInfo.setNetEquity("ERROR");
		bsInfo.setReceivables("ERROR");
		bsInfo.setShareHoldersEquity("ERROR");
		bsInfo.setShortTermDebt("ERROR");
		bsInfo.setTotalIntangibleAssets("ERROR");
		bsInfo.setShortTermInvestments("ERROR");
		return bsInfo;
	}

}
