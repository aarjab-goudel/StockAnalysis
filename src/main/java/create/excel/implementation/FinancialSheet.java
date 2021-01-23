/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.implementation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;

public class FinancialSheet {

	private Workbook excelFile;
	private Sheet sheet;
	private Row firstRow;
	private Row lastRow;
	private int year;
	private Map<String, Row> tickerToRow;
	private DataService dataService;

	public FinancialSheet(Workbook excelFile, int year, DataService dataService) {
		sheet = excelFile.createSheet();
		this.excelFile = excelFile;
		this.year = year;
		this.dataService = dataService;
		this.tickerToRow = this.buildTickerToRowMap();
		this.createStockNameColumn();
		this.createTickerColumn();
		this.createStockSectorColumn();
		this.createStockIndustryColumn();

	}

	public Map<String, Row> buildTickerToRowMap() {
		tickerToRow = new HashMap<String, Row>();
		List<String> companies = this.getDataService().getOrderedCompanies();
		int numTickers = companies.size();
		int rowCounter = 0;
		firstRow = sheet.createRow(0);
		rowCounter++;
		for (int i = 0; i < numTickers; i++) {
			Row row = sheet.createRow(rowCounter);
			rowCounter++;
			tickerToRow.put(companies.get(i), row);
		}
		lastRow = sheet.createRow(rowCounter);
		return tickerToRow;
	}

	public void autoSizeAllColumns(int numColumns) {
		for (int i = 0; i < numColumns; i++) {
			this.getSheet().autoSizeColumn(i);
		}
	}

	public void createStockNameColumn() {
		Iterator<Entry<String, String>> iterator = this.getDataService().getTickerToStockName().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String stockName = entry.getValue();
			Row row = this.getTickerToRow().get(entry.getKey());
			Cell cell = row.createCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
			cell.setCellValue(stockName);
		}
	}

	public void createTickerColumn() {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (int i = 0; i < companies.size(); i++) {
			String company = companies.get(i);
			Row row = this.getTickerToRow().get(company);
			Cell cell = row.createCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			cell.setCellValue(company);
		}
	}

	public void createLastTickerColumn(int lastColNum) {
		List<String> companies = this.getDataService().getOrderedCompanies();
		for (int i = 0; i < companies.size(); i++) {
			String company = companies.get(i);
			Row row = this.getTickerToRow().get(company);
			Cell cell = row.createCell(lastColNum);
			cell.setCellValue(company);
		}
	}

	public void createStockSectorColumn() {
		Iterator<Entry<String, String>> iterator = this.getDataService().getTickerToSector().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String sector = entry.getValue();
			Row row = this.getTickerToRow().get(ticker);
			Cell cell = row.createCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
			cell.setCellValue(sector);
		}
	}

	public void createStockIndustryColumn() {
		Iterator<Entry<String, String>> iterator = this.getDataService().getTickerToIndustry().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String ticker = entry.getKey();
			String industry = entry.getValue();
			Row row = this.getTickerToRow().get(ticker);
			Cell cell = row.createCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
			cell.setCellValue(industry);
		}
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public Row getFirstRow() {
		return firstRow;
	}

	public int getYear() {
		return year;
	}

	public Map<String, Row> getTickerToRow() {
		return tickerToRow;
	}

	public DataService getDataService() {
		return dataService;
	}

	public Row getLastRow() {
		return lastRow;
	}

}
