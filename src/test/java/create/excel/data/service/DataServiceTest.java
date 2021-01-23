/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
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
		CSVReader csvReader = new CSVReader(null);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(companies, null);

	}

	@Test
	public void testBuildTickerToStockNameMap() throws JSONException, IOException {
		Map<String, String> tickerToName = dataService.getTickerToStockName();
		assertEquals("Stock name is wrong for AAPL ", "Apple Inc.", tickerToName.get("AAPL"));

	}

	@Test
	public void testBuildTickerToStockIndustry() throws JSONException, IOException {
		Map<String, String> tickerToIndustry = dataService.getTickerToIndustry();
		assertEquals("Stock industry is wrong for AAPL ", "Consumer Electronics", tickerToIndustry.get("AAPL"));

	}

	@Test
	public void testTickerToStockSector() throws JSONException, IOException {
		Map<String, String> tickerToSector = dataService.getTickerToSector();
		assertEquals("Stock sector is wrong for AAPL ", "Technology", tickerToSector.get("AAPL"));

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
