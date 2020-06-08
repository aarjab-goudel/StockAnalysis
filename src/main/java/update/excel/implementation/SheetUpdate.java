package update.excel.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import create.excel.bo.PairBO;
import create.excel.data.service.DataService;
import create.excel.data.service.SaveExcel;
import create.excel.enums.CommonSheetConstants;
import excel.library.CommonFinancialLibrary;

public class SheetUpdate {

	private Sheet sheet;
	private List<String> tickersToRemove;
	private List<String> tickersToAdd;
	private Map<String, String> tickerToSector;
	private Map<String, String> tickerToIndustry;
	private Map<String, Row> addedTickersToRow;
	private Integer year;
	private DataService dataService;

	public SheetUpdate(Integer year, Sheet sheet, List<String> tickersToRemove, List<String> tickersToAdd,
			DataService dataService) {
		this.sheet = sheet;
		this.tickersToRemove = tickersToRemove;
		this.tickersToAdd = tickersToAdd;
		this.dataService = dataService;
		this.updateRemovedTickers();
		this.tickerToSector = this.dataService.getTickerToSector();
		this.tickerToIndustry = this.dataService.getTickerToIndustry();
		this.year = year;
		addedTickersToRow = new HashMap<String, Row>();
	}

	public void styleAddedRow(Row rowToStyle) {
		Workbook excelFile = SaveExcel.getInstance();
		CellStyle cellStyle = excelFile.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		for (Cell cell : rowToStyle) {
			cell.setCellStyle(cellStyle);
		}
	}

	public PairBO<String, Integer> shiftRowForTicker(String ticker) {
		Map<String, String> tickerToSector = this.getTickerToSector();
		Map<String, String> tickerToIndustry = this.getTickerToIndustry();
		String sector = tickerToSector.get(ticker);
		String industry = tickerToIndustry.get(ticker);
		PairBO<String, Integer> tickerToRowNumToAdd = null;
		for (Row row : this.getSheet()) {
			Integer rowNum = row.getRowNum();
			if (rowNum != 0) {
				Cell sectorCell = row.getCell(CommonSheetConstants.SECTOR_COLUMN.getCommonColumn());
				String sectorToCompare = sectorCell.getStringCellValue();
				int sectorComparator = sector.compareTo(sectorToCompare);
				if (sectorComparator <= 0) {
					if (sectorComparator == 0) {
						Cell industryCell = row.getCell(CommonSheetConstants.INDUSTRY_COLUMN.getCommonColumn());
						String industryToCompare = industryCell.getStringCellValue();
						int industryComparator = industry.compareTo(industryToCompare);
						if (industryComparator == 0) {
							Cell tickerCell = row.getCell(CommonSheetConstants.TICKER_COLUMN.getCommonColumn());
							String tickerToCompare = tickerCell.getStringCellValue();
							int tickerComparator = ticker.compareTo(tickerToCompare);
							if (tickerComparator <= 0) {
								this.getSheet().shiftRows(rowNum, this.getSheet().getLastRowNum() + 1, 1, true, true);
								tickerToRowNumToAdd = new PairBO<String, Integer>(ticker, rowNum);
								return tickerToRowNumToAdd;
							}
						} else if (industryComparator < 0) {
							this.getSheet().shiftRows(rowNum, this.getSheet().getLastRowNum() + 1, 1, true, true);
							tickerToRowNumToAdd = new PairBO<String, Integer>(ticker, rowNum);
							return tickerToRowNumToAdd;
						}
					} else {
						this.getSheet().shiftRows(rowNum, this.getSheet().getLastRowNum() + 1, 1, true, true);
						tickerToRowNumToAdd = new PairBO<String, Integer>(ticker, rowNum);
						return tickerToRowNumToAdd;
					}

				}
			}
		}
		if (tickerToRowNumToAdd == null) {
			tickerToRowNumToAdd = new PairBO<String, Integer>(ticker, this.getSheet().getLastRowNum() + 1);
		}
		return tickerToRowNumToAdd;
	}

	public void updateRemovedTickers() {
		for (String ticker : tickersToRemove) {
			CommonFinancialLibrary.removeTickerBySheet(this.getSheet(), ticker);
		}
	}

	public Sheet getSheet() {
		return sheet;
	}

	public List<String> getTickersToRemove() {
		return tickersToRemove;
	}

	public List<String> getTickersToAdd() {
		return tickersToAdd;
	}

	public DataService getDataService() {
		return dataService;
	}

	public Map<String, String> getTickerToSector() {
		return tickerToSector;
	}

	public Integer getYear() {
		return year;
	}

	public Map<String, Row> getAddedTickersToRow() {
		return addedTickersToRow;
	}

	public Map<String, String> getTickerToIndustry() {
		return tickerToIndustry;
	}

}
