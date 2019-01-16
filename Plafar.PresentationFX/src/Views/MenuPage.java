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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * MenuPage is a view class responsible for creating the menu bar of the application
 */
public class MenuPage extends Page {
	/**
	 * The button which sets the StoreListPage as the application active page
	 */
	private Button storeBtn = null;
	/**
	 * The button which sets the BillListPage as the application active page
	 */
	private Button billBtn = null;
	
	/**
	 * Initializes the MenuPage
	 */
	public MenuPage() {
		setTitle("Main Menu");
		contents = doContents();
		setButtonsAction();
	}
	
	@Override
	protected Parent doContents() {
		VBox content = new VBox();
		content.setFillWidth(true);
		
		// logo
		addImage("file:resources/icons/plafar_logo_icon.png", false, content);
		
		// app name
		BorderPane namePane = new BorderPane();
		Label appName = new Label("Plafar");
		namePane.setCenter(appName);
		content.getChildren().add(namePane);
		addSeparator(content);
		
		// store list page
		storeBtn = addButton("Store", "file:resources/icons/store_icon.png", true, content);
		
		// bill list page
		billBtn = addButton("Bill history", "file:resources/icons/history_icon.png", false, content);
		
		
		return content;
	}
	
	/**
	 * This method is used to add a separator to the page.
	 * Mainly used to separate menu's buttons
	 * @param content - the pane to which the separator will be added
	 */
	private void addSeparator(Pane content) {
		Separator sep = new Separator();
		sep.setPadding(new Insets(5));
		
		content.getChildren().add(sep);
	}
	
	/**
	 * This method is used to get a ImageView with the desired sizes and conditions from the image at the specified path
	 * @param graphicPath - the path to the image
	 * @param width - the width of the image
	 * @param height - the height of the image
	 * @param preserveRatio - if true preserve the aspect ratio of the image, if false do not preserve the aspect ratio of the image
	 * @return ImageView - the loaded image at specified sizes
	 */
	private ImageView getImage(String graphicPath, int width, int height, boolean preserveRatio) {
		ImageView img = new ImageView(graphicPath);
		img.setSmooth(true);
        img.setCache(true);
		img.setPreserveRatio(preserveRatio);
		img.setFitWidth(width);
		img.setFitHeight(height);
        
        return img;
	}
	
	/**
	 * This method is used to get a ImageView with standard parameters (50x50) preserve aspect ratio
	 * @param graphicPath - the path to the image
	 * @return ImageView - the loaded image
	 */
	private ImageView getImage(String graphicPath) {
		return getImage(graphicPath, 50, 50, true);
	}
	
	/**
	 * This method is used to add a image to menu's bar
	 * @param graphicPath - the path to the image
	 * @param separator - true - puts a separator after the image, false - is doing nothing
	 * @param content - the pane to which the image will be added
	 */
	private void addImage(String graphicPath, boolean separator, Pane content) {
		if(graphicPath == null) {
			return;
		}
		
		BorderPane pane = new BorderPane();
		pane.setCenter(getImage(graphicPath));
		
		content.getChildren().add(pane);
		if(separator) {
			addSeparator(content);
		}
	}
	
	/**
	 * This method is used to add a button to menu's bar
	 * @param text - text of the button being added
	 * @param graphicPath - the path to the image of the button
	 * @param separator - true - puts a separator after the image, false - is doing nothing
	 * @param content - the pane to which the button will be added
	 * @return Button - the newly added button
	 */
	private Button addButton(String text, String graphicPath, boolean separator, Pane content) {
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
		
		content.getChildren().add(btn);
		if(separator) {
			addSeparator(content);
		}
		
		return btn;
	}
	
	/**
	 * This method is used to disable the button which leads to StoreListPage
	 * @param val - true - disables the button, false - enables the button
	 */
	public void setStoreBtnDisabled(boolean val) {
		storeBtn.setDisable(val);
	}
	
	/**
	 * This method is used to disable the button which leads to BillListPage
	 * @param val - true - disables the button, false - enables the button
	 */
	public void setBillBtnDisabled(boolean val) {
		billBtn.setDisable(val);
	}
	
	/**
	 * This method is used to link menu buttons with their functionality
	 */
	public void setButtonsAction() {
		storeBtn.setOnAction((event) -> {
			StoreController.setStorePage();
		});
		
		billBtn.setOnAction((event) -> {
			BillController.setBillPage();
		});
	}
}