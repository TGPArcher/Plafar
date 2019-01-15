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

/**
 * MenuPage is a view class responsible for creating the menu bar of the application
 */
public class MenuPage extends Page {
	/**
	 * Contents of the menu page
	 * Note: this class will go through refactoring and the pageContent will be removed, use contents instead
	 */
	private VBox pageContent = new VBox();
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
		pageContent.setFillWidth(true);
		contents = doContents();
		setButtonsAction();
	}
	
	@Override
	protected Parent doContents() {
		// logo
		addImage("file:resources/icons/plafar_logo_icon.png", false);
		
		// app name
		BorderPane namePane = new BorderPane();
		Label appName = new Label("Plafar");
		namePane.setCenter(appName);
		pageContent.getChildren().add(namePane);
		addSeparator();
		
		// store list page
		storeBtn = addButton("Store", "file:resources/icons/store_icon.png", true);
		
		// bill list page
		billBtn = addButton("Bill history", "file:resources/icons/history_icon.png", false);
		
		
		return pageContent;
	}
	
	/**
	 * This method is used to add a separator to the page.
	 * Mainly used to separate menu's buttons
	 */
	private void addSeparator() {
		Separator sep = new Separator();
		sep.setPadding(new Insets(5));
		
		pageContent.getChildren().add(sep);
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
	 */
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
	
	/**
	 * This method is used to add a button to menu's bar
	 * @param text - text of the button being added
	 * @param graphicPath - the path to the image of the button
	 * @param separator - true - puts a separator after the image, false - is doing nothing
	 * @return Button - the newly added button
	 */
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