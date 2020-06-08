package analyze.excel.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import create.excel.bo.BSInfoBO;

public class BSAnalyzer {

	private Map<String, List<BSInfoBO>> tickerToBSData;
	private Map<String, List<String>> tickerToNetEquity;
	private Map<String, List<String>> tickerToNetEquityGrowth;
	private Map<String, List<String>> tickerToShareholderEquity;
	private Map<String, List<String>> tickerToShareholderEquityGrowth;

	public BSAnalyzer(Map<String, List<BSInfoBO>> tickerToBSData) {
		this.tickerToBSData = tickerToBSData;
		tickerToNetEquity = new HashMap<String, List<String>>();
		tickerToNetEquityGrowth = new HashMap<String, List<String>>();
		tickerToShareholderEquity = new HashMap<String, List<String>>();
		tickerToShareholderEquityGrowth = new HashMap<String, List<String>>();
		this.extractBSData();
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

			List<String> netEquityList = new ArrayList<String>();
			List<String> netEquityGrowthList = new ArrayList<String>();
			List<String> shareHolderEquityList = new ArrayList<String>();
			List<String> shareHolderEquityGrowthList = new ArrayList<String>();

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

			tickerToNetEquity.put(ticker, netEquityList);
			tickerToNetEquityGrowth.put(ticker, netEquityGrowthList);
			tickerToShareholderEquity.put(ticker, shareHolderEquityList);
			tickerToShareholderEquityGrowth.put(ticker, shareHolderEquityGrowthList);
		}
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

}
