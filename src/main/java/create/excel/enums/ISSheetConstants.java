package create.excel.enums;

public enum ISSheetConstants {
	NUM_COLUMNS(15), IS_DATE(4), CURRENCY_TYPE(5), REVENUE_COLUMN(6), REVENUE_GROWTH_COLUMN(7),
	COST_OF_REVENUE_COLUMN(8), COST_OF_REVENUE_GROWTH_COLUMN(9), GROSS_PROFIT_COLUMN(10),
	GROSS_PROFIT_GROWTH_COLUMN(11), NET_INCOME_COLUMN(12), FIRST_IS_YEAR(6), SECOND_IS_YEAR(7), THIRD_IS_YEAR(8),
	FOURTH_IS_YEAR(9), FIFTH_IS_YEAR(10), NET_INCOME_GOWTH_COLUMN(13), LAST_COLUMN(14);

	private Integer isData;

	private ISSheetConstants(Integer isCol) {
		this.isData = isCol;
	}

	public Integer getIsData() {
		return isData;
	}

}
