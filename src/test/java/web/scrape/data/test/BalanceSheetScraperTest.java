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

import create.excel.bo.BSInfoBO;
import web.scrape.data.BalanceSheetScraper;

public class BalanceSheetScraperTest {

	private static BalanceSheetScraper balanceSheetScrapper;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		List<String> tickers = new ArrayList<String>();
		tickers.add("AAPL");
		tickers.add("DIS");
		tickers.add("F");
		tickers.add("T");
		tickers.add("GM");

		balanceSheetScrapper = new BalanceSheetScraper(tickers);

	}

	@Test
	public void testTickerToBalanceSheet() {
		Map<String, List<BSInfoBO>> tickerToBSInfo = balanceSheetScrapper.getTickerToBalanceSheet();
		Iterator<Entry<String, List<BSInfoBO>>> iterator = tickerToBSInfo.entrySet().iterator();
		System.out.println(
				"----------------------------------- BEGIN TESTING FOR BUILDING BALANCE SHEET --------------------");
		while (iterator.hasNext()) {
			Map.Entry<String, List<BSInfoBO>> entry = iterator.next();
			String ticker = entry.getKey();
			List<BSInfoBO> list = entry.getValue();
			System.out.println("====== " + ticker + " =====");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Year " + i);
				System.out.println("Cash and cash equivalents: " + list.get(i).getCashAndCashEquivalent());
				System.out.println("Short term investments: " + list.get(i).getShortTermInvestments());
				System.out.println("Recievables: " + list.get(i).getReceivables());
				System.out.println("Long term investments: " + list.get(i).getLongTermInvestments());
				System.out.println("Intangible Assets: " + list.get(i).getTotalIntangibleAssets());
				System.out.println("Short term debt: " + list.get(i).getShortTermDebt());
				System.out.println("Accounts Payable: " + list.get(i).getAccountsPayable());
				System.out.println("Long term debt: " + list.get(i).getLongTermDebt());
				System.out.println("Shareholders equity: " + list.get(i).getShareHoldersEquity());

			}
		}
		System.out.println(
				"----------------------------------- END TESTING FOR BUILDING BALANCE SHEET --------------------");

	}

}
