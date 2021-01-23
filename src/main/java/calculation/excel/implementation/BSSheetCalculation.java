/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package calculation.excel.implementation;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.enums.BSSheetConstants;
import create.excel.implementation.BSSheet;

public class BSSheetCalculation extends FinancialSheetCalculation {
	private static final String NET_EQUITY_LETTER = "G";

	private static final String TOTAL_SHAREHOLDERS_EQUITY_LETTER = "I";

	private static final String CASH_AND_CASH_EQUIVALENT_COLUMN = "L";
	private static final String SHORT_TERM_INVESTMENT_COLUMN = "M";
	private static final String RECIEVABLES_COLUMN = "N";
	private static final String LONG_TERM_INVESTMENT_COLUMN = "O";
	private static final String SHORT_TERM_DEBT_COLUMN = "P";
	private static final String ACCOUNTS_PAYABLE_COLUMN = "Q";
	private static final String LONG_TERM_DEBT_COLUMN = "R";

	private static final int FURTHEST_YEAR = -4;

	public BSSheetCalculation(Integer year) {
		super(year);
	}

	public BSSheetCalculation(Integer year, BSSheet bsSheet) {
		super(year, bsSheet);
		this.calculateBSSheet();
	}

	private void calculateBSSheet() {
		BSSheet bsSheet = (BSSheet) this.getFinancialSheet();
		List<String> companies = bsSheet.getDataService().getOrderedCompanies();
		for (String company : companies) {
			Row tickerRow = this.getFinancialSheet().getTickerToRow().get(company);
			this.writeToBalanceSheetRow(tickerRow);
		}

	}

	public void writeToBalanceSheetRow(Row tickerRow) {
		int tickerRowNum = tickerRow.getRowNum() + 1;
		String netEquityFormula = this.setUpNetEquityFormula(tickerRowNum);
		Cell netEquityCell = tickerRow.createCell(BSSheetConstants.NET_EQUITY_COLUMN.getBSData());
		netEquityCell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		netEquityCell.setCellFormula(netEquityFormula);

		if (this.getYear() != FURTHEST_YEAR) {
			int previousYear = this.getYear() - 1;
			String netEquityGrowthFormula = this.setUpGrowthFormula(NET_EQUITY_LETTER, "BS", tickerRowNum,
					previousYear);
			String shareHoldersGrowthFormula = this.setUpGrowthFormula(TOTAL_SHAREHOLDERS_EQUITY_LETTER, "BS",
					tickerRowNum, previousYear);

			Cell netEquityGrowthCell = tickerRow.createCell(BSSheetConstants.NET_EQUITY_GROWTH_COLUMN.getBSData());
			Cell shareHoldersGrowthCell = tickerRow
					.createCell(BSSheetConstants.SHARE_HOLDER_EQUITY_GROWTH_COLUMN.getBSData());

			shareHoldersGrowthCell.setCellFormula(shareHoldersGrowthFormula);
			netEquityGrowthCell.setCellFormula(netEquityGrowthFormula);
		}

	}

	public String setUpNetEquityFormula(Integer colNum) {
		String netEquityFormula = "(" + CASH_AND_CASH_EQUIVALENT_COLUMN + colNum.toString() + "+"
				+ SHORT_TERM_INVESTMENT_COLUMN + colNum.toString() + "+" + LONG_TERM_INVESTMENT_COLUMN
				+ colNum.toString() + ")-(" + SHORT_TERM_DEBT_COLUMN + colNum.toString() + "+" + LONG_TERM_DEBT_COLUMN
				+ colNum.toString() + ")";
		return netEquityFormula;
	}

	public static String getCashAndCashEquivalentColumn() {
		return CASH_AND_CASH_EQUIVALENT_COLUMN;
	}

	public static String getShortTermInvestmentColumn() {
		return SHORT_TERM_INVESTMENT_COLUMN;
	}

	public static String getRecievablesColumn() {
		return RECIEVABLES_COLUMN;
	}

	public static String getLongTermInvestmentColumn() {
		return LONG_TERM_INVESTMENT_COLUMN;
	}

	public static String getShortTermDebtColumn() {
		return SHORT_TERM_DEBT_COLUMN;
	}

	public static String getAccountsPayableColumn() {
		return ACCOUNTS_PAYABLE_COLUMN;
	}

	public static String getLongTermDebtColumn() {
		return LONG_TERM_DEBT_COLUMN;
	}

	public static String getNetEquityLetter() {
		return NET_EQUITY_LETTER;
	}

}
