package calculation.excel.implementation;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import create.excel.enums.CFSheetConstants;
import create.excel.implementation.CFSheet;
import create.excel.implementation.FinancialSheet;

public class CFSheetCalculation extends FinancialSheetCalculation {
	private static final String NET_CASH_BY_OPERATING_LETTER = "I";
	private static final String CAPITAL_EXPENDITURE_LETTER = "M";
	private static final String FREE_CASH_FLOW_LETTER = "G";

	private static final int FURTHEST_YEAR = -4;

	public CFSheetCalculation(Integer year, FinancialSheet cfSheet) {
		super(year, cfSheet);
		this.calculateCFSheet();
	}

	public CFSheetCalculation(Integer year) {
		super(year);
	}

	public void calculateCFSheet() {
		CFSheet cfSheet = (CFSheet) this.getFinancialSheet();
		List<String> companies = cfSheet.getDataService().getOrderedCompanies();
		for (String company : companies) {
			Row tickerRow = this.getFinancialSheet().getTickerToRow().get(company);
			this.writeToCashFlowRow(tickerRow);
		}
	}

	public void writeToCashFlowRow(Row tickerRow) {
		int tickerRowNum = tickerRow.getRowNum() + 1;

		if (this.getYear() != FURTHEST_YEAR) {
			int previousYear = this.getYear() - 1;
			String netCashByOperatingActivitiesGrowthFormula = this.setUpGrowthFormula(NET_CASH_BY_OPERATING_LETTER,
					"CF", tickerRowNum, previousYear);
			Cell netCashByOperatingActivitiesGrowthCell = tickerRow
					.createCell(CFSheetConstants.NET_CASH_BY_OPERATING_ACTIVITIES_GROWTH_COLUMN.getCfData());
			netCashByOperatingActivitiesGrowthCell.setCellFormula(netCashByOperatingActivitiesGrowthFormula);

			String freeCashFlowGrowthFormula = this.setUpGrowthFormula(FREE_CASH_FLOW_LETTER, "CF", tickerRowNum,
					previousYear);
			Cell freeCashFlowGrowthCell = tickerRow
					.createCell(CFSheetConstants.FREE_CASH_FLOW_GROWTH_COLUMN.getCfData());
			freeCashFlowGrowthCell.setCellFormula(freeCashFlowGrowthFormula);
		}

	}

	public static String getNetCashByOperatingLetter() {
		return NET_CASH_BY_OPERATING_LETTER;
	}

	public static String getCapitalExpenditureLetter() {
		return CAPITAL_EXPENDITURE_LETTER;
	}

	public static String getFreeCashFlowLetter() {
		return FREE_CASH_FLOW_LETTER;
	}

}
