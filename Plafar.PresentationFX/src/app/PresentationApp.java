package app;

import controller.PageHandler;
import controller.StoreController;
import javafx.application.Application;
import javafx.stage.Stage;

public class PresentationApp extends Application{
	
	public void run() {
		// start the api server
		APIHandler.start();
		
		// start the fx application
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize PageHandler
		PageHandler.setStage(primaryStage);
		// Set entry page
		StoreController.setStorePage();
	}
	
	@Override
	public void stop() {
		APIHandler.shutdown();
		
		try {
			super.stop();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}