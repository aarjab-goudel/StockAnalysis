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
import create.excel.bo.RatioInfoBO;

public class AnalyzeRatioDataTest {

	private static AnalyzeAnnualExcelFile analyzeExcel;

	@BeforeClass
	public static void initializeFields() throws IOException, JSONException, ParseException {
		analyzeExcel = new AnalyzeAnnualExcelFile();
	}

	@Test
	public void testExtractRatioData() {
		Map<String, List<RatioInfoBO>> tickerToRatioData = analyzeExcel.getTickerToRatioInfo();
		Iterator<Entry<String, List<RatioInfoBO>>> iterator = tickerToRatioData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<RatioInfoBO>> entry = iterator.next();
			System.out.println("------- " + entry.getKey() + " -------");
			List<RatioInfoBO> ratioInfoList = entry.getValue();
			int yearCounter = 0;
			for (RatioInfoBO ratioInfo : ratioInfoList) {
				System.out.println("This is year counter: " + yearCounter);
				System.out.println("Current Ratio: " + ratioInfo.getCurrentRatio());
				System.out.println("Current Ratio Growth: " + ratioInfo.getCurrentRatioGrowth());
				System.out.println("Debt Equity Ratio: " + ratioInfo.getDebtToEquityRatio());
				System.out.println("Debt Equity Growth Ratio: " + ratioInfo.getDebtToEquityRatioGrowth());
				System.out.println("EPS: " + ratioInfo.getEarningsPerShare());
				System.out.println("EPS Growth: " + ratioInfo.getEarningsPerShareGrowth());
				yearCounter--;
			}
		}
	}

}
