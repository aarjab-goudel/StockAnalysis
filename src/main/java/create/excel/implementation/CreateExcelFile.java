package create.excel.implementation;

import java.io.FileOutputStream;
import java.io.IOException;
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

public class CreateExcelFile {
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

	private DataService dataService;
	private static final String FILE_PATH = getFilePath();

	public CreateExcelFile() throws IOException, JSONException, ParseException {
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader();
		List<String> companies = csvReader.getStockList();
		dataService = new DataService(companies);
		this.setUpSheets();
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

		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();

	}

	// Check OS
	public static String getFilePath ()
	{
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("windows") != -1)
			return System.getProperty("user.home") + "\\Desktop\\StockData2.xlsx";
		else if(os.indexOf("mac") != -1)
			return System.getProperty("user.home") + "/Desktop/StockData2.xlsx";
		else if(os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1)
			return System.getProperty("user.home") + "/Desktop/StockData2.xlsx";
		else
			return System.getProperty("user.home") + "\\Desktop\\StockData2.xlsx"; // temp
	}
	
	public void setUpSheets() throws JSONException, IOException {
		stockDataSheet = new StockSheet(excelFile, 0, dataService);
		excelFile.setSheetName(0, "Stock Data");

		BSSheet = new BSSheet(excelFile, 0, dataService);
		excelFile.setSheetName(1, "BS");

		BSSheetMinusOne = new BSSheet(excelFile, -1, dataService);
		excelFile.setSheetName(2, "BSOne");

		BSSheetMinusTwo = new BSSheet(excelFile, -2, dataService);
		excelFile.setSheetName(3, "BSTwo");

		BSSheetMinusThree = new BSSheet(excelFile, -3, dataService);
		excelFile.setSheetName(4, "BSThree");

		BSSheetMinusFour = new BSSheet(excelFile, -4, dataService);
		excelFile.setSheetName(5, "BSFour");

		ISSheet = new ISSheet(excelFile, 0, dataService);
		excelFile.setSheetName(6, "IS");

		ISSheetMinusOne = new ISSheet(excelFile, -1, dataService);
		excelFile.setSheetName(7, "ISOne");

		ISSheetMinusTwo = new ISSheet(excelFile, -2, dataService);
		excelFile.setSheetName(8, "ISTwo");

		ISSheetMinusThree = new ISSheet(excelFile, -3, dataService);
		excelFile.setSheetName(9, "ISThree");

		ISSheetMinusFour = new ISSheet(excelFile, -4, dataService);
		excelFile.setSheetName(10, "ISFour");

		CFSheet = new CFSheet(excelFile, 0, dataService);
		excelFile.setSheetName(11, "CF");

		CFSheetMinusOne = new CFSheet(excelFile, -1, dataService);
		excelFile.setSheetName(12, "CFOne");

		CFSheetMinusTwo = new CFSheet(excelFile, -2, dataService);
		excelFile.setSheetName(13, "CFTwo");

		CFSheetMinusThree = new CFSheet(excelFile, -3, dataService);
		excelFile.setSheetName(14, "CFThree");

		CFSheetMinusFour = new CFSheet(excelFile, -4, dataService);
		excelFile.setSheetName(15, "CFFour");

		RatioSheet = new RatioSheet(excelFile, 0, dataService);
		excelFile.setSheetName(16, "Ratios");

		RatioSheetMinusOne = new RatioSheet(excelFile, -1, dataService);
		excelFile.setSheetName(17, "RatiosOne");

		RatioSheetMinusTwo = new RatioSheet(excelFile, -2, dataService);
		excelFile.setSheetName(18, "RatiosTwo");

		RatioSheetMinusThree = new RatioSheet(excelFile, -3, dataService);
		excelFile.setSheetName(19, "RatiosThree");

		RatioSheetMinusFour = new RatioSheet(excelFile, -4, dataService);
		excelFile.setSheetName(20, "RatiosFour");
	}

	// Getters and Setters

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

}
