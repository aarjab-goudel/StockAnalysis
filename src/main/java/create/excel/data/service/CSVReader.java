package create.excel.data.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	// Contains the path to Stocks.txt file
	private static final String CSV_FILE = new java.io.File("Stocks.txt").getAbsolutePath();
	// Contains the path to SubsetStocks.txt file
	private static final String SUBSET_STOCKS_CSV_FILE = new java.io.File("SubsetStocks.txt").getAbsolutePath();
	private List<String> unOrderedStockList;
	private List<String> unOrderedSubsetStocksList;

	public CSVReader() {
		unOrderedStockList = new ArrayList<String>();
		unOrderedSubsetStocksList = new ArrayList<String>();
		this.buildUnorderedStockList(CSV_FILE, false);
		this.buildUnorderedStockList(SUBSET_STOCKS_CSV_FILE, true);
	}

	public void buildUnorderedStockList(String filePathToStockList, boolean isSubsetStock) {
		System.out.println(filePathToStockList);
		BufferedReader bufferReader = null;
		try {
			String line = "";
			String csvSplitBy = ",";
			bufferReader = new BufferedReader(new FileReader(filePathToStockList));
			while ((line = bufferReader.readLine()) != null) {
				String[] stocks = line.split(csvSplitBy);
				for (int i = 0; i < stocks.length; i++) {
					if (isSubsetStock) {
						unOrderedSubsetStocksList.add(stocks[i]);
					} else {
						unOrderedStockList.add(stocks[i]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public List<String> getStockList() {
		return unOrderedStockList;
	}

	public List<String> getUnOrderedSubsetStocksList() {
		return unOrderedSubsetStocksList;
	}

}
