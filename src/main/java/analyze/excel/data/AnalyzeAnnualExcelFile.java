/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */

package analyze.excel.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import create.excel.bo.RatioInfoBO;
import create.excel.data.service.SaveExcel;
import excel.library.RatioFinancialLibrary;
import javafx.concurrent.Task;

public class AnalyzeAnnualExcelFile extends AnalyzeExcelFile {

	private Map<String, List<RatioInfoBO>> tickerToRatioInfo;
	private SetupDirectories setUp;
	private Task<Void> analyzeAnnualTask;
	private PropertyChangeSupport support;
	private boolean isEverythingFinished;
	private static final String PROPERTY_CHANGE_NAME = "isEverythingDoneForAnnualAnalyze";

	private AnalyzeFutureDataExcelFile analyzeFutureData;

	public AnalyzeAnnualExcelFile(Task<Void> task) throws IOException {
		super(SaveExcel.getAnnualExcelInstance());
		if (!task.isCancelled()) {
			this.analyzeAnnualTask = task;
			support = new PropertyChangeSupport(this);
			isEverythingFinished = false;
			if (this.getExcelFile() != null) {
				tickerToRatioInfo = this.extractRatioData();
				setUp = new SetupDirectories(this.getCompanies(), true);

				BSAnalyzer bsAnalyzer = new BSAnalyzer(this.getTickerToBSInfo(), setUp.getTickerToTxtFilePath());
				ISAnalyzer isAnalyzer = new ISAnalyzer(this.getTickerToISInfo(), setUp.getTickerToTxtFilePath());
				CFAnalyzer cfAnalyzer = new CFAnalyzer(this.getTickerToCFInfo(), setUp.getTickerToTxtFilePath());
				RatioAnalyzer ratioAnalyzer = new RatioAnalyzer(tickerToRatioInfo, setUp.getTickerToTxtFilePath());
				SaveExcel.closeAndSaveAnnualFile();

//				analyzeFutureData = new AnalyzeFutureDataExcelFile();
//				FutureDataAnalyzer futureDataAnalyzer = new FutureDataAnalyzer(
//						analyzeFutureData.getTickerToCurrentYearInfo(), analyzeFutureData.getTickerToNextYearInfo(),
//						setUp.getTickerToTxtFilePath());

			}

		}

	}

	public AnalyzeAnnualExcelFile() throws IOException {
		super(SaveExcel.getAnnualExcelInstance());
		//ickerToRatioInfo = this.extractRatioData();
		setUp = new SetupDirectories(this.getCompanies(), true);
		AnalyzeFutureDataExcelFile analyzeFutureExcelData = new AnalyzeFutureDataExcelFile();

		BSAnalyzer bsAnalyzer = new BSAnalyzer(this.getTickerToBSInfo(), setUp.getTickerToTxtFilePath());
		ISAnalyzer isAnalyzer = new ISAnalyzer(this.getTickerToISInfo(), setUp.getTickerToTxtFilePath());
		CFAnalyzer cfAnalyzer = new CFAnalyzer(this.getTickerToCFInfo(), setUp.getTickerToTxtFilePath());
		//RatioAnalyzer ratioAnalyzer = new RatioAnalyzer(tickerToRatioInfo, setUp.getTickerToTxtFilePath());
		FutureDataAnalyzer futureDataAnalyzer = new FutureDataAnalyzer( analyzeFutureExcelData.getTickerToCurrencyType(),
				analyzeFutureExcelData.getTickerToCurrentYearInfo(), analyzeFutureExcelData.getTickerToNextYearInfo(),
				setUp.getTickerToTxtFilePath());

		SaveExcel.closeAndSaveAnnualFile();
	}

	public Map<String, List<RatioInfoBO>> extractRatioData() {
		Sheet firstRatioSheet = this.getExcelFile().getSheet("Ratios");
		Sheet secondRatioSheet = this.getExcelFile().getSheet("RatiosOne");
		Sheet thirdRatioSheet = this.getExcelFile().getSheet("RatiosTwo");
		Sheet fourthRatioSheet = this.getExcelFile().getSheet("RatiosThree");
		Sheet fifthRatioSheet = this.getExcelFile().getSheet("RatiosFour");

		List<Sheet> ratiosSheet = new ArrayList<Sheet>();

		ratiosSheet.add(firstRatioSheet);
		ratiosSheet.add(secondRatioSheet);
		ratiosSheet.add(thirdRatioSheet);
		ratiosSheet.add(fourthRatioSheet);
		ratiosSheet.add(fifthRatioSheet);

		Map<String, List<RatioInfoBO>> tickerToRatioInfo = RatioFinancialLibrary.readRatioData(ratiosSheet);

		return tickerToRatioInfo;

	}

	public void setIsEverythingDone(boolean isEverythingDone) {
		support.firePropertyChange(PROPERTY_CHANGE_NAME, this.isEverythingFinished, isEverythingDone);
		this.isEverythingFinished = isEverythingDone;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	public Map<String, List<RatioInfoBO>> getTickerToRatioInfo() {
		return tickerToRatioInfo;
	}

	public SetupDirectories getSetUp() {
		return setUp;
	}

	public Task<Void> getAnalyzeAnnualTask() {
		return analyzeAnnualTask;
	}

	public PropertyChangeSupport getSupport() {
		return support;
	}

	public boolean isEverythingFinished() {
		return isEverythingFinished;
	}

	public static String getPropertyChangeName() {
		return PROPERTY_CHANGE_NAME;
	}

	public AnalyzeFutureDataExcelFile getAnalyzeFutureData() {
		return analyzeFutureData;
	}

}
