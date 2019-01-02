package controller;

import Views.*;
import javafx.application.*;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		PageHandler.setStage(primaryStage);
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new ItemPage());
		PageHandler.show();
	}
}
