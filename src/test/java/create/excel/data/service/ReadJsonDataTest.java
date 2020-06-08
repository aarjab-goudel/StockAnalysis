package create.excel.data.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadJsonDataTest {
	private static ReadJsonData readJson;
	private static URLBuilder urlBuilder;

	@BeforeClass
	public static void initializeFields() {
		readJson = new ReadJsonData();
		CSVReader csvReader = new CSVReader();
		List<String> companies = csvReader.getStockList();
		urlBuilder = new URLBuilder(companies);
	}

	@Test
	public void testReadJSONFromURL() throws IOException, JSONException {
		Map<String, String> profileUrls = urlBuilder.getCompanyProfileURLMap();
		Iterator<Entry<String, String>> iterator = profileUrls.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> pair = iterator.next();
			String url = pair.getValue();
			JSONObject jsonObj = readJson.readJsonFromUrl(url);
			assertEquals(pair.getKey(), jsonObj.getString("symbol"));
		}
	}

}
