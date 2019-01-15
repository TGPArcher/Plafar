package controller;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.Page;

/**
 * PageHandler is a utility class responsible of application's page handling.
 * Changes the menu, the active page. Displays the current active pages.
 */
public final class PageHandler {
	/**
	 * The application main stage
	 */
	private static Stage _stage = null;
	
	/**
	 * The menu page
	 */
	private static Page menuPage = null;
	/**
	 * The active page
	 */
	private static Page activePage = null;
	
	/**
	 * This method is used to set the stage on which PageHandler can operate
	 * @param stage - the current stage
	 */
	public static void setStage(Stage stage) {
		if(stage == null) {
			return;
		}
		
		_stage = stage;
	}
	
	/**
	 * This method is used to set the menu page of the application
	 * @param menu - the new menu page
	 */
	public static void setMenu(Page menu) {
		if(menu == null) {
			return;
		}
		
		menuPage = menu;
	}
	
	/**
	 * This method is used to set the active page of the application
	 * @param page - the active page
	 */
	public static void setActivePage(Page page) {
		if(page == null) {
			return;
		}
		
		activePage = page;
	}
	
	/**
	 * This method is used to display the current pages
	 */
	public static void show() {
		if(_stage == null) {
			return;
		}
		
		_stage.setScene(createScene());
		_stage.show();
	}
	
	/**
	 * This method is used to create the scene for the new pages to be displayed on
	 * @return Scene - the created scene
	 */
	private static Scene createScene() {
		HBox screenContent = new HBox();
		if(menuPage != null) {
			screenContent.getChildren().add(menuPage.getContents());
		}
		if(activePage != null) {
			screenContent.getChildren().add(activePage.getContents());
		}
		
		double width = 900;
		double height = 600;
		if(_stage != null) {
			Scene currentScene = _stage.getScene();
			if(currentScene != null) {
				width = currentScene.getWidth();
				height = currentScene.getHeight();
			}
		}
		
		return new Scene(screenContent, width, height);
	}
}