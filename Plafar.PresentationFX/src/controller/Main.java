package controller;

import Views.Page;
import Views.StoreListPage;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(createScene(new StoreListPage("Store Items")));
		primaryStage.show();
	}
	
	private Scene createScene(Page page) {
		return new Scene(page.doContents(), 700, 500);
	}
}
