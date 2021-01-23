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
import analyze.excel.data.CFAnalyzer;
import create.excel.bo.CFInfoBO;

public class AnalyzeCFDataTest {

	private static AnalyzeAnnualExcelFile analyzeExcel;
	private static CFAnalyzer cfAnalyzer;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		analyzeExcel = new AnalyzeAnnualExcelFile();
		cfAnalyzer = new CFAnalyzer(analyzeExcel.getTickerToCFInfo(), analyzeExcel.getSetUp().getTickerToTxtFilePath());
	}

	@Test
	public void testExtractCFData() {
		Map<String, List<CFInfoBO>> tickerToCFData = analyzeExcel.getTickerToCFInfo();
		Iterator<Entry<String, List<CFInfoBO>>> iterator = tickerToCFData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<CFInfoBO>> entry = iterator.next();
			System.out.println("------- " + entry.getKey() + " -------");
			List<CFInfoBO> cfInfoList = entry.getValue();
			int yearCounter = 0;
			for (CFInfoBO cfInfo : cfInfoList) {
				System.out.println("This is year counter: " + yearCounter);
				System.out.println("Free Cash Flow: " + cfInfo.getFreeCashFlow());
				System.out.println("Free Cash Flow Growth: " + cfInfo.getFreeCashFlowGrowth());
				System.out.println("Net Cash By Investing: " + cfInfo.getNetCashForInvestingActivities());
				System.out.println("Net Cash By Financing: " + cfInfo.getNetCashForFinancingActivities());
				yearCounter--;
			}
		}
	}

	@Test
	public void testExtractISDataLists() {
		Map.Entry<String, List<String>> freeCashFlowEntry = cfAnalyzer.getTickerToFreeCashFlow().entrySet().iterator()
				.next();
		Map.Entry<String, List<String>> freeCashFlowGrowthEntry = cfAnalyzer.getTickerToFreeCashFlowGrowth().entrySet()
				.iterator().next();
		Map.Entry<String, List<String>> netCashForInvestingEntry = cfAnalyzer.getTickerToNetCashForInvesting()
				.entrySet().iterator().next();
		Map.Entry<String, List<String>> netCashForFinancingEntry = cfAnalyzer.getTickerToNetCashForFinancing()
				.entrySet().iterator().next();

		System.out.println("===========================================================================");

		System.out.println("Ticker: " + freeCashFlowEntry.getKey() + "," + freeCashFlowGrowthEntry.getKey() + ","
				+ netCashForInvestingEntry.getKey() + "," + netCashForFinancingEntry.getKey());
		List<String> freeCashFlowList = freeCashFlowEntry.getValue();
		List<String> freeCashFlowGrowthList = freeCashFlowGrowthEntry.getValue();
		List<String> netCashForInvestingList = netCashForInvestingEntry.getValue();
		List<String> netCashForFinancingList = netCashForFinancingEntry.getValue();
		System.out.println("Free Cash Flow: " + freeCashFlowList.get(0) + "," + freeCashFlowList.get(1) + ","
				+ freeCashFlowList.get(2) + "," + freeCashFlowList.get(3) + "," + freeCashFlowList.get(4));

		System.out
				.println("Free Cash Flow Growth: " + freeCashFlowGrowthList.get(0) + "," + freeCashFlowGrowthList.get(1)
						+ "," + freeCashFlowGrowthList.get(2) + "," + freeCashFlowGrowthList.get(3));

		System.out.println("Net Cash For Investing: " + netCashForInvestingList.get(0) + ","
				+ netCashForInvestingList.get(1) + "," + netCashForInvestingList.get(2) + ","
				+ netCashForInvestingList.get(3) + "," + netCashForInvestingList.get(4));

		System.out.println("Net Cash For Financing: " + netCashForFinancingList.get(0) + ","
				+ netCashForFinancingList.get(1) + "," + netCashForFinancingList.get(2) + ","
				+ netCashForFinancingList.get(3) + "," + netCashForFinancingList.get(4));

		System.out.println("===========================================================================");

	}

}
