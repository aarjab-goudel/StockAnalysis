package analyze.excel.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import create.excel.bo.ISInfoBO;

public class ISAnalyzer {

	private Map<String, List<ISInfoBO>> tickerToISData;
	private Map<String, List<String>> tickerToRevenue;
	private Map<String, List<String>> tickerToRevenueGrowth;
	private Map<String, List<String>> tickerToGrossProfit;
	private Map<String, List<String>> tickerToGrossProfitGrowth;
	private Map<String, List<String>> tickerToNetIncome;
	private Map<String, List<String>> tickerToNetIncomeGrowth;

	public ISAnalyzer(Map<String, List<ISInfoBO>> tickerToISData) {
		this.tickerToISData = tickerToISData;
		tickerToRevenue = new HashMap<String, List<String>>();
		tickerToRevenueGrowth = new HashMap<String, List<String>>();
		tickerToGrossProfit = new HashMap<String, List<String>>();
		tickerToGrossProfitGrowth = new HashMap<String, List<String>>();
		tickerToNetIncome = new HashMap<String, List<String>>();
		tickerToNetIncomeGrowth = new HashMap<String, List<String>>();
		this.extractISData();
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
			ISInfoBO fifthISInfo = isInfoList.get(4);

			String firstRevenue = firstISInfo.getRevenue();
			String secondRevenue = secondISInfo.getRevenue();
			String thirdRevenue = thirdISInfo.getRevenue();
			String fourthRevenue = fourthISInfo.getRevenue();
			String fifthRevenue = fifthISInfo.getRevenue();

			String firstRevenueGrowth = firstISInfo.getRevenueGrowth();
			String secondRevenueGrowth = secondISInfo.getRevenueGrowth();
			String thirdRevenueGrowth = thirdISInfo.getRevenueGrowth();
			String fourthRevenueGrowth = fourthISInfo.getRevenueGrowth();

			String firstGrossProfit = firstISInfo.getGrossProfit();
			String secondGrossProfit = secondISInfo.getGrossProfit();
			String thirdGrossProfit = thirdISInfo.getGrossProfit();
			String fourthGrossProfit = fourthISInfo.getGrossProfit();
			String fifthGrossProfit = fifthISInfo.getGrossProfit();

			String firstGrossProfitGrowth = firstISInfo.getGrossProfitGrowth();
			String secondGrossProfitGrowth = secondISInfo.getGrossProfitGrowth();
			String thirdGrossProfitGrowth = thirdISInfo.getGrossProfitGrowth();
			String fourthGrossProfitGrowth = fourthISInfo.getGrossProfitGrowth();

			String firstNetIncome = firstISInfo.getNetIncome();
			String secondNetIncome = secondISInfo.getNetIncome();
			String thirdNetIncome = thirdISInfo.getNetIncome();
			String fourthNetIncome = fourthISInfo.getNetIncome();
			String fifthNetIncome = fifthISInfo.getNetIncome();

			String firstNetIncomeGrowth = firstISInfo.getNetIncomeGrowth();
			String secondNetIncomeGrowth = secondISInfo.getNetIncomeGrowth();
			String thirdNetIncomeGrowth = thirdISInfo.getNetIncomeGrowth();
			String fourthNetIncomeGrowth = fourthISInfo.getNetIncomeGrowth();

			List<String> revenueList = new ArrayList<String>();
			List<String> revenueGrowthList = new ArrayList<String>();
			List<String> grossProfitList = new ArrayList<String>();
			List<String> grossProfitGrowthList = new ArrayList<String>();
			List<String> netIncomeList = new ArrayList<String>();
			List<String> netIncomeGrowthList = new ArrayList<String>();

			revenueList.add(firstRevenue);
			revenueList.add(secondRevenue);
			revenueList.add(thirdRevenue);
			revenueList.add(fourthRevenue);
			revenueList.add(fifthRevenue);

			revenueGrowthList.add(firstRevenueGrowth);
			revenueGrowthList.add(secondRevenueGrowth);
			revenueGrowthList.add(thirdRevenueGrowth);
			revenueGrowthList.add(fourthRevenueGrowth);

			grossProfitList.add(firstGrossProfit);
			grossProfitList.add(secondGrossProfit);
			grossProfitList.add(thirdGrossProfit);
			grossProfitList.add(fourthGrossProfit);
			grossProfitList.add(fifthGrossProfit);

			grossProfitGrowthList.add(firstGrossProfitGrowth);
			grossProfitGrowthList.add(secondGrossProfitGrowth);
			grossProfitGrowthList.add(thirdGrossProfitGrowth);
			grossProfitGrowthList.add(fourthGrossProfitGrowth);

			netIncomeList.add(firstNetIncome);
			netIncomeList.add(secondNetIncome);
			netIncomeList.add(thirdNetIncome);
			netIncomeList.add(fourthNetIncome);
			netIncomeList.add(fifthNetIncome);

			netIncomeGrowthList.add(firstNetIncomeGrowth);
			netIncomeGrowthList.add(secondNetIncomeGrowth);
			netIncomeGrowthList.add(thirdNetIncomeGrowth);
			netIncomeGrowthList.add(fourthNetIncomeGrowth);

			tickerToRevenue.put(ticker, revenueList);
			tickerToRevenueGrowth.put(ticker, revenueGrowthList);
			tickerToGrossProfit.put(ticker, grossProfitList);
			tickerToGrossProfitGrowth.put(ticker, grossProfitGrowthList);
			tickerToNetIncome.put(ticker, netIncomeList);
			tickerToNetIncomeGrowth.put(ticker, netIncomeGrowthList);
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

}
