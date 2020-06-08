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

import create.excel.bo.CFInfoBO;
import web.scrape.data.CashSheetScraper;

public class CashSheetScraperTest {

	private static CashSheetScraper cashSheetScrapper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("AAPL");
		tickers.add("DIS");
		tickers.add("F");
		tickers.add("T");
		tickers.add("GM");

		cashSheetScrapper = new CashSheetScraper(tickers);

	}

	@Test
	public void testTickerToCashSheet() {
		Map<String, List<CFInfoBO>> tickerToCFInfo = cashSheetScrapper.getTickerToCashSheet();
		Iterator<Entry<String, List<CFInfoBO>>> iterator = tickerToCFInfo.entrySet().iterator();
		System.out.println(
				"----------------------------------- BEGIN TESTING FOR BUILDING CASH SHEET --------------------");
		while (iterator.hasNext()) {
			Map.Entry<String, List<CFInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<CFInfoBO> list = entry.getValue();
			System.out.println("====== " + ticker + " =====");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Year " + i);
				System.out.println("Net cash by operating activities: " + list.get(i).getNetCashByOperatingActivites());
				System.out.println(
						"Net cash for investing activities : " + list.get(i).getNetCashForInvestingActivities());
				System.out.println(
						"Net cash for financing activities: " + list.get(i).getNetCashForFinancingActivities());
				System.out.println("Capital Expenditure: " + list.get(i).getCapitalExpenditure());

			}
		}
		System.out.println(
				"----------------------------------- END TESTING FOR BUILDING BALANCE SHEET --------------------");

	}

}
