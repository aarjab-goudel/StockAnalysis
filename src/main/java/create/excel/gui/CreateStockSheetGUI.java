/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import create.excel.implementation.CreateStockSheetExcelFile;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CreateStockSheetGUI implements PropertyChangeListener {

	private VBox root;
	private Button backButton;
	private Task<Void> createStockSheetTask;
	private ProgressIndicator stockSheetIndicator;
	private Text copyrightText;

	public CreateStockSheetGUI(Task<Void> createStockSheetTask) {
		this.createStockSheetTask = createStockSheetTask;
		this.createStockSheetGUI();
	}

	public VBox createStockSheetGUI() {
		this.createProgressIndicator();
		this.createBackButton();
		root = new VBox(20);
		root.getChildren().add(generateTitleText());
		root.getChildren().add(stockSheetIndicator);
		root.getChildren().add(backButton);
		root.getChildren().add(generateCopyrightText());
		return root;
	}

	public Text generateCopyrightText() {
		String str = "Copyright \u00a9 2021 Aarjab Goudel. All rights reserved.";
		copyrightText = new Text(65, 60, str);
		Font font = Font.font("Open Sans", 10);
		copyrightText.setFont(font);
		int xTranslation = 265;
		int yTranslation = 322;
		copyrightText.setTranslateX(xTranslation);
		copyrightText.setTranslateY(yTranslation);
		return copyrightText;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean isDone = (boolean) evt.getNewValue();
		String eventName = evt.getPropertyName();
		if (isDone && eventName.equals(CreateStockSheetExcelFile.getEverythingDonePropertyChangeName())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					stockSheetIndicator.setProgress(1.0f);
					stockSheetIndicator.setMaxSize(75, 75);
					stockSheetIndicator.setStyle("-fx-padding: 0 0 0 0;");
					backButton.setTranslateY(330);
					copyrightText.setTranslateY(335);
				}
			});
		}
	}

	public void createBackButton() {
		int xTranslation = 360;
		int yTranslation = 318;
		backButton = new Button("Back");
		backButton.setCursor(Cursor.HAND);
		backButton.setMaxWidth(60);
		backButton.setMaxHeight(40);
		backButton.setTranslateX(xTranslation);
		backButton.setTranslateY(yTranslation);
		backButton.setOnMouseClicked(e -> {
			createStockSheetTask.cancel();
			CreateHomeGUI homeGUI = new CreateHomeGUI();
			VBox vbox = homeGUI.getRoot();
			System.out.println("Back butto hit!");
			root.getScene().setRoot(vbox);
		});
	}

	public void createProgressIndicator() {
		int xTranslation = 200;
		int yTranslation = 150;
		stockSheetIndicator = new ProgressIndicator(-1.0f);
		stockSheetIndicator.setMaxSize(75, 75);
		stockSheetIndicator.setTranslateX(xTranslation);
		stockSheetIndicator.setTranslateY(yTranslation);

	}

	public Text generateTitleText() {
		String str = "Create Stock Sheet Data";
		Text text = new Text(65.0, 60.0, str);
		Font font = Font.font("Open Sans", FontWeight.BOLD, 18);
		text.setFont(font);
		int titleXTranslation = 140;
		int titleYTranslation = 30;
		text.setTranslateX(titleXTranslation);
		text.setTranslateY(titleYTranslation);
		return text;
	}

	public VBox getRoot() {
		return root;
	}

	public Button getBackButton() {
		return backButton;
	}

	public Task<Void> getCreateStockSheetTask() {
		return createStockSheetTask;
	}

	public ProgressIndicator getStockSheetIndicator() {
		return stockSheetIndicator;
	}

}
