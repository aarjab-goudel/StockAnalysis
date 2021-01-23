/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package calculation.excel.implementation;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.enums.ISSheetConstants;
import create.excel.implementation.ISSheet;

public class ISSheetCalculation extends FinancialSheetCalculation {
	private static final String REVENUE_LETTER = "G";
	private static final String COST_OF_REVENUE_LETTER = "I";
	private static final String GROSS_PROFIT_LETTER = "K";
	private static final String NET_INCOME_LETTER = "M";
	private static final String EPS_LETTER = "O";
	private static final String RESEARCH_AND_DEVELOPMENT_LETTER = "Q";

	private static final int FURTHEST_YEAR = -4;

	public ISSheetCalculation(Integer year) {
		super(year);
	}

	public ISSheetCalculation(Integer year, ISSheet isSheet) {
		super(year, isSheet);
		this.calculateISSheet();
	}

	private void calculateISSheet() {
		ISSheet isSheet = (ISSheet) this.getFinancialSheet();
		List<String> companies = isSheet.getDataService().getOrderedCompanies();
		for (String company : companies) {
			Row tickerRow = this.getFinancialSheet().getTickerToRow().get(company);
			this.writeToIncomeStatementRow(tickerRow);
		}
	}

	public void writeToIncomeStatementRow(Row tickerRow) {
		int tickerRowNum = tickerRow.getRowNum() + 1;
		if (this.getYear() != FURTHEST_YEAR) {
			int previousYear = this.getYear() - 1;

			String revenueGrowthFormula = this.setUpGrowthFormula(REVENUE_LETTER, "IS", tickerRowNum, previousYear);
			String costOfRevenueGrowthFormula = this.setUpGrowthFormula(COST_OF_REVENUE_LETTER, "IS", tickerRowNum,
					previousYear);
			String grossProfitGrowthFormula = this.setUpGrowthFormula(GROSS_PROFIT_LETTER, "IS", tickerRowNum,
					previousYear);
			String netIncomeGrowthFormula = this.setUpGrowthFormula(NET_INCOME_LETTER, "IS", tickerRowNum,
					previousYear);
			String epsGrowthFormula = this.setUpGrowthFormula(EPS_LETTER, "IS", tickerRowNum, previousYear);
			String researchAndDevelopmentGrowthFormula = this.setUpGrowthFormula(RESEARCH_AND_DEVELOPMENT_LETTER, "IS",
					tickerRowNum, previousYear);

			Cell revenueGrowthCell = tickerRow.createCell(ISSheetConstants.REVENUE_GROWTH_COLUMN.getIsData());
			Cell costOfRevenueGrowthCell = tickerRow
					.createCell(ISSheetConstants.COST_OF_REVENUE_GROWTH_COLUMN.getIsData());
			Cell grossProfitGrowthCell = tickerRow.createCell(ISSheetConstants.GROSS_PROFIT_GROWTH_COLUMN.getIsData());
			Cell netIncomeGrowthCell = tickerRow.createCell(ISSheetConstants.NET_INCOME_GOWTH_COLUMN.getIsData());
			Cell epsGrowthCell = tickerRow.createCell(ISSheetConstants.EPS_GROWTH_COLUMN.getIsData());
			Cell researchAndDevelopmentGrowthCell = tickerRow
					.createCell(ISSheetConstants.RESEARCH_AND_DEVELOPMENT_GROWTH_COLUMN.getIsData());

			revenueGrowthCell.setCellFormula(revenueGrowthFormula);
			costOfRevenueGrowthCell.setCellFormula(costOfRevenueGrowthFormula);
			grossProfitGrowthCell.setCellFormula(grossProfitGrowthFormula);
			netIncomeGrowthCell.setCellFormula(netIncomeGrowthFormula);
			epsGrowthCell.setCellFormula(epsGrowthFormula);
			researchAndDevelopmentGrowthCell.setCellFormula(researchAndDevelopmentGrowthFormula);

		}

	}

	public static String getRevenueLetter() {
		return REVENUE_LETTER;
	}

	public static String getCostOfRevenueLetter() {
		return COST_OF_REVENUE_LETTER;
	}

	public static String getGrossProfitLetter() {
		return GROSS_PROFIT_LETTER;
	}

	public static String getNetIncomeLetter() {
		return NET_INCOME_LETTER;
	}

}
