package views;

import controller.BillController;
import controller.StoreController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuPage extends Page {
	private VBox pageContent = new VBox();
	private Button storeBtn = null;
	private Button billBtn = null;
	
	public MenuPage() {
		setTitle("Main Menu");
		pageContent.setFillWidth(true);
		contents = doContents();
		setButtonsAction();
	}
	
	@Override
	protected Parent doContents() {
		// logo
		addImage("file:C:/Users/TGP/Desktop/icon-herb.png", false);
		
		// app name
		BorderPane namePane = new BorderPane();
		Label appName = new Label("Plafar");
		namePane.setCenter(appName);
		pageContent.getChildren().add(namePane);
		addSeparator();
		
		// store list page
		storeBtn = addButton("Store", "file:C:/Users/TGP/Desktop/listsm.png", true);
		
		// bill list page
		billBtn = addButton("Bill history", "file:C:/Users/TGP/Desktop/listsm.png", false);
		
		
		return pageContent;
	}
	
	private void addSeparator() {
		Separator sep = new Separator();
		sep.setPadding(new Insets(5));
		
		pageContent.getChildren().add(sep);
	}
	
	private ImageView getImage(String graphicPath, int width, int height, boolean preserveRatio) {
		ImageView img = new ImageView(graphicPath);
		img.setPreserveRatio(preserveRatio);
		img.setFitWidth(width);
		img.setFitHeight(height);
        img.setSmooth(true);
        img.setCache(true);
        
        return img;
	}
	
	private ImageView getImage(String graphicPath) {
		return getImage(graphicPath, 50, 50, true);
	}
	
	private void addImage(String graphicPath, boolean separator) {
		if(graphicPath == null) {
			return;
		}
		
		BorderPane pane = new BorderPane();
		pane.setCenter(getImage(graphicPath));
		
		pageContent.getChildren().add(pane);
		if(separator) {
			addSeparator();
		}
	}
	
	private Button addButton(String text, String graphicPath, boolean separator) {
		ImageView btnImage = null;
		if(graphicPath != null) {
			btnImage = getImage(graphicPath);
		}
		
		Button btn = new Button();
		if(text != null) {
			btn.setText(text);
		}
		btn.setGraphic(btnImage);
		btn.setAlignment(Pos.CENTER_LEFT);
		btn.setMaxWidth(Double.MAX_VALUE);
		
		pageContent.getChildren().add(btn);
		if(separator) {
			addSeparator();
		}
		
		return btn;
	}
	
	public void setStoreBtnDisabled(boolean val) {
		storeBtn.setDisable(val);
	}
	
	public void setBillBtnDisabled(boolean val) {
		billBtn.setDisable(val);
	}
	
	public void setButtonsAction() {
		storeBtn.setOnAction((event) -> {
			StoreController.setStorePage();
		});
		
		billBtn.setOnAction((event) -> {
			BillController.setBillPage();
		});
	}
}
