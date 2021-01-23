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

import create.excel.bo.RatioInfoBO;
import excel.library.CommonFinancialLibrary;

public class RatioAnalyzer {

	private Map<String, List<RatioInfoBO>> tickerToRatioData;
	private Map<String, String> tickerToTxtFilePaths;
	private Map<String, List<String>> tickerToRatioDates;
	private Map<String, List<String>> tickerToEPS;
	private Map<String, List<String>> tickerToEPSGrowth;
	private Map<String, List<String>> tickerToDebtEquityRatio;
	private Map<String, List<String>> tickerToDebtEquityGrowthRatio;
	private Map<String, List<String>> tickerToCurrentRatio;
	private Map<String, List<String>> tickerToCurrentRatioGrowth;

	public RatioAnalyzer(Map<String, List<RatioInfoBO>> tickerToRatioData, Map<String, String> tickerToTxtFilePaths)
			throws IOException {
		this.tickerToRatioData = tickerToRatioData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
		tickerToRatioDates = new HashMap<String, List<String>>();
		tickerToEPS = new HashMap<String, List<String>>();
		tickerToEPSGrowth = new HashMap<String, List<String>>();
		tickerToDebtEquityRatio = new HashMap<String, List<String>>();
		tickerToDebtEquityGrowthRatio = new HashMap<String, List<String>>();
		tickerToCurrentRatio = new HashMap<String, List<String>>();
		tickerToCurrentRatioGrowth = new HashMap<String, List<String>>();
		this.extractRatioData();
		this.writeRatioDataToTxtFiles();
	}

	public void writeRatioDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String txtFilePath = entry.getValue();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);
			this.writeRatioSeperator(ticker, info);

			List<String> epsList = tickerToEPS.get(ticker);
			List<String> epsGrowthList = tickerToEPSGrowth.get(ticker);
			List<String> ratioDateList = tickerToRatioDates.get(ticker);
			List<String> currentRatioList = tickerToCurrentRatio.get(ticker);
			List<String> currentRatioGrowthList = tickerToCurrentRatioGrowth.get(ticker);
			List<String> debtEquityRatioList = tickerToDebtEquityRatio.get(ticker);
			List<String> debtEquityRatioGrowthList = tickerToDebtEquityGrowthRatio.get(ticker);

			String dateLines = "                                         " + ratioDateList.get(0) + "             "
					+ ratioDateList.get(1) + "             " + ratioDateList.get(2) + "             "
					+ ratioDateList.get(3) + "             " + ratioDateList.get(4);
			info.write(dateLines);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String currentRatioLine = "Current Ratio                            " + currentRatioList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioList.get(0)) + currentRatioList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioList.get(1)) + currentRatioList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioList.get(2)) + currentRatioList.get(3)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioList.get(3)) + currentRatioList.get(4);
			info.write(currentRatioLine);
			info.newLine();

			String currentRatioGrowthLine = "Current Ratio Growth                               "
					+ currentRatioGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioGrowthList.get(0))
					+ currentRatioGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioGrowthList.get(1))
					+ currentRatioGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(currentRatioGrowthList.get(2))
					+ currentRatioGrowthList.get(3);
			info.write(currentRatioGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String debtEquityRatioLine = "Debt Equity Ratio                        " + debtEquityRatioList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioList.get(0))
					+ debtEquityRatioList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioList.get(1))
					+ debtEquityRatioList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioList.get(2))
					+ debtEquityRatioList.get(3)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioList.get(3))
					+ debtEquityRatioList.get(4)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioList.get(4));
			info.write(debtEquityRatioLine);
			info.newLine();

			String debtEquityRatioGrowthLine = "Debt Equity Ratio Growth                           "
					+ debtEquityRatioGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioGrowthList.get(0))
					+ debtEquityRatioGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioGrowthList.get(1))
					+ debtEquityRatioGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(debtEquityRatioGrowthList.get(2))
					+ debtEquityRatioGrowthList.get(3);
			info.write(debtEquityRatioGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String epsLine = "EPS                                      " + epsList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(0)) + epsList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(1)) + epsList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(2)) + epsList.get(3)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(3)) + epsList.get(4)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(4));
			info.write(epsLine);
			info.newLine();

			String epsGrowthLine = "EPS Growth                                         " + epsGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(0)) + epsGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(1)) + epsGrowthList.get(2)
					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(2)) + epsGrowthList.get(3);
			info.write(epsGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String fiveYearAvgEPSString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(epsList.get(0), epsList.get(4));
			String fiveYearAvgCurrentRatioString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(currentRatioList.get(0), currentRatioList.get(4));
			String fiveYearAvgDebtEquityRatioString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFivePeriods(debtEquityRatioList.get(0), debtEquityRatioList.get(4));

			String fiveYearAvgEPSLine = "EPS 5 Yr Growth:  " + fiveYearAvgEPSString;
			info.write(fiveYearAvgEPSLine);
			info.newLine();

			String fiveYearAvgNetEquityLine = "Current Ratio 5 Yr Growth:  " + fiveYearAvgCurrentRatioString;
			info.write(fiveYearAvgNetEquityLine);
			info.newLine();

			String fiveYearAvgDebtEquityLine = "Debt Equity Ratio 5 Yr Growth:  " + fiveYearAvgDebtEquityRatioString;
			info.write(fiveYearAvgDebtEquityLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			this.writeRatioSeperator(ticker, info);

			info.newLine();

			info.close();
		}
	}

	private void writeRatioSeperator(String ticker, BufferedWriter info) throws IOException {
		info.write("==================================================== " + ticker
				+ " - Ratio Sheet ====================================================");
		info.newLine();
	}

	public void extractRatioData() {
		Iterator<Entry<String, List<RatioInfoBO>>> iterator = tickerToRatioData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<RatioInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<RatioInfoBO> ratioInfoList = entry.getValue();
			RatioInfoBO firstRatioInfo = ratioInfoList.get(0);
			RatioInfoBO secondRatioInfo = ratioInfoList.get(1);
			RatioInfoBO thirdRatioInfo = ratioInfoList.get(2);
			RatioInfoBO fourthRatioInfo = ratioInfoList.get(3);
			RatioInfoBO fifthRatioInfo = ratioInfoList.get(4);

			String firstRatioDate = firstRatioInfo.getRatioDate();
			String secondRatioDate = secondRatioInfo.getRatioDate();
			String thirdRatioDate = thirdRatioInfo.getRatioDate();
			String fourthRatioDate = fourthRatioInfo.getRatioDate();
			String fifthRatioDate = fifthRatioInfo.getRatioDate();

			String firstCurrentRatio = firstRatioInfo.getCurrentRatio();
			String secondCurrentRatio = secondRatioInfo.getCurrentRatio();
			String thirdCurrentRatio = thirdRatioInfo.getCurrentRatio();
			String fourthCurrentRatio = fourthRatioInfo.getCurrentRatio();
			String fifthCurrentRatio = fifthRatioInfo.getCurrentRatio();

			String firstCurrentRatioGrowth = firstRatioInfo.getCurrentRatioGrowth();
			String secondCurrentRatioGrowth = secondRatioInfo.getCurrentRatioGrowth();
			String thirdCurrentRatioGrowth = thirdRatioInfo.getCurrentRatioGrowth();
			String fourthCurrentRatioGrowth = fourthRatioInfo.getCurrentRatioGrowth();

			String firstDebtEquityRatio = firstRatioInfo.getDebtToEquityRatio();
			String secondDebtEquityRatio = secondRatioInfo.getDebtToEquityRatio();
			String thirdDebtEquityRatio = thirdRatioInfo.getDebtToEquityRatio();
			String fourthDebtEquityRatio = fourthRatioInfo.getDebtToEquityRatio();
			String fifthDebtEquityRatio = fifthRatioInfo.getDebtToEquityRatio();

			String firstDebtEquityRatioGrowth = firstRatioInfo.getDebtToEquityRatioGrowth();
			String secondDebtEquityRatioGrowth = secondRatioInfo.getDebtToEquityRatioGrowth();
			String thirdDebtEquityRatioGrowth = thirdRatioInfo.getDebtToEquityRatioGrowth();
			String fourthDebtEquityRatioGrowth = fourthRatioInfo.getDebtToEquityRatioGrowth();

			String firstEPS = firstRatioInfo.getEarningsPerShare();
			String secondEPS = secondRatioInfo.getEarningsPerShare();
			String thirdEPS = thirdRatioInfo.getEarningsPerShare();
			String fourthEPS = fourthRatioInfo.getEarningsPerShare();
			String fifthEPS = fifthRatioInfo.getEarningsPerShare();

			String firstEPSGrowth = firstRatioInfo.getEarningsPerShareGrowth();
			String secondEPSGrowth = secondRatioInfo.getEarningsPerShareGrowth();
			String thirdEPSGrowth = thirdRatioInfo.getEarningsPerShareGrowth();
			String fourthEPSGrowth = fourthRatioInfo.getEarningsPerShareGrowth();

			List<String> epsList = new ArrayList<String>();
			List<String> epsGrowthList = new ArrayList<String>();
			List<String> debtEquityRatioList = new ArrayList<String>();
			List<String> debtEquityRatioGrowthList = new ArrayList<String>();
			List<String> currentRatioList = new ArrayList<String>();
			List<String> currentRatioGrowthList = new ArrayList<String>();
			List<String> ratioDates = new ArrayList<String>();

			ratioDates.add(firstRatioDate);
			ratioDates.add(secondRatioDate);
			ratioDates.add(thirdRatioDate);
			ratioDates.add(fourthRatioDate);
			ratioDates.add(fifthRatioDate);

			epsList.add(firstEPS);
			epsList.add(secondEPS);
			epsList.add(thirdEPS);
			epsList.add(fourthEPS);
			epsList.add(fifthEPS);

			epsGrowthList.add(firstEPSGrowth);
			epsGrowthList.add(secondEPSGrowth);
			epsGrowthList.add(thirdEPSGrowth);
			epsGrowthList.add(fourthEPSGrowth);

			debtEquityRatioList.add(firstDebtEquityRatio);
			debtEquityRatioList.add(secondDebtEquityRatio);
			debtEquityRatioList.add(thirdDebtEquityRatio);
			debtEquityRatioList.add(fourthDebtEquityRatio);
			debtEquityRatioList.add(fifthDebtEquityRatio);

			debtEquityRatioGrowthList.add(firstDebtEquityRatioGrowth);
			debtEquityRatioGrowthList.add(secondDebtEquityRatioGrowth);
			debtEquityRatioGrowthList.add(thirdDebtEquityRatioGrowth);
			debtEquityRatioGrowthList.add(fourthDebtEquityRatioGrowth);

			currentRatioList.add(firstCurrentRatio);
			currentRatioList.add(secondCurrentRatio);
			currentRatioList.add(thirdCurrentRatio);
			currentRatioList.add(fourthCurrentRatio);
			currentRatioList.add(fifthCurrentRatio);

			currentRatioGrowthList.add(firstCurrentRatioGrowth);
			currentRatioGrowthList.add(secondCurrentRatioGrowth);
			currentRatioGrowthList.add(thirdCurrentRatioGrowth);
			currentRatioGrowthList.add(fourthCurrentRatioGrowth);

			Collections.reverse(epsList);
			Collections.reverse(epsGrowthList);
			Collections.reverse(debtEquityRatioList);
			Collections.reverse(debtEquityRatioGrowthList);
			Collections.reverse(currentRatioList);
			Collections.reverse(currentRatioGrowthList);
			Collections.reverse(ratioDates);

			tickerToCurrentRatio.put(ticker, currentRatioList);
			tickerToCurrentRatioGrowth.put(ticker, currentRatioGrowthList);
			tickerToDebtEquityRatio.put(ticker, debtEquityRatioList);
			tickerToDebtEquityGrowthRatio.put(ticker, debtEquityRatioGrowthList);
			tickerToEPS.put(ticker, epsList);
			tickerToEPSGrowth.put(ticker, epsGrowthList);
			tickerToRatioDates.put(ticker, ratioDates);

		}
	}

	public Map<String, List<RatioInfoBO>> getTickerToRatioData() {
		return tickerToRatioData;
	}

	public Map<String, String> getTickerToTxtFilePaths() {
		return tickerToTxtFilePaths;
	}

	public Map<String, List<String>> getTickerToRatioDates() {
		return tickerToRatioDates;
	}

	public Map<String, List<String>> getTickerToEPS() {
		return tickerToEPS;
	}

	public Map<String, List<String>> getTickerToEPSGrowth() {
		return tickerToEPSGrowth;
	}

	public Map<String, List<String>> getTickerToDebtEquityRatio() {
		return tickerToDebtEquityRatio;
	}

	public Map<String, List<String>> getTickerToDebtEquityGrowthRatio() {
		return tickerToDebtEquityGrowthRatio;
	}

	public Map<String, List<String>> getTickerToCurrentRatio() {
		return tickerToCurrentRatio;
	}

	public Map<String, List<String>> getTickerToCurrentRatioGrowth() {
		return tickerToCurrentRatioGrowth;
	}

}
