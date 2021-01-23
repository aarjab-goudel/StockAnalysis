/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.bo;

import excel.library.CommonFinancialLibrary;

public class CFInfoBO extends InfoBO {

	private String netCashByOperatingActivites;
	private String netCashForInvestingActivities;
	private String netCashForFinancingActivities;
	private String capitalExpenditure;
	private String cfDate;
	private String freeCashFlow;
	private String freeCashFlowGrowth;
	private String currencyType;

	public CFInfoBO(int year, String ticker) {
		super(year, ticker);
	}

	public String getNetCashByOperatingActivites() {
		return netCashByOperatingActivites.replace(",", "");
	}

	public String getNetCashForInvestingActivities() {
		return netCashForInvestingActivities.replace(",", "");
	}

	public String getNetCashForFinancingActivities() {
		return netCashForFinancingActivities.replace(",", "");
	}

	public void setNetCashByOperatingActivites(String netCashByOperatingActivites) {
		String checkedNetCashByOperatingActivities = CommonFinancialLibrary
				.formatStringValueForExcelSheet(netCashByOperatingActivites);
		this.netCashByOperatingActivites = checkedNetCashByOperatingActivities;
	}

	public void setNetCashForInvestingActivities(String netCashForInvestingActivities) {
		String checkedNetCashForInvestingActivities = CommonFinancialLibrary
				.formatStringValueForExcelSheet(netCashForInvestingActivities);
		this.netCashForInvestingActivities = checkedNetCashForInvestingActivities;
	}

	public void setNetCashForFinancingActivities(String netCashForFinancingActivities) {
		String checkedNetCashForFinancingActivities = CommonFinancialLibrary
				.formatStringValueForExcelSheet(netCashForFinancingActivities);
		this.netCashForFinancingActivities = checkedNetCashForFinancingActivities;
	}

	public String getCapitalExpenditure() {
		return capitalExpenditure.replace(",", "");
	}

	public void setCapitalExpenditure(String capitalExpenditure) {
		String checkedCapitalExpenditure = CommonFinancialLibrary.formatStringValueForExcelSheet(capitalExpenditure);
		this.capitalExpenditure = checkedCapitalExpenditure;
	}

	public String getCfDate() {
		return cfDate;
	}

	public void setCfDate(String cfDate) {
		this.cfDate = cfDate;
	}

	public String getFreeCashFlow() {
		return freeCashFlow.replace(",", "");
	}

	public void setFreeCashFlow(String freeCashFlow) {
		String checkedFreeCashFlow = CommonFinancialLibrary.formatStringValueForExcelSheet(freeCashFlow);
		this.freeCashFlow = checkedFreeCashFlow;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getFreeCashFlowGrowth() {
		return freeCashFlowGrowth;
	}

	public void setFreeCashFlowGrowth(String freeCashFlowGrowth) {
		this.freeCashFlowGrowth = freeCashFlowGrowth;
	}

}
