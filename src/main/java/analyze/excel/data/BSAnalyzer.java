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
		this.extractBSData();
		this.writeBSDataToTxtFiles();
	}

	public void writeBSDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String txtFilePath = entry.getValue();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);
			this.writeBSSeperator(ticker, info);
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

			String dateLines = "                                         " + bsDateList.get(0) + "             "
					+ bsDateList.get(1) + "             " + bsDateList.get(2) + "             " + bsDateList.get(3)
					+ "             " + bsDateList.get(4);
			info.write(dateLines);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstCashVal = CommonFinancialLibrary.addCommasToNumericString(cashList.get(0));
			String secondCashVal = CommonFinancialLibrary.addCommasToNumericString(cashList.get(1));
			String thirdCashVal = CommonFinancialLibrary.addCommasToNumericString(cashList.get(2));
			String fourthCashVal = CommonFinancialLibrary.addCommasToNumericString(cashList.get(3));
			String fifthCashVal = CommonFinancialLibrary.addCommasToNumericString(cashList.get(4));

			String cashLine = "Cash and Cash Equivalent                 " + firstCashVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstCashVal) + secondCashVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondCashVal) + thirdCashVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdCashVal) + fourthCashVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthCashVal) + fifthCashVal;
			info.write(cashLine);
			info.newLine();

			String cashGrowthLine = "Cash and Cash Equivalent Growth                    " + cashGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(cashGrowthList.get(0)) + cashGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(cashGrowthList.get(1)) + cashGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(cashGrowthList.get(2)) + cashGrowthList.get(3);
			info.write(cashGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstShortInvestmentVal = CommonFinancialLibrary
					.addCommasToNumericString(shortInvestmentList.get(0));
			String secondShortInvestmentVal = CommonFinancialLibrary
					.addCommasToNumericString(shortInvestmentList.get(1));
			String thirdShortInvestmentVal = CommonFinancialLibrary
					.addCommasToNumericString(shortInvestmentList.get(2));
			String fourthShortInvestmentVal = CommonFinancialLibrary
					.addCommasToNumericString(shortInvestmentList.get(3));
			String fifthShortInvestmentVal = CommonFinancialLibrary
					.addCommasToNumericString(shortInvestmentList.get(4));

			String shortInvestmentLine = "Short Term Investment                    " + firstShortInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstShortInvestmentVal) + secondShortInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondShortInvestmentVal) + thirdShortInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdShortInvestmentVal) + fourthShortInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthShortInvestmentVal) + fifthShortInvestmentVal;
			info.write(shortInvestmentLine);
			info.newLine();

			String shortInvestmentGrowthLine = "Short Term Investment Growth                       "
					+ shortInvestmentGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortInvestmentGrowthList.get(0))
					+ shortInvestmentGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortInvestmentGrowthList.get(1))
					+ shortInvestmentGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortInvestmentGrowthList.get(2))
					+ shortInvestmentGrowthList.get(3);
			info.write(shortInvestmentGrowthLine);
			info.newLine();
			CommonFinancialLibrary.writeSeperator(info);

			String firstLongInvestmentVal = CommonFinancialLibrary.addCommasToNumericString(longInvestmentList.get(0));
			String secondLongInvestmentVal = CommonFinancialLibrary.addCommasToNumericString(longInvestmentList.get(1));
			String thirdLongInvestmentVal = CommonFinancialLibrary.addCommasToNumericString(longInvestmentList.get(2));
			String fourthLongInvestmentVal = CommonFinancialLibrary.addCommasToNumericString(longInvestmentList.get(3));
			String fifthLongInvestmentVal = CommonFinancialLibrary.addCommasToNumericString(longInvestmentList.get(4));

			String longInvestmentLine = "Long Term Investment                     " + firstLongInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstLongInvestmentVal) + secondLongInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondLongInvestmentVal) + thirdLongInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdLongInvestmentVal) + fourthLongInvestmentVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthLongInvestmentVal) + fifthLongInvestmentVal;
			info.write(longInvestmentLine);
			info.newLine();

			String longInvestmentGrowthLine = "Long Term Investment Growth                        "
					+ longInvestmentGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longInvestmentGrowthList.get(0))
					+ longInvestmentGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longInvestmentGrowthList.get(1))
					+ longInvestmentGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longInvestmentGrowthList.get(2))
					+ longInvestmentGrowthList.get(3);
			info.write(longInvestmentGrowthLine);
			info.newLine();
			CommonFinancialLibrary.writeSeperator(info);

			String firstShortDebtVal = CommonFinancialLibrary.addCommasToNumericString(shortDebtList.get(0));
			String secondShortDebtVal = CommonFinancialLibrary.addCommasToNumericString(shortDebtList.get(1));
			String thirdShortDebtVal = CommonFinancialLibrary.addCommasToNumericString(shortDebtList.get(2));
			String fourthShortDebtVal = CommonFinancialLibrary.addCommasToNumericString(shortDebtList.get(3));
			String fifthShortDebtVal = CommonFinancialLibrary.addCommasToNumericString(shortDebtList.get(4));

			String shortDebtLine = "Short Term Debt                          " + firstShortDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstShortDebtVal) + secondShortDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondShortDebtVal) + thirdShortDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdShortDebtVal) + fourthShortDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthShortDebtVal) + fifthShortDebtVal;
			info.write(shortDebtLine);
			info.newLine();

			String shortDebtGrowthLine = "Short Term Debt Growth                             "
					+ shortDebtGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortDebtGrowthList.get(0))
					+ shortDebtGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortDebtGrowthList.get(1))
					+ shortDebtGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shortDebtGrowthList.get(2))
					+ shortDebtGrowthList.get(3);
			info.write(shortDebtGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstLongDebtVal = CommonFinancialLibrary.addCommasToNumericString(longDebtList.get(0));
			String secondLongDebtVal = CommonFinancialLibrary.addCommasToNumericString(longDebtList.get(1));
			String thirdLongDebtVal = CommonFinancialLibrary.addCommasToNumericString(longDebtList.get(2));
			String fourthLongDebtVal = CommonFinancialLibrary.addCommasToNumericString(longDebtList.get(3));
			String fifthLongDebtVal = CommonFinancialLibrary.addCommasToNumericString(longDebtList.get(4));

			String longDebtLine = "Long Term Debt                           " + firstLongDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstLongDebtVal) + secondLongDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondLongDebtVal) + thirdLongDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdLongDebtVal) + fourthLongDebtVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthLongDebtVal) + fifthLongDebtVal;
			info.write(longDebtLine);
			info.newLine();

			String longDebtGrowthLine = "Long Term Debt Growth                              "
					+ longDebtGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longDebtGrowthList.get(0))
					+ longDebtGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longDebtGrowthList.get(1))
					+ longDebtGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(longDebtGrowthList.get(2))
					+ longDebtGrowthList.get(3);
			info.write(longDebtGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(0));
			String secondShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(1));
			String thirdShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(2));
			String fourthShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(3));
			String fifthShareHolderEquityVal = CommonFinancialLibrary
					.addCommasToNumericString(shareHolderEquityList.get(4));

			String shareHolderEquityLine = "Total Shareholders Equity                " + firstShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstShareHolderEquityVal)
					+ secondShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondShareHolderEquityVal)
					+ thirdShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdShareHolderEquityVal)
					+ fourthShareHolderEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthShareHolderEquityVal)
					+ fifthShareHolderEquityVal;
			info.write(shareHolderEquityLine);
			info.newLine();

			String shareHolderEquityGrowthLine = "Total Shareholders Equity Growth                   "
					+ shareHolderEquityGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shareHolderEquityGrowthList.get(0))
					+ shareHolderEquityGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shareHolderEquityGrowthList.get(1))
					+ shareHolderEquityGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(shareHolderEquityGrowthList.get(2))
					+ shareHolderEquityGrowthList.get(3);
			info.write(shareHolderEquityGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstNetEquityVal = CommonFinancialLibrary.addCommasToNumericString(netEquityList.get(0));
			String secondNetEquityVal = CommonFinancialLibrary.addCommasToNumericString(netEquityList.get(1));
			String thirdNetEquityVal = CommonFinancialLibrary.addCommasToNumericString(netEquityList.get(2));
			String fourthNetEquityVal = CommonFinancialLibrary.addCommasToNumericString(netEquityList.get(3));
			String fifthNetEquityVal = CommonFinancialLibrary.addCommasToNumericString(netEquityList.get(4));

			String netEquityLine = "Net Equity                               " + firstNetEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstNetEquityVal) + secondNetEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondNetEquityVal) + thirdNetEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdNetEquityVal) + fourthNetEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fourthNetEquityVal) + fifthNetEquityVal
					+ CommonFinancialLibrary.getApprorpriateSpaces(fifthNetEquityVal);
			info.write(netEquityLine);
			info.newLine();

			String netEquityGrowthLine = "Net Equity Growth                                  "
					+ netEquityGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(netEquityGrowthList.get(0))
					+ netEquityGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(netEquityGrowthList.get(1))
					+ netEquityGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(netEquityGrowthList.get(2))
					+ netEquityGrowthList.get(3);
			info.write(netEquityGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String fiveYearAvgCashEquivalentString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(cashList.get(0), cashList.get(4));
			String fiveYearAvgShortInvestmentString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(shortInvestmentList.get(0), shortInvestmentList.get(4));
			String fiveYearAvgLongInvestmentString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(longInvestmentList.get(0), longInvestmentList.get(4));
			String fiveYearAvgShortDebtString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(shortDebtList.get(0), shortDebtList.get(4));
			String fiveYearAvgLongDebtString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(longDebtList.get(0), longDebtList.get(4));
			String fiveYearAvgShareHolderEquityString = CommonFinancialLibrary.calculateAverageGrowthRateForFivePeriods(
					shareHolderEquityList.get(0), shareHolderEquityList.get(4));
			String fiveYearAvgNetEquityString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(netEquityList.get(0), netEquityList.get(4));

			String fiveYearAvgCashLine = "Cash And Cash Equivalent 5 Period Growth: " + fiveYearAvgCashEquivalentString;
			info.write(fiveYearAvgCashLine);
			info.newLine();
			String fiveYearShortInvestmentLine = "5 Period Short Term Investments: " + fiveYearAvgShortInvestmentString;
			info.write(fiveYearShortInvestmentLine);
			info.newLine();
			String fiveYearLongInvestmentLine = "5 Period Long Term Investments: " + fiveYearAvgLongInvestmentString;
			info.write(fiveYearLongInvestmentLine);
			info.newLine();
			String fiveYearShortDebtLine = "5 Period Short Term Debt: " + fiveYearAvgShortDebtString;
			info.write(fiveYearShortDebtLine);
			info.newLine();
			String fiveYearLongDebtLine = "5 Period Long Term Debt: " + fiveYearAvgLongDebtString;
			info.write(fiveYearLongDebtLine);
			info.newLine();

			String fiveYearAvgShareholderEquityLine = "Total Shareholders Equity 5 Period Growth:  "
					+ fiveYearAvgShareHolderEquityString;
			info.write(fiveYearAvgShareholderEquityLine);
			info.newLine();

			String fiveYearAvgNetEquityLine = "Net Equity 5 Period Growth:  " + fiveYearAvgNetEquityString;
			info.write(fiveYearAvgNetEquityLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			this.writeBSSeperator(ticker, info);

			info.newLine();

			info.close();
		}
	}

	private void writeBSSeperator(String ticker, BufferedWriter info) throws IOException {
		info.write("===================================================== " + ticker
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
			BSInfoBO fifthBSInfo = bsInfoList.get(4);

			String firstBSDate = firstBSInfo.getBsDate();
			String secondBSDate = secondBSInfo.getBsDate();
			String thirdBSDate = thirdBSInfo.getBsDate();
			String fourthBSDate = fourthBSInfo.getBsDate();
			String fifthBSDate = fifthBSInfo.getBsDate();

			String firstCash = firstBSInfo.getCashAndCashEquivalent();
			String secondCash = secondBSInfo.getCashAndCashEquivalent();
			String thirdCash = thirdBSInfo.getCashAndCashEquivalent();
			String fourthCash = fourthBSInfo.getCashAndCashEquivalent();
			String fifthCash = fifthBSInfo.getCashAndCashEquivalent();

			String firstCashGrowth = firstBSInfo.getCashAndCashEquivalentGrowth();
			String secondCashGrowth = secondBSInfo.getCashAndCashEquivalentGrowth();
			String thirdCashGrowth = thirdBSInfo.getCashAndCashEquivalentGrowth();
			String fourthCashGrowth = fourthBSInfo.getCashAndCashEquivalentGrowth();

			String firstShortDebt = firstBSInfo.getShortTermDebt();
			String secondShortDebt = secondBSInfo.getShortTermDebt();
			String thirdShortDebt = thirdBSInfo.getShortTermDebt();
			String fourthShortDebt = fourthBSInfo.getShortTermDebt();
			String fifthShortDebt = fifthBSInfo.getShortTermDebt();

			String firstShortDebtGrowth = firstBSInfo.getShortTermDebtGrowth();
			String secondShortDebtGrowth = secondBSInfo.getShortTermDebtGrowth();
			String thirdShortDebtGrowth = thirdBSInfo.getShortTermDebtGrowth();
			String fourthShortDebtGrowth = fourthBSInfo.getShortTermDebtGrowth();

			String firstLongDebt = firstBSInfo.getLongTermDebt();
			String secondLongDebt = secondBSInfo.getLongTermDebt();
			String thirdLongDebt = thirdBSInfo.getLongTermDebt();
			String fourthLongDebt = fourthBSInfo.getLongTermDebt();
			String fifthLongDebt = fifthBSInfo.getLongTermDebt();

			String firstLongDebtGrowth = firstBSInfo.getLongTermDebtGrowth();
			String secondLongDebtGrowth = secondBSInfo.getLongTermDebtGrowth();
			String thirdLongDebtGrowth = thirdBSInfo.getLongTermDebtGrowth();
			String fourthLongDebtGrowth = fourthBSInfo.getLongTermDebtGrowth();

			String firstShortInvestment = firstBSInfo.getShortTermInvestments();
			String secondShortInvestment = secondBSInfo.getShortTermInvestments();
			String thirdShortInvestment = thirdBSInfo.getShortTermInvestments();
			String fourthShortInvestment = fourthBSInfo.getShortTermInvestments();
			String fifthShortInvestment = fifthBSInfo.getShortTermInvestments();

			String firstShortInvestmentGrowth = firstBSInfo.getShortTermInvestmentsGrowth();
			String secondShortInvestmentGrowth = secondBSInfo.getShortTermInvestmentsGrowth();
			String thirdShortInvestmentGrowth = thirdBSInfo.getShortTermInvestmentsGrowth();
			String fourthShortInvestmentGrowth = fourthBSInfo.getShortTermInvestmentsGrowth();

			String firstLongInvestment = firstBSInfo.getLongTermInvestments();
			String secondLongInvestment = secondBSInfo.getLongTermInvestments();
			String thirdLongInvestment = thirdBSInfo.getLongTermInvestments();
			String fourthLongInvestment = fourthBSInfo.getLongTermInvestments();
			String fifthLongInvestment = fifthBSInfo.getLongTermInvestments();

			String firstLongInvestmentGrowth = firstBSInfo.getLongTermInvestmentsGrowth();
			String secondLongInvestmentGrowth = secondBSInfo.getLongTermInvestmentsGrowth();
			String thirdLongInvestmentGrowth = thirdBSInfo.getLongTermInvestmentsGrowth();
			String fourthLongInvestmentGrowth = fourthBSInfo.getLongTermInvestmentsGrowth();

			String firstNetEquity = firstBSInfo.getNetEquity();
			String secondNetEquity = secondBSInfo.getNetEquity();
			String thirdNetEquity = thirdBSInfo.getNetEquity();
			String fourthNetEquity = fourthBSInfo.getNetEquity();
			String fifthNetEquity = fifthBSInfo.getNetEquity();

			String firstNetEquityGrowth = firstBSInfo.getNetEquityGrowth();
			String secondNetEquityGrowth = secondBSInfo.getNetEquityGrowth();
			String thirdNetEquityGrowth = thirdBSInfo.getNetEquityGrowth();
			String fourthNetEquityGrowth = fourthBSInfo.getNetEquityGrowth();

			String firstShareHolderEquity = firstBSInfo.getShareHoldersEquity();
			String secondShareHolderEquity = secondBSInfo.getShareHoldersEquity();
			String thirdShareHolderEquity = thirdBSInfo.getShareHoldersEquity();
			String fourthShareHolderEquity = fourthBSInfo.getShareHoldersEquity();
			String fifthShareHolderEquity = fifthBSInfo.getShareHoldersEquity();

			String firstShareHolderEquityGrowth = firstBSInfo.getShareHoldersEquityGrowth();
			String secondShareHolderEquityGrowth = secondBSInfo.getShareHoldersEquityGrowth();
			String thirdShareHolderEquityGrowth = thirdBSInfo.getShareHoldersEquityGrowth();
			String fourthShareHolderEquityGrowth = fourthBSInfo.getShareHoldersEquityGrowth();

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

			cashAndEquivalentList.add(firstCash);
			cashAndEquivalentList.add(secondCash);
			cashAndEquivalentList.add(thirdCash);
			cashAndEquivalentList.add(fourthCash);
			cashAndEquivalentList.add(fifthCash);

			cashAndEquivalentGrowthList.add(firstCashGrowth);
			cashAndEquivalentGrowthList.add(secondCashGrowth);
			cashAndEquivalentGrowthList.add(thirdCashGrowth);
			cashAndEquivalentGrowthList.add(fourthCashGrowth);

			shortDebtList.add(firstShortDebt);
			shortDebtList.add(secondShortDebt);
			shortDebtList.add(thirdShortDebt);
			shortDebtList.add(fourthShortDebt);
			shortDebtList.add(fifthShortDebt);

			shortDebtGrowthList.add(firstShortDebtGrowth);
			shortDebtGrowthList.add(secondShortDebtGrowth);
			shortDebtGrowthList.add(thirdShortDebtGrowth);
			shortDebtGrowthList.add(fourthShortDebtGrowth);

			longDebtList.add(firstLongDebt);
			longDebtList.add(secondLongDebt);
			longDebtList.add(thirdLongDebt);
			longDebtList.add(fourthLongDebt);
			longDebtList.add(fifthLongDebt);

			longDebtGrowthList.add(firstLongDebtGrowth);
			longDebtGrowthList.add(secondLongDebtGrowth);
			longDebtGrowthList.add(thirdLongDebtGrowth);
			longDebtGrowthList.add(fourthLongDebtGrowth);

			shortInvestmentList.add(firstShortInvestment);
			shortInvestmentList.add(secondShortInvestment);
			shortInvestmentList.add(thirdShortInvestment);
			shortInvestmentList.add(fourthShortInvestment);
			shortInvestmentList.add(fifthShortInvestment);

			shortInvestmentGrowthList.add(firstShortInvestmentGrowth);
			shortInvestmentGrowthList.add(secondShortInvestmentGrowth);
			shortInvestmentGrowthList.add(thirdShortInvestmentGrowth);
			shortInvestmentGrowthList.add(fourthShortInvestmentGrowth);

			longInvestmentList.add(firstLongInvestment);
			longInvestmentList.add(secondLongInvestment);
			longInvestmentList.add(thirdLongInvestment);
			longInvestmentList.add(fourthLongInvestment);
			longInvestmentList.add(fifthLongInvestment);

			longInvestmentGrowthList.add(firstLongInvestmentGrowth);
			longInvestmentGrowthList.add(secondLongInvestmentGrowth);
			longInvestmentGrowthList.add(thirdLongInvestmentGrowth);
			longInvestmentGrowthList.add(fourthLongInvestmentGrowth);

			bsDates.add(firstBSDate);
			bsDates.add(secondBSDate);
			bsDates.add(thirdBSDate);
			bsDates.add(fourthBSDate);
			bsDates.add(fifthBSDate);

			netEquityList.add(firstNetEquity);
			netEquityList.add(secondNetEquity);
			netEquityList.add(thirdNetEquity);
			netEquityList.add(fourthNetEquity);
			netEquityList.add(fifthNetEquity);

			netEquityGrowthList.add(firstNetEquityGrowth);
			netEquityGrowthList.add(secondNetEquityGrowth);
			netEquityGrowthList.add(thirdNetEquityGrowth);
			netEquityGrowthList.add(fourthNetEquityGrowth);

			shareHolderEquityList.add(firstShareHolderEquity);
			shareHolderEquityList.add(secondShareHolderEquity);
			shareHolderEquityList.add(thirdShareHolderEquity);
			shareHolderEquityList.add(fourthShareHolderEquity);
			shareHolderEquityList.add(fifthShareHolderEquity);

			shareHolderEquityGrowthList.add(firstShareHolderEquityGrowth);
			shareHolderEquityGrowthList.add(secondShareHolderEquityGrowth);
			shareHolderEquityGrowthList.add(thirdShareHolderEquityGrowth);
			shareHolderEquityGrowthList.add(fourthShareHolderEquityGrowth);

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

}
