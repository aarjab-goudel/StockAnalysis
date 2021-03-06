/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package quarterly.web.scrape.data;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import annual.web.scrape.data.FinancialScrapper;
import create.excel.bo.ISInfoBO;
import create.excel.enums.CommonSheetConstants;
import excel.library.ISFinancialLibrary;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;

public class QuarterlyIncomeSheetScraper extends FinancialScrapper {
	private static final String INCOME_SHEET_URL = "http://financials.morningstar.com/income-statement/is.html?t=";
	private Map<String, List<ISInfoBO>> tickerToIncomeSheet;

	public QuarterlyIncomeSheetScraper(List<String> companies, Task<Void> task)
			throws UnsupportedEncodingException, IOException {
		super(companies, task);
		this.tickerToIncomeSheet = this.buildTickerToIncomeSheetMap();
	}

	public Map<String, List<ISInfoBO>> buildTickerToIncomeSheetMap() throws UnsupportedEncodingException, IOException {
		Map<String, List<ISInfoBO>> tickerToISInfoBOMap = new HashMap<String, List<ISInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
				List<ISInfoBO> isInfoBOList = this.readIncomeSheetURL(ticker);

				tickerToISInfoBOMap.put(ticker, isInfoBOList);
			}
		}
		return tickerToISInfoBOMap;
	}

	public List<ISInfoBO> readIncomeSheetURL(String ticker) throws UnsupportedEncodingException, IOException {
		List<ISInfoBO> isInfoList = new ArrayList<ISInfoBO>();

		ISInfoBO isInfoBO_Y_1 = new ISInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_2 = new ISInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_3 = new ISInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_4 = new ISInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		ISInfoBO isInfoBO_Y_5 = new ISInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			System.out.println("Ticker: " + ticker + " Module: QuarterlyIncomeSheetScraper");
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

			List<?> quarterlySelection = (List<?>) page.getByXPath("//*[@id=\"menu_A\"]/ul/li[2]/a");
			HtmlAnchor anchorToClick = (HtmlAnchor) quarterlySelection.get(0);
			anchorToClick.click();

			webClient.waitForBackgroundJavaScript(10000);

			page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			List<?> currencyType = (List<?>) page.getByXPath("//*[@id=\"unitsAndFiscalYear\"]/text()[2]");
			DomText currencyDomText = (DomText) currencyType.get(0);
			String currency = currencyDomText.toString().substring(0, 3);
			isInfoBO_Y_1.setCurrencyType(currency);
			isInfoBO_Y_2.setCurrencyType(currency);
			isInfoBO_Y_3.setCurrencyType(currency);
			isInfoBO_Y_4.setCurrencyType(currency);
			isInfoBO_Y_5.setCurrencyType(currency);

			List<?> quarterlyISDate = (List<?>) page.getByXPath("//*[@id=\"Year\"]");
			DomNode isDateNodes = (DomNode) quarterlyISDate.get(0);
			List<String> incomeSheetDates = this.getOrderedDateValuesFromDomNodeRow(isDateNodes);
			isInfoBO_Y_1.setIsDate(incomeSheetDates.get(0));
			isInfoBO_Y_2.setIsDate(incomeSheetDates.get(1));
			isInfoBO_Y_3.setIsDate(incomeSheetDates.get(2));
			isInfoBO_Y_4.setIsDate(incomeSheetDates.get(3));
			isInfoBO_Y_5.setIsDate(incomeSheetDates.get(4));

			List<?> revenueList = (List<?>) page.getByXPath("//*[@id=\"data_i1\"]");
			DomNode revenueNodes = (DomNode) revenueList.get(0);
			List<String> revenueVals = this.getOrderedRowValuesFromDomNodeRow(revenueNodes);
			isInfoBO_Y_1.setRevenue(revenueVals.get(0));
			isInfoBO_Y_2.setRevenue(revenueVals.get(1));
			isInfoBO_Y_3.setRevenue(revenueVals.get(2));
			isInfoBO_Y_4.setRevenue(revenueVals.get(3));
			isInfoBO_Y_5.setRevenue(revenueVals.get(4));

			List<?> costOfRevenueList = (List<?>) page.getByXPath("//*[@id=\"data_i6\"]");
			DomNode costOfRevenueNodes = (DomNode) costOfRevenueList.get(0);
			List<String> costOfRevenueVals = this.getOrderedRowValuesFromDomNodeRow(costOfRevenueNodes);
			isInfoBO_Y_1.setCostOfRevenue(costOfRevenueVals.get(0));
			isInfoBO_Y_2.setCostOfRevenue(costOfRevenueVals.get(1));
			isInfoBO_Y_3.setCostOfRevenue(costOfRevenueVals.get(2));
			isInfoBO_Y_4.setCostOfRevenue(costOfRevenueVals.get(3));
			isInfoBO_Y_5.setCostOfRevenue(costOfRevenueVals.get(4));

			List<?> grossProfitList = (List<?>) page.getByXPath("//*[@id=\"data_i10\"]");
			DomNode grossProfitNodes = (DomNode) grossProfitList.get(0);
			List<String> grossProfitVals = this.getOrderedRowValuesFromDomNodeRow(grossProfitNodes);
			isInfoBO_Y_1.setGrossProfit(grossProfitVals.get(0));
			isInfoBO_Y_2.setGrossProfit(grossProfitVals.get(1));
			isInfoBO_Y_3.setGrossProfit(grossProfitVals.get(2));
			isInfoBO_Y_4.setGrossProfit(grossProfitVals.get(3));
			isInfoBO_Y_5.setGrossProfit(grossProfitVals.get(4));

			List<?> netIncomeList = (List<?>) page.getByXPath("//*[@id=\"data_i80\"]");
			DomNode netIncomeNodes = (DomNode) netIncomeList.get(0);
			List<String> netIncomeVals = this.getOrderedRowValuesFromDomNodeRow(netIncomeNodes);
			isInfoBO_Y_1.setNetIncome(netIncomeVals.get(0));
			isInfoBO_Y_2.setNetIncome(netIncomeVals.get(1));
			isInfoBO_Y_3.setNetIncome(netIncomeVals.get(2));
			isInfoBO_Y_4.setNetIncome(netIncomeVals.get(3));
			isInfoBO_Y_5.setNetIncome(netIncomeVals.get(4));

			List<?> epsList = (List<?>) page.getByXPath("//*[@id=\"data_i83\"]");
			DomNode epsNodes = (DomNode) epsList.get(0);
			List<String> epsVals = this.getOrderedRowValuesFromDomNodeRow(epsNodes);
			isInfoBO_Y_1.setEps(epsVals.get(0));
			isInfoBO_Y_2.setEps(epsVals.get(1));
			isInfoBO_Y_3.setEps(epsVals.get(2));
			isInfoBO_Y_4.setEps(epsVals.get(3));
			isInfoBO_Y_5.setEps(epsVals.get(4));

			List<?> researchAndDevelopmentList = (List<?>) page.getByXPath("//*[@id=\"data_i11\"]");
			HtmlDivision researchAndDevelopmentDivision = (HtmlDivision) researchAndDevelopmentList.get(0);
			List<String> researchAndDevelopmentVals = this
					.getOrderedRowValuesFromDomNodeRow(researchAndDevelopmentDivision);
			isInfoBO_Y_1.setResearchAndDevelopment(researchAndDevelopmentVals.get(0));
			isInfoBO_Y_2.setResearchAndDevelopment(researchAndDevelopmentVals.get(1));
			isInfoBO_Y_3.setResearchAndDevelopment(researchAndDevelopmentVals.get(2));
			isInfoBO_Y_4.setResearchAndDevelopment(researchAndDevelopmentVals.get(3));
			isInfoBO_Y_5.setResearchAndDevelopment(researchAndDevelopmentVals.get(4));

			isInfoList.add(isInfoBO_Y_1);
			isInfoList.add(isInfoBO_Y_2);
			isInfoList.add(isInfoBO_Y_3);
			isInfoList.add(isInfoBO_Y_4);
			isInfoList.add(isInfoBO_Y_5);

			webClient.close();
			return isInfoList;

		} catch (Exception e) {
			e.printStackTrace();
			ScrapperLogger.log("Exception in read MorningStar Income Sheet for: " + INCOME_SHEET_URL
					+ URLEncoder.encode(ticker, "UTF-8"));
			ScrapperLogger.log("Exception Message is: " + e.getMessage());
			ScrapperLogger.log("Localized Exception Message is: " + e.getLocalizedMessage());
			ISInfoBO firstISInfo = ISFinancialLibrary.createErrorISInfoBO(ticker);
			ISInfoBO secondISInfo = ISFinancialLibrary.createErrorISInfoBO(ticker);
			ISInfoBO thirdISInfo = ISFinancialLibrary.createErrorISInfoBO(ticker);
			ISInfoBO fourthISInfo = ISFinancialLibrary.createErrorISInfoBO(ticker);
			ISInfoBO fifthISInfo = ISFinancialLibrary.createErrorISInfoBO(ticker);
			List<ISInfoBO> errorIsInfoList = new ArrayList<ISInfoBO>();
			errorIsInfoList.add(firstISInfo);
			errorIsInfoList.add(secondISInfo);
			errorIsInfoList.add(thirdISInfo);
			errorIsInfoList.add(fourthISInfo);
			errorIsInfoList.add(fifthISInfo);
			return errorIsInfoList;
		}

	}

	public Map<String, List<ISInfoBO>> getTickerToIncomeSheet() {
		return tickerToIncomeSheet;
	}

	public void setTickerToIncomeSheet(Map<String, List<ISInfoBO>> tickerToIncomeSheet) {
		this.tickerToIncomeSheet = tickerToIncomeSheet;
	}

}
