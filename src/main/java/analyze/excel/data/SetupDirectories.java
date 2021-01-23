/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import create.excel.data.service.SaveExcel;

public class SetupDirectories {

	private static final String ROOT_PATH = SaveExcel.getRootPath();
	private List<String> tickers;
	private Map<String, String> tickerToTxtFilePath;
	private boolean isAnnual;

	public SetupDirectories(List<String> companies, boolean isAnnual) throws IOException {
		this.tickers = companies;
		this.isAnnual = isAnnual;
		tickerToTxtFilePath = new HashMap<String, String>();
		this.setUpDirectories();
	}

	private String getEpochTime() {
		Date currentDate = new Date();
		return Long.toString(currentDate.getTime());
	}

	private void setUpDirectories() throws IOException {
		String directoryName = "";
		if (isAnnual) {
			directoryName = "Annual - ";
		} else {
			directoryName = "Quarterly - ";
		}
		directoryName = directoryName + this.getEpochTime();
		String path = SaveExcel.addToPath(ROOT_PATH, directoryName);
		File file = new File(path);
		boolean success = file.mkdir();
		if (!success) {
			System.out.println("Directory could not be made!");
		}
		this.createTickerDirectories(path);

	}

	private void createTickerDirectories(String rootFolderPath) throws IOException {
		for (String company : this.getTickers()) {
			String companyPath = SaveExcel.addToPath(rootFolderPath, company);
			File tickerPath = new File(companyPath);
			boolean success = tickerPath.mkdir();
			if (!success) {
				System.out.println("Directory: " + company + " could not be made!");
			} else {
				this.createTxtFilesForEachCompany(company, companyPath);
			}
		}
	}

	private void createTxtFilesForEachCompany(String ticker, String companyFilePath) throws IOException {
		String txtFileName = ticker + ".txt";
		String txtFilePath = SaveExcel.addToPath(companyFilePath, txtFileName);
		File pathToTxtFile = new File(txtFilePath);
		if (pathToTxtFile.createNewFile()) {
			System.out.println("File created: " + pathToTxtFile.getName());
			tickerToTxtFilePath.put(ticker, txtFilePath);
		} else {
			System.out.println("File already exists.");
		}
	}

	public List<String> getTickers() {
		return tickers;
	}

	public Map<String, String> getTickerToTxtFilePath() {
		return tickerToTxtFilePath;
	}

}
