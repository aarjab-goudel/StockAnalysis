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

import annual.web.scrape.data.AnnualStockSheetScraper;
import create.excel.bo.StockDataInfoBO;
import javafx.concurrent.Task;

public class StockSheetScraperTest {
	private static AnnualStockSheetScraper stockSheetScraper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("PGR");
		Task<Void> task = new Task<Void>() {
			public Void call() {
				return null;
			}
		};
		stockSheetScraper = new AnnualStockSheetScraper(tickers, task);

	}

	@Test
	public void testTickerToStockSheetData() {
		Map<String, StockDataInfoBO> tickerToStockInfo = stockSheetScraper.getTickerToStockInfo();
		Iterator<Entry<String, StockDataInfoBO>> iterator = tickerToStockInfo.entrySet().iterator();
		System.out.println(
				"----------------------------------- BEGIN TESTING FOR BUILDING STOCK SHEET --------------------");
		while (iterator.hasNext()) {
			Map.Entry<String, StockDataInfoBO> entry = iterator.next();
			String ticker = entry.getKey();
			StockDataInfoBO stockData = entry.getValue();
			System.out.println("====== " + ticker + " =====");
			System.out.println("Sector: " + stockData.getSector());
			System.out.println("Industry: " + stockData.getIndustry());
			System.out.println("Annual Divided Percent: " + stockData.getAnnualDividendPercent());
			System.out.println("Average Dividend Percent: " + stockData.getAverageDividendPercent());
			System.out.println("PE Ratio: " + stockData.getPERatio());
			System.out.println("Average PE Ratio: " + stockData.getPERatio());
			System.out.println("PB Ratio: " + stockData.getPBRatio());
			System.out.println("Average PB Ratio: " + stockData.getAveragePBRatio());
			System.out.println("Forward PE Ratio: " + stockData.getForwardPERatio());
			System.out.println("PEG Ratio: " + stockData.getPegRatio());
			System.out.println("Stock Name: " + stockData.getStockName());
		}
		System.out.println(
				"----------------------------------- END TESTING FOR BUILDING STOCK SHEET --------------------");

	}

}
