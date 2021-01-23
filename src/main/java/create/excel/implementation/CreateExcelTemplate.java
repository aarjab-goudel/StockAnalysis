/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.concurrent.Task;

public class CreateExcelTemplate {
	private Task<Void> task;
	private boolean isEverythingFinished;
	private boolean isBSDataFinished;
	private boolean isISDataFinished;
	private boolean isCFDataFinished;
	private boolean isRatioDataFinished;
	private boolean isStockDataFinished;

	private static final String BS_PROPERTY_CHANGE_NAME = "isBalanceSheetDone";
	private static final String IS_PROPERTY_CHANGE_NAME = "isIncomeSheetDone";
	private static final String CF_PROPERTY_CHANGE_NAME = "isCashSheetDone";
	private static final String STOCK_SHEET_PROPERTY_CHANGE_NAME = "isStockSheetDone";

	private PropertyChangeSupport support;

	public CreateExcelTemplate(Task<Void> task) {
		this.task = task;
		this.isEverythingFinished = false;
		this.isBSDataFinished = false;
		this.isISDataFinished = false;
		this.isCFDataFinished = false;
		this.isRatioDataFinished = false;
		this.isStockDataFinished = false;
		support = new PropertyChangeSupport(this);
	}

	public CreateExcelTemplate() {

	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	public Task<Void> getTask() {
		return task;
	}

	public boolean isEverythingFinished() {
		return isEverythingFinished;
	}

	public void setIsEverythingDone(boolean isEverythingFinished) {
		this.isEverythingFinished = isEverythingFinished;
	}

	public boolean isBSDataFinished() {
		return isBSDataFinished;
	}

	public void setBSDataFinished(boolean isBSDataFinished) {
		this.isBSDataFinished = isBSDataFinished;
	}

	public boolean isISDataFinished() {
		return isISDataFinished;
	}

	public void setISDataFinished(boolean isISDataFinished) {
		this.isISDataFinished = isISDataFinished;
	}

	public boolean isCFDataFinished() {
		return isCFDataFinished;
	}

	public void setCFDataFinished(boolean isCFDataFinished) {
		this.isCFDataFinished = isCFDataFinished;
	}

	public boolean isRatioDataFinished() {
		return isRatioDataFinished;
	}

	public void setRatioDataFinished(boolean isRatioDataFinished) {
		this.isRatioDataFinished = isRatioDataFinished;
	}

	public boolean isStockDataFinished() {
		return isStockDataFinished;
	}

	public void setStockDataFinished(boolean isStockDataFinished) {
		this.isStockDataFinished = isStockDataFinished;
	}

	public void setEverythingFinished(boolean isEverythingFinished) {
		this.isEverythingFinished = isEverythingFinished;
	}

	public PropertyChangeSupport getSupport() {
		return support;
	}

	public static String getBsPropertyChangeName() {
		return BS_PROPERTY_CHANGE_NAME;
	}

	public static String getIsPropertyChangeName() {
		return IS_PROPERTY_CHANGE_NAME;
	}

	public static String getCfPropertyChangeName() {
		return CF_PROPERTY_CHANGE_NAME;
	}

	public static String getStockSheetPropertyChangeName() {
		return STOCK_SHEET_PROPERTY_CHANGE_NAME;
	}

}
