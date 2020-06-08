package analyze.excel.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.bo.BSInfoBO;
import create.excel.bo.CFInfoBO;
import create.excel.bo.ISInfoBO;
import create.excel.bo.RatioInfoBO;
import create.excel.data.service.SaveExcel;
import excel.library.BSFinancialLibrary;
import excel.library.CFFinancialLibrary;
import excel.library.ISFinancialLibrary;
import excel.library.RatioFinancialLibrary;

public class AnalyzeExcelFile {

	private Workbook excelFile;
	private Map<String, List<BSInfoBO>> tickerToBSInfo;
	private Map<String, List<ISInfoBO>> tickerToISInfo;
	private Map<String, List<CFInfoBO>> tickerToCFInfo;
	private Map<String, List<RatioInfoBO>> tickerToRatioInfo;

	public AnalyzeExcelFile() throws IOException {
		this.excelFile = SaveExcel.getInstance();
		tickerToBSInfo = this.extractBSData();
		tickerToISInfo = this.extractISData();
		tickerToCFInfo = this.extractCFData();
		tickerToRatioInfo = this.extractRatioData();
		SaveExcel.closeAndSaveFile();
	}

	public Map<String, List<BSInfoBO>> extractBSData() {
		Sheet firstBSSheet = excelFile.getSheet("BS");
		Sheet secondBSSheet = excelFile.getSheet("BSOne");
		Sheet thirdBSSheet = excelFile.getSheet("BSTwo");
		Sheet fourthBSSheet = excelFile.getSheet("BSThree");
		Sheet fifthBSSheet = excelFile.getSheet("BSFour");

		List<Sheet> bsSheets = new ArrayList<Sheet>();

		bsSheets.add(firstBSSheet);
		bsSheets.add(secondBSSheet);
		bsSheets.add(thirdBSSheet);
		bsSheets.add(fourthBSSheet);
		bsSheets.add(fifthBSSheet);

		Map<String, List<BSInfoBO>> tickerToBSInfo = BSFinancialLibrary.readBSData(bsSheets);
		return tickerToBSInfo;

	}

	public Map<String, List<ISInfoBO>> extractISData() {
		Sheet firstISSheet = excelFile.getSheet("IS");
		Sheet secondISSheet = excelFile.getSheet("ISOne");
		Sheet thirdISSheet = excelFile.getSheet("ISTwo");
		Sheet fourthISSheet = excelFile.getSheet("ISThree");
		Sheet fifthISSheet = excelFile.getSheet("ISFour");

		List<Sheet> isSheets = new ArrayList<Sheet>();

		isSheets.add(firstISSheet);
		isSheets.add(secondISSheet);
		isSheets.add(thirdISSheet);
		isSheets.add(fourthISSheet);
		isSheets.add(fifthISSheet);

		Map<String, List<ISInfoBO>> tickerToISInfo = ISFinancialLibrary.readISData(isSheets);
		return tickerToISInfo;

	}

	public Map<String, List<CFInfoBO>> extractCFData() {
		Sheet firstCFSheet = excelFile.getSheet("CF");
		Sheet secondCFSheet = excelFile.getSheet("CFOne");
		Sheet thirdCFSheet = excelFile.getSheet("CFTwo");
		Sheet fourthCFSheet = excelFile.getSheet("CFThree");
		Sheet fifthCFSheet = excelFile.getSheet("CFFour");

		List<Sheet> cfSheets = new ArrayList<Sheet>();

		cfSheets.add(firstCFSheet);
		cfSheets.add(secondCFSheet);
		cfSheets.add(thirdCFSheet);
		cfSheets.add(fourthCFSheet);
		cfSheets.add(fifthCFSheet);

		Map<String, List<CFInfoBO>> tickerToCFInfo = CFFinancialLibrary.readCFData(cfSheets);

		return tickerToCFInfo;

	}

	public Map<String, List<RatioInfoBO>> extractRatioData() {
		Sheet firstRatioSheet = excelFile.getSheet("Ratios");
		Sheet secondRatioSheet = excelFile.getSheet("RatiosOne");
		Sheet thirdRatioSheet = excelFile.getSheet("RatiosTwo");
		Sheet fourthRatioSheet = excelFile.getSheet("RatiosThree");
		Sheet fifthRatioSheet = excelFile.getSheet("RatiosFour");

		List<Sheet> ratiosSheet = new ArrayList<Sheet>();

		ratiosSheet.add(firstRatioSheet);
		ratiosSheet.add(secondRatioSheet);
		ratiosSheet.add(thirdRatioSheet);
		ratiosSheet.add(fourthRatioSheet);
		ratiosSheet.add(fifthRatioSheet);

		Map<String, List<RatioInfoBO>> tickerToRatioInfo = RatioFinancialLibrary.readRatioData(ratiosSheet);

		return tickerToRatioInfo;

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

	public Map<String, List<RatioInfoBO>> getTickerToRatioInfo() {
		return tickerToRatioInfo;
	}

}
