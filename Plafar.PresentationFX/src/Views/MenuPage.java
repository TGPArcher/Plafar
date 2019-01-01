package Views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuPage extends Page {

	@Override
	public Parent doContents() {
		// logo
		ImageView image = new ImageView("file:C:/Users/TGP/Desktop/listsm.png");
		
		// store list page
		ImageView storeImage = new ImageView("file:C:/Users/TGP/Desktop/listsm.png");
		Button storeButton = new Button();
		storeButton.setGraphic(storeImage);
		
		// bill list page
		ImageView billImage = new ImageView("file:C:/Users/TGP/Desktop/listsm.png");
		Button billButton = new Button();
		billButton.setGraphic(billImage);
		
		return new VBox(image,
						new Separator(),
						storeButton,
						new Separator(),
						billButton);
	}

}
