package create.excel.data.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataServiceTest {
	private static DataService dataService;

	@BeforeClass
	public static void intializeFields() throws JSONException, IOException, ParseException {
		CSVReader csvReader = new CSVReader();
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(companies);

	}

	@Test
	public void testBuildTickerToStockNameMap() throws JSONException, IOException {
		Map<String, String> tickerToName = dataService.getTickerToStockName();
		assertEquals("Stock name is wrong for AAPL ", "Apple Inc.", tickerToName.get("AAPL"));
		assertEquals("Stock name is wrong for F ", "Ford Motor Company", tickerToName.get("F"));
		assertEquals("Stock name is wrong for XOM ", "Exxon Mobil Corporation", tickerToName.get("XOM"));
		assertEquals("Stock name is wrong for COF ", "Capital One Financial Corporation", tickerToName.get("COF"));
		assertEquals("Stock name is wrong for JNJ ", "Johnson & Johnson", tickerToName.get("JNJ"));
		assertEquals("Stock name is wrong for PFE", "Pfizer Inc.", tickerToName.get("PFE"));
	}

	@Test
	public void testBuildTickerToStockIndustry() throws JSONException, IOException {
		Map<String, String> tickerToIndustry = dataService.getTickerToIndustry();
		assertEquals("Stock industry is wrong for AAPL ", "Computer Hardware", tickerToIndustry.get("AAPL"));
		assertEquals("Stock industry is wrong for F ", "Autos", tickerToIndustry.get("F"));
		assertEquals("Stock industry is wrong for XOM ", "Oil & Gas - Integrated", tickerToIndustry.get("XOM"));
		assertEquals("Stock industry is wrong for COF ", "Credit Services", tickerToIndustry.get("COF"));
		assertEquals("Stock industry is wrong for JNJ ", "Drug Manufacturers", tickerToIndustry.get("JNJ"));
		assertEquals("Stock industry is wrong for PFE", "Drug Manufacturers", tickerToIndustry.get("PFE"));
	}

	@Test
	public void testTickerToStockSector() throws JSONException, IOException {
		Map<String, String> tickerToSector = dataService.getTickerToSector();
		assertEquals("Stock sector is wrong for AAPL ", "Technology", tickerToSector.get("AAPL"));
		assertEquals("Stock sector is wrong for F ", "Consumer Cyclical", tickerToSector.get("F"));
		assertEquals("Stock sector is wrong for XOM ", "Energy", tickerToSector.get("XOM"));
		assertEquals("Stock sector is wrong for COF ", "Financial Services", tickerToSector.get("COF"));
		assertEquals("Stock sector is wrong for JNJ ", "Healthcare", tickerToSector.get("JNJ"));
		assertEquals("Stock sector is wrong for PFE", "Healthcare", tickerToSector.get("PFE"));
	}

	@Test
	public void testOrderedCompanyList() {
		System.out.println(" =================== BEGIN MANUAL TEST FOR orderedCompanyList ====================== ");
		List<String> orderedCompanies = dataService.getOrderedCompanies();
		Map<String, String> tickerToSector = dataService.getTickerToSector();
		for (String company : orderedCompanies) {
			String sector = tickerToSector.get(company);
			System.out.println(company + "|| " + sector);
		}
		System.out.println(" =================== END MANUAL TEST FOR orderedCompanyList ====================== ");
	}

}
