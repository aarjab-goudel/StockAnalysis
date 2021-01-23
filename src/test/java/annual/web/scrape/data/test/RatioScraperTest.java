/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package annual.web.scrape.data.test;

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

import annual.web.scrape.data.AnnualRatioScraper;
import create.excel.bo.RatioInfoBO;
import javafx.concurrent.Task;

public class RatioScraperTest {

	private static AnnualRatioScraper ratioScrapper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("AAPL");
		tickers.add("DIS");
		tickers.add("F");
		tickers.add("T");
		tickers.add("GM");
		Task<Void> task = new Task<Void>() {
			public Void call() {
				return null;
			}
		};
		ratioScrapper = new AnnualRatioScraper(tickers, task);

	}

	@Test
	public void testTickerToRatio() {
		Map<String, List<RatioInfoBO>> tickerToRatioInfo = ratioScrapper.getTickerToRatio();
		Iterator<Entry<String, List<RatioInfoBO>>> iterator = tickerToRatioInfo.entrySet().iterator();
		System.out.println(
				"----------------------------------- BEGIN TESTING FOR BUILDING RATIO SHEET --------------------");
		while (iterator.hasNext()) {
			Map.Entry<String, List<RatioInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<RatioInfoBO> list = entry.getValue();
			System.out.println("====== " + ticker + " =====");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Year " + i);
				System.out.println("Current Ratio: " + list.get(i).getCurrentRatio());
				System.out.println("Debt to Equity Ratio: " + list.get(i).getDebtToEquityRatio());
				System.out.println("Date: " + list.get(i).getRatioDate());

			}
		}
		System.out.println(
				"----------------------------------- END TESTING FOR BUILDING RATIO SHEET --------------------");

	}

}
