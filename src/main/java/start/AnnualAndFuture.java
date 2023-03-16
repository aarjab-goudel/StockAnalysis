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
import create.excel.future.CreateFutureDataExcelFile;
import create.excel.implementation.CreateAnnualExcelFile;

public class AnnualAndFuture {

//	private CreateAnnualExcelFile createAnnual;
//	private CreateFutureDataExcelFile createFuture;
	private AnalyzeAnnualExcelFile analyzeAnnual;

	public AnnualAndFuture() throws JSONException, IOException, ParseException {
		System.out.println("STARTING ANNUAL AND FUTURE");
		//createAnnual = new CreateAnnualExcelFile();
		//createFuture = new CreateFutureDataExcelFile();
		analyzeAnnual = new AnalyzeAnnualExcelFile();
		System.out.println("FINISHED ANNUAL AND FUTURE");

	}

	public static void main(String[] args) throws JSONException, IOException, ParseException {
		new AnnualAndFuture();
	}

//	public CreateAnnualExcelFile getCreateAnnual() {
//		return createAnnual;
//	}
//
//	public CreateFutureDataExcelFile getCreateFuture() {
//		return createFuture;
//	}

	public AnalyzeAnnualExcelFile getAnalyzeAnnual() {
		return analyzeAnnual;
	}

}
