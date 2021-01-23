/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.future;

public class FutureInfoBO {

	private String ticker;
	private String date;
	private String eps;
	private String revenue;
	private String epsGrowth;
	private String revenueGrowth;

	public FutureInfoBO() {

	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getEpsGrowth() {
		return epsGrowth;
	}

	public void setEpsGrowth(String epsGrowth) {
		this.epsGrowth = epsGrowth;
	}

	public String getRevenueGrowth() {
		return revenueGrowth;
	}

	public void setRevenueGrowth(String revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}

}
