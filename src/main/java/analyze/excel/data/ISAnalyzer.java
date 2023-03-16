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

import create.excel.bo.ISInfoBO;
import excel.library.CommonFinancialLibrary;

public class ISAnalyzer {

	private Map<String, List<ISInfoBO>> tickerToISData;
	private Map<String, String> tickerToTxtFilePaths;
	private Map<String, List<String>> tickerToRevenue;
	private Map<String, List<String>> tickerToRevenueGrowth;
	private Map<String, List<String>> tickerToGrossProfit;
	private Map<String, List<String>> tickerToGrossProfitGrowth;
	private Map<String, List<String>> tickerToNetIncome;
	private Map<String, List<String>> tickerToNetIncomeGrowth;
	private Map<String, List<String>> tickerToISDates;
	private Map<String, List<String>> tickerToEPS;
	private Map<String, List<String>> tickerToEPSGrowth;
	private Map<String, List<String>> tickerToResearchAndDevelopment;
	private Map<String, List<String>> tickerToResearchAndDevelopmentGrowth;

	public ISAnalyzer(Map<String, List<ISInfoBO>> tickerToISData, Map<String, String> tickerToTxtFilePaths)
			throws IOException {
		this.tickerToISData = tickerToISData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
		tickerToRevenue = new HashMap<String, List<String>>();
		tickerToRevenueGrowth = new HashMap<String, List<String>>();
		tickerToGrossProfit = new HashMap<String, List<String>>();
		tickerToGrossProfitGrowth = new HashMap<String, List<String>>();
		tickerToNetIncome = new HashMap<String, List<String>>();
		tickerToNetIncomeGrowth = new HashMap<String, List<String>>();
		tickerToISDates = new HashMap<String, List<String>>();
		tickerToEPS = new HashMap<String, List<String>>();
		tickerToEPSGrowth = new HashMap<String, List<String>>();
		tickerToResearchAndDevelopment = new HashMap<String, List<String>>();
		tickerToResearchAndDevelopmentGrowth = new HashMap<String, List<String>>();

		this.extractISData();
		this.writeISDataToTxtFiles();
	}

	public void writeISDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String txtFilePath = entry.getValue();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);
			this.writeISSeperator(ticker, info);

			List<String> isDateList = tickerToISDates.get(ticker);
			List<String> revenueList = tickerToRevenue.get(ticker);
			List<String> revenueGrowthList = tickerToRevenueGrowth.get(ticker);
			List<String> grossProfitList = tickerToGrossProfit.get(ticker);
			List<String> grossProfitGrowthList = tickerToGrossProfitGrowth.get(ticker);
			List<String> netIncomeList = tickerToNetIncome.get(ticker);
			List<String> netIncomeGrowthList = tickerToNetIncomeGrowth.get(ticker);
			List<String> epsList = tickerToEPS.get(ticker);
			List<String> epsGrowthList = tickerToEPSGrowth.get(ticker);
			List<String> researchAndDevelopmentList = tickerToResearchAndDevelopment.get(ticker);
			List<String> researchAndDevelopmentGrowthList = tickerToResearchAndDevelopmentGrowth.get(ticker);

			String dateLines = "                                         " + isDateList.get(0) + "             "
					+ isDateList.get(1) + "             " + isDateList.get(2) + "             " + isDateList.get(3);
			info.write(dateLines);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstRevenue = CommonFinancialLibrary.addCommasToNumericString(revenueList.get(0));
			String secondRevenue = CommonFinancialLibrary.addCommasToNumericString(revenueList.get(1));
			String thirdRevenue = CommonFinancialLibrary.addCommasToNumericString(revenueList.get(2));
			String fourthRevenue = CommonFinancialLibrary.addCommasToNumericString(revenueList.get(3));
			//String fifthRevenue = CommonFinancialLibrary.addCommasToNumericString(revenueList.get(4));

			String revenueLine = "Revenue                                  " + firstRevenue
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstRevenue) + secondRevenue
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondRevenue) + thirdRevenue
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdRevenue) + fourthRevenue;
			info.write(revenueLine);
			info.newLine();

			String revenueGrowthLine = "Revenue Growth                                     " + revenueGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(revenueGrowthList.get(0)) + revenueGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(revenueGrowthList.get(1)) + revenueGrowthList.get(2);
			info.write(revenueGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstGrossProfit = CommonFinancialLibrary.addCommasToNumericString(grossProfitList.get(0));
			String secondGrossProfit = CommonFinancialLibrary.addCommasToNumericString(grossProfitList.get(1));
			String thirdGrossProfit = CommonFinancialLibrary.addCommasToNumericString(grossProfitList.get(2));
			String fourthGrossProfit = CommonFinancialLibrary.addCommasToNumericString(grossProfitList.get(3));
//			String fifthGrossProfit = CommonFinancialLibrary.addCommasToNumericString(grossProfitList.get(4));

			String grossProfitLine = "Gross Profit                             " + firstGrossProfit
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstGrossProfit) + secondGrossProfit
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondGrossProfit) + thirdGrossProfit
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdGrossProfit) + fourthGrossProfit;
			info.write(grossProfitLine);
			info.newLine();

			String grossProfitGrowthLine = "Gross Profit Growth                                "
					+ grossProfitGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(grossProfitGrowthList.get(0))
					+ grossProfitGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(grossProfitGrowthList.get(1))
					+ grossProfitGrowthList.get(2);
			info.write(grossProfitGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String firstNetIncome = CommonFinancialLibrary.addCommasToNumericString(netIncomeList.get(0));
			String secondNetIncome = CommonFinancialLibrary.addCommasToNumericString(netIncomeList.get(1));
			String thirdNetIncome = CommonFinancialLibrary.addCommasToNumericString(netIncomeList.get(2));
			String fourthNetIncome = CommonFinancialLibrary.addCommasToNumericString(netIncomeList.get(3));
			//String fifthNetIncome = CommonFinancialLibrary.addCommasToNumericString(netIncomeList.get(4));

			String netIncomeLine = "Net Income                               " + firstNetIncome
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstNetIncome) + secondNetIncome
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondNetIncome) + thirdNetIncome
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdNetIncome) + fourthNetIncome;
			info.write(netIncomeLine);
			info.newLine();

			String netIncomeGrowthLine = "Net Income Growth                                  "
					+ netIncomeGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(netIncomeGrowthList.get(0))
					+ netIncomeGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(netIncomeGrowthList.get(1))
					+ netIncomeGrowthList.get(2);
			info.write(netIncomeGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

//			String epsLine = "EPS                                      " + epsList.get(0)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(0)) + epsList.get(1)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(1)) + epsList.get(2)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(2)) + epsList.get(3)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(3)) + epsList.get(4)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsList.get(4));
//			info.write(epsLine);
//			info.newLine();
//
//			String epsGrowthLine = "EPS Growth                                         " + epsGrowthList.get(0)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(0)) + epsGrowthList.get(1)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(1)) + epsGrowthList.get(2)
//					+ CommonFinancialLibrary.getApprorpriateSpaces(epsGrowthList.get(2)) + epsGrowthList.get(3);
//			info.write(epsGrowthLine);
//			info.newLine();
//
//			CommonFinancialLibrary.writeSeperator(info);

			String firstResearchAndDevelopment = CommonFinancialLibrary
					.addCommasToNumericString(researchAndDevelopmentList.get(0));
			String secondResearchAndDevelopment = CommonFinancialLibrary
					.addCommasToNumericString(researchAndDevelopmentList.get(1));
			String thirdResearchAndDevelopment = CommonFinancialLibrary
					.addCommasToNumericString(researchAndDevelopmentList.get(2));
			String fourthResearchAndDevelopment = CommonFinancialLibrary
					.addCommasToNumericString(researchAndDevelopmentList.get(3));
//			String fifthResearchAndDevelopment = CommonFinancialLibrary
//					.addCommasToNumericString(researchAndDevelopmentList.get(4));

			String researchAndDevelopmentLine = "Research And Development                 "
					+ firstResearchAndDevelopment
					+ CommonFinancialLibrary.getApprorpriateSpaces(firstResearchAndDevelopment)
					+ secondResearchAndDevelopment
					+ CommonFinancialLibrary.getApprorpriateSpaces(secondResearchAndDevelopment)
					+ thirdResearchAndDevelopment
					+ CommonFinancialLibrary.getApprorpriateSpaces(thirdResearchAndDevelopment)
					+ fourthResearchAndDevelopment;
			info.write(researchAndDevelopmentLine);
			info.newLine();

			String researchAndDevelopmentGrowthLine = "Research And Development Growth                    "
					+ researchAndDevelopmentGrowthList.get(0)
					+ CommonFinancialLibrary.getApprorpriateSpaces(researchAndDevelopmentGrowthList.get(0))
					+ researchAndDevelopmentGrowthList.get(1)
					+ CommonFinancialLibrary.getApprorpriateSpaces(researchAndDevelopmentGrowthList.get(1))
					+ researchAndDevelopmentGrowthList.get(2);
			info.write(researchAndDevelopmentGrowthLine);
			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			String fourYearAvgRevenueString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFourPeriods(revenueList.get(0), revenueList.get(3));
			String fourYearAvgGrossProfitString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFourPeriods(grossProfitList.get(0), grossProfitList.get(3));
			String fourYearAvgNetIncomeString = CommonFinancialLibrary
					.calculateAverageGrowthRateForFourPeriods(netIncomeList.get(0), netIncomeList.get(3));
//			String fourearAvgResearchAndDevelopmentString = CommonFinancialLibrary
//					.calculateAverageGrowthRateForFourPeriods(researchAndDevelopmentList.get(0),
//							researchAndDevelopmentList.get(4));

			String fiveYearAvgRevenueLine = "Revenue 4 Period Growth:  " + fourYearAvgRevenueString;
			info.write(fiveYearAvgRevenueLine);
			info.newLine();

			String fiverYearAvgGrossProfitLine = "Gross Profit 4 Period Growth: " + fourYearAvgGrossProfitString;
			info.write(fiverYearAvgGrossProfitLine);
			info.newLine();

			String fiveYearAvgNetIncomeLine = "Net Income 4 Period Growth: " + fourYearAvgNetIncomeString;
			info.write(fiveYearAvgNetIncomeLine);
			info.newLine();

//			String fiveYearAvgResearchAndDevelopmentLine = "Research And Development 5 Period Growth: "
//					+ fiveYearAvgResearchAndDevelopmentString;
//			info.write(fiveYearAvgResearchAndDevelopmentLine);
//			info.newLine();

			CommonFinancialLibrary.writeSeperator(info);

			this.writeISSeperator(ticker, info);

			info.newLine();
			info.newLine();
			info.newLine();

			info.close();
		}

	}

	private void writeISSeperator(String ticker, BufferedWriter info) throws IOException {
		info.write("===================================================== " + ticker
				+ " - Income Sheet =====================================================");
		info.newLine();
	}

	public void extractISData() {
		Iterator<Entry<String, List<ISInfoBO>>> iterator = tickerToISData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<ISInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<ISInfoBO> isInfoList = entry.getValue();
			ISInfoBO firstISInfo = isInfoList.get(0);
			ISInfoBO secondISInfo = isInfoList.get(1);
			ISInfoBO thirdISInfo = isInfoList.get(2);
			ISInfoBO fourthISInfo = isInfoList.get(3);
			//ISInfoBO fifthISInfo = isInfoList.get(4);

			String firstISDate = firstISInfo.getIsDate();
			String secondISDate = secondISInfo.getIsDate();
			String thirdISDate = thirdISInfo.getIsDate();
			String fourthISDate = fourthISInfo.getIsDate();
			//String fifthISDate = fifthISInfo.getIsDate();

			String firstResearchAndDevelopment = firstISInfo.getResearchAndDevelopment();
			String secondResearchAndDevelopment = secondISInfo.getResearchAndDevelopment();
			String thirdResearchAndDevelopment = thirdISInfo.getResearchAndDevelopment();
			String fourthResearchAndDevelopment = fourthISInfo.getResearchAndDevelopment();
			//String fifthResearchAndDevelopment = fifthISInfo.getResearchAndDevelopment();

			String firstResearchAndDevelopmentGrowth = firstISInfo.getResearchAndDevelopmentGrowth();
			String secondResearchAndDevelopmentGrowth = secondISInfo.getResearchAndDevelopmentGrowth();
			String thirdResearchAndDevelopmentGrowth = thirdISInfo.getResearchAndDevelopmentGrowth();
			//String fourthResearchAndDevelopmentGrowth = fourthISInfo.getResearchAndDevelopmentGrowth();

			String firstRevenue = firstISInfo.getRevenue();
			String secondRevenue = secondISInfo.getRevenue();
			String thirdRevenue = thirdISInfo.getRevenue();
			String fourthRevenue = fourthISInfo.getRevenue();
			//String fifthRevenue = fifthISInfo.getRevenue();

			String firstRevenueGrowth = firstISInfo.getRevenueGrowth();
			String secondRevenueGrowth = secondISInfo.getRevenueGrowth();
			String thirdRevenueGrowth = thirdISInfo.getRevenueGrowth();
			//String fourthRevenueGrowth = fourthISInfo.getRevenueGrowth();

			String firstGrossProfit = firstISInfo.getGrossProfit();
			String secondGrossProfit = secondISInfo.getGrossProfit();
			String thirdGrossProfit = thirdISInfo.getGrossProfit();
			String fourthGrossProfit = fourthISInfo.getGrossProfit();
			//String fifthGrossProfit = fifthISInfo.getGrossProfit();

			String firstGrossProfitGrowth = firstISInfo.getGrossProfitGrowth();
			String secondGrossProfitGrowth = secondISInfo.getGrossProfitGrowth();
			String thirdGrossProfitGrowth = thirdISInfo.getGrossProfitGrowth();
			//String fourthGrossProfitGrowth = fourthISInfo.getGrossProfitGrowth();

			String firstNetIncome = firstISInfo.getNetIncome();
			String secondNetIncome = secondISInfo.getNetIncome();
			String thirdNetIncome = thirdISInfo.getNetIncome();
			String fourthNetIncome = fourthISInfo.getNetIncome();
			//String fifthNetIncome = fifthISInfo.getNetIncome();

			String firstNetIncomeGrowth = firstISInfo.getNetIncomeGrowth();
			String secondNetIncomeGrowth = secondISInfo.getNetIncomeGrowth();
			String thirdNetIncomeGrowth = thirdISInfo.getNetIncomeGrowth();
			//String fourthNetIncomeGrowth = fourthISInfo.getNetIncomeGrowth();

			String firstEPS = firstISInfo.getEps();
			String secondEPS = secondISInfo.getEps();
			String thirdEPS = thirdISInfo.getEps();
			String fourthEPS = fourthISInfo.getEps();
			//String fifthEPS = fifthISInfo.getEps();

			String firstEPSGrowth = firstISInfo.getEpsGrowth();
			String secondEPSGrowth = secondISInfo.getEpsGrowth();
			String thirdEPSGrowth = thirdISInfo.getEpsGrowth();
			//String fourthEPSGrowth = fourthISInfo.getEpsGrowth();

			List<String> researchAndDevelopmentList = new ArrayList<String>();
			List<String> researchAndDevelopmentGrowthList = new ArrayList<String>();
			List<String> revenueList = new ArrayList<String>();
			List<String> revenueGrowthList = new ArrayList<String>();
			List<String> grossProfitList = new ArrayList<String>();
			List<String> grossProfitGrowthList = new ArrayList<String>();
			List<String> netIncomeList = new ArrayList<String>();
			List<String> netIncomeGrowthList = new ArrayList<String>();
			List<String> epsList = new ArrayList<String>();
			List<String> epsGrowthList = new ArrayList<String>();
			List<String> isDates = new ArrayList<String>();

			isDates.add(firstISDate);
			isDates.add(secondISDate);
			isDates.add(thirdISDate);
			isDates.add(fourthISDate);
			//isDates.add(fifthISDate);

			researchAndDevelopmentList.add(firstResearchAndDevelopment);
			researchAndDevelopmentList.add(secondResearchAndDevelopment);
			researchAndDevelopmentList.add(thirdResearchAndDevelopment);
			researchAndDevelopmentList.add(fourthResearchAndDevelopment);
			//researchAndDevelopmentList.add(fifthResearchAndDevelopment);

			researchAndDevelopmentGrowthList.add(firstResearchAndDevelopmentGrowth);
			researchAndDevelopmentGrowthList.add(secondResearchAndDevelopmentGrowth);
			researchAndDevelopmentGrowthList.add(thirdResearchAndDevelopmentGrowth);
			//researchAndDevelopmentGrowthList.add(fourthResearchAndDevelopmentGrowth);

			revenueList.add(firstRevenue);
			revenueList.add(secondRevenue);
			revenueList.add(thirdRevenue);
			revenueList.add(fourthRevenue);
			//revenueList.add(fifthRevenue);

			revenueGrowthList.add(firstRevenueGrowth);
			revenueGrowthList.add(secondRevenueGrowth);
			revenueGrowthList.add(thirdRevenueGrowth);
			//revenueGrowthList.add(fourthRevenueGrowth);

			grossProfitList.add(firstGrossProfit);
			grossProfitList.add(secondGrossProfit);
			grossProfitList.add(thirdGrossProfit);
			grossProfitList.add(fourthGrossProfit);
			//grossProfitList.add(fifthGrossProfit);

			grossProfitGrowthList.add(firstGrossProfitGrowth);
			grossProfitGrowthList.add(secondGrossProfitGrowth);
			grossProfitGrowthList.add(thirdGrossProfitGrowth);
			//grossProfitGrowthList.add(fourthGrossProfitGrowth);

			netIncomeList.add(firstNetIncome);
			netIncomeList.add(secondNetIncome);
			netIncomeList.add(thirdNetIncome);
			netIncomeList.add(fourthNetIncome);
			//netIncomeList.add(fifthNetIncome);

			netIncomeGrowthList.add(firstNetIncomeGrowth);
			netIncomeGrowthList.add(secondNetIncomeGrowth);
			netIncomeGrowthList.add(thirdNetIncomeGrowth);
			//netIncomeGrowthList.add(fourthNetIncomeGrowth);

			epsList.add(firstEPS);
			epsList.add(secondEPS);
			epsList.add(thirdEPS);
			epsList.add(fourthEPS);
			//epsList.add(fifthEPS);

			epsGrowthList.add(firstEPSGrowth);
			epsGrowthList.add(secondEPSGrowth);
			epsGrowthList.add(thirdEPSGrowth);
			//epsGrowthList.add(fourthEPSGrowth);

			Collections.reverse(researchAndDevelopmentList);
			Collections.reverse(researchAndDevelopmentGrowthList);
			Collections.reverse(revenueList);
			Collections.reverse(revenueGrowthList);
			Collections.reverse(grossProfitList);
			Collections.reverse(grossProfitGrowthList);
			Collections.reverse(netIncomeList);
			Collections.reverse(netIncomeGrowthList);
			Collections.reverse(epsList);
			Collections.reverse(epsGrowthList);
			Collections.reverse(isDates);

			tickerToRevenue.put(ticker, revenueList);
			tickerToRevenueGrowth.put(ticker, revenueGrowthList);
			tickerToGrossProfit.put(ticker, grossProfitList);
			tickerToGrossProfitGrowth.put(ticker, grossProfitGrowthList);
			tickerToNetIncome.put(ticker, netIncomeList);
			tickerToNetIncomeGrowth.put(ticker, netIncomeGrowthList);
			tickerToEPS.put(ticker, epsList);
			tickerToEPSGrowth.put(ticker, epsGrowthList);
			tickerToISDates.put(ticker, isDates);
			tickerToResearchAndDevelopment.put(ticker, researchAndDevelopmentList);
			tickerToResearchAndDevelopmentGrowth.put(ticker, researchAndDevelopmentGrowthList);

		}

	}

	public Map<String, List<ISInfoBO>> getTickerToISData() {
		return tickerToISData;
	}

	public Map<String, List<String>> getTickerToRevenue() {
		return tickerToRevenue;
	}

	public Map<String, List<String>> getTickerToRevenueGrowth() {
		return tickerToRevenueGrowth;
	}

	public Map<String, List<String>> getTickerToGrossProfit() {
		return tickerToGrossProfit;
	}

	public Map<String, List<String>> getTickerToGrossProfitGrowth() {
		return tickerToGrossProfitGrowth;
	}

	public Map<String, List<String>> getTickerToNetIncome() {
		return tickerToNetIncome;
	}

	public Map<String, List<String>> getTickerToNetIncomeGrowth() {
		return tickerToNetIncomeGrowth;
	}

	public Map<String, String> getTickerToTxtFilePaths() {
		return tickerToTxtFilePaths;
	}

	public Map<String, List<String>> getTickerToResearchAndDevelopment() {
		return tickerToResearchAndDevelopment;
	}

	public Map<String, List<String>> getTickerToResearchAndDevelopmentGrowth() {
		return tickerToResearchAndDevelopmentGrowth;
	}

}
