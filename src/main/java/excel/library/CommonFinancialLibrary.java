/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package excel.library;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import create.excel.data.service.DataService;
import create.excel.enums.CommonSheetConstants;

public class CommonFinancialLibrary {

	private static final String TICKER = "Ticker";
	private static final int NUM_SPACES_BETWEEN_DATES = 20;
	private static final String ERROR_MSSG = "Error calculating Avg Growth Rate";
	private static final String ERROR = "ERROR";

	public CommonFinancialLibrary() {

	}

	public static String errorMessage() {
		return CommonFinancialLibrary.ERROR;
	}

	public static boolean determineHeaderRow(Row row) {
		DataFormatter dataFormatter = new DataFormatter();
		Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
		Cell stockNameCell = row.getCell(CommonSheetConstants.STOCK_NAME_COLUMN.getCommonColumn());
		Cell sectorCell = row.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
		Cell industryCell = row.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());

		String tickerValue = dataFormatter.formatCellValue(tickerCell);
		String stockNameValue = dataFormatter.formatCellValue(stockNameCell);
		String sectorValue = dataFormatter.formatCellValue(sectorCell);
		String industryValue = dataFormatter.formatCellValue(industryCell);

		if (tickerValue.equals("Ticker") || stockNameValue.equals("Stock") || sectorValue.equals("Sector")
				|| industryValue.equals("Industry")) {
			return true;
		} else {
			return false;
		}
	}

	public static String formatStringValueForExcelSheet(String value) {
		String firstConversion = checkAndConvertEmptyValueToZero(value);
		return formatNegativeValue(firstConversion);
	}

	public static String formatNegativeValue(String value) {
		if (!value.equals(CommonFinancialLibrary.ERROR)) {
			StringBuilder sb = new StringBuilder(value);
			if (value.charAt(0) == '(' && value.charAt(value.length() - 1) == ')') {
				sb.deleteCharAt(value.length() - 1);
				sb.deleteCharAt(0);
				String strValue = sb.toString();
				return "-" + strValue;
			}
			return sb.toString();
		}
		return CommonFinancialLibrary.ERROR;

	}

	public static String calculateGrowthRate(String mostRecentYearValue, String lastYearValue) {
		if (mostRecentYearValue.equals("—") || lastYearValue.equals("—")) {
			return CommonFinancialLibrary.ERROR;
		} else {
			if (!CommonFinancialLibrary.determineValidGrowthRate(mostRecentYearValue, lastYearValue)) {
				return CommonFinancialLibrary.ERROR;
			}
			try {

				BigDecimal mostRecentYearNumericValue = new BigDecimal(mostRecentYearValue);
				BigDecimal lastYearNumericValue = new BigDecimal(lastYearValue);
				mostRecentYearNumericValue = mostRecentYearNumericValue.abs();
				lastYearNumericValue = lastYearNumericValue.abs();
				BigDecimal difference = mostRecentYearNumericValue.subtract(lastYearNumericValue);
				difference = difference.abs();

				BigDecimal growthRate = difference.divide(lastYearNumericValue, 4, RoundingMode.HALF_UP);
				BigDecimal multiplyer = new BigDecimal("100");
				growthRate = growthRate.multiply(multiplyer);
				growthRate = growthRate.setScale(2, RoundingMode.CEILING);
				if (Double.parseDouble(mostRecentYearValue) > Double.parseDouble(lastYearValue)) {
					return "+" + growthRate.toString() + "%";
				} else if (growthRate.doubleValue() == 0) {
					return growthRate.toString() + "%";
				} else {
					return "-" + growthRate.toString() + "%";
				}
			} catch (ArithmeticException e) {
				return CommonFinancialLibrary.ERROR;
			}
		}

	}

	public static String convertNumberWithLetterToFullNumber(String number) {
		try {
			String letter = number.substring(number.length() - 1);
			String numberWithLetterRemoved = number.substring(0, number.length() - 1);
			BigDecimal numberWithLetterRemovedDecimal = new BigDecimal(numberWithLetterRemoved);
			if (letter.equals("M")) {
				BigDecimal millionAsBigDecimal = new BigDecimal("1000000");
				BigDecimal result = numberWithLetterRemovedDecimal.multiply(millionAsBigDecimal);
				return result.toString();
			} else if (letter.equals("B")) {
				BigDecimal billionAsBigDecimal = new BigDecimal("1000000000");
				BigDecimal result = numberWithLetterRemovedDecimal.multiply(billionAsBigDecimal);
				return result.toString();
			} else if (letter.equals("K")) {
				BigDecimal thousandAsBigDecimal = new BigDecimal("1000");
				BigDecimal result = numberWithLetterRemovedDecimal.multiply(thousandAsBigDecimal);
				return result.toString();
			} else if (letter.equals("T")) {
				BigDecimal trillionAsBigDecimal = new BigDecimal("1000000000000");
				BigDecimal result = numberWithLetterRemovedDecimal.multiply(trillionAsBigDecimal);
				return result.toString();
			} else {
				return "ERROR";
			}
		} catch (Exception e) {
			return "ERROR";
		}

	}

	public static boolean determineValidGrowthRate(String mostRecentYearValue, String lastYearValue) {
		try {
			Double mostRecentYearValueDouble = Double.parseDouble(mostRecentYearValue);
			Double lastYearValueDouble = Double.parseDouble(lastYearValue);
			if ((mostRecentYearValueDouble > 0 && lastYearValueDouble < 0)
					|| (mostRecentYearValueDouble < 0 && lastYearValueDouble > 0)) {
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String checkAndConvertEmptyValueToZero(String value) {
		if (value.equals("—") || value.isEmpty()) {
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

	public static String addAppropriateCommasToNumber(String number) {
		System.out.println(number);
		if (!number.equals(CommonFinancialLibrary.errorMessage())) {
			try {
				double double_number = Double.parseDouble(number);
				DecimalFormat formatter = new DecimalFormat("#,###.00");
				String formattedNumber = formatter.format(double_number);
				return formattedNumber;
			} catch (Exception e) {
				return CommonFinancialLibrary.ERROR;
			}
		} else {
			return CommonFinancialLibrary.errorMessage();
		}

	}

	public static String addDollarSignToNumber(String number) {
		return "$" + number;
	}

	public static String removeDecimalFromNumber(String number) {
		if (number.contains(".")) {
			return number.split(Pattern.quote("."))[0];
		} else {
			return number;
		}
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

	public static String convertToPercentage(BigDecimal decimalValue) {
		String result = "";
		BigDecimal multiplyer = new BigDecimal("100");
		BigDecimal resultDecimal = decimalValue.multiply(multiplyer);
		resultDecimal = resultDecimal.setScale(2, RoundingMode.CEILING);
		result = resultDecimal.toString() + "%";
		return result;
	}

	public static String handleNaNData(BigDecimal NaNData, String beginValue, String endValue) {
		if (NaNData == null) {
			return ERROR_MSSG;
		} else {
			try {
				if (Double.parseDouble(endValue) > Double.parseDouble(beginValue)) {
					String convertedToPercentage = CommonFinancialLibrary.convertToPercentage(NaNData);
					if (!Character.toString(convertedToPercentage.charAt(0)).equals("-")) {
						return "+" + convertedToPercentage;
					} else {
						convertedToPercentage = convertedToPercentage.substring(1);
						return "+" + convertedToPercentage;
					}
				} else {
					String convertedToPercentage = CommonFinancialLibrary.convertToPercentage(NaNData);
					if (!Character.toString(convertedToPercentage.charAt(0)).equals("-")) {
						return "-" + convertedToPercentage;
					} else {
						return convertedToPercentage;
					}
				}
			} catch (Exception e) {
				return CommonFinancialLibrary.ERROR;
			}

		}
	}

	public static String addCommasToNumericString(String digits) {
		if (!digits.equals(CommonFinancialLibrary.errorMessage())) {
			String value = "";
			if (Character.toString(digits.charAt(0)).equals("-")) {
				value = digits.substring(1);
			} else {
				value = digits;
			}
			String result = "";
			for (int i = 1; i <= value.length(); ++i) {
				char ch = value.charAt(value.length() - i);
				if (i % 3 == 1 && i > 1) {
					result = "," + result;
				}
				result = ch + result;
			}

			if (Character.toString(digits.charAt(0)).equals("-")) {
				return "-" + result;
			} else {
				return result;
			}
		} else {
			return CommonFinancialLibrary.errorMessage();
		}

	}

	public static String getApprorpriateSpaces(String value) {
		int valueLength = value.length();
		int numSpaces = NUM_SPACES_BETWEEN_DATES - valueLength;
		String spaces = "";
		for (int i = 0; i < numSpaces; i++) {
			spaces = spaces + " ";
		}
		return spaces;
	}

	public static String calculateAverageGrowthRateForFivePeriods(String beginValue, String endValue) {
		if (!(beginValue.equals("—") || endValue.equals("—"))) {
			if (!CommonFinancialLibrary.determineValidGrowthRate(beginValue, endValue)) {
				return CommonFinancialLibrary.ERROR;
			}
			BigDecimal beginNumeric = new BigDecimal(beginValue);
			BigDecimal endNumeric = new BigDecimal(endValue);
			BigDecimal powerValue = new BigDecimal("0.20");
			try {
				BigDecimal divisionResult = endNumeric.divide(beginNumeric, 4, RoundingMode.HALF_UP);
				double divResultAsDouble = divisionResult.doubleValue();
				if (divResultAsDouble < 0) {
					return null;
				}
				double powerValAsDouble = powerValue.doubleValue();
				double powerResultAsDouble = Math.pow(divResultAsDouble, powerValAsDouble);
				BigDecimal powerResult = new BigDecimal(powerResultAsDouble);
				BigDecimal subtractionValue = new BigDecimal("1");
				BigDecimal result = powerResult.subtract(subtractionValue);
				result = result.setScale(4, RoundingMode.CEILING);
				return CommonFinancialLibrary.handleNaNData(result, beginValue, endValue);
			} catch (ArithmeticException e) {
				return CommonFinancialLibrary.ERROR;
			}
		}
		return CommonFinancialLibrary.ERROR;

	}

	public static String calculateAverageGrowthRateForThreePeriods(String beginValue, String endValue) {
		if (!(beginValue.equals("—") || endValue.equals("—"))) {
			if (!CommonFinancialLibrary.determineValidGrowthRate(beginValue, endValue)) {
				return CommonFinancialLibrary.ERROR;
			}
			BigDecimal beginNumeric = new BigDecimal(beginValue);
			BigDecimal endNumeric = new BigDecimal(endValue);
			BigDecimal powerValue = new BigDecimal("0.3333");
			try {
				BigDecimal divisionResult = endNumeric.divide(beginNumeric, 4, RoundingMode.HALF_UP);
				double divResultAsDouble = divisionResult.doubleValue();
				if (divResultAsDouble < 0) {
					return null;
				}
				double powerValAsDouble = powerValue.doubleValue();
				double powerResultAsDouble = Math.pow(divResultAsDouble, powerValAsDouble);
				BigDecimal powerResult = new BigDecimal(powerResultAsDouble);
				BigDecimal subtractionValue = new BigDecimal("1");
				BigDecimal result = powerResult.subtract(subtractionValue);
				result = result.setScale(4, RoundingMode.CEILING);
				return CommonFinancialLibrary.handleNaNData(result, beginValue, endValue);
			} catch (ArithmeticException e) {
				return CommonFinancialLibrary.ERROR;
			}
		}
		return CommonFinancialLibrary.ERROR;

	}

	public static boolean areValuesNegative(List<String> listOfValues) {
		BigDecimal compareToZero = new BigDecimal("0");
		for (String value : listOfValues) {
			BigDecimal valueAsNumeric = new BigDecimal(value);
			if (valueAsNumeric.compareTo(compareToZero) < 0) {
				return true;
			}
		}
		return false;
	}

	public static int countNegativeValues(List<String> listOfValues) {
		BigDecimal compareToZero = new BigDecimal("0");
		int countNegValues = 0;
		for (String value : listOfValues) {
			BigDecimal valueAsNumeric = new BigDecimal(value);
			if (valueAsNumeric.compareTo(compareToZero) < 0) {
				countNegValues++;
			}
		}
		return countNegValues;
	}

	public static void writeSeperator(BufferedWriter writer) throws IOException {
		writer.write(
				"---------------------------------------------------------------------------------------------------------------------------------");
		writer.newLine();
	}

}
