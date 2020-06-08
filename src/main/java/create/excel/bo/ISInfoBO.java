package create.excel.bo;

import excel.library.CommonFinancialLibrary;

public class ISInfoBO extends InfoBO {

	private String revenue;
	private String revenueGrowth;

	private String costOfRevenue;
	private String grossProfit;
	private String grossProfitGrowth;

	private String netIncome;
	private String netIncomeGrowth;

	private String isDate;
	private String eps;
	private String currencyType;

	public ISInfoBO(int year, String ticker) {
		super(year, ticker);
	}

	public String getRevenue() {
		return revenue.replace(",", "");
	}

	public String getCostOfRevenue() {
		return costOfRevenue.replace(",", "");
	}

	public String getGrossProfit() {
		return grossProfit.replace(",", "");
	}

	public String getNetIncome() {
		return netIncome.replace(",", "");
	}

	public String getIsDate() {
		return isDate;
	}

	public void setRevenue(String revenue) {
		String checkedRevenue = CommonFinancialLibrary.formatStringValueForExcelSheet(revenue);
		this.revenue = checkedRevenue;
	}

	public void setCostOfRevenue(String costOfRevenue) {
		String checkedCostOfRevenue = CommonFinancialLibrary.formatStringValueForExcelSheet(costOfRevenue);
		this.costOfRevenue = checkedCostOfRevenue;
	}

	public void setGrossProfit(String grossProfit) {
		String checkedGrossProfit = CommonFinancialLibrary.formatStringValueForExcelSheet(grossProfit);
		this.grossProfit = checkedGrossProfit;
	}

	public void setNetIncome(String netIncome) {
		String checkedNetIncome = CommonFinancialLibrary.formatStringValueForExcelSheet(netIncome);
		this.netIncome = checkedNetIncome;
	}

	public void setIsDate(String isDate) {
		String checkedISDate = CommonFinancialLibrary.formatStringValueForExcelSheet(isDate);
		this.isDate = checkedISDate;
	}

	public String getEps() {
		return eps.replace(",", "");
	}

	public void setEps(String eps) {
		String checkedEPS = CommonFinancialLibrary.formatStringValueForExcelSheet(eps);
		this.eps = checkedEPS;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getRevenueGrowth() {
		return revenueGrowth;
	}

	public void setRevenueGrowth(String revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}

	public String getGrossProfitGrowth() {
		return grossProfitGrowth;
	}

	public void setGrossProfitGrowth(String grossProfitGrowth) {
		this.grossProfitGrowth = grossProfitGrowth;
	}

	public String getNetIncomeGrowth() {
		return netIncomeGrowth;
	}

	public void setNetIncomeGrowth(String netIncomeGrowth) {
		this.netIncomeGrowth = netIncomeGrowth;
	}

}
