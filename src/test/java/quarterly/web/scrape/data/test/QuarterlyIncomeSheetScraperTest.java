/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package quarterly.web.scrape.data.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.bo.ISInfoBO;
import quarterly.web.scrape.data.QuarterlyIncomeSheetScraper;

public class QuarterlyIncomeSheetScraperTest {
	private static QuarterlyIncomeSheetScraper incomeSheetScrapper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("AAPL");

		incomeSheetScrapper = new QuarterlyIncomeSheetScraper(tickers, null);

	}

	@Test
	public void testTickerToIncomeSheet() {
		Map<String, List<ISInfoBO>> tickerToISInfo = incomeSheetScrapper.getTickerToIncomeSheet();
		Iterator<Entry<String, List<ISInfoBO>>> iterator = tickerToISInfo.entrySet().iterator();
		System.out.println(
				"----------------------------------- BEGIN TESTING FOR BUILDING INCOME SHEET --------------------");
		while (iterator.hasNext()) {
			Map.Entry<String, List<ISInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<ISInfoBO> list = entry.getValue();
			System.out.println("====== " + ticker + " =====");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Year " + i);
				System.out.println("IS Date: " + list.get(i).getIsDate());
				System.out.println("Revenue: " + list.get(i).getRevenue());
				System.out.println("Cost of Revenue: " + list.get(i).getCostOfRevenue());
				System.out.println("Gross Profit: " + list.get(i).getGrossProfit());
				System.out.println("Net Income: " + list.get(i).getNetIncome());
				System.out.println("EPS: " + list.get(i).getEps());

			}
		}
		System.out.println(
				"----------------------------------- END TESTING FOR BUILDING BALANCE SHEET --------------------");

	}

}
