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

import create.excel.bo.BSInfoBO;
import create.excel.enums.CommonSheetConstants;
import excel.library.BSFinancialLibrary;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;

public class AnnualBalanceSheetScraper extends FinancialScrapper {

	private static final String BALANCE_SHEET_URL = "http://financials.morningstar.com/balance-sheet/bs.html?t=";
	private Map<String, List<BSInfoBO>> tickerToBalanceSheet;

	public AnnualBalanceSheetScraper(List<String> tickersToBuild, Task<Void> task)
			throws UnsupportedEncodingException, IOException {
		super(tickersToBuild, task);
		this.tickerToBalanceSheet = this.buildTickerToBalanceSheetMap();

	}

	public Map<String, List<BSInfoBO>> buildTickerToBalanceSheetMap() throws UnsupportedEncodingException, IOException {
		Map<String, List<BSInfoBO>> tickerToBSInfoBOMap = new HashMap<String, List<BSInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
				List<BSInfoBO> bsInfoBOList = this.readBalanceSheetURL(ticker);

				tickerToBSInfoBOMap.put(ticker, bsInfoBOList);
			}

		}
		return tickerToBSInfoBOMap;
	}

	public List<BSInfoBO> readBalanceSheetURL(String ticker) throws UnsupportedEncodingException, IOException {

		List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();

		BSInfoBO bsInfoBO_Y_1 = new BSInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_2 = new BSInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_3 = new BSInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_4 = new BSInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_5 = new BSInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			ScrapperLogger.log("MorningStar Balance Sheet: " + ticker + "=================");
			String searchUrl = BALANCE_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			ScrapperLogger.log("Before 1.getPage() in MorningStar Balance Sheet: " + searchUrl + "=================");
			webClient.getPage(searchUrl);
			ScrapperLogger.log("After 1.getPage() in MorningStar Balance Sheet: " + searchUrl + "=================");

			webClient.waitForBackgroundJavaScript(10000);

			ScrapperLogger.log("Before 2.getPage() in MorningStar Balance Sheet: " + searchUrl + "=================");
			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
			ScrapperLogger.log("After 2.getPage() in MorningStar Balance Sheet: " + searchUrl + "=================");

			assertNotNull("Page is null! ", page);

			ScrapperLogger.log("Before currency type in Balance Sheet");
			List<?> currencyType = (List<?>) page.getByXPath("//*[@id=\"unitsAndFiscalYear\"]/text()[2]");
			DomText currencyDomText = (DomText) currencyType.get(0);
			String currency = currencyDomText.toString().substring(0, 3);
			bsInfoBO_Y_1.setCurrencyType(currency);
			bsInfoBO_Y_2.setCurrencyType(currency);
			bsInfoBO_Y_3.setCurrencyType(currency);
			bsInfoBO_Y_4.setCurrencyType(currency);
			bsInfoBO_Y_5.setCurrencyType(currency);
			ScrapperLogger.log("After currency type in Balance Sheet");

			List<?> cashAndCashEquivalentList = (List<?>) page.getByXPath("//*[@id=\"data_i1\"]");
			HtmlDivision cashAndCashEquivalentDivision = (HtmlDivision) cashAndCashEquivalentList.get(0);
			List<String> cashAndCashEquivalentVals = this
					.getOrderedRowValuesFromDomNodeRow(cashAndCashEquivalentDivision);
//			bsInfoBO_Y_1.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(0));
//			bsInfoBO_Y_2.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(1));
//			bsInfoBO_Y_3.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(2));
//			bsInfoBO_Y_4.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(3));
//			bsInfoBO_Y_5.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(4));

			List<?> shortTermInvestmentList = (List<?>) page.getByXPath("//*[@id=\"data_i2\"]");
			HtmlDivision shortTermInvestmentDivision = (HtmlDivision) shortTermInvestmentList.get(0);
			List<String> shortTermInvestmentsVals = this.getOrderedRowValuesFromDomNodeRow(shortTermInvestmentDivision);
//			bsInfoBO_Y_1.setShortTermInvestments(shortTermInvestmentsVals.get(0));
//			bsInfoBO_Y_2.setShortTermInvestments(shortTermInvestmentsVals.get(1));
//			bsInfoBO_Y_3.setShortTermInvestments(shortTermInvestmentsVals.get(2));
//			bsInfoBO_Y_4.setShortTermInvestments(shortTermInvestmentsVals.get(3));
//			bsInfoBO_Y_5.setShortTermInvestments(shortTermInvestmentsVals.get(4));

			List<?> receivablesList = (List<?>) page.getByXPath("//*[@id=\"data_i3\"]");
			HtmlDivision recievablesDivision = (HtmlDivision) receivablesList.get(0);
			List<String> receivablesVals = this.getOrderedRowValuesFromDomNodeRow(recievablesDivision);
//			bsInfoBO_Y_1.setReceivables(receivablesVals.get(0));
//			bsInfoBO_Y_2.setReceivables(receivablesVals.get(1));
//			bsInfoBO_Y_3.setReceivables(receivablesVals.get(2));
//			bsInfoBO_Y_4.setReceivables(receivablesVals.get(3));
//			bsInfoBO_Y_5.setReceivables(receivablesVals.get(4));

			List<?> longTermInvestmentList = (List<?>) page.getByXPath("//*[@id=\"data_i11\"]");
			HtmlDivision longTermInvestmentDivision = (HtmlDivision) longTermInvestmentList.get(0);
			List<String> longTermInvestmentVals = this.getOrderedRowValuesFromDomNodeRow(longTermInvestmentDivision);
//			bsInfoBO_Y_1.setLongTermInvestments(longTermInvestmentVals.get(0));
//			bsInfoBO_Y_2.setLongTermInvestments(longTermInvestmentVals.get(1));
//			bsInfoBO_Y_3.setLongTermInvestments(longTermInvestmentVals.get(2));
//			bsInfoBO_Y_4.setLongTermInvestments(longTermInvestmentVals.get(3));
//			bsInfoBO_Y_5.setLongTermInvestments(longTermInvestmentVals.get(4));

			List<?> intangibleAssetsList = (List<?>) page.getByXPath("//*[@id=\"data_i13\"]");
			HtmlDivision intangibleAssetsDivision = (HtmlDivision) intangibleAssetsList.get(0);
			List<String> intangibleAssetsVal = this.getOrderedRowValuesFromDomNodeRow(intangibleAssetsDivision);
//			bsInfoBO_Y_1.setTotalIntangibleAssets(intangibleAssetsVal.get(0));
//			bsInfoBO_Y_2.setTotalIntangibleAssets(intangibleAssetsVal.get(1));
//			bsInfoBO_Y_3.setTotalIntangibleAssets(intangibleAssetsVal.get(2));
//			bsInfoBO_Y_4.setTotalIntangibleAssets(intangibleAssetsVal.get(3));
//			bsInfoBO_Y_5.setTotalIntangibleAssets(intangibleAssetsVal.get(4));

			List<?> shortTermDebtList = (List<?>) page.getByXPath("//*[@id=\"data_i41\"]");
			HtmlDivision shortTermDebtDivision = (HtmlDivision) shortTermDebtList.get(0);
			List<String> shortTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(shortTermDebtDivision);
//			bsInfoBO_Y_1.setShortTermDebt(shortTermDebtVals.get(0));
//			bsInfoBO_Y_2.setShortTermDebt(shortTermDebtVals.get(1));
//			bsInfoBO_Y_3.setShortTermDebt(shortTermDebtVals.get(2));
//			bsInfoBO_Y_4.setShortTermDebt(shortTermDebtVals.get(3));
//			bsInfoBO_Y_5.setShortTermDebt(shortTermDebtVals.get(4));

			List<?> payablesList = (List<?>) page.getByXPath("//*[@id=\"data_i43\"]");
			HtmlDivision payablesDivision = (HtmlDivision) payablesList.get(0);
			List<String> payableVals = this.getOrderedRowValuesFromDomNodeRow(payablesDivision);
//			bsInfoBO_Y_1.setAccountsPayable(payableVals.get(0));
//			bsInfoBO_Y_2.setAccountsPayable(payableVals.get(1));
//			bsInfoBO_Y_3.setAccountsPayable(payableVals.get(2));
//			bsInfoBO_Y_4.setAccountsPayable(payableVals.get(3));
//			bsInfoBO_Y_5.setAccountsPayable(payableVals.get(4));

			List<?> longTermDebtList = (List<?>) page.getByXPath("//*[@id=\"data_i50\"]");
			HtmlDivision longTermDebtDivision = (HtmlDivision) longTermDebtList.get(0);
			List<String> longTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(longTermDebtDivision);
//			bsInfoBO_Y_1.setLongTermDebt(longTermDebtVals.get(0));
//			bsInfoBO_Y_2.setLongTermDebt(longTermDebtVals.get(1));
//			bsInfoBO_Y_3.setLongTermDebt(longTermDebtVals.get(2));
//			bsInfoBO_Y_4.setLongTermDebt(longTermDebtVals.get(3));
//			bsInfoBO_Y_5.setLongTermDebt(longTermDebtVals.get(4));

			List<?> shareholderEquityList = (List<?>) page.getByXPath("//*[@id=\"data_ttg8\"]");
			HtmlDivision shareHolderEquityDivision = (HtmlDivision) shareholderEquityList.get(0);
			List<String> shareholdersVals = this.getOrderedRowValuesFromDomNodeRow(shareHolderEquityDivision);
			bsInfoBO_Y_1.setShareHoldersEquity(shareholdersVals.get(0));
			bsInfoBO_Y_2.setShareHoldersEquity(shareholdersVals.get(1));
			bsInfoBO_Y_3.setShareHoldersEquity(shareholdersVals.get(2));
			bsInfoBO_Y_4.setShareHoldersEquity(shareholdersVals.get(3));
			bsInfoBO_Y_5.setShareHoldersEquity(shareholdersVals.get(4));

			List<?> bsYearList = (List<?>) page.getByXPath("//*[@id=\"Year\"]");
			HtmlDivision bsYearDivision = (HtmlDivision) bsYearList.get(0);
			List<String> balanceSheetDates = this.getOrderedDateValuesFromDomNodeRow(bsYearDivision);

			bsInfoBO_Y_1.setBsDate(balanceSheetDates.get(0));
			bsInfoBO_Y_2.setBsDate(balanceSheetDates.get(1));
			bsInfoBO_Y_3.setBsDate(balanceSheetDates.get(2));
			bsInfoBO_Y_4.setBsDate(balanceSheetDates.get(3));
			bsInfoBO_Y_5.setBsDate(balanceSheetDates.get(4));

			bsInfoList.add(bsInfoBO_Y_1);
			bsInfoList.add(bsInfoBO_Y_2);
			bsInfoList.add(bsInfoBO_Y_3);
			bsInfoList.add(bsInfoBO_Y_4);
			bsInfoList.add(bsInfoBO_Y_5);

			webClient.close();

			return bsInfoList;
		} catch (Exception e) {
			ScrapperLogger.log("Exception in read MorningStar Balance Sheet for: " + BALANCE_SHEET_URL
					+ URLEncoder.encode(ticker, "UTF-8"));
			ScrapperLogger.log("Exception Message is: " + e.getMessage());
			ScrapperLogger.log("Localized Exception Message is: " + e.getLocalizedMessage());
			BSInfoBO firstBSInfo = BSFinancialLibrary.createErrorBSInfoBO(ticker);
			BSInfoBO secondBSInfo = BSFinancialLibrary.createErrorBSInfoBO(ticker);
			BSInfoBO thirdBSInfo = BSFinancialLibrary.createErrorBSInfoBO(ticker);
			BSInfoBO fourthBSInfo = BSFinancialLibrary.createErrorBSInfoBO(ticker);
			BSInfoBO fifthBSInfo = BSFinancialLibrary.createErrorBSInfoBO(ticker);
			List<BSInfoBO> errorBsInfoList = new ArrayList<BSInfoBO>();
			errorBsInfoList.add(firstBSInfo);
			errorBsInfoList.add(secondBSInfo);
			errorBsInfoList.add(thirdBSInfo);
			errorBsInfoList.add(fourthBSInfo);
			errorBsInfoList.add(fifthBSInfo);
			return errorBsInfoList;
		}
	}

	public Map<String, List<BSInfoBO>> getTickerToBalanceSheet() {
		return tickerToBalanceSheet;
	}

}
