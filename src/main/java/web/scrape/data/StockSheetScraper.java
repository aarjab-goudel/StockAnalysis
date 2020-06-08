package web.scrape.data;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import create.excel.bo.StockDataInfoBO;

public class StockSheetScraper extends FinancialScrapper {
	private static final String MORNING_STAR_STOCK_SHEET_URL = "http://financials.morningstar.com/valuation/price-ratio.html?t=";
	private static final String SEEKING_ALPHA_STOCK_SHEET_URL = "https://seekingalpha.com/symbol/";
	private Map<String, StockDataInfoBO> tickerToStockInfo;

	public StockSheetScraper(List<String> tickersToBuild) {
		super(tickersToBuild);
		tickerToStockInfo = new HashMap<String, StockDataInfoBO>();
		this.buildTickerToStockInfoMap();
	}

	public void buildTickerToStockInfoMap() {
		for (String ticker : this.getTickersToBuild()) {
			StockDataInfoBO stockInfo = this.readStockSheetUrl(ticker);
			tickerToStockInfo.put(ticker, stockInfo);
		}
	}

	public StockDataInfoBO readStockSheetUrl(String ticker) {
		StockDataInfoBO stockBO = new StockDataInfoBO(0, ticker);
		this.readSeekingAlphaStockSheetUrl(stockBO, ticker);
		this.readMorningStarStockSheetUrl(stockBO, ticker);
		return stockBO;
	}

	public void readSeekingAlphaStockSheetUrl(StockDataInfoBO stockBO, String ticker) {
		try {
			String searchUrl = SEEKING_ALPHA_STOCK_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			searchUrl = searchUrl + "/overview";
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getPage(searchUrl);
			webClient.waitForBackgroundJavaScript(10000);

			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			String beta = "ERROR";
			List<?> betaList = (List<?>) page.getByXPath("//*[@id=\"trading60mBeta\"]");
			if (!betaList.isEmpty()) {
				HtmlTableDataCell betaTableData = (HtmlTableDataCell) betaList.get(0);
				DomText betaText = (DomText) betaTableData.getChildNodes().get(0);
				beta = betaText.asText();
			}
			stockBO.setBeta(beta);

			String marketCap = "ERROR";
			List<?> marketCapList = (List<?>) page.getByXPath("//*[@id=\"valuationMarketCap\"]");
			if (!marketCapList.isEmpty()) {
				HtmlTableDataCell marketCapTableData = (HtmlTableDataCell) marketCapList.get(0);
				DomText marketCapText = (DomText) marketCapTableData.getChildNodes().get(0);
				marketCap = marketCapText.asText();
				stockBO.setMarketCap(marketCap);
			}
			stockBO.setMarketCap(marketCap);

			String trimmedStockName = "ERROR";
			List<?> stockNameList = (List<?>) page.getByXPath("//*[@id=\"main_content\"]/div[1]/div[1]/h1");
			if (!stockNameList.isEmpty()) {
				HtmlHeading1 stockNameData = (HtmlHeading1) stockNameList.get(0);
				DomText stockNameText = (DomText) stockNameData.getChildNodes().get(0);
				String stockName = stockNameText.asText();
				trimmedStockName = stockName.substring(stockName.indexOf("-") + 1);
				trimmedStockName = trimmedStockName.trim();
			}
			stockBO.setStockName(trimmedStockName);

			String sector = "ERROR";
			List<?> sectorList = (List<?>) page
					.getByXPath("//*[@id=\"key-data-company-profile\"]/div/table[1]/tbody/tr[1]/td[2]");
			if (!sectorList.isEmpty()) {
				HtmlTableDataCell sectorTableData = (HtmlTableDataCell) sectorList.get(0);
				DomText sectorText = (DomText) sectorTableData.getChildNodes().get(0);
				sector = sectorText.asText();
			}
			stockBO.setSector(sector);

			String industry = "ERROR";
			List<?> industryList = (List<?>) page
					.getByXPath("//*[@id=\"key-data-company-profile\"]/div/table[1]/tbody/tr[2]/td[2]");
			if (!industryList.isEmpty()) {
				HtmlTableDataCell industryTableData = (HtmlTableDataCell) industryList.get(0);
				DomText industryText = (DomText) industryTableData.getChildNodes().get(0);
				industry = industryText.asText();
			}
			stockBO.setIndustry(industry);

			webClient.close();

		} catch (Exception e) {

		}
	}

	public void readMorningStarStockSheetUrl(StockDataInfoBO stockBO, String ticker) {
		try {
			System.out.println("Ticker: " + ticker + " Module: StocksheetScraper");
			String searchUrl = MORNING_STAR_STOCK_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getPage(searchUrl);
			webClient.waitForBackgroundJavaScript(10000);

			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

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

		} catch (Exception e) {

		}

	}

	public Map<String, StockDataInfoBO> getTickerToStockInfo() {
		return tickerToStockInfo;
	}

}
