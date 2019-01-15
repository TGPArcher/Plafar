package app;

import controller.PageHandler;
import controller.StoreController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class of the presentation application.
 * It controls the startup of the application, the initialization, and the closing.
 */
public class PresentationApp extends Application{
	/**
	 * This method is used to start the application
	 */
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