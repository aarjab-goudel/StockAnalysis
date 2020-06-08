package create.excel.data.service;

import java.io.IOException;
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

import create.excel.bo.BSInfoBO;
import create.excel.bo.CFInfoBO;
import create.excel.bo.ISInfoBO;
import create.excel.bo.RatioInfoBO;
import create.excel.bo.StockDataInfoBO;
import web.scrape.data.StockSheetScraper;
import web.scrape.data.WebScraper;

public class DataService {
	private URLBuilder urlBuilder;
	private List<String> orderedCompanies;
	private List<String> unorderedCompanies;

	private Map<String, List<BSInfoBO>> tickerToBSInfo;
	private Map<String, List<CFInfoBO>> tickerToCFInfo;
	private Map<String, List<ISInfoBO>> tickerToISInfo;
	private Map<String, List<RatioInfoBO>> tickerToRatioInfo;
	private Map<String, StockDataInfoBO> tickerToStockDataInfo;

	private Map<String, String> tickerToSector;
	private Map<String, String> tickerToIndustry;
	private Map<String, String> tickerToStockName;

	private ReadJsonData readJsonData;

	public DataService(List<String> companies) throws JSONException, IOException, ParseException {
		this.unorderedCompanies = companies;
		this.buildURLData(companies);

		tickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
		tickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
		tickerToISInfo = new HashMap<String, List<ISInfoBO>>();
		tickerToRatioInfo = new HashMap<String, List<RatioInfoBO>>();
		tickerToStockDataInfo = new HashMap<String, StockDataInfoBO>();

		tickerToSector = new HashMap<String, String>();
		tickerToIndustry = new HashMap<String, String>();
		tickerToStockName = new HashMap<String, String>();

		orderedCompanies = new ArrayList<String>();

		this.buildAllMaps();

	}

	public DataService() {

		tickerToBSInfo = new HashMap<String, List<BSInfoBO>>();
		tickerToCFInfo = new HashMap<String, List<CFInfoBO>>();
		tickerToISInfo = new HashMap<String, List<ISInfoBO>>();
		tickerToRatioInfo = new HashMap<String, List<RatioInfoBO>>();
		tickerToStockDataInfo = new HashMap<String, StockDataInfoBO>();

		tickerToSector = new HashMap<String, String>();
		tickerToIndustry = new HashMap<String, String>();
		tickerToStockName = new HashMap<String, String>();

		orderedCompanies = new ArrayList<String>();

	}

	public void buildURLData(List<String> companies) {
		urlBuilder = new URLBuilder(companies);
		readJsonData = new ReadJsonData();
	}

	public void buildAllMaps() throws JSONException, IOException, ParseException {
		this.buildStockSheetDataMaps();
		this.buildOrderedCompaniesListBySector(tickerToSector);
		this.build_bs_cf_is_ratio_maps();
	}

	public void buildStockSheetDataMaps() {
		StockSheetScraper stockSheetScraper = new StockSheetScraper(unorderedCompanies);
		tickerToStockDataInfo = stockSheetScraper.getTickerToStockInfo();
		Iterator<Entry<String, StockDataInfoBO>> iterator = tickerToStockDataInfo.entrySet().iterator();
		while (iterator.hasNext()) {
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

	public void build_bs_cf_is_ratio_maps() {
		WebScraper webScraper = new WebScraper(orderedCompanies);
		tickerToBSInfo = webScraper.getBsScraper().getTickerToBalanceSheet();
		tickerToISInfo = webScraper.getIsScraper().getTickerToIncomeSheet();
		tickerToCFInfo = webScraper.getCfScraper().getTickerToCashSheet();
		tickerToRatioInfo = webScraper.getRatioScraper().getTickerToRatio();

	}

	private void buildOrderedCompaniesListBySector(Map<String, String> map) {
		Map<String, String> tickerToIndustryMap = tickerToIndustry;
		Set<Entry<String, String>> entries = map.entrySet();
		List<Entry<String, String>> listOfEntries = new ArrayList<Entry<String, String>>(entries);
		Collections.sort(listOfEntries, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
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
		return tickerToBSInfo;
	}

	public void setTickerToBSInfo(Map<String, List<BSInfoBO>> tickerToBSInfo) {
		this.tickerToBSInfo = tickerToBSInfo;
	}

	public Map<String, List<CFInfoBO>> getTickerToCFInfo() {
		return tickerToCFInfo;
	}

	public void setTickerToCFInfo(Map<String, List<CFInfoBO>> tickerToCFInfo) {
		this.tickerToCFInfo = tickerToCFInfo;
	}

	public Map<String, List<ISInfoBO>> getTickerToISInfo() {
		return tickerToISInfo;
	}

	public void setTickerToISInfo(Map<String, List<ISInfoBO>> tickerToISInfo) {
		this.tickerToISInfo = tickerToISInfo;
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

//	public Map<String, String> getTickerToBeta() {
//		return tickerToBeta;
//	}
//
//	public void setTickerToBeta(Map<String, String> tickerToBeta) {
//		this.tickerToBeta = tickerToBeta;
//	}
//
//	public Map<String, String> getTickerToStockPrice() {
//		return tickerToStockPrice;
//	}
//
//	public void setTickerToStockPrice(Map<String, String> tickerToStockPrice) {
//		this.tickerToStockPrice = tickerToStockPrice;
//	}
//
//	public Map<String, String> getTickerToChangePercentage() {
//		return tickerToChangePercentage;
//	}
//
//	public void setTickerToChangePercentage(Map<String, String> tickerToChangePercentage) {
//		this.tickerToChangePercentage = tickerToChangePercentage;
//	}
//
//	public Map<String, String> getTickerToMarketCapitalization() {
//		return tickerToMarketCapitalization;
//	}
//
//	public void setTickerToMarketCapitalization(Map<String, String> tickerToMarketCapitalization) {
//		this.tickerToMarketCapitalization = tickerToMarketCapitalization;
//	}

	public ReadJsonData getReadJsonData() {
		return readJsonData;
	}

	public void setReadJsonData(ReadJsonData readJsonData) {
		this.readJsonData = readJsonData;
	}

}
