package start;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import create.excel.implementation.CreateStockSheetExcelFile;

public class Stock {

	private CreateStockSheetExcelFile stockSheetExcelFile;

	public Stock() throws IOException {
		System.out.println("STARTING STOCK SHEET");
		stockSheetExcelFile = new CreateStockSheetExcelFile();
		System.out.println("FINISHING STOCK SHEET");

	}

	public static void main(String[] args) throws JSONException, IOException, ParseException {
		new Stock();
	}

	public CreateStockSheetExcelFile getStockSheetExcelFile() {
		return stockSheetExcelFile;
	}

}
