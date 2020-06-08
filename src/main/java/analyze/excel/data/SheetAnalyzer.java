package analyze.excel.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SheetAnalyzer {

	public SheetAnalyzer() {

	}

	public static BigDecimal calculateAverageGrowthRate(String beginValue, String endValue) {
		BigDecimal beginNumeric = new BigDecimal(beginValue);
		BigDecimal endNumeric = new BigDecimal(endValue);
		BigDecimal powerValue = new BigDecimal("0.20");
		BigDecimal divisionResult = beginNumeric.divide(endNumeric, 4, RoundingMode.HALF_UP);
		double divResultAsDouble = divisionResult.doubleValue();
		double powerValAsDouble = powerValue.doubleValue();
		double powerResultAsDouble = Math.pow(divResultAsDouble, powerValAsDouble);
		BigDecimal powerResult = new BigDecimal(powerResultAsDouble);
		BigDecimal subtractionValue = new BigDecimal("1");
		BigDecimal result = powerResult.subtract(subtractionValue);
		result = result.setScale(4, RoundingMode.CEILING);
		return result;
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

}
