package views;

import Formatters.UnsignedDecimalFormatter;
import Formatters.UnsignedIntegerFormatter;
import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

public class ItemPage extends Page{
	protected StoreItem item = null;
	
	private TextField name = null;
	private TextArea description = null;
	private TextField price = null;
	private TextField quantity = null;
	
	public ItemPage() {
		setTitle("Add Item");
		item = new StoreItem();
		contents = doContents();
	}
	
	public ItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		setTitle("Edit Item");
		this.item = item;
		contents = doContents();
		fillPage();
	}
	
	@Override
	protected Parent doContents() {
		Label nameLabel = new Label("Name:");
		name = new TextField();
		Label descriptionLabel = new Label("Description:");
		description = new TextArea();
		
		VBox grid1 = new VBox(nameLabel, name, descriptionLabel, description);
		grid1.setPadding(new Insets(5, 2.5, 5, 5));
		
		Label priceLabel = new Label("Price:");
		price = new TextField("");
		price.setTextFormatter(UnsignedDecimalFormatter.getFormatter());
		Label quantityLabel = new Label("Quantity:");
		quantity = new TextField();
		quantity.setTextFormatter(UnsignedIntegerFormatter.getFormatter());
		
		VBox grid2 = new VBox(priceLabel, price, quantityLabel, quantity);
		grid2.setPadding(new Insets(5, 5, 5, 2.5));
		HBox grid = new HBox(grid1, grid2);
		
		Button addBtn = new Button("Add");
		addBtn.setOnAction((event) -> {
			if(getTitle() == "Add Item") {
				ItemController.addItem(getItem());
			}
			else {
				ItemController.editItem(getItem());
			}
		});
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction((event) -> {
			ItemController.back();
		});
		HBox btns = new HBox(5, addBtn, cancelBtn);
		btns.setAlignment(Pos.CENTER);
		
		return new VBox(drawTitle(),
						grid,
						btns);
	}
	
	private StoreItem getItem() {
		try {
			item.setName(name.getText());
			item.setDescription(description.getText());
			item.setPrice(Float.parseFloat(price.getText()));
			item.setQuantity(Integer.parseInt(quantity.getText()));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		return item;
	}
	
	private void fillPage() {
		try {
			if(item.getName() != null) {
				name.setText(item.getName());
			}
			if(item.getDescription() != null) {
				description.setText(item.getDescription());
			}
			if(item.getPrice() >= 0) {
				price.setText(String.valueOf(item.getPrice()));
			}
			if(item.getQuantity() >= 0) {
				quantity.setText(String.valueOf(item.getQuantity()));
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
