/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.bo;

import excel.library.CommonFinancialLibrary;

public class BSInfoBO extends InfoBO {

	//private String netEquity;
	//private String netEquityGrowth;
	//private String cashAndCashEquivalent;
	//private String cashAndCashEquivalentGrowth;
	//private String shortTermInvestments;
	//private String shortTermInvestmentsGrowth;
	//private String receivables;
	//private String longTermInvestments;
	//private String longTermInvestmentsGrowth;
	//private String shortTermDebt;
	//private String shortTermDebtGrowth;
	//private String accountsPayable;
	//private String longTermDebt;
	//private String longTermDebtGrowth;
	private String bsDate;
	private String shareHoldersEquity;
	private String shareHoldersEquityGrowth;
	//private String totalIntangibleAssets;
	private String currencyType;
	
	private String totalAssets;
	private String totalAssetsGrowth;
	private String totalLiabilities;
	private String totalLiabilitiesGrowth;
	
	//private String totalTangibleAssets;
	//private String totalTangibleAssetsGrowth;
	//private String totalDebt;
	//private String totalDebtGrowth;
	//private String totalNetDebt;
	//private String totalNetDebtGrowth;
	
	private String totalStrCash;
	private String totalStrDebt;
	private String strCurrentRatio;
	
	

	public BSInfoBO(int year, String ticker) {
		super(year, ticker);
	}
	
	public String getTotalStrCash() {
		return totalStrCash;
	}
	
	public String getTotalStrDebt() {
		return totalStrDebt;
	}
	
	public String getStrCurrentRatio() {
		return strCurrentRatio;
	}

//	public String getCashAndCashEquivalent() {
//		return cashAndCashEquivalent.replace(",", "");
//	}
//
//	public String getNetEquity() {
//		return netEquity.replace(",", "");
//	}
//
//	public String getShortTermInvestments() {
//		return shortTermInvestments.replace(",", "");
//	}

//	public String getReceivables() {
//		return receivables.replace(",", "");
//	}
//
//	public String getLongTermInvestments() {
//		return longTermInvestments.replace(",", "");
//	}
//
//	public String getShortTermDebt() {
//		return shortTermDebt.replace(",", "");
//	}
//
//	public String getAccountsPayable() {
//		return accountsPayable.replace(",", "");
//	}
//
//	public String getLongTermDebt() {
//		return longTermDebt.replace(",", "");
//	}

	public String getBsDate() {
		return bsDate;
	}

	public String getShareHoldersEquity() {
		return shareHoldersEquity.replace(",", "");
	}

//	public String getTotalIntangibleAssets() {
//		return totalIntangibleAssets.replace(",", "");
//	}

//	public void setNetEquity(String netEquity) {
//		String checkedNetEquity = CommonFinancialLibrary.formatStringValueForExcelSheet(netEquity);
//		this.netEquity = checkedNetEquity;
//	}
//
//	public void setCashAndCashEquivalent(String cashAndCashEquivalent) {
//		String checkedCashAndCashEquivalent = CommonFinancialLibrary
//				.formatStringValueForExcelSheet(cashAndCashEquivalent);
//		this.cashAndCashEquivalent = checkedCashAndCashEquivalent;
//	}
//
//	public void setShortTermInvestments(String shortTermInvestments) {
//		String checkedShortTermInvestment = CommonFinancialLibrary.formatStringValueForExcelSheet(shortTermInvestments);
//		this.shortTermInvestments = checkedShortTermInvestment;
//	}

//	public void setReceivables(String receivables) {
//		String checkedReceivables = CommonFinancialLibrary.formatStringValueForExcelSheet(receivables);
//		this.receivables = checkedReceivables;
//	}
//
//	public void setLongTermInvestments(String longTermInvestments) {
//		String checkedLongTermInvestments = CommonFinancialLibrary.formatStringValueForExcelSheet(longTermInvestments);
//		this.longTermInvestments = checkedLongTermInvestments;
//	}
//
//	public void setShortTermDebt(String shortTermDebt) {
//		String checkedShortTermDebt = CommonFinancialLibrary.formatStringValueForExcelSheet(shortTermDebt);
//		this.shortTermDebt = checkedShortTermDebt;
//	}
//
//	public void setAccountsPayable(String accountsPayable) {
//		String checkedAccountsPayable = CommonFinancialLibrary.formatStringValueForExcelSheet(accountsPayable);
//		this.accountsPayable = checkedAccountsPayable;
//	}
//
//	public void setLongTermDebt(String longTermDebt) {
//		String checkedLongTermDebt = CommonFinancialLibrary.formatStringValueForExcelSheet(longTermDebt);
//		this.longTermDebt = checkedLongTermDebt;
//	}

	public void setBsDate(String bsDate) {
		String checkedBSDate = CommonFinancialLibrary.formatStringValueForExcelSheet(bsDate);
		this.bsDate = checkedBSDate;
	}

	public void setShareHoldersEquity(String shareHoldersEquity) {
		String checkedShareholdersEquity = CommonFinancialLibrary.formatStringValueForExcelSheet(shareHoldersEquity);
		this.shareHoldersEquity = checkedShareholdersEquity;
	}

//	public void setTotalIntangibleAssets(String totalIntangibleAssets) {
//		String checkedTotalIntangibleAssets = CommonFinancialLibrary
//				.formatStringValueForExcelSheet(totalIntangibleAssets);
//		this.totalIntangibleAssets = checkedTotalIntangibleAssets;
//	}
	
	public void setTotalStrCash(String totalStrCash ) {
		this.totalStrCash = totalStrCash;
	}
	
	public void setTotalDebtCash(String totalStrDebtCash) {
		this.totalStrDebt = totalStrDebtCash;
	}
	
	public void setStrCurrentRatio(String strCurrentRatio) {
		this.strCurrentRatio = strCurrentRatio;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

//	public String getNetEquityGrowth() {
//		return netEquityGrowth;
//	}
//
//	public void setNetEquityGrowth(String netEquityGrowth) {
//		this.netEquityGrowth = netEquityGrowth;
//	}

	public String getShareHoldersEquityGrowth() {
		return shareHoldersEquityGrowth;
	}

	public void setShareHoldersEquityGrowth(String shareHoldersEquityGrowth) {
		this.shareHoldersEquityGrowth = shareHoldersEquityGrowth;
	}

//	public String getCashAndCashEquivalentGrowth() {
//		return cashAndCashEquivalentGrowth;
//	}

//	public String getShortTermInvestmentsGrowth() {
//		return shortTermInvestmentsGrowth;
//	}

//	public String getLongTermInvestmentsGrowth() {
//		return longTermInvestmentsGrowth;
//	}

//	public String getShortTermDebtGrowth() {
//		return shortTermDebtGrowth;
//	}

//	public String getLongTermDebtGrowth() {
//		return longTermDebtGrowth;
//	}
//
//	public void setCashAndCashEquivalentGrowth(String cashAndCashEquivalentGrowth) {
//		this.cashAndCashEquivalentGrowth = cashAndCashEquivalentGrowth;
//	}
//
//	public void setShortTermInvestmentsGrowth(String shortTermInvestmentsGrowth) {
//		this.shortTermInvestmentsGrowth = shortTermInvestmentsGrowth;
//	}
//
//	public void setLongTermInvestmentsGrowth(String longTermInvestmentsGrowth) {
//		this.longTermInvestmentsGrowth = longTermInvestmentsGrowth;
//	}
//
//	public void setShortTermDebtGrowth(String shortTermDebtGrowth) {
//		this.shortTermDebtGrowth = shortTermDebtGrowth;
//	}
//
//	public void setLongTermDebtGrowth(String longTermDebtGrowth) {
//		this.longTermDebtGrowth = longTermDebtGrowth;
//	}

	public String getTotalAssets() {
		return totalAssets.replace(",", "");
	}

	public void setTotalAssets(String totalAssets) {
		String formattedTotalAssets = CommonFinancialLibrary.formatStringValueForExcelSheet(totalAssets);
		this.totalAssets = formattedTotalAssets;
	}

	public String getTotalLiabilities() {
		return totalLiabilities.replace(",", "");
	}

	public void setTotalLiabilities(String totalLiabilities) {
		String formattedTotalLiabilities = CommonFinancialLibrary.formatStringValueForExcelSheet(totalLiabilities);
		this.totalLiabilities = formattedTotalLiabilities;
	}

//	public String getTotalTangibleAssets() {
//		return totalTangibleAssets;
//	}
//
//	public void setTotalTangibleAssets(String totalTangibleAssets) {
//		this.totalTangibleAssets = totalTangibleAssets;
//	}
//
//	public String getTotalDebt() {
//		return totalDebt;
//	}
//
//	public void setTotalDebt(String totalDebt) {
//		this.totalDebt = totalDebt;
//	}

//	public String getTotalNetDebt() {
//		return totalNetDebt;
//	}
//
//	public void setTotalNetDebt(String totalNetDebt) {
//		this.totalNetDebt = totalNetDebt;
//	}
//
	public String getTotalAssetsGrowth() {
		return totalAssetsGrowth;
	}

	public void setTotalAssetsGrowth(String totalAssetsGrowth) {
		this.totalAssetsGrowth = totalAssetsGrowth;
	}
//
	public String getTotalLiabilitiesGrowth() {
		return totalLiabilitiesGrowth;
	}

	public void setTotalLiabilitiesGrowth(String totalLiabilitiesGrowth) {
		this.totalLiabilitiesGrowth = totalLiabilitiesGrowth;
	}
//
//	public String getTotalTangibleAssetsGrowth() {
//		return totalTangibleAssetsGrowth;
//	}
//
//	public void setTotalTangibleAssetsGrowth(String totalTangibleAssetsGrowth) {
//		this.totalTangibleAssetsGrowth = totalTangibleAssetsGrowth;
//	}
//
//	public String getTotalDebtGrowth() {
//		return totalDebtGrowth;
//	}
//
//	public void setTotalDebtGrowth(String totalDebtGrowth) {
//		this.totalDebtGrowth = totalDebtGrowth;
//	}
//
//	public String getTotalNetDebtGrowth() {
//		return totalNetDebtGrowth;
//	}
//
//	public void setTotalNetDebtGrowth(String totalNetDebtGrowth) {
//		this.totalNetDebtGrowth = totalNetDebtGrowth;
//	}
	
	
	
	

}
