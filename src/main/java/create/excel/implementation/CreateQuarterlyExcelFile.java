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
import create.excel.data.service.CSVReader;
import create.excel.data.service.DataService;
import create.excel.data.service.SaveExcel;
import javafx.concurrent.Task;

public class CreateQuarterlyExcelFile extends CreateExcelTemplate {
	private static final Integer NUM_SHEETS = 15;
	private Workbook excelFile;
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
	private static final String PROPERTY_CHANGE_NAME = "isEverythingDoneForQuarterly";

	private DataService dataService;
	private static final String FILE_PATH = SaveExcel.getQuarterlyFilePath();

	public CreateQuarterlyExcelFile(Task<Void> task, boolean generateEverything,
			List<String> unOrderedStockListFromTextField) throws IOException, JSONException, ParseException {
		super(task);
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(task);
		dataService.setUnorderedCompanies(companies);
		dataService.buildURLData(companies);

	}

	public CreateQuarterlyExcelFile(Task<Void> task, List<String> unOrderedStockListFromTextField)
			throws IOException, JSONException, ParseException {
		super(task);
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(task);
		dataService.buildURLData(companies);
		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
		dataService.build_quarterly_bs_cf_is_maps();

		this.setUpSheets();
		this.calculateStockData();

		this.checkTaskAndSave();

	}

	public CreateQuarterlyExcelFile() throws IOException, JSONException, ParseException {
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(null);
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(null);
		dataService.buildURLData(companies);
		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
		dataService.build_quarterly_bs_cf_is_maps();

		this.setUpSheets();

		this.calculateStockData();
		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();

	}

	public void generateStockSheetData() throws IOException {
		dataService.buildStockSheetDataMaps();
		dataService.buildOrderedCompaniesListBySector(dataService.getTickerToSector());
	}

	public void generateBSData() throws UnsupportedEncodingException, IOException {
		dataService.buildQuarterlyBSMap();
	}

	public void generateISData() throws UnsupportedEncodingException, IOException {
		dataService.buildQuarterlyISMap();
	}

	public void generateCFData() throws UnsupportedEncodingException, IOException {
		dataService.buildQuarterlyCFMap();
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
	}

	public void setUpSheets() throws JSONException, IOException {

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheet = new BSSheet(excelFile, 0, dataService, true);
			excelFile.setSheetName(0, "BS");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusOne = new BSSheet(excelFile, -1, dataService, true);
			excelFile.setSheetName(1, "BSOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusTwo = new BSSheet(excelFile, -2, dataService, true);
			excelFile.setSheetName(2, "BSTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusThree = new BSSheet(excelFile, -3, dataService, true);
			excelFile.setSheetName(3, "BSThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			BSSheetMinusFour = new BSSheet(excelFile, -4, dataService, true);
			excelFile.setSheetName(4, "BSFour");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheet = new ISSheet(excelFile, 0, dataService, true);
			excelFile.setSheetName(5, "IS");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusOne = new ISSheet(excelFile, -1, dataService, true);
			excelFile.setSheetName(6, "ISOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusTwo = new ISSheet(excelFile, -2, dataService, true);
			excelFile.setSheetName(7, "ISTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusThree = new ISSheet(excelFile, -3, dataService, true);
			excelFile.setSheetName(8, "ISThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			ISSheetMinusFour = new ISSheet(excelFile, -4, dataService, true);
			excelFile.setSheetName(9, "ISFour");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheet = new CFSheet(excelFile, 0, dataService, true);
			excelFile.setSheetName(10, "CF");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusOne = new CFSheet(excelFile, -1, dataService, true);
			excelFile.setSheetName(11, "CFOne");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusTwo = new CFSheet(excelFile, -2, dataService, true);
			excelFile.setSheetName(12, "CFTwo");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusThree = new CFSheet(excelFile, -3, dataService, true);
			excelFile.setSheetName(13, "CFThree");
		}

		if ((this.getTask() != null && !this.getTask().isCancelled()) || (this.getTask() == null)) {
			CFSheetMinusFour = new CFSheet(excelFile, -4, dataService, true);
			excelFile.setSheetName(14, "CFFour");
		}

	}

	public void setIsEverythingDone(boolean isEverythingDone) {
		this.getSupport().firePropertyChange(PROPERTY_CHANGE_NAME, this.isEverythingFinished(), isEverythingDone);
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

	public void setIsStockSheetDone(boolean isStockSheetDone) {
		this.getSupport().firePropertyChange(CreateExcelTemplate.getStockSheetPropertyChangeName(),
				this.isStockDataFinished(), isStockSheetDone);
		super.setStockDataFinished(isStockSheetDone);
	}

	public static Integer getNumSheets() {
		return NUM_SHEETS;
	}

	public static String getFilePath() {
		return FILE_PATH;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public static String getPropertyChangeName() {
		return PROPERTY_CHANGE_NAME;
	}

}
