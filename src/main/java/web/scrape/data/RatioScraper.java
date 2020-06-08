package web.scrape.data;

import static org.junit.Assert.assertEquals;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import create.excel.bo.RatioInfoBO;
import create.excel.enums.CommonSheetConstants;

public class RatioScraper extends FinancialScrapper {

	private static final String RATIO_URL = "http://financials.morningstar.com/ratios/r.html?t=";
	private Map<String, List<RatioInfoBO>> tickerToRatio;

	public RatioScraper(List<String> tickersToBuild) {
		super(tickersToBuild);
		this.tickerToRatio = this.buildTickerToRatioMap();
	}

	public Map<String, List<RatioInfoBO>> buildTickerToRatioMap() {
		Map<String, List<RatioInfoBO>> tickerToRatioMap = new HashMap<String, List<RatioInfoBO>>();
		for (String ticker : this.getTickersToBuild()) {
			List<RatioInfoBO> ratioInfoBOList = this.readRatioURL(ticker);
			assertEquals("The size in ratio sheet scraper for " + ticker + " is " + ratioInfoBOList.size(),
					ratioInfoBOList.size(), 5);
			tickerToRatioMap.put(ticker, ratioInfoBOList);
		}
		return tickerToRatioMap;
	}

	public List<RatioInfoBO> readRatioURL(String ticker) {

		List<RatioInfoBO> ratioInfoList = new ArrayList<RatioInfoBO>();

		RatioInfoBO ratioInfoBO_Y_1 = new RatioInfoBO(CommonSheetConstants.FIRST_YEAR.getCommonColumn(), ticker);
		RatioInfoBO ratioInfoBO_Y_2 = new RatioInfoBO(CommonSheetConstants.SECOND_YEAR.getCommonColumn(), ticker);
		RatioInfoBO ratioInfoBO_Y_3 = new RatioInfoBO(CommonSheetConstants.THIRD_YEAR.getCommonColumn(), ticker);
		RatioInfoBO ratioInfoBO_Y_4 = new RatioInfoBO(CommonSheetConstants.FOURTH_YEAR.getCommonColumn(), ticker);
		RatioInfoBO ratioInfoBO_Y_5 = new RatioInfoBO(CommonSheetConstants.FIFTH_YEAR.getCommonColumn(), ticker);

		try {
			System.out.println("Ticker: " + ticker + " Module: RatioScraper");
			String searchUrl = RATIO_URL + URLEncoder.encode(ticker, "UTF-8");
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getPage(searchUrl);
			webClient.waitForBackgroundJavaScript(10000);

			HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			List<?> anchorElements = (List<?>) page.getByXPath("//*[@id=\"keyStatWrap\"]/div/ul/li[4]/a");

			HtmlAnchor anchorToClick = (HtmlAnchor) anchorElements.get(0);
			anchorToClick.click();

			webClient.waitForBackgroundJavaScript(10000);

			page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

			List<?> currentRatioRowList = (List<?>) page.getByXPath("//*[@id=\"tab-financial\"]/table[2]/tbody/tr[2]");

			List<?> debtEquityRatioRowList = (List<?>) page
					.getByXPath("//*[@id=\"tab-financial\"]/table[2]/tbody/tr[8]");

			List<?> ratioDateList = (List<?>) page.getByXPath("//*[@id=\"tab-financial\"]/table[2]/thead/tr");

			HtmlTableRow currentRatioRow = (HtmlTableRow) currentRatioRowList.get(0);
			List<String> currentRatioList = this.getOrderedRatiosFromHtmlTableRow(currentRatioRow);
			ratioInfoBO_Y_1.setCurrentRatio(currentRatioList.get(0));
			ratioInfoBO_Y_2.setCurrentRatio(currentRatioList.get(1));
			ratioInfoBO_Y_3.setCurrentRatio(currentRatioList.get(2));
			ratioInfoBO_Y_4.setCurrentRatio(currentRatioList.get(3));
			ratioInfoBO_Y_5.setCurrentRatio(currentRatioList.get(4));

			HtmlTableRow debtEquityRatioRow = (HtmlTableRow) debtEquityRatioRowList.get(0);
			List<String> debtEquityRatioList = this.getOrderedRatiosFromHtmlTableRow(debtEquityRatioRow);
			ratioInfoBO_Y_1.setDebtToEquityRatio(debtEquityRatioList.get(0));
			ratioInfoBO_Y_2.setDebtToEquityRatio(debtEquityRatioList.get(1));
			ratioInfoBO_Y_3.setDebtToEquityRatio(debtEquityRatioList.get(2));
			ratioInfoBO_Y_4.setDebtToEquityRatio(debtEquityRatioList.get(3));
			ratioInfoBO_Y_5.setDebtToEquityRatio(debtEquityRatioList.get(4));

			HtmlTableRow dateRow = (HtmlTableRow) ratioDateList.get(0);
			List<String> dateList = this.getOrderedRatioDatesFromHtmlTableRow(dateRow);
			ratioInfoBO_Y_1.setRatioDate(dateList.get(0));
			ratioInfoBO_Y_2.setRatioDate(dateList.get(1));
			ratioInfoBO_Y_3.setRatioDate(dateList.get(2));
			ratioInfoBO_Y_4.setRatioDate(dateList.get(3));
			ratioInfoBO_Y_5.setRatioDate(dateList.get(4));

			ratioInfoList.add(ratioInfoBO_Y_1);
			ratioInfoList.add(ratioInfoBO_Y_2);
			ratioInfoList.add(ratioInfoBO_Y_3);
			ratioInfoList.add(ratioInfoBO_Y_4);
			ratioInfoList.add(ratioInfoBO_Y_5);

			webClient.close();

			return ratioInfoList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratioInfoList;
	}

	public List<String> getOrderedRatiosFromHtmlTableRow(HtmlTableRow tableRow) {
		List<String> orderedRow = new ArrayList<String>();
		if (tableRow != null) {
			Iterator<DomElement> iterator = tableRow.getChildElements().iterator();
			iterator.next();
			while (iterator.hasNext()) {
				HtmlTableDataCell htmlTableCell = (HtmlTableDataCell) iterator.next();
				DomText tableCellText = (DomText) htmlTableCell.getFirstChild();
				String tableCellString = tableCellText.toString();
				orderedRow.add(tableCellString);
			}
		}
		Collections.reverse(orderedRow);
		List<String> firstFiveRatios = orderedRow.stream().limit(RatioScraper.getNumYears() + 1)
				.collect(Collectors.toList());
		firstFiveRatios.remove(0);
		return firstFiveRatios;
	}

	public List<String> getOrderedRatioDatesFromHtmlTableRow(HtmlTableRow tableRow) {
		List<String> orderedRow = new ArrayList<String>();
		if (tableRow != null) {
			Iterator<DomElement> iterator = tableRow.getChildElements().iterator();
			while (iterator.hasNext()) {
				HtmlTableHeaderCell htmlTableCell = (HtmlTableHeaderCell) iterator.next();
				DomText tableCellText = (DomText) htmlTableCell.getFirstChild();
				String tableCellString = tableCellText.toString();
				orderedRow.add(tableCellString);
			}
		}
		Collections.reverse(orderedRow);
		List<String> firstFiveDates = orderedRow.stream().limit(RatioScraper.getNumYears() + 1)
				.collect(Collectors.toList());
		firstFiveDates.remove(0);
		return firstFiveDates;
	}

	public Map<String, List<RatioInfoBO>> getTickerToRatio() {
		return tickerToRatio;
	}

}
