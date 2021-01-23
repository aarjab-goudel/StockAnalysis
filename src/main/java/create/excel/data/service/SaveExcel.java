/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.data.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SaveExcel {

	private static Workbook annualExcelFile = null;
	private static Workbook quarterlyExcelFile = null;
	private static Workbook futureDataExcelFile = null;
	private static final String ROOT_PATH = new File("").getAbsolutePath();
	private static final String ANNUAL_FILE_PATH = getFilePath("AnnualStockData.xlsx");
	private static final String QUARTERLY_FILE_PATH = getFilePath("QuarterlyStockData.xlsx");
	private static final String STOCK_SHEET_FILE_PATH = getFilePath("StockData.xlsx");
	private static final String FUTURE_FILE_PATH = getFilePath("FutureStockData.xlsx");
	private static FileInputStream annualExcelFileInputStream;
	private static FileInputStream quarterlyExcelFileInputStream;
	private static FileInputStream futureExcelFileInputStream;

	private SaveExcel() {

	}

	public static String getInProgressImageURL() throws MalformedURLException {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1) {
			String inProgressFilePath = ROOT_PATH + "\\images\\progress.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else if (os.indexOf("mac") != -1) {
			String inProgressFilePath = ROOT_PATH + "/images/progress.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else if (os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1) {
			String inProgressFilePath = ROOT_PATH + "/images/progress.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else {
			String inProgressFilePath = ROOT_PATH + "\\images\\progress.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		}
	}

	public static String getDoneImageURL() throws MalformedURLException {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1) {
			String inProgressFilePath = ROOT_PATH + "\\images\\done.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else if (os.indexOf("mac") != -1) {
			String inProgressFilePath = ROOT_PATH + "/images/done.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else if (os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1) {
			String inProgressFilePath = ROOT_PATH + "/images/done.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		} else {
			String inProgressFilePath = ROOT_PATH + "\\images\\done.png";
			File file = new File(inProgressFilePath);
			return file.toURI().toURL().toString();
		}
	}

	public static String addToPath(String originalFilePath, String toAdd) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1)
			return originalFilePath + "\\" + toAdd;
		else if (os.indexOf("mac") != -1)
			return originalFilePath + "/" + toAdd;
		else if (os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1)
			return originalFilePath + "/" + toAdd;
		else
			return originalFilePath + "\\" + toAdd;
	}

	// Check OS
	public static String getFilePath(String fileName) {

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1)
			return getRootPath() + "\\" + fileName;
		else if (os.indexOf("mac") != -1)
			return getRootPath() + "/" + fileName;
		else if (os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1)
			return getRootPath() + "/" + fileName;
		else
			return getRootPath() + "//" + fileName;
	}

	public static String getLogPath() {

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1)
			return getRootPath() + "\\logs\\";
		else if (os.indexOf("mac") != -1)
			return getRootPath() + "/logs/";
		else if (os.indexOf("nix") != -1 || os.indexOf("nux") != -1 || os.indexOf("aix") != -1)
			return getRootPath() + "/logs/";
		else
			return getRootPath() + "//logs//";
	}

	public static Workbook getFutureDataExcelInstance() {

		if (futureDataExcelFile == null) {
			File futureFile = new File(FUTURE_FILE_PATH);
			if (futureFile.exists()) {
				try {
					futureExcelFileInputStream = new FileInputStream(futureFile);
					futureDataExcelFile = WorkbookFactory.create(futureExcelFileInputStream);
				} catch (IOException | EncryptedDocumentException ex) {
					ex.printStackTrace();
				}
			}
		}
		return futureDataExcelFile;
	}

	public static Workbook getAnnualExcelInstance() {
		if (annualExcelFile == null) {
			try {
				annualExcelFileInputStream = new FileInputStream(new File(ANNUAL_FILE_PATH));
				annualExcelFile = WorkbookFactory.create(annualExcelFileInputStream);
			} catch (IOException | EncryptedDocumentException ex) {
				annualExcelFile = null;
				ex.printStackTrace();

				return annualExcelFile;

			}
		}

		return annualExcelFile;

	}

	public static Workbook getQuarterlyExcelInstance() {
		if (quarterlyExcelFile == null) {
			try {
				quarterlyExcelFileInputStream = new FileInputStream(new File(QUARTERLY_FILE_PATH));
				quarterlyExcelFile = WorkbookFactory.create(quarterlyExcelFileInputStream);
			} catch (IOException | EncryptedDocumentException ex) {
				ex.printStackTrace();
			}
		}
		return quarterlyExcelFile;
	}

	public static void closeAndSaveAnnualFile() throws IOException {
		if (annualExcelFileInputStream != null) {
			annualExcelFileInputStream.close();
			FileOutputStream fileSaver = new FileOutputStream(ANNUAL_FILE_PATH);
			annualExcelFile.write(fileSaver);
			annualExcelFile.close();
			fileSaver.close();
			annualExcelFile = null;
			annualExcelFileInputStream = null;

		}
	}

	public static void closeAndSaveQuarterlyFile() throws IOException {
		if (quarterlyExcelFileInputStream != null) {
			quarterlyExcelFileInputStream.close();
			FileOutputStream fileSaver = new FileOutputStream(QUARTERLY_FILE_PATH);
			quarterlyExcelFile.write(fileSaver);
			quarterlyExcelFile.close();
			fileSaver.close();
			quarterlyExcelFile = null;
			quarterlyExcelFileInputStream = null;
		}
	}

	public static void closeAndSaveFutureExcelFile() throws IOException {
		if (futureExcelFileInputStream != null) {
			futureExcelFileInputStream.close();
			FileOutputStream fileSaver = new FileOutputStream(FUTURE_FILE_PATH);
			futureDataExcelFile.write(fileSaver);
			futureDataExcelFile.close();
			fileSaver.close();
			futureDataExcelFile = null;
			futureExcelFileInputStream = null;
		}
	}

	public static String getRootPath() {
		return ROOT_PATH;
	}

	public static String getSavePath() {
		return ANNUAL_FILE_PATH;
	}

	public static String getAnnualFilePath() {
		return ANNUAL_FILE_PATH;
	}

	public static String getStockSheetFilePath() {
		return STOCK_SHEET_FILE_PATH;
	}

	public static String getQuarterlyFilePath() {
		return QUARTERLY_FILE_PATH;
	}

	public static String getFutureFilePath() {
		return FUTURE_FILE_PATH;
	}

}
