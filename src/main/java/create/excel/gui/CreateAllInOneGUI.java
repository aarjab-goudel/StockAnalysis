/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;

import analyze.excel.data.AnalyzeAnnualExcelFile;
import analyze.excel.data.AnalyzeQuarterlyExcelFile;
import create.excel.future.CreateFutureDataExcelFile;
import create.excel.implementation.CreateAnnualExcelFile;
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

public class CreateAllInOneGUI implements PropertyChangeListener {

	private VBox root;
	private ProgressIndicator createAnnualIndicator;
	private ProgressIndicator createFutureIndicator;
	private ProgressIndicator createQuarterlyIndicator;
	private ProgressIndicator analyzeAnnualIndicator;
	private ProgressIndicator analyzeQuarterlyIndicator;
	private Button backButton;
	private Task<Void> allInOneTask;
	private Text copyrightText;

	public CreateAllInOneGUI(Task<Void> allInOneTask) throws MalformedURLException {
		this.allInOneTask = allInOneTask;
		this.createAllInOneGUI();
	}

	public VBox createAllInOneGUI() throws MalformedURLException {
		this.createProgressIndicators();
		this.createBackButton();
		root = new VBox(30);
		root.getChildren().add(generateTitleText());

		HBox createAnnualHBox = new HBox(15);
		createAnnualHBox.getChildren().add(createAllInOneLabel("Create Annual Data"));
		createAnnualHBox.getChildren().add(createAnnualIndicator);
		createAnnualHBox.setTranslateY(10);
		root.getChildren().add(createAnnualHBox);

		HBox createFutureHBox = new HBox(15);
		createFutureHBox.getChildren().add(createAllInOneLabel("Create Future Data"));
		createFutureHBox.getChildren().add(createFutureIndicator);
		createFutureHBox.setTranslateY(10);
		root.getChildren().add(createFutureHBox);

		HBox createQuarterlyHBox = new HBox(15);
		createQuarterlyHBox.getChildren().add(createAllInOneLabel("Create Quarterly Data"));
		createQuarterlyHBox.getChildren().add(createQuarterlyIndicator);
		createQuarterlyHBox.setTranslateY(10);
		root.getChildren().add(createQuarterlyHBox);

		HBox analyzeAnnualHBox = new HBox(15);
		analyzeAnnualHBox.getChildren().add(createAllInOneLabel("Analyze Annual and Future Data"));
		analyzeAnnualHBox.getChildren().add(analyzeAnnualIndicator);
		analyzeAnnualHBox.setTranslateY(10);
		root.getChildren().add(analyzeAnnualHBox);

		HBox analyzeQuarterlyHBox = new HBox(15);
		analyzeQuarterlyHBox.getChildren().add(createAllInOneLabel("Analyze Quarterly Data"));
		analyzeQuarterlyHBox.getChildren().add(analyzeQuarterlyIndicator);
		analyzeAnnualHBox.setTranslateY(10);
		root.getChildren().add(analyzeQuarterlyHBox);

		root.getChildren().add(backButton);
		root.getChildren().add(generateCopyrightText());

		return root;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean isEverythingDone = (boolean) evt.getNewValue();
		String eventName = evt.getPropertyName();
		if (isEverythingDone && eventName.equals(CreateAnnualExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					createAnnualIndicator.setProgress(1.0f);
					createAnnualIndicator.setMaxSize(75, 75);
					createAnnualIndicator.setStyle("-fx-fill:null;");
					createAnnualIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(85);
					copyrightText.setTranslateY(80);
				}
			});
		} else if (isEverythingDone && eventName.equals(CreateFutureDataExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					createFutureIndicator.setProgress(1.0f);
					createFutureIndicator.setMaxSize(75, 75);
					createFutureIndicator.setStyle("-fx-fill:null;");
					createFutureIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(70);
					copyrightText.setTranslateY(63);

				}
			});
		} else if (isEverythingDone && eventName.equals(CreateQuarterlyExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					createQuarterlyIndicator.setProgress(1.0f);
					createQuarterlyIndicator.setMaxSize(75, 75);
					createQuarterlyIndicator.setStyle("-fx-fill:null;");
					createQuarterlyIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(55);
					copyrightText.setTranslateY(45);

				}
			});
		} else if (isEverythingDone && eventName.equals(AnalyzeAnnualExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					analyzeAnnualIndicator.setProgress(1.0f);
					analyzeAnnualIndicator.setMaxSize(75, 75);
					analyzeAnnualIndicator.setStyle("-fx-fill:null;");
					analyzeAnnualIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(40);
					copyrightText.setTranslateY(30);

				}
			});
		} else if (isEverythingDone && eventName.equals(AnalyzeQuarterlyExcelFile.getPropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					analyzeQuarterlyIndicator.setProgress(1.0f);
					analyzeQuarterlyIndicator.setMaxSize(75, 75);
					analyzeQuarterlyIndicator.setStyle("-fx-fill:null;");
					analyzeQuarterlyIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(20);
					copyrightText.setTranslateY(12);

				}
			});
		}

	}

	public Text generateCopyrightText() {
		String str = "Copyright \u00a9 2021 Aarjab Goudel. All rights reserved.";
		copyrightText = new Text(65, 60, str);
		Font font = Font.font("Open Sans", 10);
		copyrightText.setFont(font);
		int xTranslation = 265;
		int yTranslation = 95;
		copyrightText.setTranslateX(xTranslation);
		copyrightText.setTranslateY(yTranslation);
		return copyrightText;
	}

	public void createProgressIndicators() {
		createAnnualIndicator = new ProgressIndicator(-1.0f);
		createAnnualIndicator.setMaxSize(25, 25);
		createAnnualIndicator.setTranslateX(10);
		createAnnualIndicator.setTranslateY(10);

		createFutureIndicator = new ProgressIndicator(-1.0f);
		createFutureIndicator.setMaxSize(25, 25);
		createFutureIndicator.setTranslateX(10);
		createFutureIndicator.setTranslateY(10);

		createQuarterlyIndicator = new ProgressIndicator(-1.0f);
		createQuarterlyIndicator.setMaxSize(25, 25);
		createQuarterlyIndicator.setTranslateX(10);
		createQuarterlyIndicator.setTranslateY(10);

		analyzeAnnualIndicator = new ProgressIndicator(-1.0f);
		analyzeAnnualIndicator.setMaxSize(25, 25);
		analyzeAnnualIndicator.setTranslateX(10);
		analyzeAnnualIndicator.setTranslateY(10);

		analyzeQuarterlyIndicator = new ProgressIndicator(-1.0f);
		analyzeQuarterlyIndicator.setMaxSize(25, 25);
		analyzeQuarterlyIndicator.setTranslateX(10);
		analyzeQuarterlyIndicator.setTranslateY(10);
	}

	public void createBackButton() {
		int xTranslation = 360;
		int yTranslation = 100;
		backButton = new Button("Back");
		backButton.setCursor(Cursor.HAND);
		backButton.setMaxWidth(60);
		backButton.setMaxHeight(40);
		backButton.setTranslateX(xTranslation);
		backButton.setTranslateY(yTranslation);
		backButton.setOnMouseClicked(e -> {
			allInOneTask.cancel();
			CreateHomeGUI homeGUI = new CreateHomeGUI();
			VBox vbox = homeGUI.getRoot();
			root.getScene().setRoot(vbox);
		});

	}

	public Label createAllInOneLabel(String labelText) throws MalformedURLException {
		// Creating a Label

		Label label = new Label(labelText);

		Font font = Font.font("Open Sans", FontWeight.BOLD, FontPosture.REGULAR, 15);
		label.setFont(font);
		label.setTranslateX(20);
		label.setTranslateY(10);
		return label;
		// Filling color to the label
	}

	public Text generateTitleText() {
		String str = "Create and Analyze Annual, Quarterly, and Future Data";
		Text text = new Text(65.0, 60.0, str);
		Font font = Font.font("Open Sans", FontWeight.BOLD, 18);
		text.setFont(font);
		int titleXTranslation = 15;
		int titleYTranslation = 20;
		text.setTranslateX(titleXTranslation);
		text.setTranslateY(titleYTranslation);
		return text;
	}

	public VBox getRoot() {
		return root;
	}

}
