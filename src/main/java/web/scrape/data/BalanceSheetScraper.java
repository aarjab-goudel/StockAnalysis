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

import create.excel.bo.BSInfoBO;
import create.excel.enums.CommonSheetConstants;

public class BalanceSheetScraper extends FinancialScrapper {

	private static final String BALANCE_SHEET_URL = "http://financials.morningstar.com/balance-sheet/bs.html?t=";
	private Map<String, List<BSInfoBO>> tickerToBalanceSheet;

	public BalanceSheetScraper(List<String> tickersToBuild) {
		super(tickersToBuild);
		this.tickerToBalanceSheet = this.buildTickerToBalanceSheetMap();

	}

	public Map<String, List<BSInfoBO>> buildTickerToBalanceSheetMap() {
		Map<String, List<BSInfoBO>> tickerToBSInfoBOMap = new HashMap<String, List<BSInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			List<BSInfoBO> bsInfoBOList = this.readBalanceSheetURL(ticker);
			assertEquals("The size in balance sheet scraper for " + ticker + " is " + bsInfoBOList.size(),
					bsInfoBOList.size(), 5);
			tickerToBSInfoBOMap.put(ticker, bsInfoBOList);
		}
		return tickerToBSInfoBOMap;
	}

	public List<BSInfoBO> readBalanceSheetURL(String ticker) {

		List<BSInfoBO> bsInfoList = new ArrayList<BSInfoBO>();

		BSInfoBO bsInfoBO_Y_1 = new BSInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_2 = new BSInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_3 = new BSInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_4 = new BSInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		BSInfoBO bsInfoBO_Y_5 = new BSInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			System.out.println("Ticker: " + ticker + " Module: BalancesheetScraper");
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

			List<?> currencyType = (List<?>) page.getByXPath("//*[@id=\"unitsAndFiscalYear\"]/text()[2]");
			DomText currencyDomText = (DomText) currencyType.get(0);
			String currency = currencyDomText.toString().substring(0, 3);
			bsInfoBO_Y_1.setCurrencyType(currency);
			bsInfoBO_Y_2.setCurrencyType(currency);
			bsInfoBO_Y_3.setCurrencyType(currency);
			bsInfoBO_Y_4.setCurrencyType(currency);
			bsInfoBO_Y_5.setCurrencyType(currency);

			List<?> rootDivList = (List<?>) page.getByXPath("//*[@id=\"makeMeScrollable\"]");
			HtmlDivision rootDiv = (HtmlDivision) rootDivList.get(0);

			DomNode yearContents = rootDiv.getFirstChild().getFirstChild().getFirstChild().getFirstChild();

			List<String> balanceSheetDates = this.getOrderedDateValuesFromDomNodeRow(yearContents);
			bsInfoBO_Y_1.setBsDate(balanceSheetDates.get(0));
			bsInfoBO_Y_2.setBsDate(balanceSheetDates.get(1));
			bsInfoBO_Y_3.setBsDate(balanceSheetDates.get(2));
			bsInfoBO_Y_4.setBsDate(balanceSheetDates.get(3));
			bsInfoBO_Y_5.setBsDate(balanceSheetDates.get(4));

			DomNode assetsContents = rootDiv.getFirstChild().getFirstChild().getFirstChild().getChildNodes().get(3);
			DomNode currentAssetsContents = assetsContents.getChildNodes().get(2);
			DomNode cashAndShortInvestmentsContents = currentAssetsContents.getChildNodes().get(2);
			DomNode nonCurrentAssetsContent = assetsContents.getChildNodes().get(5);

			DomNode cashAndCashEquivalentRow = cashAndShortInvestmentsContents.getFirstChild();
			DomNode shortTermInvestmentRow = cashAndShortInvestmentsContents.getChildNodes().get(1);
			DomNode receivablesRow = currentAssetsContents.getChildNodes().get(3);

			DomNode longTermInvestmentRow = nonCurrentAssetsContent.getChildNodes().get(3);
			DomNode intangibleAssetsRow = nonCurrentAssetsContent.getChildNodes().get(6);

			List<String> cashAndCashEquivalentVals = this.getOrderedRowValuesFromDomNodeRow(cashAndCashEquivalentRow);
			bsInfoBO_Y_1.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(0));
			bsInfoBO_Y_2.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(1));
			bsInfoBO_Y_3.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(2));
			bsInfoBO_Y_4.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(3));
			bsInfoBO_Y_5.setCashAndCashEquivalent(cashAndCashEquivalentVals.get(4));

			List<String> shortTermInvestmentsVals = this.getOrderedRowValuesFromDomNodeRow(shortTermInvestmentRow);
			bsInfoBO_Y_1.setShortTermInvestments(shortTermInvestmentsVals.get(0));
			bsInfoBO_Y_2.setShortTermInvestments(shortTermInvestmentsVals.get(1));
			bsInfoBO_Y_3.setShortTermInvestments(shortTermInvestmentsVals.get(2));
			bsInfoBO_Y_4.setShortTermInvestments(shortTermInvestmentsVals.get(3));
			bsInfoBO_Y_5.setShortTermInvestments(shortTermInvestmentsVals.get(4));

			List<String> receivablesVals = this.getOrderedRowValuesFromDomNodeRow(receivablesRow);
			bsInfoBO_Y_1.setReceivables(receivablesVals.get(0));
			bsInfoBO_Y_2.setReceivables(receivablesVals.get(1));
			bsInfoBO_Y_3.setReceivables(receivablesVals.get(2));
			bsInfoBO_Y_4.setReceivables(receivablesVals.get(3));
			bsInfoBO_Y_5.setReceivables(receivablesVals.get(4));

			List<String> longTermInvestmentVals = this.getOrderedRowValuesFromDomNodeRow(longTermInvestmentRow);
			bsInfoBO_Y_1.setLongTermInvestments(longTermInvestmentVals.get(0));
			bsInfoBO_Y_2.setLongTermInvestments(longTermInvestmentVals.get(1));
			bsInfoBO_Y_3.setLongTermInvestments(longTermInvestmentVals.get(2));
			bsInfoBO_Y_4.setLongTermInvestments(longTermInvestmentVals.get(3));
			bsInfoBO_Y_5.setLongTermInvestments(longTermInvestmentVals.get(4));

			List<String> intangibleAssetsVal = this.getOrderedRowValuesFromDomNodeRow(intangibleAssetsRow);
			bsInfoBO_Y_1.setTotalIntangibleAssets(intangibleAssetsVal.get(0));
			bsInfoBO_Y_2.setTotalIntangibleAssets(intangibleAssetsVal.get(1));
			bsInfoBO_Y_3.setTotalIntangibleAssets(intangibleAssetsVal.get(2));
			bsInfoBO_Y_4.setTotalIntangibleAssets(intangibleAssetsVal.get(3));
			bsInfoBO_Y_5.setTotalIntangibleAssets(intangibleAssetsVal.get(4));

			DomNode liabilitiesContent = rootDiv.getFirstChild().getFirstChild().getFirstChild().getChildNodes().get(6)
					.getChildNodes().get(2);
			DomNode totalShareHoldersContent = rootDiv.getFirstChild().getFirstChild().getFirstChild().getChildNodes()
					.get(6).getChildNodes().get(5);
			DomNode shortTermDebtAndAccountsPayable = liabilitiesContent.getChildNodes().get(2);

			DomNode shortTermDebtRow = shortTermDebtAndAccountsPayable.getFirstChild();
			DomNode payableRow = shortTermDebtAndAccountsPayable.getChildNodes().get(2);
			DomNode longTermDebtRow = liabilitiesContent.getChildNodes().get(5).getFirstChild();

			List<String> shortTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(shortTermDebtRow);
			bsInfoBO_Y_1.setShortTermDebt(shortTermDebtVals.get(0));
			bsInfoBO_Y_2.setShortTermDebt(shortTermDebtVals.get(1));
			bsInfoBO_Y_3.setShortTermDebt(shortTermDebtVals.get(2));
			bsInfoBO_Y_4.setShortTermDebt(shortTermDebtVals.get(3));
			bsInfoBO_Y_5.setShortTermDebt(shortTermDebtVals.get(4));

			List<String> payableVals = this.getOrderedRowValuesFromDomNodeRow(payableRow);
			bsInfoBO_Y_1.setAccountsPayable(payableVals.get(0));
			bsInfoBO_Y_2.setAccountsPayable(payableVals.get(1));
			bsInfoBO_Y_3.setAccountsPayable(payableVals.get(2));
			bsInfoBO_Y_4.setAccountsPayable(payableVals.get(3));
			bsInfoBO_Y_5.setAccountsPayable(payableVals.get(4));

			List<String> longTermDebtVals = this.getOrderedRowValuesFromDomNodeRow(longTermDebtRow);
			bsInfoBO_Y_1.setLongTermDebt(longTermDebtVals.get(0));
			bsInfoBO_Y_2.setLongTermDebt(longTermDebtVals.get(1));
			bsInfoBO_Y_3.setLongTermDebt(longTermDebtVals.get(2));
			bsInfoBO_Y_4.setLongTermDebt(longTermDebtVals.get(3));
			bsInfoBO_Y_5.setLongTermDebt(longTermDebtVals.get(4));

			DomNode totalShareHoldersRow = totalShareHoldersContent.getChildNodes().get(7);

			List<String> shareholdersVals = this.getOrderedRowValuesFromDomNodeRow(totalShareHoldersRow);
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
			e.printStackTrace();
		}
		return bsInfoList;
	}

	public Map<String, List<BSInfoBO>> getTickerToBalanceSheet() {
		return tickerToBalanceSheet;
	}

}
