/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;

import create.excel.future.CreateFutureDataExcelFile;
import create.excel.implementation.CreateAnnualExcelFile;
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

public class CreateAnnualAndFutureGUI implements PropertyChangeListener {

	private VBox root;
	private Button backButton;
	private Task<Void> createAnnualAndFutureTask;
	private ProgressIndicator stockSheetIndicator;
	private ProgressIndicator balanceSheetIndicator;
	private ProgressIndicator incomeSheetIndicator;
	private ProgressIndicator cashSheetIndicator;
	private ProgressIndicator ratioSheetIndicator;
	private ProgressIndicator futureIndicator;
	private ProgressIndicator analyzeAnnualIndicator;
	private Text copyrightText;

	public CreateAnnualAndFutureGUI(Task<Void> createAnnualAndFutureTask) throws MalformedURLException {
		this.createAnnualAndFutureTask = createAnnualAndFutureTask;
		this.createAnnualAndFutureGUI();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean isDone = (boolean) evt.getNewValue();
		String eventName = evt.getPropertyName();
		if (isDone && eventName.equals(CreateAnnualExcelFile.getStockSheetPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					stockSheetIndicator.setProgress(1.0f);
					stockSheetIndicator.setMaxSize(75, 75);
					stockSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(60);
					copyrightText.setTranslateY(60);
				}
			});
		} else if (isDone && eventName.equals(CreateAnnualExcelFile.getBsPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					balanceSheetIndicator.setProgress(1.0f);
					balanceSheetIndicator.setMaxSize(75, 75);
					balanceSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(45);
					copyrightText.setTranslateY(42);

				}
			});
		} else if (isDone && eventName.equals(CreateAnnualExcelFile.getIsPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					incomeSheetIndicator.setProgress(1.0f);
					incomeSheetIndicator.setMaxSize(75, 75);
					incomeSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(30);
					copyrightText.setTranslateY(28);

				}
			});
		} else if (isDone && eventName.equals(CreateAnnualExcelFile.getCfPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					cashSheetIndicator.setProgress(1.0f);
					cashSheetIndicator.setMaxSize(75, 75);
					cashSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(15);
					copyrightText.setTranslateY(10);

				}
			});
		} else if (isDone && eventName.equals(CreateAnnualExcelFile.getRatioPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ratioSheetIndicator.setProgress(1.0f);
					ratioSheetIndicator.setMaxSize(75, 75);
					ratioSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(0);
					copyrightText.setTranslateY(-2);

				}
			});
		} else if (isDone && eventName.equals(CreateFutureDataExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					futureIndicator.setProgress(1.0f);
					futureIndicator.setMaxSize(75, 75);
					futureIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(-8);
					copyrightText.setTranslateY(-2);

				}
			});
		} else if (isDone && eventName.equals(CreateAnnualExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					analyzeAnnualIndicator.setProgress(1.0f);
					analyzeAnnualIndicator.setMaxSize(75, 75);
					analyzeAnnualIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(-15);
					copyrightText.setTranslateY(-2);
				}
			});
		}
	}

	public VBox createAnnualAndFutureGUI() throws MalformedURLException {
		this.createProgressIndicators();
		this.createBackButton();

		root = new VBox(20);
		root.getChildren().add(generateTitleText());

		HBox createStockSheetHBox = new HBox(15);
		createStockSheetHBox.getChildren().add(createAnnualAndFutureLabel("Create Annual Stock Sheet Data"));
		createStockSheetHBox.getChildren().add(stockSheetIndicator);
		root.getChildren().add(createStockSheetHBox);

		HBox createBalanceSheetHBox = new HBox(15);
		createBalanceSheetHBox.getChildren().add(createAnnualAndFutureLabel("Create Annual Balance Sheet Data"));
		createBalanceSheetHBox.getChildren().add(balanceSheetIndicator);
		root.getChildren().add(createBalanceSheetHBox);

		HBox createIncomeSheetHBox = new HBox(15);
		createIncomeSheetHBox.getChildren().add(createAnnualAndFutureLabel("Create Annual Income Sheet Data"));
		createIncomeSheetHBox.getChildren().add(incomeSheetIndicator);
		root.getChildren().add(createIncomeSheetHBox);

		HBox createCashSheetHBox = new HBox(15);
		createCashSheetHBox.getChildren().add(createAnnualAndFutureLabel("Create Annual Cash Sheet Data"));
		createCashSheetHBox.getChildren().add(cashSheetIndicator);
		root.getChildren().add(createCashSheetHBox);

		HBox createRatioSheetHBox = new HBox(15);
		createRatioSheetHBox.getChildren().add(createAnnualAndFutureLabel("Create Annual Ratio Sheet Data"));
		createRatioSheetHBox.getChildren().add(ratioSheetIndicator);
		root.getChildren().add(createRatioSheetHBox);

		HBox createFutureHBox = new HBox(15);
		createFutureHBox.getChildren().add(createAnnualAndFutureLabel("Create Future Data"));
		createFutureHBox.getChildren().add(futureIndicator);
		root.getChildren().add(createFutureHBox);

		HBox createAnalyzeHBox = new HBox(15);
		createAnalyzeHBox.getChildren().add(createAnnualAndFutureLabel("Analyze Annual Data"));
		createAnalyzeHBox.getChildren().add(analyzeAnnualIndicator);
		root.getChildren().add(createAnalyzeHBox);

		root.getChildren().add(backButton);
		root.getChildren().add(generateCopyrightText());

		return root;
	}

	public void createBackButton() {
		int xTranslation = 360;
		int yTranslation = 70;
		backButton = new Button("Back");
		backButton.setCursor(Cursor.HAND);
		backButton.setMaxWidth(60);
		backButton.setMaxHeight(40);
		backButton.setTranslateX(xTranslation);
		backButton.setTranslateY(yTranslation);
		backButton.setOnMouseClicked(e -> {
			createAnnualAndFutureTask.cancel();
			CreateHomeGUI homeGUI = new CreateHomeGUI();
			VBox vbox = homeGUI.getRoot();
			root.getScene().setRoot(vbox);
		});
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

		ratioSheetIndicator = new ProgressIndicator(-1.0f);
		ratioSheetIndicator.setMaxSize(25, 25);
		ratioSheetIndicator.setTranslateX(10);
		ratioSheetIndicator.setTranslateY(10);

		futureIndicator = new ProgressIndicator(-1.0f);
		futureIndicator.setMaxSize(25, 25);
		futureIndicator.setTranslateX(10);
		futureIndicator.setTranslateY(10);

		analyzeAnnualIndicator = new ProgressIndicator(-1.0f);
		analyzeAnnualIndicator.setMaxSize(25, 25);
		analyzeAnnualIndicator.setTranslateX(10);
		analyzeAnnualIndicator.setTranslateY(10);
	}

	public Label createAnnualAndFutureLabel(String labelText) throws MalformedURLException {
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
		int yTranslation = 78;
		copyrightText.setTranslateX(xTranslation);
		copyrightText.setTranslateY(yTranslation);
		return copyrightText;
	}

	public Text generateTitleText() {
		String str = "Create and Analyze Annual and Future Data";
		Text text = new Text(65.0, 60.0, str);
		Font font = Font.font("Open Sans", FontWeight.BOLD, 18);
		text.setFont(font);
		int titleXTranslation = 60;
		int titleYTranslation = 20;
		text.setTranslateX(titleXTranslation);
		text.setTranslateY(titleYTranslation);
		return text;
	}

	public VBox getRoot() {
		return root;
	}

}
