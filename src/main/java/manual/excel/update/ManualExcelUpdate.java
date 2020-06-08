package manual.excel.update;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;

import create.excel.data.service.SaveExcel;

public class ManualExcelUpdate {
	private Workbook excelFile;
	private StockSheetManualUpdate stockSheetManualUpdate;

	public ManualExcelUpdate() throws JSONException, IOException, ParseException {
		excelFile = SaveExcel.getInstance();
		stockSheetManualUpdate = new StockSheetManualUpdate(excelFile.getSheet("Stock Data"));
		stockSheetManualUpdate.update_price_marketCap_priceChange_beta_dividendYield_columns();
		SaveExcel.closeAndSaveFile();
	}

	public StockSheetManualUpdate getStockSheetManualUpdate() {
		return stockSheetManualUpdate;
	}

}
