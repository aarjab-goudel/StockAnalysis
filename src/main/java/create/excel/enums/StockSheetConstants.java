/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.enums;

public enum StockSheetConstants {
	NUM_COLUMNS(15), DIVIDEND_ANNUAL_PERCENTAGE_COLUMN(4), AVERAGE_DIVIDEND_ANNUAL_PERCENTAGE_COLUMN(5),
	PB_RATIO_COLUMN(6), AVERAGE_PB_RATIO_COLUMN(7), PE_RATIO_COLUMN(8), AVERAGE_PE_RATIO_COLUMN(9),
	FORWARD_PE_RATIO_COLUMN(10), PEG_RATIO_COLUMN(11), AVERAGE_CURRENT_RATIO(12), AVERAGE_DEBT_EQUITY_RATIO(13);
	private Integer stockSheetData;

	private StockSheetConstants(Integer stockSheetCol) {
		this.stockSheetData = stockSheetCol;
	}

	public Integer getStockSheetData() {
		return stockSheetData;
	}

}
