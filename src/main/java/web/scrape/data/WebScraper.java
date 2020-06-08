package web.scrape.data;

import java.util.List;

public class WebScraper {

	private List<String> companies;
	private BalanceSheetScraper bsScraper;
	private IncomeSheetScraper isScraper;
	private CashSheetScraper cfScraper;
	private RatioScraper ratioScraper;

	public WebScraper(List<String> companies) {
		this.companies = companies;

		bsScraper = new BalanceSheetScraper(companies);
		isScraper = new IncomeSheetScraper(companies);
		cfScraper = new CashSheetScraper(companies);
		ratioScraper = new RatioScraper(companies);

	}

	public List<String> getCompanies() {
		return companies;
	}

	public BalanceSheetScraper getBsScraper() {
		return bsScraper;
	}

	public IncomeSheetScraper getIsScraper() {
		return isScraper;
	}

	public CashSheetScraper getCfScraper() {
		return cfScraper;
	}

	public RatioScraper getRatioScraper() {
		return ratioScraper;
	}


}
