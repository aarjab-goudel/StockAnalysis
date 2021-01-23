/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.data.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONException;

import annual.web.scrape.data.AnnualBalanceSheetScraper;
import annual.web.scrape.data.AnnualCashSheetScraper;
import annual.web.scrape.data.AnnualIncomeSheetScraper;
import annual.web.scrape.data.AnnualRatioScraper;
import annual.web.scrape.data.AnnualStockSheetScraper;
import annual.web.scrape.data.AnnualWebScraper;
import create.excel.bo.BSInfoBO;
import create.excel.bo.CFInfoBO;
import create.excel.bo.ISInfoBO;
import create.excel.bo.RatioInfoBO;
import create.excel.bo.StockDataInfoBO;
import javafx.concurrent.Task;
import log.excel.ScrapperLogger;
import quarterly.web.scrape.data.QuarterlyBalanceSheetScraper;
import quarterly.web.scrape.data.QuarterlyCashSheetScraper;
import quarterly.web.scrape.data.QuarterlyIncomeSheetScraper;
import quarterly.web.scrape.data.QuarterlyWebScraper;

public class DataService {
	private URLBuilder urlBuilder;
	private List<String> orderedCompanies;
	private List<String> unorderedCompanies;

	private Map<String, List<BSInfoBO>> quarterlyTickerToBSInfo;
	private Map<String, List<CFInfoBO>> quarterlyTickerToCFInfo;
	private Map<String, List<ISInfoBO>> quarterlyTickerToISInfo;

	private Map<String, List<BSInfoBO>> annualTickerToBSInfo;
	private Map<String, List<CFInfoBO>> annualTickerToCFInfo;
	private Map<String, List<ISInfoBO>> annualTickerToISInfo;
	private Map<String, List<RatioInfoBO>> tickerToRatioInfo;
	private Map<String, StockDataInfoBO> tickerToStockDataInfo;

	private Map<String, String> tickerToSector;
	private Map<String, String> tickerToIndustry;
	private Map<String, String> tickerToStockName;

	private ReadJsonData readJsonData;
	private Task<Void> task;

	public DataService(List<String> companies, Task<Void> task) throws JSONException, IOException, ParseException {
		this.task = task;
		if ((task != null && !task.isCancelled()) || (task == null)) {
			this.unorderedCompanies = companies;
			this.buildURLData(companies);

			annualTickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
			annualTickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
			annualTickerToISInfo = new HashMap<String, List<ISInfoBO>>();
			tickerToRatioInfo = new HashMap<String, List<RatioInfoBO>>();
			tickerToStockDataInfo = new HashMap<String, StockDataInfoBO>();

			quarterlyTickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
			quarterlyTickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
			quarterlyTickerToISInfo = new HashMap<String, List<ISInfoBO>>();

			tickerToSector = new HashMap<String, String>();
			tickerToIndustry = new HashMap<String, String>();
			tickerToStockName = new HashMap<String, String>();

			orderedCompanies = new ArrayList<String>();

			this.buildAllMaps();
		}

	}

	public DataService(Task<Void> task) {
		this.task = task;
		annualTickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
		annualTickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
		annualTickerToISInfo = new HashMap<String, List<ISInfoBO>>();
		tickerToRatioInfo = new HashMap<String, List<RatioInfoBO>>();
		tickerToStockDataInfo = new HashMap<String, StockDataInfoBO>();

		quarterlyTickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
		quarterlyTickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
		quarterlyTickerToISInfo = new HashMap<String, List<ISInfoBO>>();

		tickerToSector = new HashMap<String, String>();
		tickerToIndustry = new HashMap<String, String>();
		tickerToStockName = new HashMap<String, String>();

		orderedCompanies = new ArrayList<String>();

	}

	public void buildURLData(List<String> companies) {
		this.unorderedCompanies = companies;
		urlBuilder = new URLBuilder(companies);
		readJsonData = new ReadJsonData();
	}

	public void buildAllMaps() throws JSONException, IOException, ParseException {
		this.buildStockSheetDataMaps();
		this.buildOrderedCompaniesListBySector(tickerToSector);
		this.build_annual_bs_cf_is_ratio_maps();
	}

	public void buildStockSheetDataMaps() throws IOException {
		AnnualStockSheetScraper stockSheetScraper = new AnnualStockSheetScraper(unorderedCompanies, task);
		tickerToStockDataInfo = stockSheetScraper.getTickerToStockInfo();
		Iterator<Entry<String, StockDataInfoBO>> iterator = tickerToStockDataInfo.entrySet().iterator();
		while ((task != null && !task.isCancelled() && iterator.hasNext()) || (task == null && iterator.hasNext())) {
			Map.Entry<String, StockDataInfoBO> entry = iterator.next();
			String ticker = entry.getKey();
			StockDataInfoBO stockData = entry.getValue();
			String sector = stockData.getSector();
			String industry = stockData.getIndustry();
			String stockName = stockData.getStockName();
			tickerToSector.put(ticker, sector);
			tickerToIndustry.put(ticker, industry);
			tickerToStockName.put(ticker, stockName);
		}

	}

	public void build_annual_bs_cf_is_ratio_maps() throws UnsupportedEncodingException, IOException {
		AnnualWebScraper webScraper = new AnnualWebScraper(orderedCompanies, task);
		annualTickerToBSInfo = webScraper.getBsScraper().getTickerToBalanceSheet();
		annualTickerToISInfo = webScraper.getIsScraper().getTickerToIncomeSheet();
		annualTickerToCFInfo = webScraper.getCfScraper().getTickerToCashSheet();
		tickerToRatioInfo = webScraper.getRatioScraper().getTickerToRatio();
	}

	public void buildAnnualBSMap() throws UnsupportedEncodingException, IOException {
		AnnualBalanceSheetScraper bsScraper = new AnnualBalanceSheetScraper(orderedCompanies, task);
		annualTickerToBSInfo = bsScraper.getTickerToBalanceSheet();
	}

	public void buildAnnualISMap() throws UnsupportedEncodingException, IOException {
		AnnualIncomeSheetScraper isScraper = new AnnualIncomeSheetScraper(orderedCompanies, task);
		annualTickerToISInfo = isScraper.getTickerToIncomeSheet();
	}

	public void buildAnnualCFMap() throws UnsupportedEncodingException, IOException {
		AnnualCashSheetScraper cfScraper = new AnnualCashSheetScraper(orderedCompanies, task);
		annualTickerToCFInfo = cfScraper.getTickerToCashSheet();
	}

	public void buildRatioMap() throws UnsupportedEncodingException, IOException {
		AnnualRatioScraper ratioScraper = new AnnualRatioScraper(orderedCompanies, task);
		tickerToRatioInfo = ratioScraper.getTickerToRatio();
	}

	public void buildQuarterlyBSMap() throws UnsupportedEncodingException, IOException {
		QuarterlyBalanceSheetScraper bsScraper = new QuarterlyBalanceSheetScraper(orderedCompanies, task);
		quarterlyTickerToBSInfo = bsScraper.getTickerToBalanceSheet();
	}

	public void buildQuarterlyISMap() throws UnsupportedEncodingException, IOException {
		QuarterlyIncomeSheetScraper isScraper = new QuarterlyIncomeSheetScraper(orderedCompanies, task);
		quarterlyTickerToISInfo = isScraper.getTickerToIncomeSheet();
	}

	public void buildQuarterlyCFMap() throws UnsupportedEncodingException, IOException {
		QuarterlyCashSheetScraper cfScraper = new QuarterlyCashSheetScraper(orderedCompanies, task);
		quarterlyTickerToCFInfo = cfScraper.getTickerToCashSheet();
	}

	public void build_quarterly_bs_cf_is_maps() throws UnsupportedEncodingException, IOException {
		QuarterlyWebScraper webScraper = new QuarterlyWebScraper(orderedCompanies, task);
		quarterlyTickerToBSInfo = webScraper.getBsScraper().getTickerToBalanceSheet();
		quarterlyTickerToISInfo = webScraper.getIsScraper().getTickerToIncomeSheet();
		quarterlyTickerToCFInfo = webScraper.getCfScraper().getTickerToCashSheet();
	}

	public void buildOrderedCompaniesListBySector(Map<String, String> map) {
		Map<String, String> tickerToIndustryMap = tickerToIndustry;
		Set<Entry<String, String>> entries = map.entrySet();
		List<Entry<String, String>> listOfEntries = new ArrayList<Entry<String, String>>(entries);
		Collections.sort(listOfEntries, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				try {
					ScrapperLogger.log("o2 Value for: " + o2.getKey() + " is: " + o2.getValue());
					ScrapperLogger.log("o1 Value for: " + o1.getKey() + " is: " + o1.getValue());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int sectorComparator = o2.getValue().compareTo(o1.getValue());
				// We want to keep the order of the companies consistent if the sector's are the
				// same
				String tickerOne = o1.getKey();
				String tickerTwo = o2.getKey();
				if (sectorComparator == 0) {
					String industryOne = tickerToIndustryMap.get(tickerOne);
					String industryTwo = tickerToIndustryMap.get(tickerTwo);
					int industryComparator = industryTwo.compareTo(industryOne);
					if (industryComparator == 0) {
						return tickerTwo.compareTo(tickerOne);
					} else {
						return industryComparator;
					}
				} else {
					return sectorComparator;
				}
			}
		});
		Collections.reverse(listOfEntries);
		for (Entry<String, String> company : listOfEntries) {
			orderedCompanies.add(company.getKey());
		}
	}

	public URLBuilder getUrlBuilder() {
		return urlBuilder;
	}

	public void setUrlBuilder(URLBuilder urlBuilder) {
		this.urlBuilder = urlBuilder;
	}

	public List<String> getOrderedCompanies() {
		return orderedCompanies;
	}

	public void setOrderedCompanies(List<String> orderedCompanies) {
		this.orderedCompanies = orderedCompanies;
	}

	public Map<String, List<BSInfoBO>> getTickerToBSInfo() {
		return annualTickerToBSInfo;
	}

	public void setTickerToBSInfo(Map<String, List<BSInfoBO>> tickerToBSInfo) {
		this.annualTickerToBSInfo = tickerToBSInfo;
	}

	public Map<String, List<CFInfoBO>> getTickerToCFInfo() {
		return annualTickerToCFInfo;
	}

	public void setTickerToCFInfo(Map<String, List<CFInfoBO>> tickerToCFInfo) {
		this.annualTickerToCFInfo = tickerToCFInfo;
	}

	public Map<String, List<ISInfoBO>> getTickerToISInfo() {
		return annualTickerToISInfo;
	}

	public void setTickerToISInfo(Map<String, List<ISInfoBO>> tickerToISInfo) {
		this.annualTickerToISInfo = tickerToISInfo;
	}

	public Map<String, List<RatioInfoBO>> getTickerToRatioInfo() {
		return tickerToRatioInfo;
	}

	public void setTickerToRatioInfo(Map<String, List<RatioInfoBO>> tickerToRatioInfo) {
		this.tickerToRatioInfo = tickerToRatioInfo;
	}

	public Map<String, StockDataInfoBO> getTickerToStockDataInfo() {
		return tickerToStockDataInfo;
	}

	public void setTickerToStockDataInfo(Map<String, StockDataInfoBO> tickerToStockDataInfo) {
		this.tickerToStockDataInfo = tickerToStockDataInfo;
	}

	public Map<String, String> getTickerToSector() {
		return tickerToSector;
	}

	public void setTickerToSector(Map<String, String> tickerToSector) {
		this.tickerToSector = tickerToSector;
	}

	public Map<String, String> getTickerToIndustry() {
		return tickerToIndustry;
	}

	public void setTickerToIndustry(Map<String, String> tickerToIndustry) {
		this.tickerToIndustry = tickerToIndustry;
	}

	public Map<String, String> getTickerToStockName() {
		return tickerToStockName;
	}

	public void setTickerToStockName(Map<String, String> tickerToStockName) {
		this.tickerToStockName = tickerToStockName;
	}

	public Map<String, List<BSInfoBO>> getQuarterlyTickerToBSInfo() {
		return quarterlyTickerToBSInfo;
	}

	public Map<String, List<CFInfoBO>> getQuarterlyTickerToCFInfo() {
		return quarterlyTickerToCFInfo;
	}

	public Map<String, List<ISInfoBO>> getQuarterlyTickerToISInfo() {
		return quarterlyTickerToISInfo;
	}

	public ReadJsonData getReadJsonData() {
		return readJsonData;
	}

	public void setReadJsonData(ReadJsonData readJsonData) {
		this.readJsonData = readJsonData;
	}

	public List<String> getUnorderedCompanies() {
		return unorderedCompanies;
	}

	public void setUnorderedCompanies(List<String> unorderedCompanies) {
		this.unorderedCompanies = unorderedCompanies;
	}

}
