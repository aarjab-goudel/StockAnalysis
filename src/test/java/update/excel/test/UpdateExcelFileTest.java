package update.excel.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import excel.library.CommonFinancialLibrary;
import update.excel.implementation.UpdateExcelFile;

public class UpdateExcelFileTest {

	private static UpdateExcelFile updateExcel;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		updateExcel = new UpdateExcelFile();
	}

	@Test
	public void testRemovedStocks() {
		List<String> listOfCurrentTickers = CommonFinancialLibrary
				.readCurrentStockList(updateExcel.getExcelFile().getSheetAt(0));
		List<String> tickersToRemove = updateExcel.getTickersToRemove();
		for (String tickerToRemove : tickersToRemove) {
			assertFalse(tickerToRemove + " has not been removed! ", listOfCurrentTickers.contains(tickerToRemove));
		}
	}

	@Test
	public void testAddedStocks() {
		List<String> listOfCurrentTickers = CommonFinancialLibrary
				.readCurrentStockList(updateExcel.getExcelFile().getSheetAt(0));
		List<String> tickersToAdd = updateExcel.getTickersToAdd();
		for (String tickerToAdd : tickersToAdd) {
			assertTrue(tickerToAdd + " has not been added! ", listOfCurrentTickers.contains(tickerToAdd));
		}
	}

}
