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

import create.excel.bo.CFInfoBO;
import excel.library.CommonFinancialLibrary;

public class CFAnalyzer {

	private Map<String, List<CFInfoBO>> tickerToCFData;
	private Map<String, String> tickerToTxtFilePaths;
	private Map<String, List<String>> tickerToFreeCashFlow;
	private Map<String, List<String>> tickerToFreeCashFlowGrowth;
	private Map<String, List<String>> tickerToNetCashForInvesting;
	private Map<String, List<String>> tickerToNetCashForFinancing;
	private Map<String, List<String>> tickerToCFDates;
	private Map<String, String> tickerToCurrencyValue;

	public CFAnalyzer(Map<String, List<CFInfoBO>> tickerToCFData, Map<String, String> tickerToTxtFilePaths)
			throws IOException {
		this.tickerToCFData = tickerToCFData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
		tickerToFreeCashFlow = new HashMap<String, List<String>>();
		tickerToFreeCashFlowGrowth = new HashMap<String, List<String>>();
		tickerToNetCashForInvesting = new HashMap<String, List<String>>();
		tickerToNetCashForFinancing = new HashMap<String, List<String>>();
		tickerToCFDates = new HashMap<String, List<String>>();
		tickerToCurrencyValue = new HashMap<String, String>();
		this.extractCFData();
		this.writeCFDataToTxtFiles();
	}

	public void writeCFDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String txtFilePath = entry.getValue();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);
			String currencyType = tickerToCurrencyValue.get(ticker);
			this.writeCFSeperator(ticker, currencyType, info);
			info.newLine();

			List<String> freeCashFlowList = tickerToFreeCashFlow.get(ticker);
			List<String> freeCashFlowGrowthList = tickerToFreeCashFlowGrowth.get(ticker);
			List<String> netCashForInvestingList = tickerToNetCashForInvesting.get(ticker);
			List<String> netCashForFinancingList = tickerToNetCashForFinancing.get(ticker);
			List<String> cfDateList = tickerToCFDates.get(ticker);

			String dateLines = "                                         " + cfDateList.get(0) + "             "
					+ cfDateList.get(1) + "             " + cfDateList.get(2) + "             " + cfDateList.get(3);
			info.write(dateLines);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstCashFlow = CommonFinancialLibrary.addCommasToNumericString(freeCashFlowList.get(0));
			String secondCashFlow = CommonFinancialLibrary.addCommasToNumericString(freeCashFlowList.get(1));
			String thirdCashFlow = CommonFinancialLibrary.addCommasToNumericString(freeCashFlowList.get(2));
			String fourthCashFlow = CommonFinancialLibrary.addCommasToNumericString(freeCashFlowList.get(3));
			//String fifthCashFlow = CommonFinancialLibrary.addCommasToNumericString(freeCashFlowList.get(4));

			String freeCashFlowLine = "Free Cash Flow                           " + firstCashFlow
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstCashFlow) + secondCashFlow
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondCashFlow) + thirdCashFlow
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdCashFlow) + fourthCashFlow;
			info.write(freeCashFlowLine);
			info.newLine();

			String freeCashFlowGrowthLine = "Free Cash Flow Growth                              "
					+ freeCashFlowGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(freeCashFlowGrowthList.get(0))
					+ freeCashFlowGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(freeCashFlowGrowthList.get(1))
					+ freeCashFlowGrowthList.get(2);
			info.write(freeCashFlowGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstNetCashForInvesting = CommonFinancialLibrary
					.addCommasToNumericString(netCashForInvestingList.get(0));
			String secondNetCashForInvesting = CommonFinancialLibrary
					.addCommasToNumericString(netCashForInvestingList.get(1));
			String thirdNetCashForInvesting = CommonFinancialLibrary
					.addCommasToNumericString(netCashForInvestingList.get(2));
			String fourthNetCashForInvesting = CommonFinancialLibrary
					.addCommasToNumericString(netCashForInvestingList.get(3));
//			String fifthNetCashForInvesting = CommonFinancialLibrary
//					.addCommasToNumericString(netCashForInvestingList.get(4));

			String netCashForInvestingLine = "Net Cash For Investing                   " + firstNetCashForInvesting
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstNetCashForInvesting) + secondNetCashForInvesting
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondNetCashForInvesting) + thirdNetCashForInvesting
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdNetCashForInvesting) + fourthNetCashForInvesting;
			info.write(netCashForInvestingLine);
			info.newLine();

			String firstNetCashForFinancing = CommonFinancialLibrary
					.addCommasToNumericString(netCashForFinancingList.get(0));
			String secondNetCashForFinancing = CommonFinancialLibrary
					.addCommasToNumericString(netCashForFinancingList.get(1));
			String thirdNetCashForFinancing = CommonFinancialLibrary
					.addCommasToNumericString(netCashForFinancingList.get(2));
			String fourthNetCashForFinancing = CommonFinancialLibrary
					.addCommasToNumericString(netCashForFinancingList.get(3));
//			String fifthNetCashForFinancing = CommonFinancialLibrary
//					.addCommasToNumericString(netCashForFinancingList.get(4));

			String netCashForFinancingLine = "Net Cash For Financing                   " + firstNetCashForFinancing
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstNetCashForFinancing) + secondNetCashForFinancing
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondNetCashForFinancing) + thirdNetCashForFinancing
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdNetCashForFinancing) + fourthNetCashForFinancing;
			info.write(netCashForFinancingLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String fourYearAvgFreeCashFlowString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFourPeriods(freeCashFlowList.get(0), freeCashFlowList.get(3));
			String fourYearAvgFreeCashFlowLine = "Free Cash Flow 4 Period Growth: " + fourYearAvgFreeCashFlowString;
			info.write(fourYearAvgFreeCashFlowLine);
			info.newLine();

//			String threeYearAvgFreeCashFlowString = CommonFinancialLibrary
//					.calculateAverageGrowthRateForThreePeriods(freeCashFlowList.get(2), freeCashFlowList.get(4));
//			String threeYearAvgFreeCashFlowLine = "Free Cash Flow 3 Period Growth: " + threeYearAvgFreeCashFlowString;
//			info.write(threeYearAvgFreeCashFlowLine);
//			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			this.writeCFSeperator(ticker, currencyType, info);

			info.newLine();
			info.newLine();
			info.newLine();

			info.close();
		}
	}

	private void writeCFSeperator(String ticker, String currencyType, BufferedWriter info) throws IOException {
		info.write("=================================================== " + ticker + " (" + currencyType + ")" 
				+ " - Cash Flow Sheet ===================================================");
		info.newLine();
	}

	public void extractCFData() {
		Iterator<Entry<String, List<CFInfoBO>>> iterator = tickerToCFData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<CFInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<CFInfoBO> cfInfoList = entry.getValue();
			CFInfoBO firstCFInfo = cfInfoList.get(0);
			CFInfoBO secondCFInfo = cfInfoList.get(1);
			CFInfoBO thirdCFInfo = cfInfoList.get(2);
			CFInfoBO fourthCFInfo = cfInfoList.get(3);
			//CFInfoBO fifthCFInfo = cfInfoList.get(4);

			String firstCFDate = firstCFInfo.getCfDate();
			String secondCFDate = secondCFInfo.getCfDate();
			String thirdCFDate = thirdCFInfo.getCfDate();
			String fourthCFDate = fourthCFInfo.getCfDate();
			//String fifthCFDate = fifthCFInfo.getCfDate();

			String firstFreeCashFlow = firstCFInfo.getFreeCashFlow();
			String secondFreeCashFlow = secondCFInfo.getFreeCashFlow();
			String thirdFreeCashFlow = thirdCFInfo.getFreeCashFlow();
			String fourthFreeCashFlow = fourthCFInfo.getFreeCashFlow();
			//String fifthFreeCashFlow = fifthCFInfo.getFreeCashFlow();

			String firstFreeCashFlowGrowth = firstCFInfo.getFreeCashFlowGrowth();
			String secondFreeCashFlowGrowth = secondCFInfo.getFreeCashFlowGrowth();
			String thirdFreeCashFlowGrowth = thirdCFInfo.getFreeCashFlowGrowth();
			//String fourthFreeCashFlowGrowth = fourthCFInfo.getFreeCashFlowGrowth();

			String firstNetCashForInvesting = firstCFInfo.getNetCashForInvestingActivities();
			String secondNetCashForInvesting = secondCFInfo.getNetCashForInvestingActivities();
			String thirdNetCashForInvesting = thirdCFInfo.getNetCashForInvestingActivities();
			String fourthNetCashForInvesting = fourthCFInfo.getNetCashForInvestingActivities();
			//String fifthNetCashForInvesting = fifthCFInfo.getNetCashForInvestingActivities();

			String firstNetCashForFinancing = firstCFInfo.getNetCashForFinancingActivities();
			String secondNetCashForFinancing = secondCFInfo.getNetCashForFinancingActivities();
			String thirdNetCashForFinancing = thirdCFInfo.getNetCashForFinancingActivities();
			String fourthNetCashForFinancing = fourthCFInfo.getNetCashForFinancingActivities();
			//String fifthNetCashForFinancing = fifthCFInfo.getNetCashForFinancingActivities();
			
			String currencyType = firstCFInfo.getCurrencyType();

			List<String> freeCashFlowList = new ArrayList<String>();
			List<String> freeCashFlowGrowthList = new ArrayList<String>();
			List<String> netCashForInvestingList = new ArrayList<String>();
			List<String> netCashForFinancingList = new ArrayList<String>();
			List<String> cfDates = new ArrayList<String>();

			cfDates.add(firstCFDate);
			cfDates.add(secondCFDate);
			cfDates.add(thirdCFDate);
			cfDates.add(fourthCFDate);
			//cfDates.add(fifthCFDate);

			freeCashFlowList.add(firstFreeCashFlow);
			freeCashFlowList.add(secondFreeCashFlow);
			freeCashFlowList.add(thirdFreeCashFlow);
			freeCashFlowList.add(fourthFreeCashFlow);
			//freeCashFlowList.add(fifthFreeCashFlow);

			freeCashFlowGrowthList.add(firstFreeCashFlowGrowth);
			freeCashFlowGrowthList.add(secondFreeCashFlowGrowth);
			freeCashFlowGrowthList.add(thirdFreeCashFlowGrowth);
			//freeCashFlowGrowthList.add(fourthFreeCashFlowGrowth);

			netCashForInvestingList.add(firstNetCashForInvesting);
			netCashForInvestingList.add(secondNetCashForInvesting);
			netCashForInvestingList.add(thirdNetCashForInvesting);
			netCashForInvestingList.add(fourthNetCashForInvesting);
			//netCashForInvestingList.add(fifthNetCashForInvesting);

			netCashForFinancingList.add(firstNetCashForFinancing);
			netCashForFinancingList.add(secondNetCashForFinancing);
			netCashForFinancingList.add(thirdNetCashForFinancing);
			netCashForFinancingList.add(fourthNetCashForFinancing);
			//netCashForFinancingList.add(fifthNetCashForFinancing);

			Collections.reverse(freeCashFlowList);
			Collections.reverse(freeCashFlowGrowthList);
			Collections.reverse(netCashForInvestingList);
			Collections.reverse(netCashForFinancingList);
			Collections.reverse(cfDates);

			tickerToFreeCashFlow.put(ticker, freeCashFlowList);
			tickerToFreeCashFlowGrowth.put(ticker, freeCashFlowGrowthList);
			tickerToNetCashForInvesting.put(ticker, netCashForInvestingList);
			tickerToNetCashForFinancing.put(ticker, netCashForFinancingList);
			tickerToCurrencyValue.put(ticker, currencyType);
			tickerToCFDates.put(ticker, cfDates);
		}

	}

	public Map<String, List<CFInfoBO>> getTickerToCFData() {
		return tickerToCFData;
	}

	public Map<String, List<String>> getTickerToFreeCashFlow() {
		return tickerToFreeCashFlow;
	}

	public Map<String, List<String>> getTickerToFreeCashFlowGrowth() {
		return tickerToFreeCashFlowGrowth;
	}

	public Map<String, List<String>> getTickerToNetCashForInvesting() {
		return tickerToNetCashForInvesting;
	}

	public Map<String, List<String>> getTickerToNetCashForFinancing() {
		return tickerToNetCashForFinancing;
	}

	public Map<String, String> getTickerToTxtFilePaths() {
		return tickerToTxtFilePaths;
	}

}
