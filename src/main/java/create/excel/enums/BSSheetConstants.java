/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.enums;

public enum BSSheetConstants {
	NUM_COLUMNS(14), BS_DATE(4), CURRENCY_TYPE(5),
	SHARE_HOLDER_EQUITY_COLUMN(6), SHARE_HOLDER_EQUITY_GROWTH_COLUMN(7), 
	TOTAL_ASSETS_COLUMN(8), TOTAL_LIABILITIES_COLUMN(9), TOTAL_CASH_MRQ_COLUMN(10), TOTAL_DEBT_MRQ_COLUMN(11), CURRENT_RATIO_MRQ_COLUMN(12),
	LAST_COLUMN(13), FIRST_BS_YEAR(1), SECOND_BS_YEAR(2), THIRD_BS_YEAR(3),
	FOURTH_BS_YEAR(4), FIFTH_BS_YEAR(5);

	private Integer bsData;

	private BSSheetConstants(Integer bsCol) {
		this.bsData = bsCol;
	}

	public Integer getBSData() {
		return bsData;
	}

}
