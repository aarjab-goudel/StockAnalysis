/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package create.excel.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateExcelGUI extends Application {

	private Scene scene;

	@Override
	public void start(Stage stage) {

		initUI(stage);
	}

	private void initUI(Stage stage) {

		CreateHomeGUI homeGUI = new CreateHomeGUI();

		scene = new Scene(homeGUI.getRoot(), 500, 500, Color.WHITE);

		stage.setTitle("Generate And Analyze Stock Data");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Scene getScene() {
		return scene;
	}

}
