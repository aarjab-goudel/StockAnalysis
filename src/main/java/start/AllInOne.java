/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package start;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import analyze.excel.data.AnalyzeAnnualExcelFile;
import analyze.excel.data.AnalyzeQuarterlyExcelFile;
import create.excel.future.CreateFutureDataExcelFile;
import create.excel.implementation.CreateAnnualExcelFile;
import create.excel.implementation.CreateQuarterlyExcelFile;

public class AllInOne {

	private CreateAnnualExcelFile createAnnual;
	private CreateFutureDataExcelFile createFuture;
	private CreateQuarterlyExcelFile createQuarterly;
	private AnalyzeAnnualExcelFile analyzeAnnual;
	private AnalyzeQuarterlyExcelFile analyzeQuarterly;

	public AllInOne() throws JSONException, IOException, ParseException {
		System.out.println("STARTING ALL IN ONE");
		createAnnual = new CreateAnnualExcelFile();
		createFuture = new CreateFutureDataExcelFile();
		createQuarterly = new CreateQuarterlyExcelFile();
		analyzeAnnual = new AnalyzeAnnualExcelFile();
		analyzeQuarterly = new AnalyzeQuarterlyExcelFile();
		System.out.println("FINISHED ALL IN ONE");

	}

	public static void main(String[] args) throws JSONException, IOException, ParseException {
		new AllInOne();
	}

	public CreateAnnualExcelFile getCreateAnnual() {
		return createAnnual;
	}

	public CreateFutureDataExcelFile getCreateFuture() {
		return createFuture;
	}

	public CreateQuarterlyExcelFile getCreateQuarterly() {
		return createQuarterly;
	}

	public AnalyzeAnnualExcelFile getAnalyzeAnnual() {
		return analyzeAnnual;
	}

	public AnalyzeQuarterlyExcelFile getAnalyzeQuarterly() {
		return analyzeQuarterly;
	}

}
