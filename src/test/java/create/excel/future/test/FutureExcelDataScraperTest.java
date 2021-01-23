/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.future.test;

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

import create.excel.future.FutureExcelDataScraper;
import create.excel.future.FutureInfoBO;

public class FutureExcelDataScraperTest {

	private static FutureExcelDataScraper scraper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("MSFT");

		scraper = new FutureExcelDataScraper(tickers, null);

	}

	@Test
	public void testFutureExcelDataScraper() {
		Map<String, FutureInfoBO> currentYearToFutureDataMap = scraper.getTickerToCurrentYearInfo();
		Map<String, FutureInfoBO> nextYearToFutureDataMap = scraper.getTickerToNextYearInfo();
		Iterator<Entry<String, FutureInfoBO>> currentYearIterator = currentYearToFutureDataMap.entrySet().iterator();
		Iterator<Entry<String, FutureInfoBO>> nextYearIterator = nextYearToFutureDataMap.entrySet().iterator();
		System.out.println("===== BEGIN TEST FOR CURRENT YEAR INFO ========");
		while (currentYearIterator.hasNext()) {
			System.out.println("--------------------------------------------");
			Map.Entry<String, FutureInfoBO> entry = currentYearIterator.next();
			String ticker = entry.getKey();
			FutureInfoBO futureInfoBO = entry.getValue();
			System.out.println("Ticker: " + ticker);
			System.out.println("Date: " + futureInfoBO.getDate());
			System.out.println("Current Year EPS: " + futureInfoBO.getEps());
			System.out.println("Current Year Revenue: " + futureInfoBO.getRevenue());
			System.out.println("--------------------------------------------");
		}
		System.out.println("===== END TEST FOR CURRENT YEAR INFO ========");
		System.out.println("===== BEGIN TEST FOR NEXT YEAR INFO ========");
		while (nextYearIterator.hasNext()) {
			System.out.println("--------------------------------------------");
			Map.Entry<String, FutureInfoBO> entry = nextYearIterator.next();
			String ticker = entry.getKey();
			FutureInfoBO futureInfoBO = entry.getValue();
			System.out.println("Ticker: " + ticker);
			System.out.println("Date: " + futureInfoBO.getDate());
			System.out.println("Current Year EPS: " + futureInfoBO.getEps());
			System.out.println("Current Year Revenue: " + futureInfoBO.getRevenue());
			System.out.println("--------------------------------------------");
		}
		System.out.println("===== END TEST FOR NEXT YEAR INFO ========");
	}

}
