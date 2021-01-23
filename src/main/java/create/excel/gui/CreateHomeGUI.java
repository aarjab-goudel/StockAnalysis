/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import analyze.excel.data.AnalyzeAnnualExcelFile;
import analyze.excel.data.AnalyzeQuarterlyExcelFile;
import create.excel.future.CreateFutureDataExcelFile;
import create.excel.implementation.CreateAnnualExcelFile;
import create.excel.implementation.CreateQuarterlyExcelFile;
import create.excel.implementation.CreateStockSheetExcelFile;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CreateHomeGUI {

	private RadioButton allInOneButton;
	private RadioButton excelAnnualAndFutureButton;
	private RadioButton excelQuarterlyButton;
	private RadioButton stockSheetExcelButton;
	private Button nextButton;
	private TextField textField;
	private ToggleGroup toggleGroup;
	private VBox root;
	private Task<Void> allInOneTask;
	private Task<Void> createAnnualAndFutureTask;
	private Task<Void> createQuarterlyTask;
	private Task<Void> createStockTask;
	private CreateAllInOneGUI allInOneGUI;
	private CreateAnnualAndFutureGUI annualAndFutureGUI;
	private CreateQuarterlyGUI createQuarterlyGUI;
	private CreateStockSheetGUI createStockSheetGUI;
	private Thread allInOneThread;
	private Thread annualAndFutureThread;
	private Thread quarterlyThread;
	private Thread stockSheetThread;

	public CreateHomeGUI() {
		root = this.createHomeGUI();

		this.createAllInOneTask();
		this.createAnnualAndFutureTask();
		this.createQuarterlyTask();
		this.createStockTask();
		this.setActionOnNextButton();
	}

	public List<String> generateStockListFromTextField() {
		List<String> stockListFromTextField = null;
		String textValue = textField.getText();
		if (!textValue.isEmpty()) {
			stockListFromTextField = new ArrayList<String>();
			String[] splitByCommas = textValue.split(",");
			for (int i = 0; i < splitByCommas.length; i++) {
				stockListFromTextField.add(splitByCommas[i]);
			}
		}
		return stockListFromTextField;
	}

	public void createStockTask() {
		createStockTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					CreateStockSheetExcelFile createStockSheet = new CreateStockSheetExcelFile(createStockTask,
							generateStockListFromTextField());
					createStockSheet.addPropertyChangeListener(createStockSheetGUI);
					createStockSheet.setIsEverythingDone(true);
					createStockSheet.removePropertyChangeListener(createStockSheetGUI);
				} catch (Exception e) {

				}
				return null;
			}
		};
	}

	public void createQuarterlyTask() {
		createQuarterlyTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					CreateQuarterlyExcelFile quarterlyExcelFile = new CreateQuarterlyExcelFile(createQuarterlyTask,
							true, generateStockListFromTextField());
					quarterlyExcelFile.addPropertyChangeListener(createQuarterlyGUI);

					quarterlyExcelFile.generateStockSheetData();
					quarterlyExcelFile.setIsStockSheetDone(true);

					quarterlyExcelFile.generateBSData();
					quarterlyExcelFile.setIsBSDone(true);

					quarterlyExcelFile.generateISData();
					quarterlyExcelFile.setIsISDone(true);

					quarterlyExcelFile.generateCFData();
					quarterlyExcelFile.setIsCFDone(true);

					quarterlyExcelFile.setUpSheets();
					quarterlyExcelFile.calculateStockData();
					quarterlyExcelFile.checkTaskAndSave();

					AnalyzeQuarterlyExcelFile analyzeQuarterly = new AnalyzeQuarterlyExcelFile(createQuarterlyTask);
					quarterlyExcelFile.setIsEverythingDone(true);
					quarterlyExcelFile.removePropertyChangeListener(createQuarterlyGUI);

				} catch (Exception e) {

				}
				return null;

			}

		};
	}

	public void createAnnualAndFutureTask() {
		createAnnualAndFutureTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					CreateAnnualExcelFile annualExcelFile = new CreateAnnualExcelFile(createAnnualAndFutureTask, true,
							generateStockListFromTextField());
					annualExcelFile.addPropertyChangeListener(annualAndFutureGUI);

					annualExcelFile.generateStockSheetData();
					annualExcelFile.setIsStockSheetDone(true);

					annualExcelFile.generateBSData();
					annualExcelFile.setIsBSDone(true);

					annualExcelFile.generateISData();
					annualExcelFile.setIsISDone(true);

					annualExcelFile.generateCFData();
					annualExcelFile.setIsCFDone(true);

					annualExcelFile.generateRatioData();
					annualExcelFile.setIsRatioDone(true);

					annualExcelFile.setUpSheets();
					annualExcelFile.calculateStockData();
					annualExcelFile.checkTaskAndSave();

					CreateFutureDataExcelFile futureExcelFile = new CreateFutureDataExcelFile(createAnnualAndFutureTask,
							generateStockListFromTextField());
					futureExcelFile.addPropertyChangeListener(annualAndFutureGUI);
					futureExcelFile.setIsEverythingDone(true);
					futureExcelFile.removePropertyChangeListener(annualAndFutureGUI);

					AnalyzeAnnualExcelFile analyzeAnnual = new AnalyzeAnnualExcelFile(createAnnualAndFutureTask);
					annualExcelFile.setIsEverythingDone(true);
					annualExcelFile.removePropertyChangeListener(annualAndFutureGUI);

				} catch (Exception e) {

				}
				return null;
			}
		};
	}

	public void createAllInOneTask() {
		allInOneTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					CreateAnnualExcelFile annualExcelFile = new CreateAnnualExcelFile(allInOneTask,
							generateStockListFromTextField());
					annualExcelFile.addPropertyChangeListener(allInOneGUI);
					annualExcelFile.setIsEverythingDone(true);
					annualExcelFile.removePropertyChangeListener(allInOneGUI);

					CreateFutureDataExcelFile futureExcelFile = new CreateFutureDataExcelFile(allInOneTask,
							generateStockListFromTextField());
					futureExcelFile.addPropertyChangeListener(allInOneGUI);
					futureExcelFile.setIsEverythingDone(true);
					futureExcelFile.removePropertyChangeListener(allInOneGUI);

					CreateQuarterlyExcelFile quarterlyExcelFile = new CreateQuarterlyExcelFile(allInOneTask,
							generateStockListFromTextField());
					quarterlyExcelFile.addPropertyChangeListener(allInOneGUI);
					quarterlyExcelFile.setIsEverythingDone(true);
					quarterlyExcelFile.removePropertyChangeListener(allInOneGUI);

					AnalyzeAnnualExcelFile analyzeAnnual = new AnalyzeAnnualExcelFile(allInOneTask);
					analyzeAnnual.addPropertyChangeListener(allInOneGUI);
					analyzeAnnual.setIsEverythingDone(true);
					analyzeAnnual.removePropertyChangeListener(allInOneGUI);

					AnalyzeQuarterlyExcelFile analyzeQuarterly = new AnalyzeQuarterlyExcelFile(allInOneTask);
					analyzeQuarterly.addPropertyChangeListener(allInOneGUI);
					analyzeQuarterly.setIsEverythingDone(true);
					analyzeQuarterly.removePropertyChangeListener(allInOneGUI);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return null;
			}
		};
	}

	public void setActionOnNextButton() {
		nextButton.setOnMouseClicked(e -> {
			if (allInOneButton.isSelected()) {
				allInOneThread = new Thread(allInOneTask);
				allInOneThread.start();
			} else if (excelAnnualAndFutureButton.isSelected()) {
				annualAndFutureThread = new Thread(createAnnualAndFutureTask);
				annualAndFutureThread.start();
			} else if (excelQuarterlyButton.isSelected()) {
				quarterlyThread = new Thread(createQuarterlyTask);
				quarterlyThread.start();
			} else if (stockSheetExcelButton.isSelected()) {
				stockSheetThread = new Thread(createStockTask);
				stockSheetThread.start();
			}
		});

	}

	public VBox createHomeGUI() {
		root = new VBox(35);
		this.generateRadioButtons();
		this.generateNextButton();

		root.getChildren().add(generateTitleText());
		root.getChildren().add(generateTextField());
		textField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

		root.getChildren().add(allInOneButton);
		root.getChildren().add(excelAnnualAndFutureButton);
		root.getChildren().add(excelQuarterlyButton);
		root.getChildren().add(stockSheetExcelButton);
		root.getChildren().add(nextButton);
		root.getChildren().add(generateCopyrightText());
		allInOneButton.requestFocus();
		return root;
	}

	public TextField generateTextField() {
		int textFieldWidth = 405;
		textField = new TextField();
		textField.setId("inputText");
		textField.setPromptText("AAPL,MSFT,TSLA");
		textField.setMaxWidth(textFieldWidth);
		textField.setTranslateX(30);
		textField.setTranslateY(20);
		return textField;
	}

	public Text generateTitleText() {
		String str = "Please Enter Tickers Separated By Commas";
		Text text = new Text(65.0, 60.0, str);
		Font font = Font.font("Open Sans", FontWeight.BOLD, 20);
		text.setFont(font);
		int titleXTranslation = 30;
		int titleYTranslation = 20;
		text.setTranslateX(titleXTranslation);
		text.setTranslateY(titleYTranslation);
		return text;
	}

	public Text generateCopyrightText() {
		String str = "Copyright \u00a9 2021 Aarjab Goudel. All rights reserved.";
		Text text = new Text(65, 60, str);
		Font font = Font.font("Open Sans", 10);
		text.setFont(font);
		int xTranslation = 265;
		int yTranslation = 90;
		text.setTranslateX(xTranslation);
		text.setTranslateY(yTranslation);
		return text;
	}

	public void generateNextButton() {
		int xTranslation = 360;
		int yTranslation = 100;
		nextButton = new Button("Next");
		nextButton.setCursor(Cursor.HAND);
		nextButton.setTranslateX(xTranslation);
		nextButton.setTranslateY(yTranslation);
		nextButton.setMaxWidth(60);
		nextButton.setMaxHeight(40);
		nextButton.setOnAction(e -> {
			if (allInOneButton.isSelected()) {
				try {
					allInOneGUI = new CreateAllInOneGUI(allInOneTask);

				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				VBox allInOneRoot = allInOneGUI.getRoot();
				allInOneButton.getScene().setRoot(allInOneRoot);

			} else if (excelAnnualAndFutureButton.isSelected()) {
				try {
					annualAndFutureGUI = new CreateAnnualAndFutureGUI(createAnnualAndFutureTask);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				VBox annualAndFutureRoot = annualAndFutureGUI.getRoot();
				excelAnnualAndFutureButton.getScene().setRoot(annualAndFutureRoot);
			} else if (excelQuarterlyButton.isSelected()) {
				try {
					createQuarterlyGUI = new CreateQuarterlyGUI(createQuarterlyTask);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				VBox quarterlyRoot = createQuarterlyGUI.getRoot();
				excelQuarterlyButton.getScene().setRoot(quarterlyRoot);
			} else if (stockSheetExcelButton.isSelected()) {
				createStockSheetGUI = new CreateStockSheetGUI(createStockTask);
				VBox stockSheetRoot = createStockSheetGUI.getRoot();
				stockSheetExcelButton.getScene().setRoot(stockSheetRoot);
			}
		});
	}

	public void generateRadioButtons() {
		int xTranslation = 80;
		int yTranslation = 30;

		allInOneButton = new RadioButton("Create and Analyze Annual, Quarterly, and Future Data");
		allInOneButton.setTranslateX(xTranslation);
		allInOneButton.setTranslateY(yTranslation);
		allInOneButton.setCursor(Cursor.HAND);
		allInOneButton.setSelected(true);

		excelAnnualAndFutureButton = new RadioButton("Create and Analyze Annual and Future Data");
		excelAnnualAndFutureButton.setTranslateX(xTranslation);
		excelAnnualAndFutureButton.setTranslateY(yTranslation);
		excelAnnualAndFutureButton.setCursor(Cursor.HAND);

		excelQuarterlyButton = new RadioButton("Create and Analyze Quarterly Data");
		excelQuarterlyButton.setTranslateX(xTranslation);
		excelQuarterlyButton.setTranslateY(yTranslation);
		excelQuarterlyButton.setCursor(Cursor.HAND);

		stockSheetExcelButton = new RadioButton("Create Stock Sheet Data");
		stockSheetExcelButton.setTranslateX(xTranslation);
		stockSheetExcelButton.setTranslateY(yTranslation);
		stockSheetExcelButton.setCursor(Cursor.HAND);

		toggleGroup = new ToggleGroup();
		allInOneButton.setToggleGroup(toggleGroup);
		excelAnnualAndFutureButton.setToggleGroup(toggleGroup);
		excelQuarterlyButton.setToggleGroup(toggleGroup);
		stockSheetExcelButton.setToggleGroup(toggleGroup);

	}

	public RadioButton getAllInOneButton() {
		return allInOneButton;
	}

	public RadioButton getExcelAnnualAndFutureButton() {
		return excelAnnualAndFutureButton;
	}

	public RadioButton getExcelQuarterlyButton() {
		return excelQuarterlyButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	public VBox getRoot() {
		return root;
	}

}
