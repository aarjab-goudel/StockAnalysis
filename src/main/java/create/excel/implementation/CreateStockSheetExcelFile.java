/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.implementation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import create.excel.bo.StockDataInfoBO;
import create.excel.data.service.CSVReader;
import create.excel.data.service.DataService;
import create.excel.data.service.SaveExcel;
import javafx.concurrent.Task;

public class CreateStockSheetExcelFile extends CreateExcelTemplate {
	private static final Integer NUM_SHEETS = 1;
	private Workbook excelFile;
	private StockSheet stockDataSheet;
	private DataService dataService;
	private Map<String, String> tickerToStockName;
	private Map<String, String> tickerToSector;
	private Map<String, String> tickerToIndustry;
	private Map<String, StockDataInfoBO> tickerToStockData;
	private static final String EVERYTHING_DONE_PROPERTY_CHANGE_NAME = "isEverythingDoneForStock";

	private static final String FILE_PATH = SaveExcel.getStockSheetFilePath();

	public CreateStockSheetExcelFile(Task<Void> task, List<String> unOrderedStockListFromTextField) throws IOException {
		super(task);
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(task);
		dataService.buildURLData(companies);

		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
		tickerToStockName = dataService.getTickerToStockName();
		tickerToIndustry = dataService.getTickerToIndustry();
		tickerToSector = dataService.getTickerToSector();
		tickerToStockData = dataService.getTickerToStockDataInfo();
		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		this.setUpSheet();
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();
	}

	public CreateStockSheetExcelFile() throws IOException {
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(null);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(null);
		dataService.buildURLData(companies);
		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
		tickerToStockName = dataService.getTickerToStockName();
		tickerToIndustry = dataService.getTickerToIndustry();
		tickerToSector = dataService.getTickerToSector();
		tickerToStockData = dataService.getTickerToStockDataInfo();
		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		this.setUpSheet();
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();
	}

	public void setUpSheet() {
		stockDataSheet = new StockSheet(excelFile, 0, dataService);
		excelFile.setSheetName(0, "Stock Data");
	}

	public void setIsEverythingDone(boolean isEverythingDone) {
		this.getSupport().firePropertyChange(EVERYTHING_DONE_PROPERTY_CHANGE_NAME, this.isEverythingFinished(),
				isEverythingDone);
		super.setIsEverythingDone(isEverythingDone);
	}

	public Map<String, String> getTickerToStockName() {
		return tickerToStockName;
	}

	public Map<String, String> getTickerToSector() {
		return tickerToSector;
	}

	public Map<String, String> getTickerToIndustry() {
		return tickerToIndustry;
	}

	public Map<String, StockDataInfoBO> getTickerToStockData() {
		return tickerToStockData;
	}

	public StockSheet getStockDataSheet() {
		return stockDataSheet;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public static Integer getNumSheets() {
		return NUM_SHEETS;
	}

	public static String getEverythingDonePropertyChangeName() {
		return EVERYTHING_DONE_PROPERTY_CHANGE_NAME;
	}

}
