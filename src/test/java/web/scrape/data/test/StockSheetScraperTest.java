package web.scrape.data.test;

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

import create.excel.bo.StockDataInfoBO;
import web.scrape.data.StockSheetScraper;

public class StockSheetScraperTest {
	private static StockSheetScraper stockSheetScraper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("PGR");
//		tickers.add("DIS");
//		tickers.add("F");
//		tickers.add("T");
//		tickers.add("GM");
		stockSheetScraper = new StockSheetScraper(tickers);

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
			System.out.println("Beta: " + stockData.getBeta());
			System.out.println("Sector: " + stockData.getSector());
			System.out.println("Industry: " + stockData.getIndustry());
			System.out.println("Market Cap: " + stockData.getMarketCap());
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
