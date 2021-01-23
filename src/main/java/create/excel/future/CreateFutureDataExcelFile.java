/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.future;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import create.excel.data.service.CSVReader;
import create.excel.data.service.SaveExcel;
import create.excel.implementation.CreateExcelTemplate;
import excel.library.CommonFinancialLibrary;
import javafx.concurrent.Task;

public class CreateFutureDataExcelFile extends CreateExcelTemplate {
	private Workbook excelFile;
	private static final Integer NUM_COLUMNS = 9;
	private static final Integer TICKER_COLUMN = 0;
	private static final Integer CURRENT_YEAR_DATE = 1;
	private static final Integer CURRENT_YEAR_EPS_COLUMN = 2;
	private static final Integer CURRENT_YEAR_REVENUE_COLUMN = 3;
	private static final Integer NEXT_YEAR_DATE = 4;
	private static final Integer NEXT_YEAR_EPS_COLUMN = 5;
	private static final Integer NEXT_YEAR_REVENUE_COLUMN = 6;
	private static final Integer EPS_GROWTH_COLUMN = 7;
	private static final Integer REVENUE_GROWTH_COLUMN = 8;
	private Sheet futureDataSheet;
	private static final String FILE_PATH = SaveExcel.getFutureFilePath();
	private List<String> companies;
	private FutureExcelDataScraper futureDataScraper;

	private static final String PROPERTY_CHANGE_NAME = "isEverythingDoneForFuture";

	public CreateFutureDataExcelFile(Task<Void> task, List<String> unOrderedStockListFromTextField) throws IOException {
		super(task);
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(unOrderedStockListFromTextField);
		companies = csvReader.getStockList();
		futureDataScraper = new FutureExcelDataScraper(companies, task);
		futureDataSheet = excelFile.createSheet();
		this.setUpFutureDataExcelFile();
		excelFile.setSheetName(0, "Future Stock Data");
		this.autoSizeAllColumns(NUM_COLUMNS);
		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();
	}

	public CreateFutureDataExcelFile() throws IOException {
		excelFile = new XSSFWorkbook();
		CSVReader csvReader = new CSVReader(null);
		companies = csvReader.getStockList();
		futureDataScraper = new FutureExcelDataScraper(companies, null);
		futureDataSheet = excelFile.createSheet();
		this.setUpFutureDataExcelFile();
		excelFile.setSheetName(0, "Future Stock Data");
		this.autoSizeAllColumns(NUM_COLUMNS);
		FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
		excelFile.write(fileSaver);
		excelFile.close();
		fileSaver.close();
	}

	public void autoSizeAllColumns(int numColumns) {
		for (int i = 0; i < numColumns; i++) {
			futureDataSheet.autoSizeColumn(i);
		}
	}

	public void setUpFutureDataExcelFile() {
		Map<String, FutureInfoBO> currentYearMap = futureDataScraper.getTickerToCurrentYearInfo();
		Map<String, FutureInfoBO> nextYearMap = futureDataScraper.getTickerToNextYearInfo();
		int numTickers = companies.size();
		int rowCounter = 0;
		Row firstRow = futureDataSheet.createRow(rowCounter);
		this.setUpFutureExcelSheetHeaderRow(firstRow);
		rowCounter++;
		for (int i = 0; i < numTickers; i++) {
			Row row = futureDataSheet.createRow(rowCounter);
			rowCounter++;
			String ticker = companies.get(i);
			FutureInfoBO currentInfo = currentYearMap.get(ticker);
			FutureInfoBO nextYearInfo = nextYearMap.get(ticker);
			String currentDate = currentInfo.getDate();
			String nextYearDate = nextYearInfo.getDate();
			String currentEPS = currentInfo.getEps();
			String nextYearEPS = nextYearInfo.getEps();
			String currentRevenue = currentInfo.getRevenue();
			String nextYearRevenue = nextYearInfo.getRevenue();
			Cell tickerCell = row.createCell(TICKER_COLUMN);
			tickerCell.setCellValue(ticker);
			Cell currentDateCell = row.createCell(CURRENT_YEAR_DATE);
			currentDateCell.setCellValue(currentDate);
			Cell currentYearEPSCell = row.createCell(CURRENT_YEAR_EPS_COLUMN);
			currentYearEPSCell.setCellValue(currentEPS);
			Cell currentYearRevenueCell = row.createCell(CURRENT_YEAR_REVENUE_COLUMN);
			currentYearRevenueCell.setCellValue(currentRevenue);
			Cell nextYearDateCell = row.createCell(NEXT_YEAR_DATE);
			nextYearDateCell.setCellValue(nextYearDate);
			Cell nextYearEPSCell = row.createCell(NEXT_YEAR_EPS_COLUMN);
			nextYearEPSCell.setCellValue(nextYearEPS);
			Cell nextYearRevenueCell = row.createCell(NEXT_YEAR_REVENUE_COLUMN);
			nextYearRevenueCell.setCellValue(nextYearRevenue);
			String epsGrowth = CommonFinancialLibrary.calculateGrowthRate(nextYearEPS, currentEPS);
			Cell epsGrowthCell = row.createCell(EPS_GROWTH_COLUMN);
			epsGrowthCell.setCellValue(epsGrowth);
			String currentRevenueAsDecimal = CommonFinancialLibrary.convertNumberWithLetterToFullNumber(currentRevenue);
			String nextYearRevenueAsDecimal = CommonFinancialLibrary
					.convertNumberWithLetterToFullNumber(nextYearRevenue);
			String revenueGrowth = CommonFinancialLibrary.calculateGrowthRate(nextYearRevenueAsDecimal,
					currentRevenueAsDecimal);
			Cell revenueGrowthCell = row.createCell(REVENUE_GROWTH_COLUMN);
			revenueGrowthCell.setCellValue(revenueGrowth);
		}
	}

	public void setUpFutureExcelSheetHeaderRow(Row headerRow) {
		CellStyle style = this.getExcelFile().createCellStyle();// Create style
		Font font = this.getExcelFile().createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		List<Cell> firstRowCells = new ArrayList<Cell>();
		for (int i = 0; i < NUM_COLUMNS; i++) {
			Cell cell = headerRow.createCell(i);
			firstRowCells.add(cell);
			cell.setCellStyle(style);
		}

		firstRowCells.get(TICKER_COLUMN).setCellValue("Ticker");
		firstRowCells.get(CURRENT_YEAR_DATE).setCellValue("Current Year");
		firstRowCells.get(CURRENT_YEAR_EPS_COLUMN).setCellValue("Current Year EPS");
		firstRowCells.get(CURRENT_YEAR_REVENUE_COLUMN).setCellValue("Current Year Revenue");
		firstRowCells.get(NEXT_YEAR_DATE).setCellValue("Next Year");
		firstRowCells.get(NEXT_YEAR_EPS_COLUMN).setCellValue("Next Year EPS");
		firstRowCells.get(NEXT_YEAR_REVENUE_COLUMN).setCellValue("Next Year Revenue");
		firstRowCells.get(EPS_GROWTH_COLUMN).setCellValue("EPS Growth");
		firstRowCells.get(REVENUE_GROWTH_COLUMN).setCellValue("Revenue Growth");

	}

	public void setIsEverythingDone(boolean isEverythingDone) {
		this.getSupport().firePropertyChange(PROPERTY_CHANGE_NAME, this.isEverythingFinished(), isEverythingDone);
		super.setIsEverythingDone(isEverythingDone);
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public List<String> getCompanies() {
		return companies;
	}

	public FutureExcelDataScraper getFutureDataScraper() {
		return futureDataScraper;
	}

	public static Integer getTickerColumn() {
		return TICKER_COLUMN;
	}

	public static Integer getCurrentYearDate() {
		return CURRENT_YEAR_DATE;
	}

	public static Integer getCurrentYearEpsColumn() {
		return CURRENT_YEAR_EPS_COLUMN;
	}

	public static Integer getCurrentYearRevenueColumn() {
		return CURRENT_YEAR_REVENUE_COLUMN;
	}

	public static Integer getNextYearDate() {
		return NEXT_YEAR_DATE;
	}

	public static Integer getNextYearEpsColumn() {
		return NEXT_YEAR_EPS_COLUMN;
	}

	public static Integer getNextYearRevenueColumn() {
		return NEXT_YEAR_REVENUE_COLUMN;
	}

	public static Integer getEpsGrowthColumn() {
		return EPS_GROWTH_COLUMN;
	}

	public static Integer getRevenueGrowthColumn() {
		return REVENUE_GROWTH_COLUMN;
	}

	public static String getPropertyChangeName() {
		return PROPERTY_CHANGE_NAME;
	}

}
