/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package calculation.excel.implementation;

import create.excel.implementation.FinancialSheet;

public class FinancialSheetCalculation {

	private FinancialSheet financialSheet;
	private Integer year;

	public FinancialSheetCalculation(Integer year) {
		this.year = year;
	}

	public FinancialSheetCalculation(Integer year, FinancialSheet financialSheet) {
		this.year = year;
		this.financialSheet = financialSheet;
	}

	public String setUpGrowthFormula(String colLetter, String financialSheetCode, Integer colNum, Integer year) {
		StringBuilder presentYearBuilder = new StringBuilder();
		presentYearBuilder.append(colLetter + colNum.toString());
		String presentYearValue = presentYearBuilder.toString();

		StringBuilder pastYearBuilder = new StringBuilder();
		String strSheetInt = this.getSheetIntString(year, financialSheetCode);
		pastYearBuilder.append(strSheetInt).append("!").append(colLetter + colNum.toString());
		String pastYearValue = pastYearBuilder.toString();

		String growthFormula = "(" + presentYearValue + "-" + pastYearValue + ")";
		growthFormula = growthFormula + "/(" + pastYearValue + ")";
		return growthFormula;
	}

	public String getSheetIntString(int num, String financialSheetCode) {
		if (num == 0) {
			return financialSheetCode;
		} else if (num == -1) {
			return financialSheetCode + "One";
		} else if (num == -2) {
			return financialSheetCode + "Two";
		} else if (num == -3) {
			return financialSheetCode + "Three";
		} else if (num == -4) {
			return financialSheetCode + "Four";
		}
		return null;
	}

	public FinancialSheet getFinancialSheet() {
		return financialSheet;
	}

	public void setFinancialSheet(FinancialSheet financialSheet) {
		this.financialSheet = financialSheet;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
