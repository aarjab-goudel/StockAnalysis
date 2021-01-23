/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package annual.web.scrape.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javafx.concurrent.Task;

public class AnnualWebScraper {

	private List<String> companies;
	private AnnualBalanceSheetScraper bsScraper;
	private AnnualIncomeSheetScraper isScraper;
	private AnnualCashSheetScraper cfScraper;
	private AnnualRatioScraper ratioScraper;

	public AnnualWebScraper(List<String> companies, Task<Void> task) throws UnsupportedEncodingException, IOException {
		this.companies = companies;

		bsScraper = new AnnualBalanceSheetScraper(companies, task);
		isScraper = new AnnualIncomeSheetScraper(companies, task);
		cfScraper = new AnnualCashSheetScraper(companies, task);
		ratioScraper = new AnnualRatioScraper(companies, task);

	}

	public List<String> getCompanies() {
		return companies;
	}

	public AnnualBalanceSheetScraper getBsScraper() {
		return bsScraper;
	}

	public AnnualIncomeSheetScraper getIsScraper() {
		return isScraper;
	}

	public AnnualCashSheetScraper getCfScraper() {
		return cfScraper;
	}

	public AnnualRatioScraper getRatioScraper() {
		return ratioScraper;
	}

}
