package create.excel.bo;

public class StockDataInfoBO extends InfoBO {

	private String annualDividendPercent;
	private String averageDividendPercent;
	private String PERatio;
	private String averagePERatio;
	private String PBRatio;
	private String averagePBRatio;
	private String forwardPERatio;
	private String pegRatio;
	private String beta;
	private String marketCap;
	private String sector;
	private String industry;
	private String stockName;

	public StockDataInfoBO(int year, String ticker) {
		super(year, ticker);
	}

	public String getAnnualDividendPercent() {
		return annualDividendPercent;
	}

	public void setAnnualDividendPercent(String annualDividendPercent) {
		this.annualDividendPercent = annualDividendPercent;
	}

	public String getAverageDividendPercent() {
		return averageDividendPercent;
	}

	public void setAverageDividendPercent(String averageDividendPercent) {
		this.averageDividendPercent = averageDividendPercent;
	}

	public String getPERatio() {
		return PERatio;
	}

	public void setPERatio(String pERatio) {
		PERatio = pERatio;
	}

	public String getAveragePERatio() {
		return averagePERatio;
	}

	public void setAveragePERatio(String averagePERatio) {
		this.averagePERatio = averagePERatio;
	}

	public String getPBRatio() {
		return PBRatio;
	}

	public void setPBRatio(String pBRatio) {
		PBRatio = pBRatio;
	}

	public String getAveragePBRatio() {
		return averagePBRatio;
	}

	public void setAveragePBRatio(String averagePBRatio) {
		this.averagePBRatio = averagePBRatio;
	}

	public String getForwardPERatio() {
		return forwardPERatio;
	}

	public void setForwardPERatio(String forwardPERatio) {
		this.forwardPERatio = forwardPERatio;
	}

	public String getPegRatio() {
		return pegRatio;
	}

	public void setPegRatio(String pegRatio) {
		this.pegRatio = pegRatio;
	}

	public String getBeta() {
		return beta;
	}

	public void setBeta(String beta) {
		this.beta = beta;
	}

	public String getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
