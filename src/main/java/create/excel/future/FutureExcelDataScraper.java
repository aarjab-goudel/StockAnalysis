/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.future;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import javafx.concurrent.Task;

public class FutureExcelDataScraper {

	private static final String FUTURE_DATA_URL = "https://finance.yahoo.com/quote/";
	private List<String> companies;
	private Map<String, FutureInfoBO> tickerToCurrentYearInfo;
	private Map<String, FutureInfoBO> tickerToNextYearInfo;
	private Task<Void> futureExcelTask;

	public FutureExcelDataScraper(List<String> companies, Task<Void> task) {
		this.companies = companies;
		this.futureExcelTask = task;
		tickerToCurrentYearInfo = new HashMap<String, FutureInfoBO>();
		tickerToNextYearInfo = new HashMap<String, FutureInfoBO>();
		this.readAllFutureExcelDataURLs();
	}

	public void readAllFutureExcelDataURLs() {
		for (String company : companies) {
			if ((this.getFutureExcelTask() != null && !this.getFutureExcelTask().isCancelled())
					|| (this.getFutureExcelTask() == null)) {
				this.readFutureExcelDataURL(company);
			}
		}
	}

	public String createFutureUrlForTicker(String ticker) {
		return FUTURE_DATA_URL + ticker + "/analysis?";
	}

	public void readFutureExcelDataURL(String ticker) {
		try {
			String searchUrl = this.createFutureUrlForTicker(ticker);
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.setJavaScriptTimeout(60000);
			webClient.getPage(searchUrl);
			webClient.waitForBackgroundJavaScript(10000);

			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			assertNotNull("Page is null! ", page);

			List<?> currentYearDate = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[1]/thead/tr/th[4]/span");
			HtmlSpan currentYearDateSpan = (HtmlSpan) currentYearDate.get(0);
			String currentYearString = currentYearDateSpan.getChildNodes().get(2).asText();
			String currentYearText = currentYearDateSpan.getChildNodes().get(0).getChildNodes().get(0).asText();
			String currentYear = currentYearText + currentYearString;
			List<?> currentYearEPS = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[1]/tbody/tr[2]/td[4]/span");
			HtmlSpan currentYearEPSSpan = (HtmlSpan) currentYearEPS.get(0);
			String currentYearEPSString = currentYearEPSSpan.getChildNodes().get(0).asText();
			List<?> currentYearRevenue = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[2]/tbody/tr[2]/td[4]/span");
			HtmlSpan currentYearRevenueSpan = (HtmlSpan) currentYearRevenue.get(0);
			String currentYearRevenueString = currentYearRevenueSpan.getChildNodes().get(0).asText();
			FutureInfoBO currentYearInfo = new FutureInfoBO();
			currentYearInfo.setTicker(ticker);
			currentYearInfo.setDate(currentYear);
			currentYearInfo.setEps(currentYearEPSString);
			currentYearInfo.setRevenue(currentYearRevenueString);
			tickerToCurrentYearInfo.put(ticker, currentYearInfo);

			List<?> nextYearDate = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[1]/thead/tr/th[5]/span");
			HtmlSpan nextYearDateSpan = (HtmlSpan) nextYearDate.get(0);
			String nextYearString = nextYearDateSpan.getChildNodes().get(2).asText();
			String nextYearText = nextYearDateSpan.getChildNodes().get(0).getChildNodes().get(0).asText();
			String nextYear = nextYearText + nextYearString;
			List<?> nextYearEPS = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[1]/tbody/tr[2]/td[5]/span");
			HtmlSpan nextYearEPSSpan = (HtmlSpan) nextYearEPS.get(0);
			String nextYearEPSString = nextYearEPSSpan.getChildNodes().get(0).asText();
			List<?> nextYearRevenue = (List<?>) page
					.getByXPath("//*[@id=\"Col1-0-AnalystLeafPage-Proxy\"]/section/table[2]/tbody/tr[2]/td[5]/span");
			HtmlSpan nextYearRevenueSpan = (HtmlSpan) nextYearRevenue.get(0);
			String nextYearRevenueString = nextYearRevenueSpan.getChildNodes().get(0).asText();
			FutureInfoBO nextYearInfo = new FutureInfoBO();
			nextYearInfo.setTicker(ticker);
			nextYearInfo.setDate(nextYear);
			nextYearInfo.setEps(nextYearEPSString);
			nextYearInfo.setRevenue(nextYearRevenueString);
			tickerToNextYearInfo.put(ticker, nextYearInfo);

			webClient.close();
			return;
		} catch (Exception e) {
			FutureInfoBO errorInfo = new FutureInfoBO();
			errorInfo.setDate("ERROR");
			errorInfo.setEps("ERROR");
			errorInfo.setRevenue("ERROR");
			errorInfo.setEps("ERROR");
			tickerToNextYearInfo.put(ticker, errorInfo);
			tickerToCurrentYearInfo.put(ticker, errorInfo);
			return;
		}
	}

	public List<String> getCompanies() {
		return companies;
	}

	public Map<String, FutureInfoBO> getTickerToCurrentYearInfo() {
		return tickerToCurrentYearInfo;
	}

	public Map<String, FutureInfoBO> getTickerToNextYearInfo() {
		return tickerToNextYearInfo;
	}

	public Task<Void> getFutureExcelTask() {
		return futureExcelTask;
	}

}
