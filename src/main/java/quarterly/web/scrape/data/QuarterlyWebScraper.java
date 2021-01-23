/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package quarterly.web.scrape.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javafx.concurrent.Task;

public class QuarterlyWebScraper {
	private List<String> companies;
	private QuarterlyBalanceSheetScraper bsScraper;
	private QuarterlyIncomeSheetScraper isScraper;
	private QuarterlyCashSheetScraper cfScraper;

	public QuarterlyWebScraper(List<String> companies, Task<Void> task)
			throws UnsupportedEncodingException, IOException {
		this.companies = companies;

		bsScraper = new QuarterlyBalanceSheetScraper(companies, task);
		isScraper = new QuarterlyIncomeSheetScraper(companies, task);
		cfScraper = new QuarterlyCashSheetScraper(companies, task);

	}

	public List<String> getCompanies() {
		return companies;
	}

	public QuarterlyBalanceSheetScraper getBsScraper() {
		return bsScraper;
	}

	public QuarterlyIncomeSheetScraper getIsScraper() {
		return isScraper;
	}

	public QuarterlyCashSheetScraper getCfScraper() {
		return cfScraper;
	}

}
