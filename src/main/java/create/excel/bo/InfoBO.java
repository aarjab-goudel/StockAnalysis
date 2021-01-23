/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.bo;

import create.excel.data.service.DataService;

public class InfoBO {

	private int year;
	private String exchange;
	private String ticker;

	private DataService dataService;

	public InfoBO(int year, String ticker) {
		this.year = year;
		this.ticker = ticker;
	}

	public int getYear() {
		return year;
	}

	public String getExchange() {
		return exchange;
	}

	public String getTicker() {
		return ticker;
	}

	public DataService getDataService() {
		return dataService;
	}

}
