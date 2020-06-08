package create.excel.enums;

public enum StockSheetConstants {
	NUM_COLUMNS(17), MARKET_CAPITALIZATION_COLUMN(4), BETA_COLUMN(5), DIVIDEND_ANNUAL_PERCENTAGE_COLUMN(6),
	AVERAGE_DIVIDEND_ANNUAL_PERCENTAGE_COLUMN(7), PB_RATIO_COLUMN(8), AVERAGE_PB_RATIO_COLUMN(9), PE_RATIO_COLUMN(10),
	AVERAGE_PE_RATIO_COLUMN(11), FORWARD_PE_RATIO_COLUMN(12), PEG_RATIO_COLUMN(13), AVERAGE_CURRENT_RATIO(14),
	AVERAGE_DEBT_EQUITY_RATIO(15);
	private Integer stockSheetData;

	private StockSheetConstants(Integer stockSheetCol) {
		this.stockSheetData = stockSheetCol;
	}

	public Integer getStockSheetData() {
		return stockSheetData;
	}

}
