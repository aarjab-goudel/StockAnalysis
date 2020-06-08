package create.excel.data.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLBuilder {
	private List<String> companies;
	private static final String COMPANY_PROFILE = "https://financialmodelingprep.com/api/v3/company/profile/";
	private static final String COMPANY_RATIO = "https://financialmodelingprep.com/api/v3/financial-ratios/";
	private static final String COMPANY_KEY_METRICS = "https://financialmodelingprep.com/api/v3/company-key-metrics/";

	private Map<String, String> companyProfileURLMap;
	private Map<String, String> companyRatioURLMap;
	private Map<String, String> companyKeyMetricsMap;

	public URLBuilder(List<String> companies) {
		companyProfileURLMap = new HashMap<String, String>();
		companyRatioURLMap = new HashMap<String, String>();
		companyKeyMetricsMap = new HashMap<String, String>();
		this.companies = companies;
		this.buildAllUrlsMap();
	}

	private void buildAllUrlsMap() {
		for (int i = 0; i < companies.size(); i++) {
			String ticker = companies.get(i);

			String companyMetricsUrl = COMPANY_KEY_METRICS + ticker;
			companyKeyMetricsMap.put(ticker, companyMetricsUrl);

			String companyProfileUrl = COMPANY_PROFILE + ticker;
			companyProfileURLMap.put(ticker, companyProfileUrl);

			String companyRatioUrl = COMPANY_RATIO + ticker;
			companyRatioURLMap.put(ticker, companyRatioUrl);

		}
	}

	public Map<String, String> getCompanyRatioURLMap() {
		return companyRatioURLMap;
	}

	public static String getCompanyProfile() {
		return COMPANY_PROFILE;
	}

	public Map<String, String> getCompanyProfileURLMap() {
		return companyProfileURLMap;
	}

	public static String getCompanyRatio() {
		return COMPANY_RATIO;
	}

	public static String getCompanyKeyMetrics() {
		return COMPANY_KEY_METRICS;
	}

	public Map<String, String> getCompanyKeyMetricsMap() {
		return companyKeyMetricsMap;
	}

	public List<String> getCompanies() {
		return companies;
	}

}
