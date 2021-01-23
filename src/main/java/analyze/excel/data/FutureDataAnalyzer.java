/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import create.excel.future.FutureInfoBO;

public class FutureDataAnalyzer {

	private Map<String, FutureInfoBO> tickerToCurrentYear;
	private Map<String, FutureInfoBO> tickerToNextYear;
	private Map<String, String> tickerToTxtFilePaths;

	public FutureDataAnalyzer(Map<String, FutureInfoBO> tickerToCurrentYearData,
			Map<String, FutureInfoBO> tickerToNextYearData, Map<String, String> tickerToTxtFilePaths)
			throws IOException {
		this.tickerToCurrentYear = tickerToCurrentYearData;
		this.tickerToNextYear = tickerToNextYearData;
		this.tickerToTxtFilePaths = tickerToTxtFilePaths;
		this.writeFutureExcelDataToTxtFiles();
	}

	public void writeFutureExcelDataToTxtFiles() throws IOException {
		Iterator<Entry<String, String>> iterator = tickerToTxtFilePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String txtFilePath = entry.getValue();
			String ticker = entry.getKey();
			FileWriter fstream = new FileWriter(txtFilePath, true);
			BufferedWriter info = new BufferedWriter(fstream);

			this.writeFutureDataSeperator(ticker, info, "EPS");
			FutureInfoBO currentYearInfoBO = tickerToCurrentYear.get(ticker);
			FutureInfoBO nextYearInfoBO = tickerToNextYear.get(ticker);

			String currentEpsLine = "For " + currentYearInfoBO.getDate() + ": " + "The EPS is expected to be "
					+ currentYearInfoBO.getEps();
			info.write(currentEpsLine);
			info.newLine();

			String nextYearEPSLine = "For " + nextYearInfoBO.getDate() + ": " + "The EPS is expected to be "
					+ nextYearInfoBO.getEps();
			info.write(nextYearEPSLine);
			info.newLine();

			String epsGrowthLine = "The EPS Growth from " + currentYearInfoBO.getDate() + " to "
					+ nextYearInfoBO.getDate() + " is " + currentYearInfoBO.getEpsGrowth();
			info.write(epsGrowthLine);
			info.newLine();

			this.writeFutureDataSeperator(ticker, info, "Revenue");

			String currentRevenueLine = "For " + currentYearInfoBO.getDate() + ": " + "The Revenue is expected to be "
					+ currentYearInfoBO.getRevenue();
			info.write(currentRevenueLine);
			info.newLine();

			String nextYearRevenueLine = "For " + nextYearInfoBO.getDate() + ": " + "The Revenue is expected to be "
					+ nextYearInfoBO.getRevenue();
			info.write(nextYearRevenueLine);
			info.newLine();

			String revenueGrowthLine = "The Revenue Growth from " + currentYearInfoBO.getDate() + " to "
					+ nextYearInfoBO.getDate() + " is " + currentYearInfoBO.getRevenueGrowth();
			info.write(revenueGrowthLine);
			info.newLine();

			info.close();

		}
	}

	private void writeFutureDataSeperator(String ticker, BufferedWriter info, String dataType) throws IOException {
		info.write("==================================================== " + ticker + " - Future Data Analysis For "
				+ dataType + " ====================================================");
		info.newLine();
	}

}
