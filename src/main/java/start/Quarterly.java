/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package start;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import analyze.excel.data.AnalyzeQuarterlyExcelFile;
import create.excel.implementation.CreateQuarterlyExcelFile;

public class Quarterly {

	private CreateQuarterlyExcelFile createQuarterly;
	private AnalyzeQuarterlyExcelFile analyzeQuarterly;

	public Quarterly() throws JSONException, IOException, ParseException {
		System.out.println("STARTING QUARTERLY");
		createQuarterly = new CreateQuarterlyExcelFile();
		analyzeQuarterly = new AnalyzeQuarterlyExcelFile();
		System.out.println("FINISHED QUARTERLY");
	}

	public static void main(String[] args) throws JSONException, IOException, ParseException {
		new Quarterly();
	}

	public CreateQuarterlyExcelFile getCreateQuarterly() {
		return createQuarterly;
	}

	public AnalyzeQuarterlyExcelFile getAnalyzeQuarterly() {
		return analyzeQuarterly;
	}

}
