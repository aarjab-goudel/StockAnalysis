/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import create.excel.bo.BSInfoBO;
import excel.library.CommonFinancialLibrary;

public class BSAnalyzer {

	private Map<String, List<BSInfoBO>> tickerToBSData;
	private Map<String, String> tickerToTxtFilePaths;
	private Map<String, List<String>> tickerToNetEquity;
	private Map<String, List<String>> tickerToNetEquityGrowth;
	private Map<String, List<String>> tickerToShareholderEquity;
	private Map<String, List<String>> tickerToShareholderEquityGrowth;
	private Map<String, List<String>> tickerToCashAndEquivalents;
	private Map<String, List<String>> tickerToCashAndEquivalentsGrowth;
	private Map<String, List<String>> tickerToShortTermDebt;
	private Map<String, List<String>> tickerToShortTermDebtGrowth;
	private Map<String, List<String>> tickerToLongTermDebt;
	private Map<String, List<String>> tickerToLongTermDebtGrowth;
	private Map<String, List<String>> tickerToShortTermInvestments;
	private Map<String, List<String>> tickerToShortTermInvestmentsGrowth;
	private Map<String, List<String>> tickerToLongTermInvestments;
	private Map<String, List<String>> tickerToLongTermInvestmentsGrowth;
	private Map<String, List<String>> tickerToBSDates;
	private Map<String, List<String>> tickerToTotalAssets;
	private Map<String, List<String>> tickerToTotalAssetsGrowth;
	private Map<String, List<String>> tickerToTotalLiabilities;
	private Map<String, List<String>> tickerToTotalLiabilitiesGrowth;
	private Map<String, List<String>> tickerToTangibleAssets;
	private Map<String, List<String>> tickerToTangibleAssetsGrowth;
	private Map<String, List<String>> tickerToTotalDebt;
	private Map<String, List<String>> tickerToTotalDebtGrowth;
	private Map<String, List<String>> tickerToTotalNetDebt;
	private Map<String, List<String>> tickerToTotalNetDebtGrowth;
	
	private Map<String, String> tickerToTotalCashMRQ;
	private Map<String, String> tickerToTotalDebtMRQ;
	private Map<String, String> tickerToCurrentRatioMRQ;
	private Map<String, String> tickerToCurrencyValue;
	
	

	public BSAnalyzer(Map<String, List<BSInfoBO>> tickerToBSData, Map<String, String> tickerToTxtFilePaths)
			throws IOException {
		this.tickerToBSData = tickerToBSData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
		tickerToNetEquity = new HashMap<String, List<String>>();
		tickerToNetEquityGrowth = new HashMap<String, List<String>>();
		tickerToShareholderEquity = new HashMap<String, List<String>>();
		tickerToShareholderEquityGrowth = new HashMap<String, List<String>>();
		tickerToBSDates = new HashMap<String, List<String>>();
		tickerToCashAndEquivalents = new HashMap<String, List<String>>();
		tickerToCashAndEquivalentsGrowth = new HashMap<String, List<String>>();
		tickerToShortTermDebt = new HashMap<String, List<String>>();
		tickerToShortTermDebtGrowth = new HashMap<String, List<String>>();
		tickerToLongTermDebt = new HashMap<String, List<String>>();
		tickerToLongTermDebtGrowth = new HashMap<String, List<String>>();
		tickerToShortTermInvestments = new HashMap<String, List<String>>();
		tickerToShortTermInvestmentsGrowth = new HashMap<String, List<String>>();
		tickerToLongTermInvestments = new HashMap<String, List<String>>();
		tickerToLongTermInvestmentsGrowth = new HashMap<String, List<String>>();
		tickerToTotalAssets = new HashMap<String, List<String>>();
		tickerToTotalAssetsGrowth = new HashMap<String, List<String>>();
		tickerToTotalLiabilities = new HashMap<String, List<String>>();
		tickerToTotalLiabilitiesGrowth = new HashMap<String, List<String>>();
		tickerToTangibleAssets = new HashMap<String, List<String>>();
		tickerToTangibleAssetsGrowth = new HashMap<String, List<String>>();
		tickerToTotalDebt = new HashMap<String, List<String>>();
		tickerToTotalDebtGrowth = new HashMap<String, List<String>>();
		tickerToTotalNetDebt = new HashMap<String, List<String>>();
		tickerToTotalNetDebtGrowth = new HashMap<String, List<String>>();
		
		
		tickerToTotalCashMRQ = new HashMap<String, String>();
		tickerToTotalDebtMRQ = new HashMap<String, String>();
		tickerToCurrentRatioMRQ = new HashMap<String, String>();
		tickerToCurrencyValue  = new HashMap<String, String>();
		
		
		this.extractBSData();
		this.writeBSDataToTxtFiles();
	}
	
	// This method writes BS Data to text files
	public void writeBSDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String txtFilePath = entry.getValue();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);
			info.newLine();
			String currencyType = tickerToCurrencyValue.get(ticker);
			this.writeBSSeperator(ticker, currencyType, info);
			List<String> bsDateList = tickerToBSDates.get(ticker);
			List<String> cashList = tickerToCashAndEquivalents.get(ticker);
			List<String> cashGrowthList = tickerToCashAndEquivalentsGrowth.get(ticker);
			List<String> shortDebtList = tickerToShortTermDebt.get(ticker);
			List<String> shortDebtGrowthList = tickerToShortTermDebtGrowth.get(ticker);
			List<String> longDebtList = tickerToLongTermDebt.get(ticker);
			List<String> longDebtGrowthList = tickerToLongTermDebtGrowth.get(ticker);
			List<String> shortInvestmentList = tickerToShortTermInvestments.get(ticker);
			List<String> shortInvestmentGrowthList = tickerToShortTermInvestmentsGrowth.get(ticker);
			List<String> longInvestmentList = tickerToLongTermInvestments.get(ticker);
			List<String> longInvestmentGrowthList = tickerToLongTermInvestmentsGrowth.get(ticker);
			List<String> netEquityList = tickerToNetEquity.get(ticker);
			List<String> netEquityGrowthList = tickerToNetEquityGrowth.get(ticker);
			List<String> shareHolderEquityList = tickerToShareholderEquity.get(ticker);
			List<String> shareHolderEquityGrowthList = tickerToShareholderEquityGrowth.get(ticker);
			List<String> totalAssetsList = tickerToTotalAssets.get(ticker);
			List<String> totalAssetsGrowthList = tickerToTotalAssetsGrowth.get(ticker);
			List<String> totalLiabilitiesList = tickerToTotalLiabilities.get(ticker);
			List<String> totalLiabilitiesGrowthList = tickerToTotalLiabilitiesGrowth.get(ticker);
			List<String> totalTangibleAssetsList = tickerToTangibleAssets.get(ticker);
			List<String> totalTangibleAssetsGrowthList = tickerToTangibleAssetsGrowth.get(ticker);
			List<String> totalDebtList = tickerToTotalDebt.get(ticker);
			List<String> totalDebtGrowthList = tickerToTotalDebtGrowth.get(ticker);
			List<String> totalNetDebtList = tickerToTotalNetDebt.get(ticker);
			List<String> totalNetDebtGrowthList = tickerToTotalNetDebtGrowth.get(ticker);
			

		

			String dateLines = "                                         " + bsDateList.get(0) + "             "
					+ bsDateList.get(1) + "             " + bsDateList.get(2) + "             " + bsDateList.get(3);
			info.write(dateLines);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			
			String firstTotalAssetsVal = CommonFinancialLibrary.addCommasToNumericString(totalAssetsList.get(0));
			String secondTotalAssetsVal = CommonFinancialLibrary.addCommasToNumericString(totalAssetsList.get(1));
			String thirdTotalAssetsVal = CommonFinancialLibrary.addCommasToNumericString(totalAssetsList.get(2));
			String fourthTotalAssetsVal = CommonFinancialLibrary.addCommasToNumericString(totalAssetsList.get(3));


			String assetsLine = "Total Assets                             " + firstTotalAssetsVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstTotalAssetsVal) + secondTotalAssetsVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondTotalAssetsVal) + thirdTotalAssetsVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdTotalAssetsVal) + fourthTotalAssetsVal;
			info.write(assetsLine);
			info.newLine();

			String assetsGrowthLine = "Total Assets Growth                                " + totalAssetsGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(totalAssetsGrowthList.get(0)) + totalAssetsGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(totalAssetsGrowthList.get(1)) + totalAssetsGrowthList.get(2);
			info.write(assetsGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);


			
			String firstTotalLiabilititesVal = CommonFinancialLibrary
					.addCommasToNumericString(totalLiabilitiesList.get(0));
			String secondTotalLiabilititesVal = CommonFinancialLibrary
					.addCommasToNumericString(totalLiabilitiesList.get(1));
			String thirdTotalLiabilititesVal = CommonFinancialLibrary
					.addCommasToNumericString(totalLiabilitiesList.get(2));
			String fourthTotalLiabilititesVal = CommonFinancialLibrary
					.addCommasToNumericString(totalLiabilitiesList.get(3));

			String totalLiabilitiesLine = "Total Liabilities                        " + firstTotalLiabilititesVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstTotalLiabilititesVal) + secondTotalLiabilititesVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondTotalLiabilititesVal) + thirdTotalLiabilititesVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdTotalLiabilititesVal) + fourthTotalLiabilititesVal;
			
			info.write(totalLiabilitiesLine);
			info.newLine();

			String totalLiabilitiesGrowthLine = "Total Liabilities Growth                           "
					+ totalLiabilitiesGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(totalLiabilitiesGrowthList.get(0))
					+ totalLiabilitiesGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(totalLiabilitiesGrowthList.get(1))
					+ totalLiabilitiesGrowthList.get(2);
			info.write(totalLiabilitiesGrowthLine);
			info.newLine();
			CommonFinancialLibrary.writeSeperator(info);

			//BEGIN HERE WHERE YOU CHANGE THE LONG TERM INVESTMENT FOR TANGIBLE ASSETS AND MAKE SURE IS AND
			//CF ONLY HANDLEE 4 ELEMENTS!
			

			String firstShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(0));
			String secondShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(1));
			String thirdShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(2));
			String fourthShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(3));
//			String fifthShareHolderEquityVal = CommonFinancialLibrary
//					.addCommasToNumericString(shareHolderEquityList.get(4));

			String shareHolderEquityLine = "Total Shareholders Equity                " + firstShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstShareHolderEquityVal)
					+ secondShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondShareHolderEquityVal)
					+ thirdShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdShareHolderEquityVal)
					+ fourthShareHolderEquityVal;
			info.write(shareHolderEquityLine);
			info.newLine();

			String shareHolderEquityGrowthLine = "Total Shareholders Equity Growth                   "
					+ shareHolderEquityGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shareHolderEquityGrowthList.get(0))
					+ shareHolderEquityGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shareHolderEquityGrowthList.get(1))
					+ shareHolderEquityGrowthList.get(2);
			info.write(shareHolderEquityGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);
			
			
			String totalCashLine = "Total Cash (Most Recent Quarter)         $" + tickerToTotalCashMRQ.get(ticker);
			info.write(totalCashLine);
			info.newLine();
			
			String totalDebtLine = "Total Debt (Most Recent Quarter)         $" + tickerToTotalDebtMRQ.get(ticker);
			info.write(totalDebtLine);
			info.newLine();
			
			String currentRatioLine = "Current Ratio (Most Recent Quarter)      " + tickerToCurrentRatioMRQ.get(ticker);
			info.write(currentRatioLine);
			info.newLine();
			
			
			String fourYearAvgTotalAssetsString = CommonFinancialLibrary.calculateAverageGrowthRateForFourPeriods(totalAssetsList.get(0), totalAssetsList.get(3));
			String fourYearAvgTotalLiabilitiesString = CommonFinancialLibrary.calculateAverageGrowthRateForFourPeriods(totalLiabilitiesList.get(0), totalLiabilitiesList.get(3));

			
			String fourYearAvgTotalAssetsLine = "Total Assets 4 Period Growth: " + fourYearAvgTotalAssetsString;
			info.write(fourYearAvgTotalAssetsLine);
			info.newLine();
			String fourYearAvgTotalLiabilitiesLine = "Total Liabilities 4 Period Growth: " + fourYearAvgTotalLiabilitiesString;
			info.write(fourYearAvgTotalLiabilitiesLine);
			info.newLine();

			String fourYearAvgShareHolderEquityString = CommonFinancialLibrary.calculateAverageGrowthRateForFourPeriods(
					shareHolderEquityList.get(0), shareHolderEquityList.get(3));
//			String fiveYearAvgNetEquityString = CommonFinancialLibrary
//					.calculateAverageGrowthRateForFivePeriods(netEquityList.get(0), netEquityList.get(4));



			String fourYearAvgShareholderEquityLine = "Total Shareholders Equity 4 Period Growth:  "
					+ fourYearAvgShareHolderEquityString;
			info.write(fourYearAvgShareholderEquityLine);
			info.newLine();


			CommonFinancialLibrary.writeSeperator(info);
			
			
			this.writeBSSeperator(ticker, currencyType, info);

			info.newLine();
			info.newLine();
			info.newLine();

			info.close();
		}
	}

	private void writeBSSeperator(String ticker, String currencyType, BufferedWriter info) throws IOException {
		info.write("===================================================== " + ticker + " (" + currencyType + ")" 
				+ " - Balance Sheet =====================================================");
		info.newLine();
	}

	public void extractBSData() {
		Iterator<Entry<String, List<BSInfoBO>>> iterator = tickerToBSData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<BSInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<BSInfoBO> bsInfoList = entry.getValue();
			BSInfoBO firstBSInfo = bsInfoList.get(0);
			BSInfoBO secondBSInfo = bsInfoList.get(1);
			BSInfoBO thirdBSInfo = bsInfoList.get(2);
			BSInfoBO fourthBSInfo = bsInfoList.get(3);
			//BSInfoBO fifthBSInfo = bsInfoList.get(4);

			String firstBSDate = firstBSInfo.getBsDate();
			String secondBSDate = secondBSInfo.getBsDate();
			String thirdBSDate = thirdBSInfo.getBsDate();
			String fourthBSDate = fourthBSInfo.getBsDate();
			//String fifthBSDate = fifthBSInfo.getBsDate();

			String firstShareHolderEquity = firstBSInfo.getShareHoldersEquity();
			String secondShareHolderEquity = secondBSInfo.getShareHoldersEquity();
			String thirdShareHolderEquity = thirdBSInfo.getShareHoldersEquity();
			String fourthShareHolderEquity = fourthBSInfo.getShareHoldersEquity();
			//String fifthShareHolderEquity = fifthBSInfo.getShareHoldersEquity();

			String firstShareHolderEquityGrowth = firstBSInfo.getShareHoldersEquityGrowth();
			String secondShareHolderEquityGrowth = secondBSInfo.getShareHoldersEquityGrowth();
			String thirdShareHolderEquityGrowth = thirdBSInfo.getShareHoldersEquityGrowth();
			//String fourthShareHolderEquityGrowth = fourthBSInfo.getShareHoldersEquityGrowth();
			
			String firstTotalAssets = firstBSInfo.getTotalAssets();
			String secondTotalAssets = secondBSInfo.getTotalAssets();
			String thirdTotalAssets = thirdBSInfo.getTotalAssets();
			String fourthTotalAssets = fourthBSInfo.getTotalAssets();
			
			String firstTotalAssetsGrowth = firstBSInfo.getTotalAssetsGrowth();
			String secondTotalAssetsGrowth = secondBSInfo.getTotalAssetsGrowth();
			String thirdTotalAssetsGrowth = thirdBSInfo.getTotalAssetsGrowth();
			
			String firstTotalLiabilities = firstBSInfo.getTotalLiabilities();
			String secondTotalLiabilities = secondBSInfo.getTotalLiabilities();
			String thirdTotalLiabilities = thirdBSInfo.getTotalLiabilities();
			String fourthTotalLiabilities = fourthBSInfo.getTotalLiabilities();
			
			String firstTotalLiabilitiesGrowth = firstBSInfo.getTotalLiabilitiesGrowth();
			String secondTotalLiabilitiesGrowth = secondBSInfo.getTotalLiabilitiesGrowth();
			String thirdTotalLiabilitiesGrowth = thirdBSInfo.getTotalLiabilitiesGrowth();
			
			String totalCashMRQ = firstBSInfo.getTotalStrCash();
			String totalDebtMRQ = firstBSInfo.getTotalStrDebt();
			String currentRatioMRQ = firstBSInfo.getStrCurrentRatio();
			String currencyString = firstBSInfo.getCurrencyType();
			
			

			List<String> cashAndEquivalentList = new ArrayList<String>();
			List<String> cashAndEquivalentGrowthList = new ArrayList<String>();
			List<String> shortDebtList = new ArrayList<String>();
			List<String> shortDebtGrowthList = new ArrayList<String>();
			List<String> shortInvestmentList = new ArrayList<String>();
			List<String> shortInvestmentGrowthList = new ArrayList<String>();
			List<String> longDebtList = new ArrayList<String>();
			List<String> longDebtGrowthList = new ArrayList<String>();
			List<String> longInvestmentList = new ArrayList<String>();
			List<String> longInvestmentGrowthList = new ArrayList<String>();
			List<String> netEquityList = new ArrayList<String>();
			List<String> netEquityGrowthList = new ArrayList<String>();
			List<String> shareHolderEquityList = new ArrayList<String>();
			List<String> shareHolderEquityGrowthList = new ArrayList<String>();
			List<String> bsDates = new ArrayList<String>();
			List<String> totalAssets = new ArrayList<String>();
			List<String> totalAssetsGrowth = new ArrayList<String>();
			List<String> totalLiabilities = new ArrayList<String>();
			List<String> totalLiabilitiesGrowth = new ArrayList<String>();
			List<String> totalTangibleAssets = new ArrayList<String>();
			List<String> totalTangibleAssetsGrowth = new ArrayList<String>();
			List<String> totalDebt = new ArrayList<String>();
			List<String> totalDebtGrowth = new ArrayList<String>();
			List<String> totalNetDebt = new ArrayList<String>();
			List<String> totalNetDebtGrowth = new ArrayList<String>();

			
			totalAssets.add(firstTotalAssets);
			totalAssets.add(secondTotalAssets);
			totalAssets.add(thirdTotalAssets);
			totalAssets.add(fourthTotalAssets);
			
			totalAssetsGrowth.add(firstTotalAssetsGrowth);
			totalAssetsGrowth.add(secondTotalAssetsGrowth);
			totalAssetsGrowth.add(thirdTotalAssetsGrowth);
			
			totalLiabilities.add(firstTotalLiabilities);
			totalLiabilities.add(secondTotalLiabilities);
			totalLiabilities.add(thirdTotalLiabilities);
			totalLiabilities.add(fourthTotalLiabilities);
			
			totalLiabilitiesGrowth.add(firstTotalLiabilitiesGrowth);
			totalLiabilitiesGrowth.add(secondTotalLiabilitiesGrowth);
			totalLiabilitiesGrowth.add(thirdTotalLiabilitiesGrowth);
			

			bsDates.add(firstBSDate);
			bsDates.add(secondBSDate);
			bsDates.add(thirdBSDate);
			bsDates.add(fourthBSDate);
			

			shareHolderEquityList.add(firstShareHolderEquity);
			shareHolderEquityList.add(secondShareHolderEquity);
			shareHolderEquityList.add(thirdShareHolderEquity);
			shareHolderEquityList.add(fourthShareHolderEquity);
			//shareHolderEquityList.add(fifthShareHolderEquity);

			shareHolderEquityGrowthList.add(firstShareHolderEquityGrowth);
			shareHolderEquityGrowthList.add(secondShareHolderEquityGrowth);
			shareHolderEquityGrowthList.add(thirdShareHolderEquityGrowth);
			//shareHolderEquityGrowthList.add(fourthShareHolderEquityGrowth);
			
			
			

			Collections.reverse(cashAndEquivalentList);
			Collections.reverse(cashAndEquivalentGrowthList);
			Collections.reverse(shortDebtList);
			Collections.reverse(shortDebtGrowthList);
			Collections.reverse(shortInvestmentList);
			Collections.reverse(shortInvestmentGrowthList);
			Collections.reverse(longDebtList);
			Collections.reverse(longDebtGrowthList);
			Collections.reverse(longInvestmentList);
			Collections.reverse(longInvestmentGrowthList);
			Collections.reverse(netEquityList);
			Collections.reverse(netEquityGrowthList);
			Collections.reverse(shareHolderEquityList);
			Collections.reverse(shareHolderEquityGrowthList);
			Collections.reverse(bsDates);
			Collections.reverse(totalAssets);
			Collections.reverse(totalAssetsGrowth);
			Collections.reverse(totalLiabilities);
			Collections.reverse(totalLiabilitiesGrowth);
			Collections.reverse(totalTangibleAssets);
			Collections.reverse(totalTangibleAssetsGrowth);
			Collections.reverse(totalDebt);
			Collections.reverse(totalDebtGrowth);
			Collections.reverse(totalNetDebt);
			Collections.reverse(totalNetDebtGrowth);

			tickerToNetEquity.put(ticker, netEquityList);
			tickerToNetEquityGrowth.put(ticker, netEquityGrowthList);
			tickerToShareholderEquity.put(ticker, shareHolderEquityList);
			tickerToShareholderEquityGrowth.put(ticker, shareHolderEquityGrowthList);
			tickerToBSDates.put(ticker, bsDates);
			tickerToCashAndEquivalents.put(ticker, cashAndEquivalentList);
			tickerToCashAndEquivalentsGrowth.put(ticker, cashAndEquivalentGrowthList);
			tickerToShortTermDebt.put(ticker, shortDebtList);
			tickerToShortTermDebtGrowth.put(ticker, shortDebtGrowthList);
			tickerToLongTermDebt.put(ticker, longDebtList);
			tickerToLongTermDebtGrowth.put(ticker, longDebtGrowthList);
			tickerToShortTermInvestments.put(ticker, shortInvestmentList);
			tickerToShortTermInvestmentsGrowth.put(ticker, shortInvestmentGrowthList);
			tickerToLongTermInvestments.put(ticker, longInvestmentList);
			tickerToLongTermInvestmentsGrowth.put(ticker, longInvestmentGrowthList);
			tickerToTotalAssets.put(ticker, totalAssets);
			tickerToTotalAssetsGrowth.put(ticker, totalAssetsGrowth);
			tickerToTotalLiabilities.put(ticker, totalLiabilities);
			tickerToTotalLiabilitiesGrowth.put(ticker, totalLiabilitiesGrowth);
			tickerToTangibleAssets.put(ticker, totalTangibleAssets);
			tickerToTangibleAssetsGrowth.put(ticker, totalTangibleAssetsGrowth);
			tickerToTotalDebt.put(ticker, totalDebt);
			tickerToTotalDebtGrowth.put(ticker, totalDebtGrowth);
			tickerToTotalNetDebt.put(ticker, totalNetDebt);
			tickerToTotalNetDebtGrowth.put(ticker, totalNetDebtGrowth);
			tickerToTotalCashMRQ.put(ticker, totalCashMRQ);
			tickerToTotalDebtMRQ.put(ticker, totalDebtMRQ);
			tickerToCurrentRatioMRQ.put(ticker, currentRatioMRQ);
			tickerToCurrencyValue.put(ticker, currencyString);
			
		}
	}

	public Map<String, String> getTickerToTxtFilePaths() {
		return tickerToTxtFilePaths;
	}

	public Map<String, List<BSInfoBO>> getTickerToBSData() {
		return tickerToBSData;
	}

	public Map<String, List<String>> getTickerToNetEquity() {
		return tickerToNetEquity;
	}

	public Map<String, List<String>> getTickerToNetEquityGrowth() {
		return tickerToNetEquityGrowth;
	}

	public Map<String, List<String>> getTickerToShareholderEquity() {
		return tickerToShareholderEquity;
	}

	public Map<String, List<String>> getTickerToShareholderEquityGrowth() {
		return tickerToShareholderEquityGrowth;
	}

	public Map<String, List<String>> getTickerToCashAndEquivalents() {
		return tickerToCashAndEquivalents;
	}

	public Map<String, List<String>> getTickerToCashAndEquivalentsGrowth() {
		return tickerToCashAndEquivalentsGrowth;
	}

	public Map<String, List<String>> getTickerToShortTermDebt() {
		return tickerToShortTermDebt;
	}

	public Map<String, List<String>> getTickerToShortTermDebtGrowth() {
		return tickerToShortTermDebtGrowth;
	}

	public Map<String, List<String>> getTickerToLongTermDebt() {
		return tickerToLongTermDebt;
	}

	public Map<String, List<String>> getTickerToLongTermDebtGrowth() {
		return tickerToLongTermDebtGrowth;
	}

	public Map<String, List<String>> getTickerToShortTermInvestments() {
		return tickerToShortTermInvestments;
	}

	public Map<String, List<String>> getTickerToShortTermInvestmentsGrowth() {
		return tickerToShortTermInvestmentsGrowth;
	}

	public Map<String, List<String>> getTickerToLongTermInvestments() {
		return tickerToLongTermInvestments;
	}

	public Map<String, List<String>> getTickerToLongTermInvestmentsGrowth() {
		return tickerToLongTermInvestmentsGrowth;
	}

	public Map<String, List<String>> getTickerToTotalAssets() {
		return tickerToTotalAssets;
	}

	public Map<String, List<String>> getTickerToTotalAssetsGrowth() {
		return tickerToTotalAssetsGrowth;
	}

	public Map<String, List<String>> getTickerToTotalLiabilities() {
		return tickerToTotalLiabilities;
	}

	public Map<String, List<String>> getTickerToTotalLiabilitiesGrowth() {
		return tickerToTotalLiabilitiesGrowth;
	}

	public Map<String, List<String>> getTickerToTangibleAssets() {
		return tickerToTangibleAssets;
	}

	public Map<String, List<String>> getTickerToTangibleAssetsGrowth() {
		return tickerToTangibleAssetsGrowth;
	}

	public Map<String, List<String>> getTickerToTotalDebt() {
		return tickerToTotalDebt;
	}

	public Map<String, List<String>> getTickerToTotalDebtGrowth() {
		return tickerToTotalDebtGrowth;
	}

	public Map<String, List<String>> getTickerToTotalNetDebt() {
		return tickerToTotalNetDebt;
	}

	public Map<String, List<String>> getTickerToTotalNetDebtGrowth() {
		return tickerToTotalNetDebtGrowth;
	}

	public Map<String, List<String>> getTickerToBSDates() {
		return tickerToBSDates;
	}


	public Map<String, String> getTickerToTotalCashMRQ() {
		return tickerToTotalCashMRQ;
	}

	public Map<String, String> getTickerToTotalDebtMRQ() {
		return tickerToTotalDebtMRQ;
	}

	public Map<String, String> getTickerToCurrentRatioMRQ() {
		return tickerToCurrentRatioMRQ;
	}
	
	
	

}
