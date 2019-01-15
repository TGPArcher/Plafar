package views;

import controller.ItemController;
import formatters.UnsignedDecimalFormatter;
import formatters.UnsignedIntegerFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

/**
 * ItemPage is a view class responsible for creating the page where a item can be created or edited
 */
public class ItemPage extends Page{
	/**
	 * The item displayed on the page
	 */
	protected StoreItem item = null;
	
	/**
	 * A text filed containing the item's name
	 */
	private TextField name = null;
	/**
	 * A text filed containing the item's description
	 */
	private TextArea description = null;
	/**
	 * A text filed containing the item's price
	 */
	private TextField price = null;
	/**
	 * A text filed containing the item's quantity
	 */
	private TextField quantity = null;
	
	/**
	 * Initializes a Add Item page
	 */
	public ItemPage() {
		setTitle("Add Item");
		item = new StoreItem();
		contents = doContents();
	}
	
	/**
	 * Initializes a Edit Item page
	 * @param item - the item to be edited
	 */
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
		HBox.setHgrow(grid1, Priority.ALWAYS);
		
		Label priceLabel = new Label("Price:");
		price = new TextField("");
		price.setTextFormatter(UnsignedDecimalFormatter.getFormatter());
		Label quantityLabel = new Label("Quantity:");
		quantity = new TextField();
		quantity.setTextFormatter(UnsignedIntegerFormatter.getFormatter());
		
		VBox grid2 = new VBox(priceLabel, price, quantityLabel, quantity);
		grid2.setPadding(new Insets(5, 5, 5, 2.5));
		HBox.setHgrow(grid2, Priority.ALWAYS);
		HBox grid = new HBox(grid1, grid2);
		
		Button addBtn;
		if(getTitle().equals("Add Item")) {
			addBtn = new Button("Add");
		}
		else {
			addBtn = new Button("Edit");
		}
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
		
		VBox pageContents =  new VBox(drawTitle(), grid, btns);
		HBox.setHgrow(pageContents, Priority.ALWAYS);
		
		return pageContents;
	}
	
	/**
	 * This method is used to map the contents of the page to a StoreItem object
	 * @return StoreItem - the item with mapped that from the page
	 */
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
	
	/**
	 * This method is used on a Edit Item page to fill the text fields with current item details
	 */
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