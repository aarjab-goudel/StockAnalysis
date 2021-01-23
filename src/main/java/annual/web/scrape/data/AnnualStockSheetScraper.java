/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package annual.web.scrape.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import create.excel.bo.StockDataInfoBO;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;

public class AnnualStockSheetScraper extends FinancialScrapper {
	private static final String MORNING_STAR_STOCK_SHEET_URL = "http://financials.morningstar.com/valuation/price-ratio.html?t=";
	private static final String YAHOO_FINANCE_STOCK_SHEET_URL = "https://finance.yahoo.com/quote/";
	private Map<String, StockDataInfoBO> tickerToStockInfo;

	public AnnualStockSheetScraper(List<String> tickersToBuild, Task<Void> task) throws IOException {
		super(tickersToBuild, task);
		tickerToStockInfo = new HashMap<String, StockDataInfoBO>();
		this.buildTickerToStockInfoMap();
	}

	public void buildTickerToStockInfoMap() throws IOException {
		for (String ticker : this.getTickersToBuild()) {
			if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
				StockDataInfoBO stockInfo = this.readStockSheetUrl(ticker);
				tickerToStockInfo.put(ticker, stockInfo);
			}
		}
	}

	public StockDataInfoBO readStockSheetUrl(String ticker) throws IOException {
		StockDataInfoBO stockBO = new StockDataInfoBO(0, ticker);
		this.readYahooFinanceUrl(stockBO, ticker);
		this.readMorningStarStockSheetUrl(stockBO, ticker);
		return stockBO;
	}

	private String createYahooFinanceUrl(String ticker) {
		return YAHOO_FINANCE_STOCK_SHEET_URL + ticker + "/profile";
	}

	public StockDataInfoBO readYahooFinanceUrl(StockDataInfoBO stockBO, String ticker) throws IOException {
		try {
			ScrapperLogger.log("Yahoo Finance: " + ticker + "=================");
			String searchUrl = this.createYahooFinanceUrl(ticker);
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			ScrapperLogger.log("Before getPage() in Yahoo Finance: " + searchUrl + "=================");
			HtmlPage page = webClient.getPage(searchUrl);
			ScrapperLogger.log("After getPage() in Yahoo Finance: " + searchUrl + "=================");

			List<?> stockNameList = (List<?>) page.getByXPath(
					"/html/body/div[1]/div/div/div[1]/div/div[3]/div[1]/div/div[1]/div/div/section/div[1]/div/h3");
			HtmlHeading3 stockName = (HtmlHeading3) stockNameList.get(0);
			String stockNameString = stockName.getChildNodes().get(0).asText();
			stockBO.setStockName(stockNameString);

			List<?> stockSectorList = (List<?>) page.getByXPath(
					"/html/body/div[1]/div/div/div[1]/div/div[3]/div[1]/div/div[1]/div/div/section/div[1]/div/div/p[2]/span[2]");
			HtmlSpan stockSector = (HtmlSpan) stockSectorList.get(0);
			String stockSectorString = stockSector.getChildNodes().get(0).asText();
			stockBO.setSector(stockSectorString);

			List<?> stockIndustryList = (List<?>) page.getByXPath(
					"/html/body/div[1]/div/div/div[1]/div/div[3]/div[1]/div/div[1]/div/div/section/div[1]/div/div/p[2]/span[4]");
			HtmlSpan stockIndustry = (HtmlSpan) stockIndustryList.get(0);
			String stockIndustryString = stockIndustry.getChildNodes().get(0).asText();
			stockBO.setIndustry(stockIndustryString);

			webClient.close();
			return stockBO;
		} catch (Exception e) {
			ScrapperLogger.log("Exception in read Yahoo Finance Url for: " + this.createYahooFinanceUrl(ticker));
			stockBO.setStockName("ERROR");
			stockBO.setSector("ERROR");
			stockBO.setIndustry("ERROR");
			return stockBO;
		}
	}

	public StockDataInfoBO readMorningStarStockSheetUrl(StockDataInfoBO stockBO, String ticker)
			throws UnsupportedEncodingException, IOException {
		try {
			ScrapperLogger.log("MorningStar Stock Sheet: " + ticker + "=================");
			String searchUrl = MORNING_STAR_STOCK_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			ScrapperLogger.log("Before 1.getPage() in MorningStar Stock Sheet: " + searchUrl + "=================");
			webClient.getPage(searchUrl);
			ScrapperLogger.log("After 1.getPage() in MorningStar Stock Sheet: " + searchUrl + "=================");

			webClient.waitForBackgroundJavaScript(10000);

			ScrapperLogger.log("Before 2.getPage() in MorningStar Stock Sheet: " + searchUrl + "=================");
			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
			ScrapperLogger.log("After 2.getPage() in MorningStar Stock Sheet: " + searchUrl + "=================");

			List<?> peRatio = (List<?>) page.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[2]/td[1]");

			List<?> fiveYearAveragePERatio = (List<?>) page
					.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[2]/td[4]");

			List<?> pbRatio = (List<?>) page.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[4]/td[1]");

			List<?> fiveYearAveragePBRatio = (List<?>) page
					.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[4]/td[4]");

			List<?> dividendYield = (List<?>) page.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[10]/td[1]");

			List<?> fiveYearAverageDividend = (List<?>) page
					.getByXPath("//*[@id=\"currentValuationTable\"]/tbody/tr[10]/td[4]");

			List<?> forwardPERatio = (List<?>) page.getByXPath("//*[@id=\"forwardValuationTable\"]/tbody/tr[2]/td[2]");

			List<?> pegRatio = (List<?>) page.getByXPath("//*[@id=\"forwardValuationTable\"]/tbody/tr[4]/td[2]");

			HtmlTableDataCell peRatioDataCell = (HtmlTableDataCell) peRatio.get(0);

			HtmlTableDataCell averagePERatioDataCell = (HtmlTableDataCell) fiveYearAveragePERatio.get(0);

			HtmlTableDataCell pbRatioDataCell = (HtmlTableDataCell) pbRatio.get(0);

			HtmlTableDataCell averagePbRatioDataCell = (HtmlTableDataCell) fiveYearAveragePBRatio.get(0);

			HtmlTableDataCell dividendYieldDataCell = (HtmlTableDataCell) dividendYield.get(0);

			HtmlTableDataCell averageDividendCell = (HtmlTableDataCell) fiveYearAverageDividend.get(0);

			HtmlTableDataCell forwardPERatioCell = (HtmlTableDataCell) forwardPERatio.get(0);

			HtmlTableDataCell pegRatioCell = (HtmlTableDataCell) pegRatio.get(0);

			String peRatioString = peRatioDataCell.asText();
			stockBO.setPERatio(peRatioString);
			String averagePERatioString = averagePERatioDataCell.asText();
			stockBO.setAveragePERatio(averagePERatioString);

			String pbRatioString = pbRatioDataCell.asText();
			stockBO.setPBRatio(pbRatioString);
			String averagePbRatioString = averagePbRatioDataCell.asText();
			stockBO.setAveragePBRatio(averagePbRatioString);

			String dividendString = dividendYieldDataCell.asText();
			stockBO.setAnnualDividendPercent(dividendString);
			String averageDividendString = averageDividendCell.asText();
			stockBO.setAverageDividendPercent(averageDividendString);

			String forwardPERatioString = forwardPERatioCell.asText();
			stockBO.setForwardPERatio(forwardPERatioString);
			String pegRatioString = pegRatioCell.asText();
			stockBO.setPegRatio(pegRatioString);

			webClient.close();
			return stockBO;
		} catch (Exception e) {
			ScrapperLogger.log("Exception in read MorningStar Stock Sheet for: " + MORNING_STAR_STOCK_SHEET_URL
					+ URLEncoder.encode(ticker, "UTF-8"));
			ScrapperLogger.log("Exception Message is: " + e.getMessage());
			ScrapperLogger.log("Localized Exception Message is: " + e.getLocalizedMessage());
			stockBO.setPERatio("ERROR");
			stockBO.setAveragePERatio("ERROR");
			stockBO.setPBRatio("ERROR");
			stockBO.setAveragePBRatio("ERROR");
			stockBO.setAnnualDividendPercent("ERROR");
			stockBO.setAverageDividendPercent("ERROR");
			stockBO.setForwardPERatio("ERROR");
			stockBO.setPegRatio("ERROR");
			return stockBO;
		}

	}

	public Map<String, StockDataInfoBO> getTickerToStockInfo() {
		return tickerToStockInfo;
	}

}
