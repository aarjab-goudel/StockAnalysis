package create.excel.data.service;
import org.junit.*;

import create.excel.data.service.CSVReader;
import java.util.List;

public class CSVReaderTest {
	private static CSVReader csvReader;
	
	@BeforeClass
	public static void initializeFields() {
		csvReader = new CSVReader();
	}
	
	@Test
	public void testCSVParse() {
		List<String> stocks = csvReader.getStockList();
		System.out.println("===========================================================================");
		System.out.println("This is a manual test for the stocks that will be analyzed by this program");
		if (stocks.isEmpty()) {
			System.out.println("There are zero valid stocks to analyze!");
		}
		for (int i = 0; i < stocks.size(); i++) {
			System.out.println(i + 1 + ". " + stocks.get(i));
		}
		System.out.println("===========================================================================");
	} 
	
	

}
