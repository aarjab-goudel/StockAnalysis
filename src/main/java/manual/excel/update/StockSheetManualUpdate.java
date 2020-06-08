package manual.excel.update;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONException;

import create.excel.bo.StockDataInfoBO;
import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;
import excel.library.CommonFinancialLibrary;
import excel.library.StockDataFinancialLibrary;
import web.scrape.data.StockSheetScraper;

public class StockSheetManualUpdate {

	private Sheet stockSheet;

	private static final String TICKER = "Ticker";

	private List<String> orderedCompanies;
	private DataService dataService;
	private StockSheetScraper stockSheetScraper;

	public StockSheetManualUpdate(Sheet stockSheet) throws JSONException, IOException, ParseException {
		this.stockSheet = stockSheet;
		this.orderedCompanies = CommonFinancialLibrary.readCurrentStockList(this.stockSheet);
		dataService = new DataService();
		dataService.buildURLData(orderedCompanies);
		dataService.buildStockSheetDataMaps();
		stockSheetScraper = new StockSheetScraper(this.orderedCompanies);

	}

	public Integer update_price_marketCap_priceChange_beta_dividendYield_columns() {
		Iterator<Row> rowIterator = stockSheet.iterator();
		DataFormatter dataFormatter = new DataFormatter();
		Integer orderedCompaniesIndex = 0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			String tickerValue = dataFormatter.formatCellValue(tickerCell);
			String orderedCompany = orderedCompanies.get(orderedCompaniesIndex);
			if (!tickerValue.equals(TICKER)) {
				assertEquals("getOrderedCompanies is not ordered!", tickerValue, orderedCompany);
				StockDataInfoBO stockInfo = this.getStockSheetScraper().getTickerToStockInfo().get(tickerValue);
				StockDataFinancialLibrary.writeStockData(dataService, stockInfo, row);

				orderedCompaniesIndex++;
			}
		}
		return orderedCompaniesIndex;
	}

	public Sheet getStockSheet() {
		return stockSheet;
	}

	public static String getTicker() {
		return TICKER;
	}

	public List<String> getOrderedCompanies() {
		return orderedCompanies;
	}

	public DataService getDataService() {
		return dataService;
	}

	public StockSheetScraper getStockSheetScraper() {
		return stockSheetScraper;
	}

}
