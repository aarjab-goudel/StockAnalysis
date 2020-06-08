package create.subset.excel.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import create.excel.data.service.SaveExcel;
import create.excel.enums.CommonSheetConstants;
import create.subset.excel.CreateSubsetExcelFile;

public class CreateSubsetExcelTest {

	private static CreateSubsetExcelFile createSubsetExcelFile;

	@BeforeClass
	public static void initializeFields()
			throws IOException, JSONException, ParseException, EncryptedDocumentException, InvalidFormatException {
		createSubsetExcelFile = new CreateSubsetExcelFile();
	}

	@Test
	public void testSuccessfullyCopied() {
		File duplicatedFile = new File(createSubsetExcelFile.getDuplicateFilePath());
		File originalFile = new File(SaveExcel.getSavePath());
		assertEquals("The files are not the same in byte size ", originalFile.getTotalSpace(),
				duplicatedFile.getTotalSpace());
	}

	@Test
	public void testBuildUnorderedSubsetStockList() {
		List<String> subsetStockList = createSubsetExcelFile.getUnorderedSubsetStockList();
		System.out.println(
				"---------------- BEGIN MANUAL TEST FOR BUILDING UNORDERED SUBSET STOCK LIST ---------------------");
		for (String subsetStock : subsetStockList) {
			System.out.println(subsetStock);
		}
		System.out.println(
				"----------------------- END MANUAL TEST FOR BUILDING UNORDERED SUBSET STOCK LIST ---------------------");
	}

	@Test
	public void testRemovedStocksToDeleteFromWorkbook() {
		Workbook workbook = createSubsetExcelFile.getDuplicateFile();
		Sheet firstSheet = workbook.getSheetAt(0);
		System.out.println(
				"---------------- BEGIN MANUAL TESTING FOR DELETING STOCKS FROM WORKBOOK --------------------");
		for (Row row : firstSheet) {
			Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
			System.out.println(tickerCell.getStringCellValue());
		}
		System.out.println(
				"----------------- END MANUAL TESTINF FOR DELETING STOCKS FROM WORKBOOK --------------------------");
	}

}
