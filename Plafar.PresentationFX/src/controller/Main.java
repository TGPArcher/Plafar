package controller;

import javafx.application.*;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize PageHandler
		PageHandler.setStage(primaryStage);
		// Set entry page
		StoreController.setStorePage();
	}
}
