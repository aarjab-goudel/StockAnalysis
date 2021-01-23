/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.util.List;
import java.util.Map;

import create.excel.bo.StockDataInfoBO;

public class StockAnalyzer {

	private Map<String, List<StockDataInfoBO>> tickerToStocksData;
	private Map<String, String> tickerToTxtFilePaths;

	public StockAnalyzer(Map<String, List<StockDataInfoBO>> tickerToStockData,
			Map<String, String> tickerToTxtFilePaths) {
		this.tickerToStocksData = tickerToStockData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
	}

}
