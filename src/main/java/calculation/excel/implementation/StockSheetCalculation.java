/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package calculation.excel.implementation;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.enums.StockSheetConstants;
import create.excel.implementation.StockSheet;

public class StockSheetCalculation extends FinancialSheetCalculation {

	private static final String CURRENT_RATIO_LETTER = "H";
	private static final String DEBT_EQUITY_RATIO_LETTER = "J";

	public StockSheetCalculation(Integer year) {
		super(year);
	}

	public StockSheetCalculation(Integer year, StockSheet stockSheet) {
		super(year, stockSheet);
		this.calculateStockSheet();
	}

	private void calculateStockSheet() {
		StockSheet stockSheet = (StockSheet) this.getFinancialSheet();
		List<String> companies = stockSheet.getDataService().getOrderedCompanies();

		for (String company : companies) {
			Row tickerRow = this.getFinancialSheet().getTickerToRow().get(company);
			this.writeToStockSheetRow(tickerRow);
		}

	}

	public void writeToStockSheetRow(Row tickerRow) {
		int tickerRowNum = tickerRow.getRowNum() + 1;

		String averageCurrentRatioFormula = this.setUpAverageRatioFormula(CURRENT_RATIO_LETTER, tickerRowNum);
		String averageDebtEquityRatioFormula = this.setUpAverageRatioFormula(DEBT_EQUITY_RATIO_LETTER, tickerRowNum);

		Cell averageCurrentRatioCell = tickerRow
				.createCell(StockSheetConstants.AVERAGE_CURRENT_RATIO.getStockSheetData());
		Cell averageDebtEquityRatioCell = tickerRow
				.createCell(StockSheetConstants.AVERAGE_DEBT_EQUITY_RATIO.getStockSheetData());

		averageCurrentRatioCell.setCellFormula(averageCurrentRatioFormula);
		averageDebtEquityRatioCell.setCellFormula(averageDebtEquityRatioFormula);

	}

	public String setUpAverageRatioFormula(String colLetter, int colNum) {
		StringBuilder averageRatioFormulaBuilder = new StringBuilder("(");
		String cellString = colLetter + Integer.toString(colNum);
		String currentYearSheet = this.getSheetIntString(0, "Ratios") + "!";
		String oneYearBackSheet = this.getSheetIntString(-1, "Ratios") + "!";
		String twoYearsBackSheet = this.getSheetIntString(-2, "Ratios") + "!";
		String threeYearsBackSheet = this.getSheetIntString(-3, "Ratios") + "!";
		String fourYearsBackSheet = this.getSheetIntString(-4, "Ratios") + "!";
		averageRatioFormulaBuilder
				.append(currentYearSheet + cellString + "+" + oneYearBackSheet + cellString + "+" + twoYearsBackSheet
						+ cellString + "+" + threeYearsBackSheet + cellString + "+" + fourYearsBackSheet + cellString);
		averageRatioFormulaBuilder.append(")");
		averageRatioFormulaBuilder.append("/5");
		return averageRatioFormulaBuilder.toString();

	}

	public static String getCurrentRatioLetter() {
		return CURRENT_RATIO_LETTER;
	}

	public static String getDebtEquityRatioLetter() {
		return DEBT_EQUITY_RATIO_LETTER;
	}

}
