package create.excel.bo;

import excel.library.CommonFinancialLibrary;

public class BSInfoBO extends InfoBO {

	private String netEquity;
	private String netEquityGrowth;
	private String cashAndCashEquivalent;
	private String shortTermInvestments;
	private String receivables;
	private String longTermInvestments;
	private String shortTermDebt;
	private String accountsPayable;
	private String longTermDebt;
	private String bsDate;
	private String shareHoldersEquity;
	private String shareHoldersEquityGrowth;
	private String totalIntangibleAssets;
	private String currencyType;

	public BSInfoBO(int year, String ticker) {
		super(year, ticker);
	}

	public String getCashAndCashEquivalent() {
		return cashAndCashEquivalent.replace(",", "");
	}

	public String getNetEquity() {
		return netEquity.replace(",", "");
	}

	public String getShortTermInvestments() {
		return shortTermInvestments.replace(",", "");
	}

	public String getReceivables() {
		return receivables.replace(",", "");
	}

	public String getLongTermInvestments() {
		return longTermInvestments.replace(",", "");
	}

	public String getShortTermDebt() {
		return shortTermDebt.replace(",", "");
	}

	public String getAccountsPayable() {
		return accountsPayable.replace(",", "");
	}

	public String getLongTermDebt() {
		return longTermDebt.replace(",", "");
	}

	public String getBsDate() {
		return bsDate;
	}

	public String getShareHoldersEquity() {
		return shareHoldersEquity.replace(",", "");
	}

	public String getTotalIntangibleAssets() {
		return totalIntangibleAssets.replace(",", "");
	}

	public void setNetEquity(String netEquity) {
		String checkedNetEquity = CommonFinancialLibrary.formatStringValueForExcelSheet(netEquity);
		this.netEquity = checkedNetEquity;
	}

	public void setCashAndCashEquivalent(String cashAndCashEquivalent) {
		String checkedCashAndCashEquivalent = CommonFinancialLibrary
				.formatStringValueForExcelSheet(cashAndCashEquivalent);
		this.cashAndCashEquivalent = checkedCashAndCashEquivalent;
	}

	public void setShortTermInvestments(String shortTermInvestments) {
		String checkedShortTermInvestment = CommonFinancialLibrary.formatStringValueForExcelSheet(shortTermInvestments);
		this.shortTermInvestments = checkedShortTermInvestment;
	}

	public void setReceivables(String receivables) {
		String checkedReceivables = CommonFinancialLibrary.formatStringValueForExcelSheet(receivables);
		this.receivables = checkedReceivables;
	}

	public void setLongTermInvestments(String longTermInvestments) {
		String checkedLongTermInvestments = CommonFinancialLibrary.formatStringValueForExcelSheet(longTermInvestments);
		this.longTermInvestments = checkedLongTermInvestments;
	}

	public void setShortTermDebt(String shortTermDebt) {
		String checkedShortTermDebt = CommonFinancialLibrary.formatStringValueForExcelSheet(shortTermDebt);
		this.shortTermDebt = checkedShortTermDebt;
	}

	public void setAccountsPayable(String accountsPayable) {
		String checkedAccountsPayable = CommonFinancialLibrary.formatStringValueForExcelSheet(accountsPayable);
		this.accountsPayable = checkedAccountsPayable;
	}

	public void setLongTermDebt(String longTermDebt) {
		String checkedLongTermDebt = CommonFinancialLibrary.formatStringValueForExcelSheet(longTermDebt);
		this.longTermDebt = checkedLongTermDebt;
	}

	public void setBsDate(String bsDate) {
		String checkedBSDate = CommonFinancialLibrary.formatStringValueForExcelSheet(bsDate);
		this.bsDate = checkedBSDate;
	}

	public void setShareHoldersEquity(String shareHoldersEquity) {
		String checkedShareholdersEquity = CommonFinancialLibrary.formatStringValueForExcelSheet(shareHoldersEquity);
		this.shareHoldersEquity = checkedShareholdersEquity;
	}

	public void setTotalIntangibleAssets(String totalIntangibleAssets) {
		String checkedTotalIntangibleAssets = CommonFinancialLibrary
				.formatStringValueForExcelSheet(totalIntangibleAssets);
		this.totalIntangibleAssets = checkedTotalIntangibleAssets;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getNetEquityGrowth() {
		return netEquityGrowth;
	}

	public void setNetEquityGrowth(String netEquityGrowth) {
		this.netEquityGrowth = netEquityGrowth;
	}

	public String getShareHoldersEquityGrowth() {
		return shareHoldersEquityGrowth;
	}

	public void setShareHoldersEquityGrowth(String shareHoldersEquityGrowth) {
		this.shareHoldersEquityGrowth = shareHoldersEquityGrowth;
	}

}
