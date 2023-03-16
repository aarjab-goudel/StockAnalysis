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

import create.excel.bo.ISInfoBO;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.ISSheetConstants;

public class ISFinancialLibrary {

	public ISFinancialLibrary() {

	}

	public static Map<String, List<ISInfoBO>> readISData(List<Sheet> isSheets) {
		Map<String, List<ISInfoBO>> tickerToISData = new HashMap<String, List<ISInfoBO>>();
		int yearCounter = 0;
		for (Sheet currentISSheet : isSheets) {
			Iterator<Row> rowIterator = currentISSheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!CommonFinancialLibrary.determineHeaderRow(row)) {
					Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
					Cell researchAndDevelopmentCell = row
							.getCell(ISSheetConstants.RESEARCH_AND_DEVELOPMENT_COLUMN.getIsData());
					Cell revenueCell = row.getCell(ISSheetConstants.REVENUE_COLUMN.getIsData());
					Cell netIncomeCell = row.getCell(ISSheetConstants.NET_INCOME_COLUMN.getIsData());
					Cell grossProfitCell = row.getCell(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData());
					Cell isDateCell = row.getCell(ISSheetConstants.IS_DATE.getIsData());
					Cell epsCell = row.getCell(ISSheetConstants.EPS_COLUMN.getIsData());

					try {
						String tickerValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell).replace(",", ""));
						String researchAndDevelopmentValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(researchAndDevelopmentCell).replace(",", ""));
						String revenueValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(revenueCell).replace(",", ""));
						String netIncomeValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(netIncomeCell).replace(",", ""));
						String grossProfitValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(grossProfitCell).replace(",", ""));
						String epsValue = dataFormatter.formatCellValue(epsCell);
						String isDate = dataFormatter.formatCellValue(isDateCell);

						if (tickerToISData.containsKey(tickerValue)) {
							List<ISInfoBO> isInfoList = tickerToISData.get(tickerValue);
							ISInfoBO isInfo = new ISInfoBO(yearCounter, tickerValue);
							isInfo.setRevenue(revenueValue);
							isInfo.setNetIncome(netIncomeValue);
							isInfo.setGrossProfit(grossProfitValue);
							isInfo.setIsDate(isDate);
							isInfo.setEps(epsValue);
							isInfo.setResearchAndDevelopment(researchAndDevelopmentValue);
							isInfoList.add(isInfo);
						} else {
							List<ISInfoBO> isInfoList = new ArrayList<ISInfoBO>();
							ISInfoBO isInfo = new ISInfoBO(yearCounter, tickerValue);
							isInfo.setRevenue(revenueValue);
							isInfo.setNetIncome(netIncomeValue);
							isInfo.setGrossProfit(grossProfitValue);
							isInfo.setIsDate(isDate);
							isInfo.setEps(epsValue);
							isInfo.setResearchAndDevelopment(researchAndDevelopmentValue);
							isInfoList.add(isInfo);
							tickerToISData.put(tickerValue, isInfoList);
						}
					} catch (Exception e) {
						String tickerValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell).replace(",", ""));
						if (tickerToISData.containsKey(tickerValue)) {
							List<ISInfoBO> isInfoList = tickerToISData.get(tickerValue);
							ISInfoBO isInfo = new ISInfoBO(yearCounter, tickerValue);
							isInfo.setRevenue(CommonFinancialLibrary.errorMessage());
							isInfo.setNetIncome(CommonFinancialLibrary.errorMessage());
							isInfo.setGrossProfit(CommonFinancialLibrary.errorMessage());
							isInfo.setIsDate(CommonFinancialLibrary.errorMessage());
							isInfo.setEps(CommonFinancialLibrary.errorMessage());
							isInfo.setResearchAndDevelopment(CommonFinancialLibrary.errorMessage());
							isInfoList.add(isInfo);
						} else {
							List<ISInfoBO> isInfoList = new ArrayList<ISInfoBO>();
							ISInfoBO isInfo = new ISInfoBO(yearCounter, tickerValue);
							isInfo.setRevenue(CommonFinancialLibrary.errorMessage());
							isInfo.setNetIncome(CommonFinancialLibrary.errorMessage());
							isInfo.setGrossProfit(CommonFinancialLibrary.errorMessage());
							isInfo.setIsDate(CommonFinancialLibrary.errorMessage());
							isInfo.setEps(CommonFinancialLibrary.errorMessage());
							isInfo.setResearchAndDevelopment(CommonFinancialLibrary.errorMessage());
							isInfoList.add(isInfo);
							tickerToISData.put(tickerValue, isInfoList);
						}
					}

				}

			}
			yearCounter--;
		}

		calculateISData(tickerToISData);

		return tickerToISData;
	}

	public static void calculateISData(Map<String, List<ISInfoBO>> tickerToISData) {

		Iterator<Entry<String, List<ISInfoBO>>> iterator = tickerToISData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<ISInfoBO>> entry = iterator.next();
			List<ISInfoBO> isInfoList = entry.getValue();

			ISInfoBO firstYearISInfo = isInfoList.get(0);
			ISInfoBO secondYearISInfo = isInfoList.get(1);
			ISInfoBO thirdYearISInfo = isInfoList.get(2);
			ISInfoBO fourthYearISInfo = isInfoList.get(3);
			//ISInfoBO fifthYearISInfo = isInfoList.get(4);

			String firstYearRevenueGrowthRate = CommonFinancialLibrary.calculateGrowthRate(firstYearISInfo.getRevenue(),
					secondYearISInfo.getRevenue());
			firstYearISInfo.setRevenueGrowth(firstYearRevenueGrowthRate);
			String secondYearRevenueGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearISInfo.getRevenue(), thirdYearISInfo.getRevenue());
			secondYearISInfo.setRevenueGrowth(secondYearRevenueGrowthRate);
			String thirdYearRevenueGrowthRate = CommonFinancialLibrary.calculateGrowthRate(thirdYearISInfo.getRevenue(),
					fourthYearISInfo.getRevenue());
			thirdYearISInfo.setRevenueGrowth(thirdYearRevenueGrowthRate);
//			String fourthYearRevenueGrowthRate = CommonFinancialLibrary
//					.calculateGrowthRate(fourthYearISInfo.getRevenue(), fifthYearISInfo.getRevenue());
//			fourthYearISInfo.setRevenueGrowth(fourthYearRevenueGrowthRate);

			String firstYearResearchAndDevelopmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearISInfo.getResearchAndDevelopment(), secondYearISInfo.getResearchAndDevelopment());
			firstYearISInfo.setResearchAndDevelopmentGrowth(firstYearResearchAndDevelopmentGrowthRate);
			String secondYearResearchAndDevelopmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearISInfo.getResearchAndDevelopment(), thirdYearISInfo.getResearchAndDevelopment());
			secondYearISInfo.setResearchAndDevelopmentGrowth(secondYearResearchAndDevelopmentGrowthRate);
			String thirdYearResearchAndDevelopmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearISInfo.getResearchAndDevelopment(), fourthYearISInfo.getResearchAndDevelopment());
			thirdYearISInfo.setResearchAndDevelopmentGrowth(thirdYearResearchAndDevelopmentGrowthRate);
//			String fourthYearResearchAndDevelopmentGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
//					fourthYearISInfo.getResearchAndDevelopment(), fifthYearISInfo.getResearchAndDevelopment());
//			fourthYearISInfo.setResearchAndDevelopmentGrowth(fourthYearResearchAndDevelopmentGrowthRate);

			String firstYearGrossProfitGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearISInfo.getGrossProfit(), secondYearISInfo.getGrossProfit());
			firstYearISInfo.setGrossProfitGrowth(firstYearGrossProfitGrowthRate);
			String secondYearGrossProfitGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearISInfo.getGrossProfit(), thirdYearISInfo.getGrossProfit());
			secondYearISInfo.setGrossProfitGrowth(secondYearGrossProfitGrowthRate);
			String thirdYearGrossProfitGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearISInfo.getGrossProfit(), fourthYearISInfo.getGrossProfit());
			thirdYearISInfo.setGrossProfitGrowth(thirdYearGrossProfitGrowthRate);
//			String fourthYearGrossProfitGrowthRate = CommonFinancialLibrary
//					.calculateGrowthRate(fourthYearISInfo.getGrossProfit(), fifthYearISInfo.getGrossProfit());
//			fourthYearISInfo.setGrossProfitGrowth(fourthYearGrossProfitGrowthRate);

			String firstYearNetIncomeGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearISInfo.getNetIncome(), secondYearISInfo.getNetIncome());
			firstYearISInfo.setNetIncomeGrowth(firstYearNetIncomeGrowthRate);
			String secondYearNetIncomeGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearISInfo.getNetIncome(), thirdYearISInfo.getNetIncome());
			secondYearISInfo.setNetIncomeGrowth(secondYearNetIncomeGrowthRate);
			String thirdYearNetIncomeGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearISInfo.getNetIncome(), fourthYearISInfo.getNetIncome());
			thirdYearISInfo.setNetIncomeGrowth(thirdYearNetIncomeGrowthRate);
//			String fourthYearNetIncomeGrowthRate = CommonFinancialLibrary
//					.calculateGrowthRate(fourthYearISInfo.getNetIncome(), fifthYearISInfo.getNetIncome());
//			fourthYearISInfo.setNetIncomeGrowth(fourthYearNetIncomeGrowthRate);

			String firstYearEPSRate = CommonFinancialLibrary.calculateGrowthRate(firstYearISInfo.getEps(),
					secondYearISInfo.getEps());
			firstYearISInfo.setEpsGrowth(firstYearEPSRate);
			String secondYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(secondYearISInfo.getEps(),
					thirdYearISInfo.getEps());
			secondYearISInfo.setEpsGrowth(secondYearEPSGrowthRate);
			String thirdYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(thirdYearISInfo.getEps(),
					fourthYearISInfo.getEps());
			thirdYearISInfo.setEpsGrowth(thirdYearEPSGrowthRate);
//			String fourthYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(fourthYearISInfo.getEps(),
//					fifthYearISInfo.getEps());
//			fourthYearISInfo.setEpsGrowth(fourthYearEPSGrowthRate);

		}

	}

	public static void writeISInfo(ISInfoBO isInfo, Row row) {

		Cell researchAndDevelopmentCell = row.createCell(ISSheetConstants.RESEARCH_AND_DEVELOPMENT_COLUMN.getIsData());
		researchAndDevelopmentCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(isInfo.getResearchAndDevelopment()));

		Cell revenueCell = row.createCell(ISSheetConstants.REVENUE_COLUMN.getIsData());
		revenueCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(isInfo.getRevenue()));

		Cell epsCell = row.createCell(ISSheetConstants.EPS_COLUMN.getIsData());
		epsCell.setCellValue(isInfo.getEps());

		Cell costOfRevenueCell = row.createCell(ISSheetConstants.COST_OF_REVENUE_COLUMN.getIsData());
		costOfRevenueCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(isInfo.getCostOfRevenue()));

		Cell grossProfitCell = row.createCell(ISSheetConstants.GROSS_PROFIT_COLUMN.getIsData());
		grossProfitCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(isInfo.getGrossProfit()));

		Cell netIncomeCell = row.createCell(ISSheetConstants.NET_INCOME_COLUMN.getIsData());
		netIncomeCell.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(isInfo.getNetIncome()));

		Cell dateCell = row.createCell(ISSheetConstants.IS_DATE.getIsData());
		dateCell.setCellValue(isInfo.getIsDate());

		Cell currencyTypeCell = row.createCell(ISSheetConstants.CURRENCY_TYPE.getIsData());
		currencyTypeCell.setCellValue(isInfo.getCurrencyType());

	}

	public static ISInfoBO createErrorISInfoBO(String ticker) {
		ISInfoBO isInfo = new ISInfoBO(0, ticker);
		isInfo.setCostOfRevenue("ERROR");
		isInfo.setCurrencyType("ERROR");
		isInfo.setEps("ERROR");
		isInfo.setGrossProfit("ERROR");
		isInfo.setIsDate("ERROR");
		isInfo.setNetIncome("ERROR");
		isInfo.setRevenue("ERROR");
		isInfo.setResearchAndDevelopment("ERROR");
		return isInfo;
	}

}
