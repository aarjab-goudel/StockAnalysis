/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.implementation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;

import calculation.excel.implementation.BSSheetCalculation;
import calculation.excel.implementation.CFSheetCalculation;
import calculation.excel.implementation.ISSheetCalculation;
import calculation.excel.implementation.RatioSheetCalculation;
import calculation.excel.implementation.StockSheetCalculation;
import create.excel.data.service.CSVReader;
import create.excel.data.service.DataService;
import create.excel.data.service.SaveExcel;
import javafx.concurrent.Task;

public class CreateAnnualExcelFile extends CreateExcelTemplate {
	private static final Integer NUM_SHEETS = 21;
	private Workbook excelFile;
	private StockSheet stockDataSheet;
	private BSSheet BSSheet;
	private BSSheet BSSheetMinusOne;
	private BSSheet BSSheetMinusTwo;
	private BSSheet BSSheetMinusThree;
	private BSSheet BSSheetMinusFour;
	private ISSheet ISSheet;
	private ISSheet ISSheetMinusOne;
	private ISSheet ISSheetMinusTwo;
	private ISSheet ISSheetMinusThree;
	private ISSheet ISSheetMinusFour;
	private CFSheet CFSheet;
	private CFSheet CFSheetMinusOne;
	private CFSheet CFSheetMinusTwo;
	private CFSheet CFSheetMinusThree;
	private CFSheet CFSheetMinusFour;
	private RatioSheet RatioSheet;
	private RatioSheet RatioSheetMinusOne;
	private RatioSheet RatioSheetMinusTwo;
	private RatioSheet RatioSheetMinusThree;
	private RatioSheet RatioSheetMinusFour;
	private static final String EVERYTHING_DONE_PROPERTY_CHANGE_NAME = "isEverythingDoneForAnnual";

	private static final String RATIO_PROPERTY_CHANGE_NAME = "isRatioSheetDone";

	private DataService dataService;
	private static final String FILE_PATH = SaveExcel.getAnnualFilePath();

	public CreateAnnualExcelFile(Task<Void> createAnnualExcelFileTask, boolean generateEverything,
			List<String> unOrderedStockListFromTextField) {
		super(createAnnualExcelFileTask);
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(createAnnualExcelFileTask);
		dataService.setUnorderedCompanies(companies);
		dataService.buildURLData(companies);
	}

	public CreateAnnualExcelFile(Task<Void> createAnnualExcelFileTask, List<String> unOrderedStockListFromTextField)
			throws IOException, JSONException, ParseException {
		super(createAnnualExcelFileTask);

		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(companies, createAnnualExcelFileTask);
		this.setUpSheets();
		this.calculateStockData();
		this.checkTaskAndSave();

	}

	public CreateAnnualExcelFile() throws JSONException, IOException, ParseException {
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(null);
		List<String> companies = csvReader.getStockList();
		Task<Void> task = new Task<Void>() {
			public Void call() {
				return null;
			}
		};
		dataService = new DataService(companies, task);
		this.setUpSheets();
		this.calculateStockData();

		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();
	}

	public void checkTaskAndSave() throws IOException {
		if (!this.getTask().isCancelled()) {
			FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
			excelFile.write(fileSaver);
			excelFile.close();
			fileSaver.close();
		}
	}

	public void calculateStockData() {

		BSSheetCalculation bsSheetCalculation = new BSSheetCalculation(0, BSSheet);
		BSSheetCalculation bsSheetCalculationMinusOne = new BSSheetCalculation(-1, BSSheetMinusOne);
		BSSheetCalculation bsSheetCalculationMinusTwo = new BSSheetCalculation(-2, BSSheetMinusTwo);
		BSSheetCalculation bsSheetCalculationMinusThree = new BSSheetCalculation(-3, BSSheetMinusThree);
		BSSheetCalculation bsSheetCalculationMinusFour = new BSSheetCalculation(-4, BSSheetMinusFour);

		ISSheetCalculation isSheetCalculation = new ISSheetCalculation(0, ISSheet);
		ISSheetCalculation isSheetCalculationMinusOne = new ISSheetCalculation(-1, ISSheetMinusOne);
		ISSheetCalculation isSheetCalculationMinusTwo = new ISSheetCalculation(-2, ISSheetMinusTwo);
		ISSheetCalculation isSheetCalculationMinusThree = new ISSheetCalculation(-3, ISSheetMinusThree);
		ISSheetCalculation isSheetCalculationMinusFour = new ISSheetCalculation(-4, ISSheetMinusFour);

		CFSheetCalculation cfSheetCalculation = new CFSheetCalculation(0, CFSheet);
		CFSheetCalculation cfSheetCalculationMinusOne = new CFSheetCalculation(-1, CFSheetMinusOne);
		CFSheetCalculation cfSheetCalculationMinusTwo = new CFSheetCalculation(-2, CFSheetMinusTwo);
		CFSheetCalculation cfSheetCalculationMinusThree = new CFSheetCalculation(-3, CFSheetMinusThree);
		CFSheetCalculation cfSheetCalculationMinusFour = new CFSheetCalculation(-4, CFSheetMinusFour);

		RatioSheetCalculation ratioSheetCalculation = new RatioSheetCalculation(0, RatioSheet);
		RatioSheetCalculation ratioSheetCalculationMinusOne = new RatioSheetCalculation(-1, RatioSheetMinusOne);
		RatioSheetCalculation ratioSheetCalculationMinusTwo = new RatioSheetCalculation(-2, RatioSheetMinusTwo);
		RatioSheetCalculation ratioSheetCalculationMinusThree = new RatioSheetCalculation(-3, RatioSheetMinusThree);
		RatioSheetCalculation ratioSheetCalculationMinusFour = new RatioSheetCalculation(-4, RatioSheetMinusFour);

		StockSheetCalculation stockSheetCalculation = new StockSheetCalculation(0, stockDataSheet);

	}

	public void generateBSData() throws UnsupportedEncodingException, IOException {
		dataService.buildAnnualBSMap();
	}

	public void generateISData() throws UnsupportedEncodingException, IOException {
		dataService.buildAnnualISMap();
	}

	public void generateCFData() throws UnsupportedEncodingException, IOException {
		dataService.buildAnnualCFMap();
	}

	public void generateRatioData() throws UnsupportedEncodingException, IOException {
		dataService.buildRatioMap();
	}

	public void generateStockSheetData() throws IOException {
		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
	}

	public void setUpSheets() throws JSONException, IOException {

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			stockDataSheet = new StockSheet(excelFile, 0, dataService);
			excelFile.setSheetName(0, "Stock Data");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheet = new BSSheet(excelFile, 0, dataService, false);
			excelFile.setSheetName(1, "BS");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusOne = new BSSheet(excelFile, -1, dataService, false);
			excelFile.setSheetName(2, "BSOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusTwo = new BSSheet(excelFile, -2, dataService, false);
			excelFile.setSheetName(3, "BSTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusThree = new BSSheet(excelFile, -3, dataService, false);
			excelFile.setSheetName(4, "BSThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusFour = new BSSheet(excelFile, -4, dataService, false);
			excelFile.setSheetName(5, "BSFour");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheet = new ISSheet(excelFile, 0, dataService, false);
			excelFile.setSheetName(6, "IS");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusOne = new ISSheet(excelFile, -1, dataService, false);
			excelFile.setSheetName(7, "ISOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusTwo = new ISSheet(excelFile, -2, dataService, false);
			excelFile.setSheetName(8, "ISTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusThree = new ISSheet(excelFile, -3, dataService, false);
			excelFile.setSheetName(9, "ISThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusFour = new ISSheet(excelFile, -4, dataService, false);
			excelFile.setSheetName(10, "ISFour");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheet = new CFSheet(excelFile, 0, dataService, false);
			excelFile.setSheetName(11, "CF");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusOne = new CFSheet(excelFile, -1, dataService, false);
			excelFile.setSheetName(12, "CFOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusTwo = new CFSheet(excelFile, -2, dataService, false);
			excelFile.setSheetName(13, "CFTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusThree = new CFSheet(excelFile, -3, dataService, false);
			excelFile.setSheetName(14, "CFThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusFour = new CFSheet(excelFile, -4, dataService, false);
			excelFile.setSheetName(15, "CFFour");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			RatioSheet = new RatioSheet(excelFile, 0, dataService);
			excelFile.setSheetName(16, "Ratios");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			RatioSheetMinusOne = new RatioSheet(excelFile, -1, dataService);
			excelFile.setSheetName(17, "RatiosOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			RatioSheetMinusTwo = new RatioSheet(excelFile, -2, dataService);
			excelFile.setSheetName(18, "RatiosTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			RatioSheetMinusThree = new RatioSheet(excelFile, -3, dataService);
			excelFile.setSheetName(19, "RatiosThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			RatioSheetMinusFour = new RatioSheet(excelFile, -4, dataService);
			excelFile.setSheetName(20, "RatiosFour");
		}

	}

	// Getters and Setters

	public void setIsEverythingDone(boolean isEverythingDone) {
		this.getSupport().firePropertyChange(EVERYTHING_DONE_PROPERTY_CHANGE_NAME, this.isEverythingFinished(),
				isEverythingDone);
		super.setIsEverythingDone(isEverythingDone);
	}

	public void setIsBSDone(boolean isBSDone) {
		this.getSupport().firePropertyChange(CreateExcelTemplate.getBsPropertyChangeName(), this.isBSDataFinished(),
				isBSDone);
		super.setBSDataFinished(isBSDone);
	}

	public void setIsISDone(boolean isISDone) {
		this.getSupport().firePropertyChange(CreateExcelTemplate.getIsPropertyChangeName(), this.isISDataFinished(),
				isISDone);
		super.setISDataFinished(isISDone);
	}

	public void setIsCFDone(boolean isCFDone) {
		this.getSupport().firePropertyChange(CreateExcelTemplate.getCfPropertyChangeName(), this.isCFDataFinished(),
				isCFDone);
		super.setCFDataFinished(isCFDone);
	}

	public void setIsRatioDone(boolean isRatioDone) {
		this.getSupport().firePropertyChange(RATIO_PROPERTY_CHANGE_NAME, this.isRatioDataFinished(), isRatioDone);
		super.setRatioDataFinished(isRatioDone);
	}

	public void setIsStockSheetDone(boolean isStockSheetDone) {
		this.getSupport().firePropertyChange(CreateExcelTemplate.getStockSheetPropertyChangeName(),
				this.isStockDataFinished(), isStockSheetDone);
		super.setStockDataFinished(isStockSheetDone);
	}

	public static Integer getNumSheets() {
		return NUM_SHEETS;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(Workbook excelFile) {
		this.excelFile = excelFile;
	}

	public StockSheet getStockDataSheet() {
		return stockDataSheet;
	}

	public void setStockDataSheet(StockSheet stockDataSheet) {
		this.stockDataSheet = stockDataSheet;
	}

	public BSSheet getBSSheet() {
		return BSSheet;
	}

	public BSSheet getBSSheetMinusOne() {
		return BSSheetMinusOne;
	}

	public BSSheet getBSSheetMinusTwo() {
		return BSSheetMinusTwo;
	}

	public BSSheet getBSSheetMinusThree() {
		return BSSheetMinusThree;
	}

	public BSSheet getBSSheetMinusFour() {
		return BSSheetMinusFour;
	}

	public ISSheet getISSheet() {
		return ISSheet;
	}

	public ISSheet getISSheetMinusOne() {
		return ISSheetMinusOne;
	}

	public ISSheet getISSheetMinusTwo() {
		return ISSheetMinusTwo;
	}

	public ISSheet getISSheetMinusThree() {
		return ISSheetMinusThree;
	}

	public ISSheet getISSheetMinusFour() {
		return ISSheetMinusFour;
	}

	public CFSheet getCFSheet() {
		return CFSheet;
	}

	public CFSheet getCFSheetMinusOne() {
		return CFSheetMinusOne;
	}

	public CFSheet getCFSheetMinusTwo() {
		return CFSheetMinusTwo;
	}

	public CFSheet getCFSheetMinusThree() {
		return CFSheetMinusThree;
	}

	public CFSheet getCFSheetMinusFour() {
		return CFSheetMinusFour;
	}

	public RatioSheet getRatioSheet() {
		return RatioSheet;
	}

	public void setRatioSheet(RatioSheet ratioSheet) {
		RatioSheet = ratioSheet;
	}

	public RatioSheet getRatioSheetMinusOne() {
		return RatioSheetMinusOne;
	}

	public void setRatioSheetMinusOne(RatioSheet ratioSheetMinusOne) {
		RatioSheetMinusOne = ratioSheetMinusOne;
	}

	public RatioSheet getRatioSheetMinusTwo() {
		return RatioSheetMinusTwo;
	}

	public void setRatioSheetMinusTwo(RatioSheet ratioSheetMinusTwo) {
		RatioSheetMinusTwo = ratioSheetMinusTwo;
	}

	public RatioSheet getRatioSheetMinusThree() {
		return RatioSheetMinusThree;
	}

	public void setRatioSheetMinusThree(RatioSheet ratioSheetMinusThree) {
		RatioSheetMinusThree = ratioSheetMinusThree;
	}

	public RatioSheet getRatioSheetMinusFour() {
		return RatioSheetMinusFour;
	}

	public void setRatioSheetMinusFour(RatioSheet ratioSheetMinusFour) {
		RatioSheetMinusFour = ratioSheetMinusFour;
	}

	public DataService getDataService() {
		return dataService;
	}

	public static String getPropertyChangeName() {
		return EVERYTHING_DONE_PROPERTY_CHANGE_NAME;
	}

	public static String getRatioPropertyChangeName() {
		return RATIO_PROPERTY_CHANGE_NAME;
	}

}
