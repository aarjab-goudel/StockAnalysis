/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package analyze.excel.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import create.excel.data.service.SaveExcel;
import javafx.concurrent.Task;

public class AnalyzeQuarterlyExcelFile extends AnalyzeExcelFile {

	private SetupDirectories setUp;
	private Task<Void> analyzeQuarterlyTask;
	private PropertyChangeSupport support;
	private boolean isEverythingFinished;
	private static final String PROPERTY_CHANGE_NAME = "isEverythingDoneForQuarterlyAnalyze";

	public AnalyzeQuarterlyExcelFile(Task<Void> task) throws IOException {
		super(SaveExcel.getQuarterlyExcelInstance());
		if (!task.isCancelled()) {
			this.analyzeQuarterlyTask = task;
			support = new PropertyChangeSupport(this);
			isEverythingFinished = false;
			setUp = new SetupDirectories(this.getCompanies(), false);
			BSAnalyzer bsAnalyzer = new BSAnalyzer(this.getTickerToBSInfo(), setUp.getTickerToTxtFilePath());
			ISAnalyzer isAnalyzer = new ISAnalyzer(this.getTickerToISInfo(), setUp.getTickerToTxtFilePath());
			CFAnalyzer cfAnalyzer = new CFAnalyzer(this.getTickerToCFInfo(), setUp.getTickerToTxtFilePath());
			SaveExcel.closeAndSaveQuarterlyFile();
		}

	}

	public AnalyzeQuarterlyExcelFile() throws IOException {
		super(SaveExcel.getQuarterlyExcelInstance());
		setUp = new SetupDirectories(this.getCompanies(), false);
		BSAnalyzer bsAnalyzer = new BSAnalyzer(this.getTickerToBSInfo(), setUp.getTickerToTxtFilePath());
		ISAnalyzer isAnalyzer = new ISAnalyzer(this.getTickerToISInfo(), setUp.getTickerToTxtFilePath());
		CFAnalyzer cfAnalyzer = new CFAnalyzer(this.getTickerToCFInfo(), setUp.getTickerToTxtFilePath());
		SaveExcel.closeAndSaveQuarterlyFile();
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

	public SetupDirectories getSetUp() {
		return setUp;
	}

	public Task<Void> getAnalyzeQuarterlyTask() {
		return analyzeQuarterlyTask;
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

}
