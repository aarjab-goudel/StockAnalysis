/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.enums;

public enum CommonSheetConstants {
	STOCK_NAME_COLUMN(0), TICKER_COLUMN(1), SECTOR_COLUMN(2), INDUSTRY_COLUMN(3), FIRST_YEAR(0), SECOND_YEAR(-1),
	THIRD_YEAR(-2), FOURTH_YEAR(-3), FIFTH_YEAR(-4);

	private Integer commonColumn;

	private CommonSheetConstants(final Integer commonColumn) {
		this.commonColumn = commonColumn;
	}

	public Integer getCommonColumn() {
		return commonColumn;
	}

}
