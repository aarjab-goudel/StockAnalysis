/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;

import create.excel.implementation.CreateQuarterlyExcelFile;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CreateQuarterlyGUI implements PropertyChangeListener {
	private VBox root;
	private Button backButton;
	private Task<Void> createQuarterlyTask;
	private ProgressIndicator balanceSheetIndicator;
	private ProgressIndicator incomeSheetIndicator;
	private ProgressIndicator cashSheetIndicator;
	private ProgressIndicator stockSheetIndicator;
	private ProgressIndicator analyzeQuarterlyIndicator;
	private Text copyrightText;

	public CreateQuarterlyGUI(Task<Void> createQuarterlyTask) throws MalformedURLException {
		this.createQuarterlyTask = createQuarterlyTask;
		this.createQuarterlyGUI();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean isDone = (boolean) evt.getNewValue();
		String eventName = evt.getPropertyName();
		if (isDone && eventName.equals(CreateQuarterlyExcelFile.getStockSheetPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					stockSheetIndicator.setProgress(1.0f);
					stockSheetIndicator.setMaxSize(75, 75);
					stockSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(145);
					copyrightText.setTranslateY(150);
				}
			});
		} else if (isDone && eventName.equals(CreateQuarterlyExcelFile.getBsPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					balanceSheetIndicator.setProgress(1.0f);
					balanceSheetIndicator.setMaxSize(75, 75);
					balanceSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(130);
					copyrightText.setTranslateY(135);

				}
			});
		} else if (isDone && eventName.equals(CreateQuarterlyExcelFile.getIsPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					incomeSheetIndicator.setProgress(1.0f);
					incomeSheetIndicator.setMaxSize(75, 75);
					incomeSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(115);
					copyrightText.setTranslateY(117);
				}
			});
		} else if (isDone && eventName.equals(CreateQuarterlyExcelFile.getCfPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					cashSheetIndicator.setProgress(1.0f);
					cashSheetIndicator.setMaxSize(75, 75);
					cashSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(100);
					copyrightText.setTranslateY(100);

				}
			});
		} else if (isDone && eventName.equals(CreateQuarterlyExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					analyzeQuarterlyIndicator.setProgress(1.0f);
					analyzeQuarterlyIndicator.setMaxSize(75, 75);
					analyzeQuarterlyIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(80);
					copyrightText.setTranslateY(85);
				}
			});
		}

	}

	public VBox createQuarterlyGUI() throws MalformedURLException {
		this.createProgressIndicators();
		this.createBackButton();

		root = new VBox(20);
		root.getChildren().add(generateTitleText());

		HBox createStockSheetHBox = new HBox(15);
		createStockSheetHBox.getChildren().add(createQuarterlyLabel("Create Stock Data"));
		createStockSheetHBox.getChildren().add(stockSheetIndicator);
		createStockSheetHBox.setTranslateY(10);
		root.getChildren().add(createStockSheetHBox);

		HBox createBalanceSheetHBox = new HBox(15);
		createBalanceSheetHBox.getChildren().add(createQuarterlyLabel("Create Quarterly Balance Sheet Data"));
		createBalanceSheetHBox.getChildren().add(balanceSheetIndicator);
		createBalanceSheetHBox.setTranslateY(10);
		root.getChildren().add(createBalanceSheetHBox);

		HBox createIncomeSheetHBox = new HBox(15);
		createIncomeSheetHBox.getChildren().add(createQuarterlyLabel("Create Quarterly Income Sheet Data"));
		createIncomeSheetHBox.getChildren().add(incomeSheetIndicator);
		createIncomeSheetHBox.setTranslateY(10);
		root.getChildren().add(createIncomeSheetHBox);

		HBox createCashSheetHBox = new HBox(15);
		createCashSheetHBox.getChildren().add(createQuarterlyLabel("Create Quarterly Cash Sheet Data"));
		createCashSheetHBox.getChildren().add(cashSheetIndicator);
		createCashSheetHBox.setTranslateY(10);
		root.getChildren().add(createCashSheetHBox);

		HBox analyzeQuarterlyHBox = new HBox(15);
		analyzeQuarterlyHBox.getChildren().add(createQuarterlyLabel("Analyze Quarterly Data"));
		analyzeQuarterlyHBox.getChildren().add(analyzeQuarterlyIndicator);
		analyzeQuarterlyHBox.setTranslateY(10);
		root.getChildren().add(analyzeQuarterlyHBox);

		root.getChildren().add(backButton);
		root.getChildren().add(generateCopyrightText());

		return root;
	}

	public void createProgressIndicators() {
		stockSheetIndicator = new ProgressIndicator(-1.0f);
		stockSheetIndicator.setMaxSize(25, 25);
		stockSheetIndicator.setTranslateX(10);
		stockSheetIndicator.setTranslateY(10);

		balanceSheetIndicator = new ProgressIndicator(-1.0f);
		balanceSheetIndicator.setMaxSize(25, 25);
		balanceSheetIndicator.setTranslateX(10);
		balanceSheetIndicator.setTranslateY(10);

		incomeSheetIndicator = new ProgressIndicator(-1.0f);
		incomeSheetIndicator.setMaxSize(25, 25);
		incomeSheetIndicator.setTranslateX(10);
		incomeSheetIndicator.setTranslateY(10);

		cashSheetIndicator = new ProgressIndicator(-1.0f);
		cashSheetIndicator.setMaxSize(25, 25);
		cashSheetIndicator.setTranslateX(10);
		cashSheetIndicator.setTranslateY(10);

		analyzeQuarterlyIndicator = new ProgressIndicator(-1.0f);
		analyzeQuarterlyIndicator.setMaxSize(25, 25);
		analyzeQuarterlyIndicator.setTranslateX(10);
		analyzeQuarterlyIndicator.setTranslateY(10);
	}

	public Text generateTitleText() {
		String str = "Create and Analyze Quarterly Data";
		Text text = new Text(65.0, 60.0, str);
		Font font = Font.font("Open Sans", FontWeight.BOLD, 18);
		text.setFont(font);
		int titleXTranslation = 100;
		int titleYTranslation = 20;
		text.setTranslateX(titleXTranslation);
		text.setTranslateY(titleYTranslation);
		return text;
	}

	public Label createQuarterlyLabel(String labelText) throws MalformedURLException {
		// Creating a Label

		Label label = new Label(labelText);

		Font font = Font.font("Open Sans", FontWeight.BOLD, FontPosture.REGULAR, 15);
		label.setFont(font);
		label.setTranslateX(20);
		label.setTranslateY(10);
		return label;
		// Filling color to the label
	}

	public Text generateCopyrightText() {
		String str = "Copyright \u00a9 2021 Aarjab Goudel. All rights reserved.";
		copyrightText = new Text(65, 60, str);
		Font font = Font.font("Open Sans", 10);
		copyrightText.setFont(font);
		int xTranslation = 265;
		int yTranslation = 165;
		copyrightText.setTranslateX(xTranslation);
		copyrightText.setTranslateY(yTranslation);
		return copyrightText;
	}

	public void createBackButton() {
		int xTranslation = 360;
		int yTranslation = 160;
		backButton = new Button("Back");
		backButton.setCursor(Cursor.HAND);
		backButton.setMaxWidth(60);
		backButton.setMaxHeight(40);
		backButton.setTranslateX(xTranslation);
		backButton.setTranslateY(yTranslation);
		backButton.setOnMouseClicked(e -> {
			createQuarterlyTask.cancel();
			CreateHomeGUI homeGUI = new CreateHomeGUI();
			VBox vbox = homeGUI.getRoot();
			System.out.println("Back butto hit!");
			root.getScene().setRoot(vbox);
		});

	}

	public VBox getRoot() {
		return root;
	}

	public Button getBackButton() {
		return backButton;
	}

	public Task<Void> getCreateQuarterlyTask() {
		return createQuarterlyTask;
	}

	public ProgressIndicator getBalanceSheetIndicator() {
		return balanceSheetIndicator;
	}

	public ProgressIndicator getIncomeSheetIndicator() {
		return incomeSheetIndicator;
	}

	public ProgressIndicator getCashSheetIndicator() {
		return cashSheetIndicator;
	}

}
