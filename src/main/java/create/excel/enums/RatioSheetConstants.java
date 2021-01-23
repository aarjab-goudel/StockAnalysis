/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.enums;

public enum RatioSheetConstants {
	NUM_COLUMNS(12), RATIO_DATE(4), EARNINGS_PER_SHARE_COLUMN(5), EARNINGS_PER_SHARE_GROWTH_COLUMN(6),
	CURRENT_RATIO_COLUMN(7), CURRENT_RATIO_GROWTH(8), DEBT_EQUITY_RATIO_COLUMN(9), DEBT_EQUITY_RATIO_GROWTH_COLUMN(10),
	FIRST_RATIO_YEAR(16), SECOND_RATIO_YEAR(17), THIRD_RATIO_YEAR(18), FOURTH_RATIO_YEAR(19), FIFTH_RATIO_YEAR(20),
	LAST_COLUMN(11);

	private Integer ratioData;

	private RatioSheetConstants(Integer ratioCol) {
		this.ratioData = ratioCol;
	}

	public Integer getRatioData() {
		return ratioData;
	}

}
