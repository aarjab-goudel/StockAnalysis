package web.scrape.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import create.excel.bo.ISInfoBO;
import create.excel.enums.CommonSheetConstants;

public class IncomeSheetScraper extends FinancialScrapper {
	private static final String INCOME_SHEET_URL = "http://financials.morningstar.com/income-statement/is.html?t=";
	private Map<String, List<ISInfoBO>> tickerToIncomeSheet;

	public IncomeSheetScraper(List<String> tickersToBuild) {
		super(tickersToBuild);
		this.tickerToIncomeSheet = this.buildTickerToIncomeSheetMap();
	}

	public Map<String, List<ISInfoBO>> buildTickerToIncomeSheetMap() {
		Map<String, List<ISInfoBO>> tickerToISInfoBOMap = new HashMap<String, List<ISInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			List<ISInfoBO> isInfoBOList = this.readIncomeSheetURL(ticker);

			assertEquals("The size in income sheet scraper for " + ticker + " is " + isInfoBOList.size(),
					isInfoBOList.size(), 5);
			tickerToISInfoBOMap.put(ticker, isInfoBOList);
		}
		return tickerToISInfoBOMap;
	}

	public List<ISInfoBO> readIncomeSheetURL(String ticker) {

		List<ISInfoBO> isInfoList = new ArrayList<ISInfoBO>();

		ISInfoBO isInfoBO_Y_1 = new ISInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_2 = new ISInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_3 = new ISInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_4 = new ISInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_5 = new ISInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			System.out.println("Ticker: " + ticker + " Module: IncomeSheetScraper");
			String searchUrl = INCOME_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getPage(searchUrl);
			webClient.waitForBackgroundJavaScript(10000);

			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			assertNotNull("Page is null! ", page);

			List<?> currencyType = (List<?>) page.getByXPath("//*[@id=\"unitsAndFiscalYear\"]/text()[2]");
			DomText currencyDomText = (DomText) currencyType.get(0);
			String currency = currencyDomText.toString().substring(0, 3);
			isInfoBO_Y_1.setCurrencyType(currency);
			isInfoBO_Y_2.setCurrencyType(currency);
			isInfoBO_Y_3.setCurrencyType(currency);
			isInfoBO_Y_4.setCurrencyType(currency);
			isInfoBO_Y_5.setCurrencyType(currency);

			List<?> rootDivList = (List<?>) page.getByXPath("//*[@id=\"makeMeScrollable\"]");
			HtmlDivision rootDiv = (HtmlDivision) rootDivList.get(0);

			DomNode yearContents = rootDiv.getFirstChild().getFirstChild().getFirstChild().getFirstChild();
			List<String> incomeSheetDates = this.getOrderedDateValuesFromDomNodeRow(yearContents);
			isInfoBO_Y_1.setIsDate(incomeSheetDates.get(0));
			isInfoBO_Y_2.setIsDate(incomeSheetDates.get(1));
			isInfoBO_Y_3.setIsDate(incomeSheetDates.get(2));
			isInfoBO_Y_4.setIsDate(incomeSheetDates.get(3));
			isInfoBO_Y_5.setIsDate(incomeSheetDates.get(4));

			DomNode contents = rootDiv.getFirstChild().getFirstChild().getFirstChild();

			DomNode revenueRow = contents.getChildNodes().get(1);
			List<String> revenueVals = this.getOrderedRowValuesFromDomNodeRow(revenueRow);
			isInfoBO_Y_1.setRevenue(revenueVals.get(0));
			isInfoBO_Y_2.setRevenue(revenueVals.get(1));
			isInfoBO_Y_3.setRevenue(revenueVals.get(2));
			isInfoBO_Y_4.setRevenue(revenueVals.get(3));
			isInfoBO_Y_5.setRevenue(revenueVals.get(4));

			DomNode costOfRevenueRow = contents.getChildNodes().get(2);
			List<String> costOfRevenueVals = this.getOrderedRowValuesFromDomNodeRow(costOfRevenueRow);
			isInfoBO_Y_1.setCostOfRevenue(costOfRevenueVals.get(0));
			isInfoBO_Y_2.setCostOfRevenue(costOfRevenueVals.get(1));
			isInfoBO_Y_3.setCostOfRevenue(costOfRevenueVals.get(2));
			isInfoBO_Y_4.setCostOfRevenue(costOfRevenueVals.get(3));
			isInfoBO_Y_5.setCostOfRevenue(costOfRevenueVals.get(4));

			DomNode grossProfitRow = contents.getChildNodes().get(3);
			List<String> grossProfitVals = this.getOrderedRowValuesFromDomNodeRow(grossProfitRow);
			isInfoBO_Y_1.setGrossProfit(grossProfitVals.get(0));
			isInfoBO_Y_2.setGrossProfit(grossProfitVals.get(1));
			isInfoBO_Y_3.setGrossProfit(grossProfitVals.get(2));
			isInfoBO_Y_4.setGrossProfit(grossProfitVals.get(3));
			isInfoBO_Y_5.setGrossProfit(grossProfitVals.get(4));

			DomNode netIncomeRow = contents.getChildNodes().get(21);
			List<String> netIncomeVals = this.getOrderedRowValuesFromDomNodeRow(netIncomeRow);
			isInfoBO_Y_1.setNetIncome(netIncomeVals.get(0));
			isInfoBO_Y_2.setNetIncome(netIncomeVals.get(1));
			isInfoBO_Y_3.setNetIncome(netIncomeVals.get(2));
			isInfoBO_Y_4.setNetIncome(netIncomeVals.get(3));
			isInfoBO_Y_5.setNetIncome(netIncomeVals.get(4));

			DomNode epsRow = contents.getChildNodes().get(27);
			List<String> epsVals = this.getOrderedRowValuesFromDomNodeRow(epsRow);
			isInfoBO_Y_1.setEps(epsVals.get(0));
			isInfoBO_Y_2.setEps(epsVals.get(1));
			isInfoBO_Y_3.setEps(epsVals.get(2));
			isInfoBO_Y_4.setEps(epsVals.get(3));
			isInfoBO_Y_5.setEps(epsVals.get(4));

			isInfoList.add(isInfoBO_Y_1);
			isInfoList.add(isInfoBO_Y_2);
			isInfoList.add(isInfoBO_Y_3);
			isInfoList.add(isInfoBO_Y_4);
			isInfoList.add(isInfoBO_Y_5);

			webClient.close();

			return isInfoList;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return isInfoList;
	}

	public Map<String, List<ISInfoBO>> getTickerToIncomeSheet() {
		return tickerToIncomeSheet;
	}

}
