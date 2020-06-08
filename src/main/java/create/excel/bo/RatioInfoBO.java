package create.excel.bo;

public class RatioInfoBO extends InfoBO {

	private String currentRatio;
	private String currentRatioGrowth;

	private String earningsPerShare;
	private String earningsPerShareGrowth;

	private String debtToEquityRatio;
	private String debtToEquityRatioGrowth;

	private String peRatio;
	private String ratioDate;

	public RatioInfoBO(int year, String ticker) {
		super(year, ticker);
	}

	public String getEarningsPerShare() {
		return earningsPerShare;
	}

	public String getCurrentRatio() {
		return currentRatio;
	}

	public String getDebtToEquityRatio() {
		return debtToEquityRatio;
	}

	public String getPeRatio() {
		return peRatio;
	}

	public String getRatioDate() {
		return ratioDate;
	}

	public void setCurrentRatio(String currentRatio) {
		this.currentRatio = currentRatio;
	}

	public void setEarningsPerShare(String earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

	public void setDebtToEquityRatio(String debtToEquityRatio) {
		this.debtToEquityRatio = debtToEquityRatio;
	}

	public void setPeRatio(String peRatio) {
		this.peRatio = peRatio;
	}

	public void setRatioDate(String ratioDate) {
		this.ratioDate = ratioDate;
	}

	public String getCurrentRatioGrowth() {
		return currentRatioGrowth;
	}

	public void setCurrentRatioGrowth(String currentRatioGrowth) {
		this.currentRatioGrowth = currentRatioGrowth;
	}

	public String getEarningsPerShareGrowth() {
		return earningsPerShareGrowth;
	}

	public void setEarningsPerShareGrowth(String earningsPerShareGrowth) {
		this.earningsPerShareGrowth = earningsPerShareGrowth;
	}

	public String getDebtToEquityRatioGrowth() {
		return debtToEquityRatioGrowth;
	}

	public void setDebtToEquityRatioGrowth(String debtToEquityRatioGrowth) {
		this.debtToEquityRatioGrowth = debtToEquityRatioGrowth;
	}

}
