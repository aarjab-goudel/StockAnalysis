/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package annual.web.scrape.data;

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
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import create.excel.bo.CFInfoBO;
import create.excel.enums.CommonSheetConstants;
import excel.library.CFFinancialLibrary;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;

public class AnnualCashSheetScraper extends FinancialScrapper {

	private static final String CASH_FLOW_SHEET_URL = "http://financials.morningstar.com/cash-flow/cf.html?t=";
	private Map<String, List<CFInfoBO>> tickerToCashSheet;

	public AnnualCashSheetScraper(List<String> tickersToBuild, Task<Void> task)
			throws UnsupportedEncodingException, IOException {
		super(tickersToBuild, task);
		this.tickerToCashSheet = this.buildTickerToCashSheetMap();
	}

	public Map<String, List<CFInfoBO>> buildTickerToCashSheetMap() throws UnsupportedEncodingException, IOException {
		Map<String, List<CFInfoBO>> tickerToCFInfoBOMap = new HashMap<String, List<CFInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
				List<CFInfoBO> cfInfoBOList = this.readCashSheetURL(ticker);
				tickerToCFInfoBOMap.put(ticker, cfInfoBOList);
			}

		}
		return tickerToCFInfoBOMap;
	}

	public List<CFInfoBO> readCashSheetURL(String ticker) throws UnsupportedEncodingException, IOException {

		List<CFInfoBO> cfInfoList = new ArrayList<CFInfoBO>();

		CFInfoBO cfInfoBO_Y_1 = new CFInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_2 = new CFInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_3 = new CFInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_4 = new CFInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		CFInfoBO cfInfoBO_Y_5 = new CFInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			ScrapperLogger.log("MorningStar Cash Sheet: " + ticker + "=================");
			String searchUrl = CASH_FLOW_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			ScrapperLogger.log("Before 1.getPage() in MorningStar Cash Sheet: " + searchUrl + "=================");
			webClient.getPage(searchUrl);
			ScrapperLogger.log("After 1.getPage() in MorningStar Cash Sheet: " + searchUrl + "=================");
			webClient.waitForBackgroundJavaScript(10000);

			ScrapperLogger.log("Before 2.getPage() in MorningStar Cash Sheet: " + searchUrl + "=================");
			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
			ScrapperLogger.log("After 2.getPage() in MorningStar Cash Sheet: " + searchUrl + "=================");

			assertNotNull("Page is null! ", page);

			List<?> currencyType = (List<?>) page.getByXPath("//*[@id=\"unitsAndFiscalYear\"]/text()[2]");
			DomText currencyDomText = (DomText) currencyType.get(0);
			String currency = currencyDomText.toString().substring(0, 3);
			cfInfoBO_Y_1.setCurrencyType(currency);
			cfInfoBO_Y_2.setCurrencyType(currency);
			cfInfoBO_Y_3.setCurrencyType(currency);
			cfInfoBO_Y_4.setCurrencyType(currency);
			cfInfoBO_Y_5.setCurrencyType(currency);

			List<?> cfYearList = (List<?>) page.getByXPath("//*[@id=\"Year\"]");
			HtmlDivision cfYearDivision = (HtmlDivision) cfYearList.get(0);
			List<String> cashSheetDates = this.getOrderedDateValuesFromDomNodeRow(cfYearDivision);

			cfInfoBO_Y_1.setCfDate(cashSheetDates.get(0));
			cfInfoBO_Y_2.setCfDate(cashSheetDates.get(1));
			cfInfoBO_Y_3.setCfDate(cashSheetDates.get(2));
			cfInfoBO_Y_4.setCfDate(cashSheetDates.get(3));
			cfInfoBO_Y_5.setCfDate(cashSheetDates.get(4));

			List<?> netCashByOperatingActivitiesList = (List<?>) page.getByXPath("//*[@id=\"data_tts1\"]");
			HtmlDivision netCashByOperatingActivitiesDivision = (HtmlDivision) netCashByOperatingActivitiesList.get(0);
			List<String> netCashByOperatingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashByOperatingActivitiesDivision);
			cfInfoBO_Y_1.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashByOperatingActivites(netCashByOperatingActivitiesVals.get(4));

			List<?> netCashForInvestingActivitiesList = (List<?>) page.getByXPath("//*[@id=\"data_tts2\"]");
			HtmlDivision netCashByInvestingActivitiesDivision = (HtmlDivision) netCashForInvestingActivitiesList.get(0);
			List<String> netCashForInvestingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashByInvestingActivitiesDivision);
			cfInfoBO_Y_1.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashForInvestingActivities(netCashForInvestingActivitiesVals.get(4));

			List<?> netCashForFinancingActivitiesList = (List<?>) page.getByXPath("//*[@id=\"data_tts3\"]");
			HtmlDivision netCashForFinancingActivitiesDivision = (HtmlDivision) netCashForFinancingActivitiesList
					.get(0);
			List<String> netCashForFinancingActivitiesVals = this
					.getOrderedRowValuesFromDomNodeRow(netCashForFinancingActivitiesDivision);
			cfInfoBO_Y_1.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(0));
			cfInfoBO_Y_2.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(1));
			cfInfoBO_Y_3.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(2));
			cfInfoBO_Y_4.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(3));
			cfInfoBO_Y_5.setNetCashForFinancingActivities(netCashForFinancingActivitiesVals.get(4));

			List<?> capitalExpenditureList = (List<?>) page.getByXPath("//*[@id=\"data_i96\"]");
			HtmlDivision capitalExpenditureDivision = (HtmlDivision) capitalExpenditureList.get(0);
			List<String> capitalExpenditureVals = this.getOrderedRowValuesFromDomNodeRow(capitalExpenditureDivision);
			cfInfoBO_Y_1.setCapitalExpenditure(capitalExpenditureVals.get(0));
			cfInfoBO_Y_2.setCapitalExpenditure(capitalExpenditureVals.get(1));
			cfInfoBO_Y_3.setCapitalExpenditure(capitalExpenditureVals.get(2));
			cfInfoBO_Y_4.setCapitalExpenditure(capitalExpenditureVals.get(3));
			cfInfoBO_Y_5.setCapitalExpenditure(capitalExpenditureVals.get(4));

			List<?> freeCashFlowList = (List<?>) page.getByXPath("//*[@id=\"data_i97\"]");
			HtmlDivision freeCashFlowDivision = (HtmlDivision) freeCashFlowList.get(0);
			List<String> freeCashFlowVals = this.getOrderedRowValuesFromDomNodeRow(freeCashFlowDivision);
			cfInfoBO_Y_1.setFreeCashFlow(freeCashFlowVals.get(0));
			cfInfoBO_Y_2.setFreeCashFlow(freeCashFlowVals.get(1));
			cfInfoBO_Y_3.setFreeCashFlow(freeCashFlowVals.get(2));
			cfInfoBO_Y_4.setFreeCashFlow(freeCashFlowVals.get(3));
			cfInfoBO_Y_5.setFreeCashFlow(freeCashFlowVals.get(4));

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
			ScrapperLogger.log("Exception in read MorningStar Cash Sheet for: " + CASH_FLOW_SHEET_URL
					+ URLEncoder.encode(ticker, "UTF-8"));
			ScrapperLogger.log("Exception Message is: " + e.getMessage());
			ScrapperLogger.log("Localized Exception Message is: " + e.getLocalizedMessage());
			CFInfoBO firstCFInfo = CFFinancialLibrary.createErrorCFInfoBO(ticker);
			CFInfoBO secondCFInfo = CFFinancialLibrary.createErrorCFInfoBO(ticker);
			CFInfoBO thirdCFInfo = CFFinancialLibrary.createErrorCFInfoBO(ticker);
			CFInfoBO fourthCFInfo = CFFinancialLibrary.createErrorCFInfoBO(ticker);
			CFInfoBO fifthCFInfo = CFFinancialLibrary.createErrorCFInfoBO(ticker);
			List<CFInfoBO> errorCfInfoList = new ArrayList<CFInfoBO>();
			errorCfInfoList.add(firstCFInfo);
			errorCfInfoList.add(secondCFInfo);
			errorCfInfoList.add(thirdCFInfo);
			errorCfInfoList.add(fourthCFInfo);
			errorCfInfoList.add(fifthCFInfo);
			return errorCfInfoList;
		}
	}

	public Map<String, List<CFInfoBO>> getTickerToCashSheet() {
		return tickerToCashSheet;
	}

}
