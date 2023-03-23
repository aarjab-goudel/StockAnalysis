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
	// This method actually reads from the balance sheet excel document and stores them into a map
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


					Cell bsDateCell = row.getCell(BSSheetConstants.BS_DATE.getBSData());
					
					Cell totalAssetsCell = row.getCell(BSSheetConstants.TOTAL_ASSETS_COLUMN.getBSData());
					Cell totalLiabilitiesCell = row.getCell(BSSheetConstants.TOTAL_LIABILITIES_COLUMN.getBSData());
					
					Cell totalCashMRQCell = row.getCell(BSSheetConstants.TOTAL_CASH_MRQ_COLUMN.getBSData());
					Cell totalDebtMRQCell = row.getCell(BSSheetConstants.TOTAL_DEBT_MRQ_COLUMN.getBSData());
					Cell currentRatioMRQCell = row.getCell(BSSheetConstants.CURRENT_RATIO_MRQ_COLUMN.getBSData());
					Cell currencyTypeCell = row.getCell(BSSheetConstants.CURRENCY_TYPE.getBSData());

					

					try {

						
						BigDecimal totalAssetsValue = new BigDecimal(
								dataFormatter.formatCellValue(totalAssetsCell).replace(",", ""));
						BigDecimal totalLiabilitiesValue = new BigDecimal(
								dataFormatter.formatCellValue(totalLiabilitiesCell).replace(",", ""));

						
						
				


						String tickerValue = CommonFinancialLibrary
								.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell));
						String shareholdersEquityValue = CommonFinancialLibrary.removeDecimalFromNumber(
								dataFormatter.formatCellValue(shareholdersEquity).replace(",", ""));
		
						String totalCashMRQValue = dataFormatter.formatCellValue(totalCashMRQCell);
						String totalDebtMRQValue = dataFormatter.formatCellValue(totalDebtMRQCell);
						String currentRatioMRQValue = dataFormatter.formatCellValue(currentRatioMRQCell);
						String currencyTypeValue = dataFormatter.formatCellValue(currencyTypeCell);
						
						String bsDate = dataFormatter.formatCellValue(bsDateCell);

						if (tickerToBSData.containsKey(tickerValue)) {
							List<BSInfoBO> bsInfoList = tickerToBSData.get(tickerValue);
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(shareholdersEquityValue);
							bsInfo.setCurrencyType(currencyTypeValue);

							
							bsInfo.setTotalAssets(
									CommonFinancialLibrary.removeDecimalFromNumber(totalAssetsValue.toString()));
							bsInfo.setTotalLiabilities(
									CommonFinancialLibrary.removeDecimalFromNumber(totalLiabilitiesValue.toString()));
							
							bsInfo.setTotalStrCash(totalCashMRQValue);
							bsInfo.setTotalDebtCash(totalDebtMRQValue);
							bsInfo.setStrCurrentRatio(currentRatioMRQValue);

							

							bsInfo.setBsDate(bsDate);
							bsInfoList.add(bsInfo);
						} else {
							List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(shareholdersEquityValue);
							bsInfo.setCurrencyType(currencyTypeValue);

							bsInfo.setTotalAssets(
									CommonFinancialLibrary.removeDecimalFromNumber(totalAssetsValue.toString()));
							bsInfo.setTotalLiabilities(
									CommonFinancialLibrary.removeDecimalFromNumber(totalLiabilitiesValue.toString()));
							

							
							bsInfo.setTotalStrCash(totalCashMRQValue);
							bsInfo.setTotalDebtCash(totalDebtMRQValue);
							bsInfo.setStrCurrentRatio(currentRatioMRQValue);
							
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
							bsInfo.setCurrencyType(CommonFinancialLibrary.errorMessage());

							
							bsInfo.setTotalAssets(
									CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setTotalLiabilities(
									CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							
							bsInfo.setTotalStrCash(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setTotalDebtCash(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setStrCurrentRatio(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							bsInfo.setBsDate(CommonFinancialLibrary.errorMessage());
							bsInfoList.add(bsInfo);
						} else {
							List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();
							BSInfoBO bsInfo = new BSInfoBO(yearCounter, tickerValue);
							bsInfo.setShareHoldersEquity(CommonFinancialLibrary.errorMessage());
							bsInfo.setCurrencyType(CommonFinancialLibrary.errorMessage());

							
							bsInfo.setTotalAssets(
									CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setTotalLiabilities(
									CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));

							
							bsInfo.setTotalStrCash(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setTotalDebtCash(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							bsInfo.setStrCurrentRatio(CommonFinancialLibrary.removeDecimalFromNumber(CommonFinancialLibrary.errorMessage()));
							
							
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
	// This method takes all the BSInfoBO and calculates the growth of the BSInfoBO values
	public static void calculateBSData(Map<String, List<BSInfoBO>> tickerToBSData) {

		Iterator<Entry<String, List<BSInfoBO>>> iterator = tickerToBSData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<BSInfoBO>> entry = iterator.next();
			List<BSInfoBO> bsInfoList = entry.getValue();

			BSInfoBO firstYearBSInfo = bsInfoList.get(0);
			BSInfoBO secondYearBSInfo = bsInfoList.get(1);
			BSInfoBO thirdYearBSInfo = bsInfoList.get(2);
			BSInfoBO fourthYearBSInfo = bsInfoList.get(3);
			//BSInfoBO fifthYearBSInfo = bsInfoList.get(4);

			String firstYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getShareHoldersEquity(), secondYearBSInfo.getShareHoldersEquity());
			firstYearBSInfo.setShareHoldersEquityGrowth(firstYearShareholdersGrowthRate);
			String secondYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getShareHoldersEquity(), thirdYearBSInfo.getShareHoldersEquity());
			secondYearBSInfo.setShareHoldersEquityGrowth(secondYearShareholdersGrowthRate);
			String thirdYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getShareHoldersEquity(), fourthYearBSInfo.getShareHoldersEquity());
			thirdYearBSInfo.setShareHoldersEquityGrowth(thirdYearShareholdersGrowthRate);
//			String fourthYearShareholdersGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
//					fourthYearBSInfo.getShareHoldersEquity(), fifthYearBSInfo.getShareHoldersEquity());
//			fourthYearBSInfo.setShareHoldersEquityGrowth(fourthYearShareholdersGrowthRate);
			
			String firstYearTotalAssetsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getTotalAssets(), secondYearBSInfo.getTotalAssets());
			firstYearBSInfo.setTotalAssetsGrowth(firstYearTotalAssetsGrowthRate);
			String secondYearTotalAssetsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getTotalAssets(), thirdYearBSInfo.getTotalAssets());
			secondYearBSInfo.setTotalAssetsGrowth(secondYearTotalAssetsGrowthRate);
			String thirdYearTotalAssetsGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getTotalAssets(), fourthYearBSInfo.getTotalAssets());
			thirdYearBSInfo.setTotalAssetsGrowth(thirdYearTotalAssetsGrowthRate);
			
			String firstYearTotalLiabilitiesGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					firstYearBSInfo.getTotalLiabilities(), secondYearBSInfo.getTotalLiabilities());
			firstYearBSInfo.setTotalLiabilitiesGrowth(firstYearTotalLiabilitiesGrowthRate);
			String secondYearTotalLiabilitiesGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					secondYearBSInfo.getTotalLiabilities(), thirdYearBSInfo.getTotalLiabilities());
			secondYearBSInfo.setTotalLiabilitiesGrowth(secondYearTotalLiabilitiesGrowthRate);
			String thirdYearTotalLiabilitiesGrowthRate = CommonFinancialLibrary.calculateGrowthRate(
					thirdYearBSInfo.getTotalLiabilities(), fourthYearBSInfo.getTotalLiabilities());
			thirdYearBSInfo.setTotalLiabilitiesGrowth(thirdYearTotalLiabilitiesGrowthRate);
			


		}

	}

	public static void writeBSInfo(BSInfoBO bsInfo, Row row) {
		Cell shareHoldersEquityCell = row.createCell(BSSheetConstants.SHARE_HOLDER_EQUITY_COLUMN.getBSData());
		shareHoldersEquityCell
				.setCellValue(CommonFinancialLibrary.addAppropriateCommasToNumber(bsInfo.getShareHoldersEquity()));


		Cell dateCell = row.createCell(BSSheetConstants.BS_DATE.getBSData());
		dateCell.setCellValue(bsInfo.getBsDate());

		Cell currencyTypeCell = row.createCell(BSSheetConstants.CURRENCY_TYPE.getBSData());
		currencyTypeCell.setCellValue(bsInfo.getCurrencyType());
	}

	public static BSInfoBO createErrorBSInfoBO(String ticker) {
		BSInfoBO bsInfo = new BSInfoBO(0, ticker);
		bsInfo.setBsDate("ERROR");
		bsInfo.setCurrencyType("ERROR");
		bsInfo.setTotalStrCash("ERROR");
		bsInfo.setTotalDebtCash("ERROR");
		bsInfo.setStrCurrentRatio("ERROR");
		
		
		bsInfo.setShareHoldersEquity("ERROR");
		
		return bsInfo;
	}

}
