/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package excel.library;

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

import create.excel.bo.CFInfoBO;
import create.excel.enums.BSSheetConstants;
import create.excel.enums.CFSheetConstants;
import create.excel.enums.CommonSheetConstants;

public class CFFinancialLibrary {

	public CFFinancialLibrary() {

	}

	public static Map<String, List<CFInfoBO>> readCFData(List<Sheet> cfSheets) {
		Map<String, List<CFInfoBO>> tickerToCFData = new HashMap<String, List<CFInfoBO>>();
		int yearCounter = 0;
		for (Sheet currentCFSheet : cfSheets) {
			Iterator<Row> rowIterator = currentCFSheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!CommonFinancialLibrary.determineHeaderRow(row)) {
					Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
					Cell freeCashFlowCell = row.getCell(CFSheetConstants.FREE_CASH_FLOW_COLUMN.getCfData());
					Cell netCashForInvestCell = row.getCell(CFSheetConstants.NET_CASH_FOR_INVESTING_COLUMN.getCfData());
					Cell netCashForFinancingCell = row
							.getCell(CFSheetConstants.NET_CASH_FOR_FINANCING_COLUMN.getCfData());
					Cell cfDateCell = row.getCell(CFSheetConstants.CF_DATE.getCfData());
					Cell currencyTypeCell = row.getCell(CFSheetConstants.CURRENCY_TYPE.getCfData());

					try {
						String tickerValue = dataFormatter.formatCellValue(tickerCell).replace(",", "");
						String freeCashFlowValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(freeCashFlowCell).replace(",", ""));
						String netCashForInvestValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(netCashForInvestCell).replace(",", ""));
						String netCashForFinancingValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(netCashForFinancingCell).replace(",", ""));
						String cfDate = dataFormatter.formatCellValue(cfDateCell);
						String currencyTypeValue = dataFormatter.formatCellValue(currencyTypeCell);

						if (tickerToCFData.containsKey(tickerValue)) {
							List<CFInfoBO> cfInfoList = tickerToCFData.get(tickerValue);
							CFInfoBO cfInfo = new CFInfoBO(yearCounter, tickerValue);
							cfInfo.setFreeCashFlow(freeCashFlowValue);
							cfInfo.setNetCashForFinancingActivities(netCashForFinancingValue);
							cfInfo.setNetCashForInvestingActivities(netCashForInvestValue);
							cfInfo.setCfDate(cfDate);
							cfInfo.setCurrencyType(currencyTypeValue);
							cfInfoList.add(cfInfo);
						} else {
							List<CFInfoBO> cfInfoList = new ArrayList<CFInfoBO>();
							CFInfoBO cfInfo = new CFInfoBO(yearCounter, tickerValue);
							cfInfo.setFreeCashFlow(freeCashFlowValue);
							cfInfo.setNetCashForFinancingActivities(netCashForFinancingValue);
							cfInfo.setNetCashForInvestingActivities(netCashForInvestValue);
							cfInfo.setCurrencyType(currencyTypeValue);
							cfInfo.setCfDate(cfDate);
							cfInfoList.add(cfInfo);
							tickerToCFData.put(tickerValue, cfInfoList);
						}
					} catch (Exception e) {
						String tickerValue = dataFormatter.formatCellValue(tickerCell).replace(",", "");
						if (tickerToCFData.containsKey(tickerValue)) {
							List<CFInfoBO> cfInfoList = tickerToCFData.get(tickerValue);
							CFInfoBO cfInfo = new CFInfoBO(yearCounter, tickerValue);
							cfInfo.setFreeCashFlow(CommonFinancialLibrary.errorMessage());
							cfInfo.setNetCashForFinancingActivities(CommonFinancialLibrary.errorMessage());
							cfInfo.setNetCashForInvestingActivities(CommonFinancialLibrary.errorMessage());
							cfInfo.setCfDate(CommonFinancialLibrary.errorMessage());
							cfInfo.setCurrencyType(CommonFinancialLibrary.errorMessage());
							cfInfoList.add(cfInfo);
						} else {
							List<CFInfoBO> cfInfoList = new ArrayList<CFInfoBO>();
							CFInfoBO cfInfo = new CFInfoBO(yearCounter, tickerValue);
							cfInfo.setFreeCashFlow(CommonFinancialLibrary.errorMessage());
							cfInfo.setNetCashForFinancingActivities(CommonFinancialLibrary.errorMessage());
							cfInfo.setNetCashForInvestingActivities(CommonFinancialLibrary.errorMessage());
							cfInfo.setCfDate(CommonFinancialLibrary.errorMessage());
							cfInfoList.add(cfInfo);
							cfInfo.setCurrencyType(CommonFinancialLibrary.errorMessage());
							tickerToCFData.put(tickerValue, cfInfoList);
						}
					}

				}

			}
			yearCounter--;
		}

		calculateCFData(tickerToCFData);

		return tickerToCFData;
	}

	public static void calculateCFData(Map<String, List<CFInfoBO>> tickerToCFData) {

		Iterator<Entry<String, List<CFInfoBO>>> iterator = tickerToCFData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<CFInfoBO>> entry = iterator.next();
			List<CFInfoBO> cfInfoList = entry.getValue();

			CFInfoBO firstYearCFInfo = cfInfoList.get(0);
			CFInfoBO secondYearCFInfo = cfInfoList.get(1);
			CFInfoBO thirdYearCFInfo = cfInfoList.get(2);
			CFInfoBO fourthYearCFInfo = cfInfoList.get(3);
			//CFInfoBO fifthYearCFInfo = cfInfoList.get(4);

			String firstYearFreeCashFlowGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearCFInfo.getFreeCashFlow(), secondYearCFInfo.getFreeCashFlow());
			firstYearCFInfo.setFreeCashFlowGrowth(firstYearFreeCashFlowGrowthRate);
			String secondYearFreeCashFlowGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearCFInfo.getFreeCashFlow(), thirdYearCFInfo.getFreeCashFlow());
			secondYearCFInfo.setFreeCashFlowGrowth(secondYearFreeCashFlowGrowthRate);
			String thirdYearFreeCashFlowGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearCFInfo.getFreeCashFlow(), fourthYearCFInfo.getFreeCashFlow());
			thirdYearCFInfo.setFreeCashFlowGrowth(thirdYearFreeCashFlowGrowthRate);
//			String fourthYearFreeCashFlowGrowthRate = CommonFinancialLibrary
//					.calculateGrowthRate(fourthYearCFInfo.getFreeCashFlow(), fifthYearCFInfo.getFreeCashFlow());
//			fourthYearCFInfo.setFreeCashFlowGrowth(fourthYearFreeCashFlowGrowthRate);

		}

	}

	public static void writeCFInfo(CFInfoBO cfInfo, Row row) {
		Cell netCashByOperatingCell = row
				.createCell(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_COLUMN.getCfData());
		netCashByOperatingCell.setCellValue(
				CommonFinancialLibrary.addAppropriateCommasToNumber(cfInfo.getNetCashByOperatingActivites()));

		Cell netCashForFinancingCell = row.createCell(CFSheetConstants.NET_CASH_FOR_FINANCING_COLUMN.getCfData());
		netCashForFinancingCell.setCellValue(
				CommonFinancialLibrary.addAppropriateCommasToNumber(cfInfo.getNetCashForFinancingActivities()));

		Cell netCashForInvestingCell = row.createCell(CFSheetConstants.NET_CASH_FOR_INVESTING_COLUMN.getCfData());
		netCashForInvestingCell.setCellValue(
				CommonFinancialLibrary.addAppropriateCommasToNumber(cfInfo.getNetCashForInvestingActivities()));

		Cell capitalExpenditureCell = row.createCell(CFSheetConstants.CAPITAL_EXPENDITURE_COLUMN.getCfData());
		capitalExpenditureCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(cfInfo.getCapitalExpenditure()));

		Cell dateCell = row.createCell(CFSheetConstants.CF_DATE.getCfData());
		dateCell.setCellValue(cfInfo.getCfDate());

		Cell freeCashFlowCell = row.createCell(CFSheetConstants.FREE_CASH_FLOW_COLUMN.getCfData());
		freeCashFlowCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(cfInfo.getFreeCashFlow()));

		Cell currencyTypeCell = row.createCell(CFSheetConstants.CURRENCY_TYPE.getCfData());
		currencyTypeCell.setCellValue(cfInfo.getCurrencyType());
	}

	public static CFInfoBO createErrorCFInfoBO(String ticker) {
		CFInfoBO cfInfo = new CFInfoBO(0, ticker);
		cfInfo.setCapitalExpenditure("ERROR");
		cfInfo.setCfDate("ERROR");
		cfInfo.setCurrencyType("ERROR");
		cfInfo.setFreeCashFlow("ERROR");
		cfInfo.setNetCashByOperatingActivites("ERROR");
		cfInfo.setNetCashForFinancingActivities("ERROR");
		cfInfo.setNetCashForInvestingActivities("ERROR");
		return cfInfo;
	}

}
