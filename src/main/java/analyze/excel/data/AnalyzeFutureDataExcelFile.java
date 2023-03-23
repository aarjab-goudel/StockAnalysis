/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.data.service.SaveExcel;
import create.excel.enums.BSSheetConstants;
import create.excel.enums.CommonSheetConstants;
import create.excel.future.CreateFutureDataExcelFile;
import create.excel.future.FutureInfoBO;
import excel.library.CommonFinancialLibrary;

public class AnalyzeFutureDataExcelFile {

	private Workbook futureDataExcelFile;
	private Workbook annualExcelFile;
	private Map<String, FutureInfoBO> tickerToCurrentYearInfo;
	private Map<String, FutureInfoBO> tickerToNextYearInfo;
	private Map<String, String> tickerToCurrencyType;

	public AnalyzeFutureDataExcelFile() throws IOException {
		futureDataExcelFile = SaveExcel.getFutureDataExcelInstance();
		annualExcelFile = SaveExcel.getAnnualExcelInstance();
		tickerToCurrentYearInfo = new HashMap<String, FutureInfoBO>();
		tickerToNextYearInfo = new HashMap<String, FutureInfoBO>();
		tickerToCurrencyType = new HashMap<String, String>();
		this.extractFutureExcelData();
		this.extractCurrencyData();
		SaveExcel.closeAndSaveFutureExcelFile();
	}

	public void extractFutureExcelData() {
		Sheet futureExcelDataSheet = futureDataExcelFile.getSheetAt(0);
		Iterator<Row> iterator = futureExcelDataSheet.iterator();
		DataFormatter dataFormatter = new DataFormatter();
		iterator.next();
		while (iterator.hasNext()) {
			Row row = iterator.next();

			FutureInfoBO currentFutureInfoBO = new FutureInfoBO();
			FutureInfoBO nextYearInfoBO = new FutureInfoBO();

			Cell tickerCell = row.getCell(CreateFutureDataExcelFile.getTickerColumn());
			String ticker = dataFormatter.formatCellValue(tickerCell);
			currentFutureInfoBO.setTicker(ticker);
			nextYearInfoBO.setTicker(ticker);

			Cell currentYearCell = row.getCell(CreateFutureDataExcelFile.getCurrentYearDate());
			String currentYearString = dataFormatter.formatCellValue(currentYearCell);
			currentFutureInfoBO.setDate(currentYearString);

			Cell currentYearEPSCell = row.getCell(CreateFutureDataExcelFile.getCurrentYearEpsColumn());
			String currentYearEPSString = dataFormatter.formatCellValue(currentYearEPSCell);
			currentFutureInfoBO.setEps(currentYearEPSString);

			Cell currentYearRevenueCell = row.getCell(CreateFutureDataExcelFile.getCurrentYearRevenueColumn());
			String currentYearRevenueString = dataFormatter.formatCellValue(currentYearRevenueCell);
			currentFutureInfoBO.setRevenue(currentYearRevenueString);

			Cell nextYearCell = row.getCell(CreateFutureDataExcelFile.getNextYearDate());
			String nextYearString = dataFormatter.formatCellValue(nextYearCell);
			nextYearInfoBO.setDate(nextYearString);

			Cell nextYearEPSCell = row.getCell(CreateFutureDataExcelFile.getNextYearEpsColumn());
			String nextYearEPSString = dataFormatter.formatCellValue(nextYearEPSCell);
			nextYearInfoBO.setEps(nextYearEPSString);

			Cell nextYearRevenueCell = row.getCell(CreateFutureDataExcelFile.getNextYearRevenueColumn());
			String nextYearRevenueString = dataFormatter.formatCellValue(nextYearRevenueCell);
			nextYearInfoBO.setRevenue(nextYearRevenueString);
			
			String currentRevenueAsDecimal = CommonFinancialLibrary.convertNumberWithLetterToFullNumber(currentYearRevenueString);
			String nextYearRevenueAsDecimal = CommonFinancialLibrary
					.convertNumberWithLetterToFullNumber(nextYearRevenueString);
			String epsGrowth = CommonFinancialLibrary.calculateGrowthRate(nextYearEPSString, currentYearEPSString);
			String revenueGrowth = CommonFinancialLibrary.calculateGrowthRate(nextYearRevenueAsDecimal,
					currentRevenueAsDecimal);

//			Cell epsGrowthCell = row.getCell(CreateFutureDataExcelFile.getEpsGrowthColumn());
//			String epsGrowthString = dataFormatter.formatCellValue(epsGrowthCell);
			currentFutureInfoBO.setEpsGrowth(epsGrowth);
			nextYearInfoBO.setEpsGrowth(epsGrowth);

//			Cell revenueGrowthCell = row.getCell(CreateFutureDataExcelFile.getRevenueGrowthColumn());
//			String revenueGrowthString = dataFormatter.formatCellValue(revenueGrowthCell);
			currentFutureInfoBO.setRevenueGrowth(revenueGrowth);
			nextYearInfoBO.setRevenueGrowth(revenueGrowth);

			tickerToCurrentYearInfo.put(ticker, currentFutureInfoBO);
			tickerToNextYearInfo.put(ticker, nextYearInfoBO);

		}
	}
	
	public void extractCurrencyData() {
		Sheet currencySheet = annualExcelFile.getSheetAt(1);
		Iterator<Row> iterator = currencySheet.iterator();
		DataFormatter dataFormatter = new DataFormatter();
		iterator.next();
		while (iterator.hasNext()) {
			Row row = iterator.next();
			if (!CommonFinancialLibrary.determineHeaderRow(row)) {
				Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
				Cell currencyTypeCell = row.getCell(BSSheetConstants.CURRENCY_TYPE.getBSData());
				String tickerValue = CommonFinancialLibrary
						.removeDecimalFromNumber(dataFormatter.formatCellValue(tickerCell));
				String currencyTypeValue = dataFormatter.formatCellValue(currencyTypeCell);
				tickerToCurrencyType.put(tickerValue, currencyTypeValue);
			}
			
		}
		
		
	}

	public Map<String, FutureInfoBO> getTickerToCurrentYearInfo() {
		return tickerToCurrentYearInfo;
	}

	public Map<String, FutureInfoBO> getTickerToNextYearInfo() {
		return tickerToNextYearInfo;
	}

	public Map<String, String> getTickerToCurrencyType() {
		return tickerToCurrencyType;
	}
	
	

}
