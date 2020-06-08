package create.excel.data.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SaveExcel {

	private static Workbook excelFile = null;
	private static final String DESKTOP_FILE_PATH = System.getProperty("user.home") + "\\Desktop\\";
	private static final String FILE_PATH = DESKTOP_FILE_PATH + "StockData2.xlsx";
	private static FileInputStream inputStream;

	private SaveExcel() {

	}

	public static Workbook getInstance() {
		if (excelFile == null) {
			try {
				inputStream = new FileInputStream(new File(FILE_PATH));
				excelFile = WorkbookFactory.create(inputStream);
			} catch (IOException | EncryptedDocumentException ex) {
				ex.printStackTrace();
			}
		}
		return excelFile;
	}

	public static void closeAndSaveFile() throws IOException {
		if (inputStream != null) {
			inputStream.close();
			FileOutputStream fileSaver = new FileOutputStream(FILE_PATH);
			excelFile.write(fileSaver);
			excelFile.close();
			fileSaver.close();
		}
	}

	public static String getSavePath() {
		return FILE_PATH;
	}

}
