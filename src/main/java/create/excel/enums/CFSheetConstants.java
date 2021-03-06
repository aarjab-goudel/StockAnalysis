/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.enums;

public enum CFSheetConstants {
	NUM_COLUMNS(14), CF_DATE(4), CAPITAL_EXPENDITURE_COLUMN(12), NET_CASH_BY_OPERATING_ACTIVITIES_COLUMN(8),
	NET_CASH_BY_OPERATING_ACTIVITIES_GROWTH_COLUMN(9), NET_CASH_FOR_INVESTING_COLUMN(10),
	NET_CASH_FOR_FINANCING_COLUMN(11), CURRENCY_TYPE(5), FREE_CASH_FLOW_COLUMN(6), FREE_CASH_FLOW_GROWTH_COLUMN(7),
	FIRST_CF_YEAR(11), SECOND_CF_YEAR(12), THIRD_CF_YEAR(13), FOURTH_CF_YEAR(14), FIFTH_CF_YEAR(15), LAST_COLUMN(13);

	private Integer cfData;

	private CFSheetConstants(Integer cfCol) {
		this.cfData = cfCol;
	}

	public Integer getCfData() {
		return cfData;
	}

}
