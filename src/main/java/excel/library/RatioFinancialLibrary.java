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
import create.excel.bo.RatioInfoBO;
import create.excel.enums.CommonSheetConstants;
import create.excel.enums.RatioSheetConstants;

public class RatioFinancialLibrary {

	public RatioFinancialLibrary() {

	}

	public static Map<String, List<RatioInfoBO>> readRatioData(List<Sheet> ratioSheets) {
		Map<String, List<RatioInfoBO>> tickerToRatioData = new HashMap<String, List<RatioInfoBO>>();
		int yearCounter = 0;
		for (Sheet currentRatioSheet : ratioSheets) {
			Iterator<Row> rowIterator = currentRatioSheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!CommonFinancialLibrary.determineHeaderRow(row)) {
					Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
					Cell currentRatioCell = row.getCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData());
					Cell debtEquityRatioCell = row.getCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData());
					Cell EPSCell = row.getCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData());
					Cell ratioDateCell = row.getCell(RatioSheetConstants.RATIO_DATE.getRatioData());

					try {
						String tickerValue = dataFormatter.formatCellValue(tickerCell);
						String currentRatioValue = dataFormatter.formatCellValue(currentRatioCell);
						String debtEquityRatioValue = dataFormatter.formatCellValue(debtEquityRatioCell);
						String EPSValue = dataFormatter.formatCellValue(EPSCell);
						String ratioDate = dataFormatter.formatCellValue(ratioDateCell);

						if (tickerToRatioData.containsKey(tickerValue)) {
							List<RatioInfoBO> ratioInfoList = tickerToRatioData.get(tickerValue);
							RatioInfoBO ratioInfo = new RatioInfoBO(yearCounter, tickerValue);
							ratioInfo.setCurrentRatio(currentRatioValue);
							ratioInfo.setDebtToEquityRatio(debtEquityRatioValue);
							ratioInfo.setEarningsPerShare(EPSValue);
							ratioInfo.setRatioDate(ratioDate);
							ratioInfoList.add(ratioInfo);
						} else {
							List<RatioInfoBO> ratioInfoList = new ArrayList<RatioInfoBO>();
							RatioInfoBO ratioInfo = new RatioInfoBO(yearCounter, tickerValue);
							ratioInfo.setCurrentRatio(currentRatioValue);
							ratioInfo.setDebtToEquityRatio(debtEquityRatioValue);
							ratioInfo.setEarningsPerShare(EPSValue);
							ratioInfo.setRatioDate(ratioDate);
							ratioInfoList.add(ratioInfo);
							tickerToRatioData.put(tickerValue, ratioInfoList);
						}
					} catch (Exception e) {
						String tickerValue = dataFormatter.formatCellValue(tickerCell);
						if (tickerToRatioData.containsKey(tickerValue)) {
							List<RatioInfoBO> ratioInfoList = tickerToRatioData.get(tickerValue);
							RatioInfoBO ratioInfo = new RatioInfoBO(yearCounter, tickerValue);
							ratioInfo.setCurrentRatio(CommonFinancialLibrary.errorMessage());
							ratioInfo.setDebtToEquityRatio(CommonFinancialLibrary.errorMessage());
							ratioInfo.setEarningsPerShare(CommonFinancialLibrary.errorMessage());
							ratioInfo.setRatioDate(CommonFinancialLibrary.errorMessage());
							ratioInfoList.add(ratioInfo);
						} else {
							List<RatioInfoBO> ratioInfoList = new ArrayList<RatioInfoBO>();
							RatioInfoBO ratioInfo = new RatioInfoBO(yearCounter, tickerValue);
							ratioInfo.setCurrentRatio(CommonFinancialLibrary.errorMessage());
							ratioInfo.setDebtToEquityRatio(CommonFinancialLibrary.errorMessage());
							ratioInfo.setEarningsPerShare(CommonFinancialLibrary.errorMessage());
							ratioInfo.setRatioDate(CommonFinancialLibrary.errorMessage());
							ratioInfoList.add(ratioInfo);
							tickerToRatioData.put(tickerValue, ratioInfoList);
						}

					}

				}

			}
			yearCounter--;
		}

		calculateRatioData(tickerToRatioData);

		return tickerToRatioData;
	}

	public static void calculateRatioData(Map<String, List<RatioInfoBO>> tickerToRatioData) {

		Iterator<Entry<String, List<RatioInfoBO>>> iterator = tickerToRatioData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<RatioInfoBO>> entry = iterator.next();
			List<RatioInfoBO> ratioInfoList = entry.getValue();

			RatioInfoBO firstYearRatioInfo = ratioInfoList.get(0);
			RatioInfoBO secondYearRatioInfo = ratioInfoList.get(1);
			RatioInfoBO thirdYearRatioInfo = ratioInfoList.get(2);
			RatioInfoBO fourthYearRatioInfo = ratioInfoList.get(3);
			RatioInfoBO fifthYearRatioInfo = ratioInfoList.get(4);

			String firstYearCurrentRatioGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(firstYearRatioInfo.getCurrentRatio(), secondYearRatioInfo.getCurrentRatio());
			firstYearRatioInfo.setCurrentRatioGrowth(firstYearCurrentRatioGrowthRate);
			String secondYearCurrentRatioGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(secondYearRatioInfo.getCurrentRatio(), thirdYearRatioInfo.getCurrentRatio());
			secondYearRatioInfo.setCurrentRatioGrowth(secondYearCurrentRatioGrowthRate);
			String thirdYearCurrentRatioGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(thirdYearRatioInfo.getCurrentRatio(), fourthYearRatioInfo.getCurrentRatio());
			thirdYearRatioInfo.setCurrentRatioGrowth(thirdYearCurrentRatioGrowthRate);
			String fourthYearCurrentRatioGrowthRate = CommonFinancialLibrary
					.calculateGrowthRate(fourthYearRatioInfo.getCurrentRatio(), fifthYearRatioInfo.getCurrentRatio());
			fourthYearRatioInfo.setCurrentRatioGrowth(fourthYearCurrentRatioGrowthRate);

			String firstYearDebtEquityRatioGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearRatioInfo.getDebtToEquityRatio(), secondYearRatioInfo.getDebtToEquityRatio());
			firstYearRatioInfo.setDebtToEquityRatioGrowth(firstYearDebtEquityRatioGrowthRate);
			String secondYearDebtEquityRatioGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearRatioInfo.getDebtToEquityRatio(), thirdYearRatioInfo.getDebtToEquityRatio());
			secondYearRatioInfo.setDebtToEquityRatioGrowth(secondYearDebtEquityRatioGrowthRate);
			String thirdYearDebtEquityRatioGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearRatioInfo.getDebtToEquityRatio(), fourthYearRatioInfo.getDebtToEquityRatio());
			thirdYearRatioInfo.setDebtToEquityRatioGrowth(thirdYearDebtEquityRatioGrowthRate);
			String fourthYearDebtEquityGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearRatioInfo.getDebtToEquityRatio(), fifthYearRatioInfo.getDebtToEquityRatio());
			fourthYearRatioInfo.setDebtToEquityRatioGrowth(fourthYearDebtEquityGrowthRate);

			String firstYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearRatioInfo.getEarningsPerShare(), secondYearRatioInfo.getEarningsPerShare());
			firstYearRatioInfo.setEarningsPerShareGrowth(firstYearEPSGrowthRate);
			String secondYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearRatioInfo.getEarningsPerShare(), thirdYearRatioInfo.getEarningsPerShare());
			secondYearRatioInfo.setEarningsPerShareGrowth(secondYearEPSGrowthRate);
			String thirdYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearRatioInfo.getEarningsPerShare(), fourthYearRatioInfo.getEarningsPerShare());
			thirdYearRatioInfo.setEarningsPerShareGrowth(thirdYearEPSGrowthRate);
			String fourthYearEPSGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					fourthYearRatioInfo.getEarningsPerShare(), fifthYearRatioInfo.getEarningsPerShare());
			fourthYearRatioInfo.setEarningsPerShareGrowth(fourthYearEPSGrowthRate);

		}

	}

	public static void writeRatioInfo(ISInfoBO isInfo, RatioInfoBO ratioInfo, Row row) {
		String ratioDate = ratioInfo.getRatioDate();

		Cell earningsPerShareCell = row.createCell(RatioSheetConstants.EARNINGS_PER_SHARE_COLUMN.getRatioData());
		earningsPerShareCell.setCellValue(isInfo.getEps());

		Cell currentRatioCell = row.createCell(RatioSheetConstants.CURRENT_RATIO_COLUMN.getRatioData());
		currentRatioCell.setCellValue(ratioInfo.getCurrentRatio());

		Cell debtEquityRatioCell = row.createCell(RatioSheetConstants.DEBT_EQUITY_RATIO_COLUMN.getRatioData());
		debtEquityRatioCell.setCellValue(ratioInfo.getDebtToEquityRatio());

		Cell dateCell = row.createCell(RatioSheetConstants.RATIO_DATE.getRatioData());
		dateCell.setCellValue(ratioDate);
	}

	public static RatioInfoBO createErrorRatioInfoBO(String ticker) {
		RatioInfoBO ratioInfo = new RatioInfoBO(0, ticker);
		ratioInfo.setCurrentRatio("ERROR");
		ratioInfo.setDebtToEquityRatio("ERROR");
		ratioInfo.setEarningsPerShare("ERROR");
		ratioInfo.setPeRatio("ERROR");
		ratioInfo.setRatioDate("ERROR");
		return ratioInfo;
	}

}
