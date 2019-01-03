package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuPage extends Page {

	private VBox pageContent = new VBox();
	
	public MenuPage() {
		setTitle("Main Menu");
		pageContent.setFillWidth(true);
	}
	
	@Override
	public Parent doContents() {
		// logo
		addImage("file:C:/Users/TGP/Desktop/icon-herb.png", true);
		
		// store list page
		addButton("Store", "file:C:/Users/TGP/Desktop/listsm.png", true);
		
		// bill list page
		addButton("Bill history", "file:C:/Users/TGP/Desktop/listsm.png", false);
		
		
		return pageContent;
	}
	
	private void addSeparator() {
		Separator sep = new Separator();
		sep.setPadding(new Insets(5));
		
		pageContent.getChildren().add(sep);
	}
	
	private ImageView getImage(String graphicPath) {
		ImageView img = new ImageView(graphicPath);
		img.setFitWidth(100);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
        
        return img;
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
	
	private void addButton(String text, String graphicPath, boolean separator) {
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
	}
}
