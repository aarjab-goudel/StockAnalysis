/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */

package analyze.excel.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.bo.BSInfoBO;
import create.excel.bo.CFInfoBO;
import create.excel.bo.ISInfoBO;
import excel.library.BSFinancialLibrary;
import excel.library.CFFinancialLibrary;
import excel.library.CommonFinancialLibrary;
import excel.library.ISFinancialLibrary;

public class AnalyzeExcelFile {

	private Workbook excelFile;
	private Map<String, List<BSInfoBO>> tickerToBSInfo;
	private Map<String, List<ISInfoBO>> tickerToISInfo;
	private Map<String, List<CFInfoBO>> tickerToCFInfo;
	private List<String> companies;

	public AnalyzeExcelFile(Workbook excelFile) {
		if (excelFile != null) {
			this.excelFile = excelFile;
			tickerToBSInfo = this.extractBSData();
			tickerToCFInfo = this.extractCFData();
			tickerToISInfo = this.extractISData();
			this.companies = CommonFinancialLibrary.readCurrentStockList(this.getExcelFile().getSheetAt(0));
		}

	}

	// This method extracts Balance Sheet data from Balance sheet spreadsheets
	public Map<String, List<BSInfoBO>> extractBSData() {
		Sheet firstBSSheet = excelFile.getSheet("BS");
		Sheet secondBSSheet = excelFile.getSheet("BSOne");
		Sheet thirdBSSheet = excelFile.getSheet("BSTwo");
		Sheet fourthBSSheet = excelFile.getSheet("BSThree");
		//Sheet fifthBSSheet = excelFile.getSheet("BSFour");

		List<Sheet> bsSheets = new ArrayList<Sheet>();

		bsSheets.add(firstBSSheet);
		bsSheets.add(secondBSSheet);
		bsSheets.add(thirdBSSheet);
		bsSheets.add(fourthBSSheet);
		//bsSheets.add(fifthBSSheet);

		// The readBSData actually reads the BS data from the sheet Array-list
		Map<String, List<BSInfoBO>> tickerToBSInfo = BSFinancialLibrary.readBSData(bsSheets);
		return tickerToBSInfo;
	}

	public Map<String, List<ISInfoBO>> extractISData() {
		Sheet firstISSheet = excelFile.getSheet("IS");
		Sheet secondISSheet = excelFile.getSheet("ISOne");
		Sheet thirdISSheet = excelFile.getSheet("ISTwo");
		Sheet fourthISSheet = excelFile.getSheet("ISThree");
		//Sheet fifthISSheet = excelFile.getSheet("ISFour");

		List<Sheet> isSheets = new ArrayList<Sheet>();

		isSheets.add(firstISSheet);
		isSheets.add(secondISSheet);
		isSheets.add(thirdISSheet);
		isSheets.add(fourthISSheet);
		//isSheets.add(fifthISSheet);

		Map<String, List<ISInfoBO>> tickerToISInfo = ISFinancialLibrary.readISData(isSheets);
		return tickerToISInfo;

	}

	public Map<String, List<CFInfoBO>> extractCFData() {
		Sheet firstCFSheet = excelFile.getSheet("CF");
		Sheet secondCFSheet = excelFile.getSheet("CFOne");
		Sheet thirdCFSheet = excelFile.getSheet("CFTwo");
		Sheet fourthCFSheet = excelFile.getSheet("CFThree");
		//Sheet fifthCFSheet = excelFile.getSheet("CFFour");

		List<Sheet> cfSheets = new ArrayList<Sheet>();

		cfSheets.add(firstCFSheet);
		cfSheets.add(secondCFSheet);
		cfSheets.add(thirdCFSheet);
		cfSheets.add(fourthCFSheet);
		//cfSheets.add(fifthCFSheet);

		Map<String, List<CFInfoBO>> tickerToCFInfo = CFFinancialLibrary.readCFData(cfSheets);

		return tickerToCFInfo;

	}

	public Map<String, List<BSInfoBO>> getTickerToBSInfo() {
		return tickerToBSInfo;
	}

	public Map<String, List<ISInfoBO>> getTickerToISInfo() {
		return tickerToISInfo;
	}

	public Map<String, List<CFInfoBO>> getTickerToCFInfo() {
		return tickerToCFInfo;
	}

	public Workbook getExcelFile() {
		return excelFile;
	}

	public List<String> getCompanies() {
		return companies;
	}

}
