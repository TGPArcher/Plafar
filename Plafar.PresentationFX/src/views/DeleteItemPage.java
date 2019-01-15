package views;

import controller.ItemController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

/**
 * DeleteItemPage is a view class responsible for creating the confirmation page when deleting a item from the store
 */
public class DeleteItemPage extends ItemPage{
	
	/**
	 * Initializing DeleteItemPage with the item to be deleted
	 * @param item - item to be deleted
	 */
	public DeleteItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		setTitle("Delete Item");
		this.item = item;
		contents = doContents();
	}
	
	@Override
	protected Parent doContents() {
		Label message = new Label("Are you sure you want to delete this item?");
		message.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label("Name:");
		Label name = new Label(item.getName());
		Label priceLabel = new Label("Price:");
		Label price = new Label(String.valueOf(item.getPrice()));
		Label quantityLabel = new Label("Quantity:");
		Label quantity = new Label(String.valueOf(item.getQuantity()));
		HBox itemBox = new HBox(10, nameLabel, name, priceLabel, price, quantityLabel, quantity);
		itemBox.setAlignment(Pos.CENTER);
		
		Button yesBtn = new Button("Yes");
		yesBtn.setOnAction((event) -> {
			ItemController.deleteItem(item);
		});
		Button noBtn = new Button("No");
		noBtn.setOnAction((event) -> {
			ItemController.back();
		});
		HBox btnBox = new HBox(10, yesBtn, noBtn);
		btnBox.setAlignment(Pos.CENTER);
		
		VBox content = new VBox(10,
								drawTitle(),
								message,
								itemBox,
								btnBox);
		content.setAlignment(Pos.TOP_CENTER);
		HBox.setHgrow(content, Priority.ALWAYS);
		return content;
	}
}