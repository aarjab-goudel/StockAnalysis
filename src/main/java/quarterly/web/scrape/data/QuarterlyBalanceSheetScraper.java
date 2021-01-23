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
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import annual.web.scrape.data.FinancialScrapper;
import create.excel.bo.BSInfoBO;
import create.excel.enums.CommonSheetConstants;
import excel.library.BSFinancialLibrary;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;

public class QuarterlyBalanceSheetScraper extends FinancialScrapper {

	private static final String BALANCE_SHEET_URL = "http://financials.morningstar.com/balance-sheet/bs.html?t=";
	private Map<String, List<BSInfoBO>> tickerToBalanceSheet;

	public QuarterlyBalanceSheetScraper(List<String> companies, Task<Void> task)
			throws UnsupportedEncodingException, IOException {
		super(companies, task);
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
			System.out.println("Ticker: " + ticker + " Module: QuarterlyBalancesheetScraper");
			String searchUrl = BALANCE_SHEET_URL + URLEncoder.encode(ticker, "UTF-8");
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
			bsInfoBO_Y_1.setCurrencyType(currency);
			bsInfoBO_Y_2.setCurrencyType(currency);
			bsInfoBO_Y_3.setCurrencyType(currency);
			bsInfoBO_Y_4.setCurrencyType(currency);
			bsInfoBO_Y_5.setCurrencyType(currency);

			List<?> quarterlyBSDate = (List<?>) page.getByXPath("//*[@id=\"Year\"]");
			DomNode bsDateNodes = (DomNode) quarterlyBSDate.get(0);
			List<String> balanceSheetDates = this.getOrderedDateValuesFromDomNodeRow(bsDateNodes);
			bsInfoBO_Y_1.setBsDate(balanceSheetDates.get(0));
			bsInfoBO_Y_2.setBsDate(balanceSheetDates.get(1));
			bsInfoBO_Y_3.setBsDate(balanceSheetDates.get(2));
			bsInfoBO_Y_4.setBsDate(balanceSheetDates.get(3));
			bsInfoBO_Y_5.setBsDate(balanceSheetDates.get(4));

			List<?> cashAndCashEquivalentList = (List<?>) page.getByXPath("//*[@id=\"data_i1\"]");
			DomNode cashAndCashEquivalentNodes = (DomNode) cashAndCashEquivalentList.get(0);
			List<String> cashAndCashEquivalentVals = this.getOrderedRowValuesFromDomNodeRow(cashAndCashEquivalentNodes);
			bsInfoBO_Y_1.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(0));
			bsInfoBO_Y_2.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(1));
			bsInfoBO_Y_3.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(2));
			bsInfoBO_Y_4.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(3));
			bsInfoBO_Y_5.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(4));

			List<?> shortTermInvestmentsList = (List<?>) page.getByXPath("//*[@id=\"data_i2\"]");
			DomNode shortTermInvestmentNodes = (DomNode) shortTermInvestmentsList.get(0);
			List<String> shortTermInvestmentVals = this.getOrderedRowValuesFromDomNodeRow(shortTermInvestmentNodes);
			bsInfoBO_Y_1.setShortTermInvestments(shortTermInvestmentVals.get(0));
			bsInfoBO_Y_2.setShortTermInvestments(shortTermInvestmentVals.get(1));
			bsInfoBO_Y_3.setShortTermInvestments(shortTermInvestmentVals.get(2));
			bsInfoBO_Y_4.setShortTermInvestments(shortTermInvestmentVals.get(3));
			bsInfoBO_Y_5.setShortTermInvestments(shortTermInvestmentVals.get(4));

			List<?> recievablesList = (List<?>) page.getByXPath("//*[@id=\"data_i3\"]");
			DomNode recievablesNodes = (DomNode) recievablesList.get(0);
			List<String> recievablesVals = this.getOrderedRowValuesFromDomNodeRow(recievablesNodes);
			bsInfoBO_Y_1.setReceivables(recievablesVals.get(0));
			bsInfoBO_Y_2.setReceivables(recievablesVals.get(1));
			bsInfoBO_Y_3.setReceivables(recievablesVals.get(2));
			bsInfoBO_Y_4.setReceivables(recievablesVals.get(3));
			bsInfoBO_Y_5.setReceivables(recievablesVals.get(4));

			List<?> longTermInvestmentsList = (List<?>) page.getByXPath("//*[@id=\"data_i11\"]");
			DomNode longTermInvestmentsNodes = (DomNode) longTermInvestmentsList.get(0);
			List<String> longTermInvestmentsVals = this.getOrderedRowValuesFromDomNodeRow(longTermInvestmentsNodes);
			bsInfoBO_Y_1.setLongTermInvestments(longTermInvestmentsVals.get(0));
			bsInfoBO_Y_2.setLongTermInvestments(longTermInvestmentsVals.get(1));
			bsInfoBO_Y_3.setLongTermInvestments(longTermInvestmentsVals.get(2));
			bsInfoBO_Y_4.setLongTermInvestments(longTermInvestmentsVals.get(3));
			bsInfoBO_Y_5.setLongTermInvestments(longTermInvestmentsVals.get(4));

			List<?> intangibleAssetsList = (List<?>) page.getByXPath("//*[@id=\"data_i13\"]");
			DomNode intangibleAssetNodes = (DomNode) intangibleAssetsList.get(0);
			List<String> intangibleAssetVals = this.getOrderedRowValuesFromDomNodeRow(intangibleAssetNodes);
			bsInfoBO_Y_1.setTotalIntangibleAssets(intangibleAssetVals.get(0));
			bsInfoBO_Y_2.setTotalIntangibleAssets(intangibleAssetVals.get(1));
			bsInfoBO_Y_3.setTotalIntangibleAssets(intangibleAssetVals.get(2));
			bsInfoBO_Y_4.setTotalIntangibleAssets(intangibleAssetVals.get(3));
			bsInfoBO_Y_5.setTotalIntangibleAssets(intangibleAssetVals.get(4));

			List<?> shortTermDebtList = (List<?>) page.getByXPath("//*[@id=\"data_i41\"]");
			DomNode shortTermDebtNodes = (DomNode) shortTermDebtList.get(0);
			List<String> shortTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(shortTermDebtNodes);
			bsInfoBO_Y_1.setShortTermDebt(shortTermDebtVals.get(0));
			bsInfoBO_Y_2.setShortTermDebt(shortTermDebtVals.get(1));
			bsInfoBO_Y_3.setShortTermDebt(shortTermDebtVals.get(2));
			bsInfoBO_Y_4.setShortTermDebt(shortTermDebtVals.get(3));
			bsInfoBO_Y_5.setShortTermDebt(shortTermDebtVals.get(4));

			List<?> payableList = (List<?>) page.getByXPath("//*[@id=\"data_i43\"]");
			DomNode payableListNodes = (DomNode) payableList.get(0);
			List<String> payableVals = this.getOrderedRowValuesFromDomNodeRow(payableListNodes);
			bsInfoBO_Y_1.setAccountsPayable(payableVals.get(0));
			bsInfoBO_Y_2.setAccountsPayable(payableVals.get(1));
			bsInfoBO_Y_3.setAccountsPayable(payableVals.get(2));
			bsInfoBO_Y_4.setAccountsPayable(payableVals.get(3));
			bsInfoBO_Y_5.setAccountsPayable(payableVals.get(4));

			List<?> longTermDebtList = (List<?>) page.getByXPath("//*[@id=\"data_i50\"]");
			DomNode longTermDebtNodes = (DomNode) longTermDebtList.get(0);
			List<String> longTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(longTermDebtNodes);
			bsInfoBO_Y_1.setLongTermDebt(longTermDebtVals.get(0));
			bsInfoBO_Y_2.setLongTermDebt(longTermDebtVals.get(1));
			bsInfoBO_Y_3.setLongTermDebt(longTermDebtVals.get(2));
			bsInfoBO_Y_4.setLongTermDebt(longTermDebtVals.get(3));
			bsInfoBO_Y_5.setLongTermDebt(longTermDebtVals.get(4));

			List<?> shareholdersList = (List<?>) page.getByXPath("//*[@id=\"data_ttg8\"]");
			DomNode shareholdersNodes = (DomNode) shareholdersList.get(0);
			List<String> shareholdersVals = this.getOrderedRowValuesFromDomNodeRow(shareholdersNodes);
			bsInfoBO_Y_1.setShareHoldersEquity(shareholdersVals.get(0));
			bsInfoBO_Y_2.setShareHoldersEquity(shareholdersVals.get(1));
			bsInfoBO_Y_3.setShareHoldersEquity(shareholdersVals.get(2));
			bsInfoBO_Y_4.setShareHoldersEquity(shareholdersVals.get(3));
			bsInfoBO_Y_5.setShareHoldersEquity(shareholdersVals.get(4));

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
