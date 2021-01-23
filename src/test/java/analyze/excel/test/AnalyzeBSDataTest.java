/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
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

import analyze.excel.data.AnalyzeAnnualExcelFile;
import analyze.excel.data.BSAnalyzer;
import create.excel.bo.BSInfoBO;

public class AnalyzeBSDataTest {

	private static AnalyzeAnnualExcelFile analyzeExcel;
	private static BSAnalyzer bsAnalyzer;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		analyzeExcel = new AnalyzeAnnualExcelFile();
		bsAnalyzer = new BSAnalyzer(analyzeExcel.getTickerToBSInfo(), analyzeExcel.getSetUp().getTickerToTxtFilePath());
	}

	@Test
	public void testExtractBSData() {
		Map<String, List<BSInfoBO>> tickerToBSData = analyzeExcel.getTickerToBSInfo();
		Iterator<Entry<String, List<BSInfoBO>>> iterator = tickerToBSData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<BSInfoBO>> entry = iterator.next();
			System.out.println("------- " + entry.getKey() + " -------");
			List<BSInfoBO> bsInfoList = entry.getValue();
			int yearCounter = 0;
			for (BSInfoBO bsInfo : bsInfoList) {
				System.out.println("This is year counter: " + yearCounter);
				System.out.println("Net equity: " + bsInfo.getNetEquity());
				System.out.println("Net equity growth: " + bsInfo.getNetEquityGrowth());
				System.out.println("Total Shareholders Equity: " + bsInfo.getShareHoldersEquity());
				System.out.println("Total Shareholders Equity Growth: " + bsInfo.getShareHoldersEquityGrowth());
				yearCounter--;
			}
		}
	}

	@Test
	public void testExtractBSDataLists() {
		Map.Entry<String, List<String>> netEquityEntry = bsAnalyzer.getTickerToNetEquity().entrySet().iterator().next();
		Map.Entry<String, List<String>> netEquityGrowthEntry = bsAnalyzer.getTickerToNetEquityGrowth().entrySet()
				.iterator().next();
		Map.Entry<String, List<String>> shareHolderEquityEntry = bsAnalyzer.getTickerToShareholderEquity().entrySet()
				.iterator().next();
		Map.Entry<String, List<String>> shareHolderEquityGrowthEntry = bsAnalyzer.getTickerToShareholderEquityGrowth()
				.entrySet().iterator().next();

		System.out.println("===========================================================================");

		System.out.println("Ticker: " + netEquityEntry.getKey() + "," + netEquityGrowthEntry.getKey() + ","
				+ shareHolderEquityEntry.getKey() + "," + shareHolderEquityGrowthEntry.getKey());
		List<String> netEquityList = netEquityEntry.getValue();
		List<String> netEquityGrowthList = netEquityGrowthEntry.getValue();
		List<String> shareHolderEquityList = shareHolderEquityEntry.getValue();
		List<String> shareHolderEquityGrowthList = shareHolderEquityGrowthEntry.getValue();
		System.out.println("Net Equity: " + netEquityList.get(0) + "," + netEquityList.get(1) + ","
				+ netEquityList.get(2) + "," + netEquityList.get(3) + "," + netEquityList.get(4));

		System.out.println("Net Equity Growth: " + netEquityGrowthList.get(0) + "," + netEquityGrowthList.get(1) + ","
				+ netEquityGrowthList.get(2) + "," + netEquityGrowthList.get(3));

		System.out.println("ShareholderEquity: " + shareHolderEquityList.get(0) + "," + shareHolderEquityList.get(1)
				+ "," + shareHolderEquityList.get(2) + "," + shareHolderEquityList.get(3) + ","
				+ shareHolderEquityList.get(4));

		System.out.println("ShareholderEquityGrowth: " + shareHolderEquityGrowthList.get(0) + ","
				+ shareHolderEquityGrowthList.get(1) + "," + shareHolderEquityGrowthList.get(2) + ","
				+ shareHolderEquityGrowthList.get(3));

		System.out.println("===========================================================================");

	}

}
