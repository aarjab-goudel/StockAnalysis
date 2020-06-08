package create.subset.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import create.excel.data.service.CSVReader;
import create.excel.data.service.SaveExcel;
import create.excel.enums.CommonSheetConstants;
import excel.library.CommonFinancialLibrary;

public class CreateSubsetExcelFile {
	private Workbook duplicateFile;

	private String duplicateFilePath;
	private List<String> unorderedStockList;
	private List<String> unorderedSubsetStockList;

	private static final int FIRST_SHEET_INDEX = 0;

	public CreateSubsetExcelFile() throws EncryptedDocumentException, InvalidFormatException, IOException {
		duplicateFilePath = System.getProperty("user.home") + "\\Desktop\\" + System.currentTimeMillis() + ".xlsx";
		this.createDuplicateFile();
		FileInputStream inputStream = new FileInputStream(new File(duplicateFilePath));
		duplicateFile = WorkbookFactory.create(inputStream);
		CSVReader csvReader = new CSVReader();
		unorderedSubsetStockList = csvReader.getUnOrderedSubsetStocksList();
		unorderedStockList = this.buildCurrentTickerList();

		this.removeStockToDeleteFromWorkBook(duplicateFile);

		inputStream.close();
		FileOutputStream fileSaver = new FileOutputStream(duplicateFilePath);
		duplicateFile.write(fileSaver);
		duplicateFile.close();
		fileSaver.close();
	}

	public void removeStockToDeleteFromWorkBook(Workbook workbook) {
		if (isValidSubset()) {
			List<String> stocksToDelete = this.buildStocksToDelete();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				for (String stockToDelete : stocksToDelete) {
					CommonFinancialLibrary.removeTickerBySheet(sheet, stockToDelete);
				}
			}
		}
	}

	public List<String> buildStocksToDelete() {
		List<String> stocksToDelete = new ArrayList<String>();
		for (String stock : unorderedStockList) {
			if (!unorderedSubsetStockList.contains(stock)) {
				stocksToDelete.add(stock);
			}
		}
		return stocksToDelete;
	}

	public boolean isValidSubset() {
		boolean isValid = true;
		for (String subsetStock : unorderedSubsetStockList) {
			if (!unorderedStockList.contains(subsetStock)) {
				isValid = false;
				System.out.println(subsetStock + " is not a subset!");
			}
		}
		return isValid;
	}

	public List<String> buildCurrentTickerList() {
		unorderedStockList = new ArrayList<String>();
		Iterator<Row> rowIterator = duplicateFile.getSheetAt(FIRST_SHEET_INDEX).iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			unorderedStockList.add(tickerCell.getStringCellValue());
		}
		return unorderedStockList;
	}

	public void createDuplicateFile() {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File originalFile = new File(SaveExcel.getSavePath());
			File duplicateFile = new File(duplicateFilePath);

			inStream = new FileInputStream(originalFile);
			outStream = new FileOutputStream(duplicateFile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Workbook getDuplicateFile() {
		return duplicateFile;
	}

	public String getDuplicateFilePath() {
		return duplicateFilePath;
	}

	public List<String> getUnorderedStockList() {
		return unorderedStockList;
	}

	public List<String> getUnorderedSubsetStockList() {
		return unorderedSubsetStockList;
	}

}
