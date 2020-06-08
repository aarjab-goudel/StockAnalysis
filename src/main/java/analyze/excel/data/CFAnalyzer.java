package analyze.excel.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import create.excel.bo.CFInfoBO;

public class CFAnalyzer {

	private Map<String, List<CFInfoBO>> tickerToCFData;
	private Map<String, List<String>> tickerToFreeCashFlow;
	private Map<String, List<String>> tickerToFreeCashFlowGrowth;
	private Map<String, List<String>> tickerToNetCashForInvesting;
	private Map<String, List<String>> tickerToNetCashForFinancing;

	public CFAnalyzer(Map<String, List<CFInfoBO>> tickerToCFData) {
		this.tickerToCFData = tickerToCFData;
		tickerToFreeCashFlow = new HashMap<String, List<String>>();
		tickerToFreeCashFlowGrowth = new HashMap<String, List<String>>();
		tickerToNetCashForInvesting = new HashMap<String, List<String>>();
		tickerToNetCashForFinancing = new HashMap<String, List<String>>();
		this.extractCFData();
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
			CFInfoBO fifthCFInfo = cfInfoList.get(4);

			String firstFreeCashFlow = firstCFInfo.getFreeCashFlow();
			String secondFreeCashFlow = secondCFInfo.getFreeCashFlow();
			String thirdFreeCashFlow = thirdCFInfo.getFreeCashFlow();
			String fourthFreeCashFlow = fourthCFInfo.getFreeCashFlow();
			String fifthFreeCashFlow = fifthCFInfo.getFreeCashFlow();

			String firstFreeCashFlowGrowth = firstCFInfo.getFreeCashFlowGrowth();
			String secondFreeCashFlowGrowth = secondCFInfo.getFreeCashFlowGrowth();
			String thirdFreeCashFlowGrowth = thirdCFInfo.getFreeCashFlowGrowth();
			String fourthFreeCashFlowGrowth = fourthCFInfo.getFreeCashFlowGrowth();

			String firstNetCashForInvesting = firstCFInfo.getNetCashForInvestingActivities();
			String secondNetCashForInvesting = secondCFInfo.getNetCashForInvestingActivities();
			String thirdNetCashForInvesting = thirdCFInfo.getNetCashForInvestingActivities();
			String fourthNetCashForInvesting = fourthCFInfo.getNetCashForInvestingActivities();
			String fifthNetCashForInvesting = fifthCFInfo.getNetCashForInvestingActivities();

			String firstNetCashForFinancing = firstCFInfo.getNetCashForFinancingActivities();
			String secondNetCashForFinancing = secondCFInfo.getNetCashForFinancingActivities();
			String thirdNetCashForFinancing = thirdCFInfo.getNetCashForFinancingActivities();
			String fourthNetCashForFinancing = fourthCFInfo.getNetCashForFinancingActivities();
			String fifthNetCashForFinancing = fifthCFInfo.getNetCashForFinancingActivities();

			List<String> freeCashFlowList = new ArrayList<String>();
			List<String> freeCashFlowGrowthList = new ArrayList<String>();
			List<String> netCashForInvestingList = new ArrayList<String>();
			List<String> netCashForFinancingList = new ArrayList<String>();

			freeCashFlowList.add(firstFreeCashFlow);
			freeCashFlowList.add(secondFreeCashFlow);
			freeCashFlowList.add(thirdFreeCashFlow);
			freeCashFlowList.add(fourthFreeCashFlow);
			freeCashFlowList.add(fifthFreeCashFlow);

			freeCashFlowGrowthList.add(firstFreeCashFlowGrowth);
			freeCashFlowGrowthList.add(secondFreeCashFlowGrowth);
			freeCashFlowGrowthList.add(thirdFreeCashFlowGrowth);
			freeCashFlowGrowthList.add(fourthFreeCashFlowGrowth);

			netCashForInvestingList.add(firstNetCashForInvesting);
			netCashForInvestingList.add(secondNetCashForInvesting);
			netCashForInvestingList.add(thirdNetCashForInvesting);
			netCashForInvestingList.add(fourthNetCashForInvesting);
			netCashForInvestingList.add(fifthNetCashForInvesting);

			netCashForFinancingList.add(firstNetCashForFinancing);
			netCashForFinancingList.add(secondNetCashForFinancing);
			netCashForFinancingList.add(thirdNetCashForFinancing);
			netCashForFinancingList.add(fourthNetCashForFinancing);
			netCashForFinancingList.add(fifthNetCashForFinancing);

			tickerToFreeCashFlow.put(ticker, freeCashFlowList);
			tickerToFreeCashFlowGrowth.put(ticker, freeCashFlowGrowthList);
			tickerToNetCashForInvesting.put(ticker, netCashForInvestingList);
			tickerToNetCashForFinancing.put(ticker, netCashForFinancingList);

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

}
