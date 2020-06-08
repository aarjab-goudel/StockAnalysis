package update.excel.implementation;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;

import create.excel.data.service.CSVReader;
import create.excel.data.service.DataService;
import create.excel.data.service.SaveExcel;
import excel.library.CommonFinancialLibrary;

public class UpdateExcelFile {
	private Workbook excelFile;
	private List<String> csvStockList;
	private List<String> currentStockList;
	private List<String> tickersToRemove;
	private List<String> tickersToAdd;
	private static final int TICKER_CELL = 1;

	private DataService dataService;

	public UpdateExcelFile()
			throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException, ParseException {
		try {
			excelFile = SaveExcel.getInstance();
			CSVReader csvReader = new CSVReader();
			List<String> companies = csvReader.getStockList();
			csvStockList = companies;
			currentStockList = CommonFinancialLibrary.readCurrentStockList(excelFile.getSheetAt(0));
			tickersToRemove = this.buildTickersToRemoveList();
			tickersToAdd = this.buildTickersToAddList();
			dataService = new DataService(tickersToAdd);
			StockSheetUpdate stockSheetUpdate = new StockSheetUpdate(0, excelFile.getSheet("Stock Data"),
					tickersToRemove, tickersToAdd, dataService);
			BalanceSheetUpdate bsUpdate = new BalanceSheetUpdate(0, excelFile.getSheet("BS"), tickersToRemove,
					tickersToAdd, dataService);
			BalanceSheetUpdate bsUpdateOne = new BalanceSheetUpdate(-1, excelFile.getSheet("BSOne"), tickersToRemove,
					tickersToAdd, dataService);
			BalanceSheetUpdate bsUpdateTwo = new BalanceSheetUpdate(-2, excelFile.getSheet("BSTwo"), tickersToRemove,
					tickersToAdd, dataService);
			BalanceSheetUpdate bsUpdateThree = new BalanceSheetUpdate(-3, excelFile.getSheet("BSThree"),
					tickersToRemove, tickersToAdd, dataService);
			BalanceSheetUpdate bsUpdateFour = new BalanceSheetUpdate(-4, excelFile.getSheet("BSFour"), tickersToRemove,
					tickersToAdd, dataService);
			bsUpdate.caluclateNewBSTickerData();
			bsUpdateOne.caluclateNewBSTickerData();
			bsUpdateTwo.caluclateNewBSTickerData();
			bsUpdateThree.caluclateNewBSTickerData();
			bsUpdateFour.caluclateNewBSTickerData();

			IncomeSheetUpdate incomeSheetUpdate = new IncomeSheetUpdate(0, excelFile.getSheet("IS"), tickersToRemove,
					tickersToAdd, dataService);
			IncomeSheetUpdate incomeSheetUpdateOne = new IncomeSheetUpdate(-1, excelFile.getSheet("ISOne"),
					tickersToRemove, tickersToAdd, dataService);
			IncomeSheetUpdate incomeSheetUpdateTwo = new IncomeSheetUpdate(-2, excelFile.getSheet("ISTwo"),
					tickersToRemove, tickersToAdd, dataService);
			IncomeSheetUpdate incomeSheetUpdateThree = new IncomeSheetUpdate(-3, excelFile.getSheet("ISThree"),
					tickersToRemove, tickersToAdd, dataService);
			IncomeSheetUpdate incomeSheetUpdateFour = new IncomeSheetUpdate(-4, excelFile.getSheet("ISFour"),
					tickersToRemove, tickersToAdd, dataService);
			incomeSheetUpdate.caluclateNewISTickerData();
			incomeSheetUpdateOne.caluclateNewISTickerData();
			incomeSheetUpdateTwo.caluclateNewISTickerData();
			incomeSheetUpdateThree.caluclateNewISTickerData();
			incomeSheetUpdateFour.caluclateNewISTickerData();

			CashFlowUpdate cashFlowUpdate = new CashFlowUpdate(0, excelFile.getSheet("CF"), tickersToRemove,
					tickersToAdd, dataService);
			CashFlowUpdate cashFlowUpdateOne = new CashFlowUpdate(-1, excelFile.getSheet("CFOne"), tickersToRemove,
					tickersToAdd, dataService);
			CashFlowUpdate cashFlowUpdateTwo = new CashFlowUpdate(-2, excelFile.getSheet("CFTwo"), tickersToRemove,
					tickersToAdd, dataService);
			CashFlowUpdate cashFlowUpdateThree = new CashFlowUpdate(-3, excelFile.getSheet("CFThree"), tickersToRemove,
					tickersToAdd, dataService);
			CashFlowUpdate cashFlowUpdateFour = new CashFlowUpdate(-4, excelFile.getSheet("CFFour"), tickersToRemove,
					tickersToAdd, dataService);
			cashFlowUpdate.caluclateNewCFTickerData();
			cashFlowUpdateOne.caluclateNewCFTickerData();
			cashFlowUpdateTwo.caluclateNewCFTickerData();
			cashFlowUpdateThree.caluclateNewCFTickerData();
			cashFlowUpdateFour.caluclateNewCFTickerData();

			RatioSheetUpdate ratioSheetUpdate = new RatioSheetUpdate(0, excelFile.getSheet("Ratios"), tickersToRemove,
					tickersToAdd, dataService);
			RatioSheetUpdate ratioSheetUpdateOne = new RatioSheetUpdate(-1, excelFile.getSheet("RatiosOne"),
					tickersToRemove, tickersToAdd, dataService);
			RatioSheetUpdate ratioSheetUpdateTwo = new RatioSheetUpdate(-2, excelFile.getSheet("RatiosTwo"),
					tickersToRemove, tickersToAdd, dataService);
			RatioSheetUpdate ratioSheetUpdateThree = new RatioSheetUpdate(-3, excelFile.getSheet("RatiosThree"),
					tickersToRemove, tickersToAdd, dataService);
			RatioSheetUpdate ratioSheetUpdateFour = new RatioSheetUpdate(-4, excelFile.getSheet("RatiosFour"),
					tickersToRemove, tickersToAdd, dataService);
			ratioSheetUpdate.caluclateNewRatiosTickerData();
			ratioSheetUpdateOne.caluclateNewRatiosTickerData();
			ratioSheetUpdateTwo.caluclateNewRatiosTickerData();
			ratioSheetUpdateThree.caluclateNewRatiosTickerData();
			ratioSheetUpdateFour.caluclateNewRatiosTickerData();

			stockSheetUpdate.caluclateNewStockTickerData();

			SaveExcel.closeAndSaveFile();
		} catch (IOException | EncryptedDocumentException ex) {
			ex.printStackTrace();
		}
	}

	private List<String> buildTickersToAddList() {
		List<String> tickersToAdd = new ArrayList<String>();
		for (String updatedTicker : csvStockList) {
			if (!currentStockList.contains(updatedTicker)) {
				tickersToAdd.add(updatedTicker);
			}
		}
		return tickersToAdd;
	}

	private List<String> buildTickersToRemoveList() {
		List<String> tickersToRemove = new ArrayList<String>();
		for (String currentTicker : currentStockList) {
			if (!csvStockList.contains(currentTicker)) {
				tickersToRemove.add(currentTicker);
			}
		}
		return tickersToRemove;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(Workbook excelFile) {
		this.excelFile = excelFile;
	}

	public List<String> getUpdatedStockList() {
		return csvStockList;
	}

	public List<String> getCurrentStockList() {
		return currentStockList;
	}

	public static int getTickerCell() {
		return TICKER_CELL;
	}

	public List<String> getTickersToRemove() {
		return tickersToRemove;
	}

	public List<String> getTickersToAdd() {
		return tickersToAdd;
	}

	public DataService getDataService() {
		return dataService;
	}

}
