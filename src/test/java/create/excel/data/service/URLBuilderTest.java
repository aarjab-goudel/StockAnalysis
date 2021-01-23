/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.data.service;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

public class URLBuilderTest {
	private static URLBuilder urlBuilder;

	@BeforeClass
	public static void intializeFields() {
		CSVReader csvReader = new CSVReader(null);
		List<String> companies = csvReader.getStockList();
		urlBuilder = new URLBuilder(companies);
	}

	@Test
	public void testCompanyProfileUrls() {
		Iterator<Entry<String, String>> iterator = urlBuilder.getCompanyProfileURLMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> pair = iterator.next();
			String key = (String) (pair.getKey());
			String value = (String) (pair.getValue());
			assertEquals("The company profile urls are incorrect!", value, URLBuilder.getCompanyProfile() + key);
		}
	}

	@Test
	public void testCompanyRatioUrls() {
		Iterator<Entry<String, String>> iterator = urlBuilder.getCompanyRatioURLMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String key = (String) (entry.getKey());
			String value = (String) (entry.getValue());
			assertEquals("The ratio urls are incorrect!", value, URLBuilder.getCompanyRatio() + key);

		}
	}

	@Test
	public void testCompanyKeyMetricsUrls() {
		Iterator<Entry<String, String>> iterator = urlBuilder.getCompanyKeyMetricsMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String ticker = (String) (entry.getKey());
			String url = (String) (entry.getValue());
			assertEquals("The company metrics urls are incorrect!", url, URLBuilder.getCompanyKeyMetrics() + ticker);
		}
	}

}
