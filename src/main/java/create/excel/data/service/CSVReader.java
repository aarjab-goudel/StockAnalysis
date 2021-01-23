/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.data.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVReader {
	private static final String CSV_FILE = new java.io.File("Stocks.txt").getAbsolutePath();
	private List<String> unOrderedStockList;

	public CSVReader(List<String> unOrderedStockListFromTextField) {
		unOrderedStockList = new ArrayList<String>();
		if (unOrderedStockListFromTextField == null) {
			this.buildUnorderedStockList(CSV_FILE);
		} else {
			unOrderedStockList = unOrderedStockListFromTextField;
		}
		this.capitalizeAllTickers();
	}

	public void capitalizeAllTickers() {
		List<String> capitalizeUnorderedStockList = new ArrayList<String>();
		for (String ticker : unOrderedStockList) {
			String capitalTicker = ticker.toUpperCase();
			capitalizeUnorderedStockList.add(capitalTicker);
		}
		unOrderedStockList = capitalizeUnorderedStockList;
	}

	public void buildUnorderedStockList(String filePathToStockList) {
		System.out.println(filePathToStockList);
		Set<String> stockSet = new HashSet<String>();
		BufferedReader bufferReader = null;
		try {
			String line = "";
			String csvSplitBy = ",";
			bufferReader = new BufferedReader(new FileReader(filePathToStockList));
			while ((line = bufferReader.readLine()) != null) {
				String[] stocks = line.split(csvSplitBy);
				for (int i = 0; i < stocks.length; i++) {
					if (stockSet.add(stocks[i])) {
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

}
