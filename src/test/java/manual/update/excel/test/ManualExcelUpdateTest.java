package manual.update.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import manual.excel.update.ManualExcelUpdate;
import manual.excel.update.StockSheetManualUpdate;

public class ManualExcelUpdateTest {

	private static ManualExcelUpdate manualUpdateExcel;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		manualUpdateExcel = new ManualExcelUpdate();
	}

	@Test
	public void test_currentPrice_beta_marketCap_todayPriceChange_dividendYield() {
		StockSheetManualUpdate stockSheetManualUpdate = manualUpdateExcel.getStockSheetManualUpdate();
		Integer updateCount = stockSheetManualUpdate.update_price_marketCap_priceChange_beta_dividendYield_columns();
		assertEquals("Did not manually update all of the tickers!", updateCount,
				(Integer) stockSheetManualUpdate.getOrderedCompanies().size());
	}

}
