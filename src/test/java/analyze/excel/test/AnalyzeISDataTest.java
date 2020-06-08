package analyze.excel.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import analyze.excel.data.AnalyzeExcelFile;
import analyze.excel.data.ISAnalyzer;
import create.excel.bo.ISInfoBO;

public class AnalyzeISDataTest {

	private static AnalyzeExcelFile analyzeExcel;
	private static ISAnalyzer isAnalyzer;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		analyzeExcel = new AnalyzeExcelFile();
		isAnalyzer = new ISAnalyzer(analyzeExcel.getTickerToISInfo());
	}

	@Test
	public void testExtractISData() {
		Map<String, List<ISInfoBO>> tickerToISData = analyzeExcel.getTickerToISInfo();
		Iterator<Entry<String, List<ISInfoBO>>> iterator = tickerToISData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<ISInfoBO>> entry = iterator.next();
			System.out.println("------- " + entry.getKey() + " -------");
			List<ISInfoBO> isInfoList = entry.getValue();
			int yearCounter = 0;
			for (ISInfoBO isInfo : isInfoList) {
				System.out.println("This is year counter: " + yearCounter);
				System.out.println("Revenue: " + isInfo.getRevenue());
				System.out.println("Revenue Growth: " + isInfo.getRevenueGrowth());
				System.out.println("Gross Profit: " + isInfo.getGrossProfit());
				System.out.println("Gross Profit Growth: " + isInfo.getGrossProfitGrowth());
				System.out.println("Net Income: " + isInfo.getNetIncome());
				System.out.println("Net Income Growth: " + isInfo.getNetIncomeGrowth());
				yearCounter--;
			}
		}
	}

	@Test
	public void testExtractISDataLists() {
		Map.Entry<String, List<String>> revenueEntry = isAnalyzer.getTickerToRevenue().entrySet().iterator().next();
		Map.Entry<String, List<String>> revenueGrowthEntry = isAnalyzer.getTickerToRevenueGrowth().entrySet().iterator()
				.next();
		Map.Entry<String, List<String>> grossProfitEntry = isAnalyzer.getTickerToGrossProfit().entrySet().iterator()
				.next();
		Map.Entry<String, List<String>> grossProfitGrowthEntry = isAnalyzer.getTickerToGrossProfitGrowth().entrySet()
				.iterator().next();
		Map.Entry<String, List<String>> netIncomeEntry = isAnalyzer.getTickerToNetIncome().entrySet().iterator().next();
		Map.Entry<String, List<String>> netIncomeGrowthEntry = isAnalyzer.getTickerToNetIncomeGrowth().entrySet()
				.iterator().next();

		System.out.println("===========================================================================");

		System.out.println("Ticker: " + revenueEntry.getKey() + "," + revenueGrowthEntry.getKey() + ","
				+ grossProfitEntry.getKey() + "," + grossProfitGrowthEntry.getKey() + "," + netIncomeEntry.getKey()
				+ "," + netIncomeGrowthEntry.getKey());
		List<String> revenueList = revenueEntry.getValue();
		List<String> revenueGrowthList = revenueGrowthEntry.getValue();
		List<String> grossProfitList = grossProfitEntry.getValue();
		List<String> grossProfitGrowthList = grossProfitGrowthEntry.getValue();
		List<String> netIncomeList = netIncomeEntry.getValue();
		List<String> netIncomeGrowthList = netIncomeGrowthEntry.getValue();
		System.out.println("Revenue: " + revenueList.get(0) + "," + revenueList.get(1) + "," + revenueList.get(2) + ","
				+ revenueList.get(3) + "," + revenueList.get(4));

		System.out.println("Revenue Growth: " + revenueGrowthList.get(0) + "," + revenueGrowthList.get(1) + ","
				+ revenueGrowthList.get(2) + "," + revenueGrowthList.get(3));

		System.out.println("Gross Profit: " + grossProfitList.get(0) + "," + grossProfitList.get(1) + ","
				+ grossProfitList.get(2) + "," + grossProfitList.get(3) + "," + grossProfitList.get(4));

		System.out.println("Gross Profit Growth: " + grossProfitGrowthList.get(0) + "," + grossProfitGrowthList.get(1)
				+ "," + grossProfitGrowthList.get(2) + "," + grossProfitGrowthList.get(3));

		System.out.println("Net Income: " + netIncomeList.get(0) + "," + netIncomeList.get(1) + ","
				+ netIncomeList.get(2) + "," + netIncomeList.get(3) + "," + netIncomeList.get(4));

		System.out.println("Net Income Growth: " + netIncomeGrowthList.get(0) + "," + netIncomeGrowthList.get(1) + ","
				+ netIncomeGrowthList.get(2) + "," + netIncomeGrowthList.get(3));

		System.out.println("===========================================================================");

	}

}
