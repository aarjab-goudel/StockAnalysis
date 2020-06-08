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

import create.excel.bo.CFInfoBO;
import create.excel.enums.CommonSheetConstants;

public class CashSheetScraper extends FinancialScrapper {

	private static final String CASH_FLOW_SHEET_URL = "http://financials.morningstar.com/cash-flow/cf.html?t=";
	private Map<String, List<CFInfoBO>> tickerToCashSheet;

	public CashSheetScraper(List<String> tickersToBuild) {
		super(tickersToBuild);
		this.tickerToCashSheet = this.buildTickerToCashSheetMap();
	}

	public Map<String, List<CFInfoBO>> buildTickerToCashSheetMap() {
		Map<String, List<CFInfoBO>> tickerToCFInfoBOMap = new HashMap<String, List<CFInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			List<CFInfoBO> cfInfoBOList = this.readCashSheetURL(ticker);
			assertEquals("The size in cash flow sheet scraper for " + ticker + " is " + cfInfoBOList.size(),
					cfInfoBOList.size(), 5);
			tickerToCFInfoBOMap.put(ticker, cfInfoBOList);
		}
		return tickerToCFInfoBOMap;
	}

	public List<CFInfoBO> readCashSheetURL(String ticker) {

		List<CFInfoBO> cfInfoList = new ArrayList<CFInfoBO>();

		CFInfoBO cfInfoBO_Y_1 = new CFInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_2 = new CFInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_3 = new CFInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_4 = new CFInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_5 = new CFInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			System.out.println("Ticker: " + ticker + " Module: CashsheetScraper");
			String searchUrl = CASH_FLOW_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
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
			cfInfoBO_Y_1.setCurrencyType(currency);
			cfInfoBO_Y_2.setCurrencyType(currency);
			cfInfoBO_Y_3.setCurrencyType(currency);
			cfInfoBO_Y_4.setCurrencyType(currency);
			cfInfoBO_Y_5.setCurrencyType(currency);

			List<?> rootDivList = (List<?>) page.getByXPath("//*[@id=\"makeMeScrollable\"]");
			System.out.println("rootDivList " + rootDivList);
			HtmlDivision rootDiv = (HtmlDivision) rootDivList.get(0);

			DomNode yearContents = rootDiv.getFirstChild().getFirstChild().getFirstChild().getFirstChild();
			List<String> cashSheetDates = this.getOrderedDateValuesFromDomNodeRow(yearContents);
			cfInfoBO_Y_1.setCfDate(cashSheetDates.get(0));
			cfInfoBO_Y_2.setCfDate(cashSheetDates.get(1));
			cfInfoBO_Y_3.setCfDate(cashSheetDates.get(2));
			cfInfoBO_Y_4.setCfDate(cashSheetDates.get(3));
			cfInfoBO_Y_5.setCfDate(cashSheetDates.get(4));

			DomNode contents = rootDiv.getFirstChild().getFirstChild().getFirstChild();
			DomNode netCashByOperatingActivitiesContent = contents.getChildNodes().get(3);
			DomNode netCashForInvestingActivitiesContent = contents.getChildNodes().get(6);
			DomNode netCashForFinancingActivitiesContent = contents.getChildNodes().get(9);

			DomNode netCashByOperatingActivitiesRow = netCashByOperatingActivitiesContent.getLastChild();
			List<String> netCashByOperatingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashByOperatingActivitiesRow);
			cfInfoBO_Y_1.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(4));

			DomNode netCashForInvestingActivitiesRow = netCashForInvestingActivitiesContent.getLastChild();
			List<String> netCashForInvestingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashForInvestingActivitiesRow);
			cfInfoBO_Y_1.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(4));

			DomNode netCashForFinancingActivitiesRow = netCashForFinancingActivitiesContent.getLastChild();
			List<String> netCashForFinancingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashForFinancingActivitiesRow);
			cfInfoBO_Y_1.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(4));

			DomNode capitalExpenditureRow = contents.getChildNodes().get(17);
			List<String> capitalExpenditureVals = this.getOrderedRowValuesFromDomNodeRow(capitalExpenditureRow);
			cfInfoBO_Y_1.setCapitalExpenditure(capitalExpenditureVals.get(0));
			cfInfoBO_Y_2.setCapitalExpenditure(capitalExpenditureVals.get(1));
			cfInfoBO_Y_3.setCapitalExpenditure(capitalExpenditureVals.get(2));
			cfInfoBO_Y_4.setCapitalExpenditure(capitalExpenditureVals.get(3));
			cfInfoBO_Y_5.setCapitalExpenditure(capitalExpenditureVals.get(4));

			DomNode freeCashFlowRow = contents.getChildNodes().get(18);
			List<String> freeCashFlowVals = this.getOrderedRowValuesFromDomNodeRow(freeCashFlowRow);
			cfInfoBO_Y_1.setFreeCashFlow(freeCashFlowVals.get(0));
			cfInfoBO_Y_2.setFreeCashFlow(freeCashFlowVals.get(1));
			cfInfoBO_Y_3.setFreeCashFlow(freeCashFlowVals.get(2));
			cfInfoBO_Y_4.setFreeCashFlow(freeCashFlowVals.get(3));
			cfInfoBO_Y_5.setFreeCashFlow(freeCashFlowVals.get(4));

			cfInfoList.add(cfInfoBO_Y_1);
			cfInfoList.add(cfInfoBO_Y_2);
			cfInfoList.add(cfInfoBO_Y_3);
			cfInfoList.add(cfInfoBO_Y_4);
			cfInfoList.add(cfInfoBO_Y_5);

			webClient.close();

			return cfInfoList;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return cfInfoList;
	}

	public Map<String, List<CFInfoBO>> getTickerToCashSheet() {
		return tickerToCashSheet;
	}

}
