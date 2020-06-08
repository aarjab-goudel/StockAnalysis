package excel.library;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;

public class CommonFinancialLibrary {

	private static final String MORNING_STAR_DIVISOR = "1000000";
	private static final String TICKER = "Ticker";

	public CommonFinancialLibrary() {

	}

	public static String formatStringValueForExcelSheet(String value) {
		String firstConversion = checkAndConvertEmptyValueToZero(value);
		return formatNegativeValue(firstConversion);
	}

	public static String formatNegativeValue(String value) {
		StringBuilder sb = new StringBuilder(value);
		if (value.charAt(0) == '(' && value.charAt(value.length() - 1) == ')') {
			sb.deleteCharAt(value.length() - 1);
			sb.deleteCharAt(0);
			String strValue = sb.toString();
			strValue = "-" + strValue;
			return strValue;
		}
		return sb.toString();
	}

	public static String calculateGrowthRate(String mostRecentYearValue, String lastYearValue) {
		BigDecimal mostRecentYearNumericValue = new BigDecimal(mostRecentYearValue);
		BigDecimal lastYearNumericValue = new BigDecimal(lastYearValue);
		BigDecimal difference = mostRecentYearNumericValue.subtract(lastYearNumericValue);
		BigDecimal growthRate = difference.divide(lastYearNumericValue, 4, RoundingMode.HALF_UP);
		return growthRate.toString();
	}

	public static String checkAndConvertEmptyValueToZero(String value) {
		if (value.equals("—")) {
			return "0";
		} else {
			return value;
		}
	}

	public static List<String> readCurrentStockList(Sheet sheetToRead) {
		List<String> currentStockList = new ArrayList<String>();
		Iterator<Row> rowIterator = sheetToRead.iterator();
		DataFormatter dataFormatter = new DataFormatter();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			String tickerValue = dataFormatter.formatCellValue(tickerCell);
			if (!tickerValue.equals(TICKER)) {
				currentStockList.add(tickerValue);
			}
		}
		return currentStockList;
	}

	public static void updateBasicStockInfo(DataService dataService, String ticker, Row row, int numCols) {
		Map<String, String> tickerToSector = dataService.getTickerToSector();
		Map<String, String> tickerToIndustry = dataService.getTickerToIndustry();
		Map<String, String> tickerToStockName = dataService.getTickerToStockName();

		Cell tickerCell = row.createCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		tickerCell.setCellValue(ticker);

		Cell lastTickerCell = row.createCell(numCols - 1);
		lastTickerCell.setCellValue(ticker);

		Cell stockNameCell = row.createCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		stockNameCell.setCellValue(tickerToStockName.get(ticker));

		Cell stockIndustryCell = row.createCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
		stockIndustryCell.setCellValue(tickerToIndustry.get(ticker));

		Cell stockSectorCell = row.createCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		stockSectorCell.setCellValue(tickerToSector.get(ticker));

	}

	public static String checkAndConvertToMorningStarNumber(String columnValue) {
		String result = columnValue;
		String upperCaseString = columnValue.toUpperCase();
		if (upperCaseString.contains("E") && upperCaseString.charAt(1) == '.') {
			result = String.format("%.0f", Double.parseDouble(result));
		}
		result = CommonFinancialLibrary.convertNumberToMorningStarMultiple(result);
		result = CommonFinancialLibrary.addAppropriateCommasToNumber(result);
		return result;
	}

	private static String convertNumberToMorningStarMultiple(String number) {
		BigDecimal bigDecimalNumber = new BigDecimal(number);
		BigDecimal morningStarDivisor = new BigDecimal(MORNING_STAR_DIVISOR);
		BigDecimal dividedResult = bigDecimalNumber.divide(morningStarDivisor);
		String morningStarValue = dividedResult.toString();
		return morningStarValue;
	}

	public static String addAppropriateCommasToNumber(String number) {
		double double_number = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		String formattedNumber = formatter.format(double_number);
		return formattedNumber;
	}

	public static String addDollarSignToNumber(String number) {
		return "$" + number;
	}

	public static void removeTickerBySheet(Sheet sheetToRemove, String ticker) {
		Row tickerRow = null;
		for (Row row : sheetToRemove) {
			if (row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn()).getStringCellValue().equals(ticker)) {
				tickerRow = row;
			}
		}
		if (tickerRow != null) {
			int tickerRowIndex = tickerRow.getRowNum();
			int lastRowNum = sheetToRemove.getLastRowNum();
			if (tickerRowIndex == lastRowNum) {
				if (tickerRow != null) {
					sheetToRemove.removeRow(tickerRow);
				}
			}
			if (tickerRowIndex >= 0 && tickerRowIndex < lastRowNum) {
				sheetToRemove.shiftRows(tickerRowIndex + 1, lastRowNum, -1);
			}
		}
	}

}
