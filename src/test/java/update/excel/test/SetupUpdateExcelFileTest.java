package update.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import update.excel.implementation.UpdateExcelFile;

public class SetupUpdateExcelFileTest {

	private static UpdateExcelFile updateExcel;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		updateExcel = new UpdateExcelFile();

	}

	@Test
	public void testNumSheets() {
		int numExcelSheets = updateExcel.getExcelFile().getNumberOfSheets();
		assertEquals("The file obtained does not have the correct number of sheets!", numExcelSheets, 21);

	}

	@Test
	public void testUpdatedStockList() {
		System.out.println("===================== BEGIN MANUAL TEST FOR UPDATES STOCK LIST ==============!");
		List<String> updatedStocks = updateExcel.getUpdatedStockList();
		for (String updatedStock : updatedStocks) {
			System.out.println(updatedStock);
		}
		System.out.println("===================== END MANUAL TEST FOR UPDATES STOCK LIST ==============!");
	}

	@Test
	public void testCurrentStockList() {
		System.out.println("===================== BEGIN MANUAL TEST FOR CURRENT STOCK LIST ==============!");
		List<String> currentStocks = updateExcel.getCurrentStockList();
		for (String currentStock : currentStocks) {
			System.out.println(currentStock);
		}
		System.out.println("===================== END MANUAL TEST FOR CURRENT STOCK LIST ==============!");
	}

	@Test
	public void testTickersToRemove() {
		System.out.println("===================== BEGIN MANUAL TEST FOR TICKERS TO REMOVE ==============!");
		List<String> tickers = updateExcel.getTickersToRemove();
		for (String ticker : tickers) {
			System.out.println(ticker);
		}
		System.out.println("===================== END MANUAL TEST FOR TICKERS TO REMOVE ==============!");

	}

	@Test
	public void testTickersToAdd() {
		System.out.println("===================== BEGIN MANUAL TEST FOR TICKERS TO ADD ==============!");
		List<String> tickers = updateExcel.getTickersToAdd();
		for (String ticker : tickers) {
			System.out.println(ticker);
		}
		System.out.println("===================== END MANUAL TEST FOR TICKERS TO ADD ==============!");

	}

	@Test
	public void testRemoveTickers() {
		System.out.println("===================== BEGIN MANUAL TEST FOR REMOVE TICKERS ==============!");

	}

}
