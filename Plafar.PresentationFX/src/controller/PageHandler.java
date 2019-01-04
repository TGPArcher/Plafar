package controller;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.Page;

public final class PageHandler {
	private static Stage _stage = null;
	
	private static Page menuPage = null;
	private static Page activePage = null;
	
	public static void setStage(Stage stage) {
		if(stage == null) {
			return;
		}
		
		_stage = stage;
	}
	
	public static void setMenu(Page menu) {
		if(menu == null) {
			return;
		}
		
		menuPage = menu;
	}
	
	public static void setActivePage(Page page) {
		if(page == null) {
			return;
		}
		
		activePage = page;
	}
	
	public static void show() {
		if(_stage == null) {
			return;
		}
		
		_stage.setScene(createScene());
		_stage.show();
	}
	
	private static Scene createScene() {
		HBox screenContent = new HBox();
		if(menuPage != null) {
			screenContent.getChildren().add(menuPage.getContents());
		}
		if(activePage != null) {
			screenContent.getChildren().add(activePage.getContents());
		}
		
		return new Scene(screenContent, 900, 600);
	}
}