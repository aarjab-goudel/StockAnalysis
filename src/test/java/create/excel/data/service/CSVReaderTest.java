/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.data.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class CSVReaderTest {
	private static CSVReader csvReader;

	@BeforeClass
	public static void initializeFields() {
		csvReader = new CSVReader(null);
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
