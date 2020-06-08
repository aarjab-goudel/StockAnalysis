package calculation.excel.implementation;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.enums.RatioSheetConstants;
import create.excel.implementation.RatioSheet;

public class RatioSheetCalculation extends FinancialSheetCalculation {

	private static final String EPS_LETTER = "F";
	private static final String CURRENT_RATIO_LETTER = "H";
	private static final String DEBT_EQUITY_RATIO_LETTER = "J";

	private static final int FURTHEST_YEAR = -4;

	public RatioSheetCalculation(Integer year) {
		super(year);
	}

	public RatioSheetCalculation(Integer year, RatioSheet ratioSheet) {
		super(year, ratioSheet);
		this.calculateRatioSheet();
	}

	private void calculateRatioSheet() {
		RatioSheet ratioSheet = (RatioSheet) this.getFinancialSheet();
		List<String> companies = ratioSheet.getDataService().getOrderedCompanies();
		for (String company : companies) {
			Row tickerRow = this.getFinancialSheet().getTickerToRow().get(company);
			this.writeToRatioSheetRow(tickerRow);
		}

	}

	public void writeToRatioSheetRow(Row tickerRow) {
		int tickerRowNum = tickerRow.getRowNum() + 1;
		if (this.getYear() != FURTHEST_YEAR) {
			int previousYear = this.getYear() - 1;

			String EPSGrowthFormula = this.setUpGrowthFormula(EPS_LETTER, "Ratios", tickerRowNum, previousYear);
			String currentRatioGrowthFormula = this.setUpGrowthFormula(CURRENT_RATIO_LETTER, "Ratios", tickerRowNum,
					previousYear);
			String debtEquityRatioGrowthFormula = this.setUpGrowthFormula(DEBT_EQUITY_RATIO_LETTER, "Ratios",
					tickerRowNum, previousYear);

			Cell EPSGrowthCell = tickerRow
					.createCell(RatioSheetConstants.EARNINGS_PER_SHARE_GROWTH_COLUMN.getRatioData());
			Cell currentRatioCell = tickerRow.createCell(RatioSheetConstants.CURRENT_RATIO_GROWTH.getRatioData());
			Cell debtEquityRatioCell = tickerRow
					.createCell(RatioSheetConstants.DEBT_EQUITY_RATIO_GROWTH_COLUMN.getRatioData());

			EPSGrowthCell.setCellFormula(EPSGrowthFormula);
			currentRatioCell.setCellFormula(currentRatioGrowthFormula);
			debtEquityRatioCell.setCellFormula(debtEquityRatioGrowthFormula);
		}

	}

	public static String getEpsLetter() {
		return EPS_LETTER;
	}

	public static String getCurrentRatioLetter() {
		return CURRENT_RATIO_LETTER;
	}

	public static String getDebtEquityRatioLetter() {
		return DEBT_EQUITY_RATIO_LETTER;
	}

}
