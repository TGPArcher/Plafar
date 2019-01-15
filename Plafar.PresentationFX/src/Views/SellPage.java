package views;

import controller.StoreController;
import formatters.UnsignedIntegerFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

/**
 * SellPage is a view class responsible for creating the confirmation page for item selling
 */
public class SellPage extends Page {
	/**
	 * The item being sold
	 */
	private StoreItem item = null;
	
	/**
	 * TextField containing quantity of items being sold
	 */
	private TextField sellField = null;
	/**
	 * The button to confirm the transaction
	 */
	private Button sellBtn = null;
	/**
	 * The button to reject the transaction
	 */
	private Button cancelBtn = null;
	
	/**
	 * Initializes the page with the item being sold
	 * @param item - the item being sold
	 */
	public SellPage(StoreItem item) {
		this.item = item;
		contents = doContents();
		setSellBtnAction();
		setCancelBtnAction();
	}
	
	@Override
	protected Parent doContents() {
		Label name = new Label(item.getName());
		Separator nameSeparator = new Separator();
		nameSeparator.setPadding(new Insets(0, 25, 5, 25));
		VBox nameBox = new VBox(name, nameSeparator);
		nameBox.setAlignment(Pos.CENTER);
		
		Label availableLabel = new Label("Available");
		TextField available = new TextField(String.valueOf(item.getQuantity()));
		available.setDisable(true);
		VBox availableBox = new VBox(availableLabel, available);
		
		Label sellLabel = new Label("Sell");
		sellField = new TextField("1");
		sellField.setTextFormatter(UnsignedIntegerFormatter.getFormatter(1, item.getQuantity()));
		VBox buyBox = new VBox(sellLabel, sellField);
		
		HBox grid1 = new HBox(10, availableBox, buyBox);
		grid1.setAlignment(Pos.CENTER);
		
		Label priceLabel = new Label("Price");
		TextField price = new TextField(String.valueOf(item.getPrice()));
		price.setDisable(true);
		VBox priceBox = new VBox(priceLabel, price);
		
		Label costLabel = new Label("Cost");
		TextField cost = new TextField(String.valueOf(item.getPrice()));
		cost.setDisable(true);
		sellField.textProperty().addListener((obs, oldText, newText) -> {
		    cost.setText(getTotalCost(newText));
		});
		VBox costBox = new VBox(costLabel, cost);
		
		HBox grid2 = new HBox(10, priceBox, costBox);
		grid2.setAlignment(Pos.CENTER);
		
		sellBtn = new Button("Confirm");
		cancelBtn = new Button("Cancel");
		HBox grid3 = new HBox(10, sellBtn, cancelBtn);
		grid3.setAlignment(Pos.CENTER);
		
		VBox pageContents = new VBox(10, nameBox, grid1, grid2, grid3);
		HBox.setHgrow(pageContents, Priority.ALWAYS);
		
		return pageContents;
	}
	
	/**
	 * This method is used to calculate the total cost of the sale
	 * @param count - number of items
	 * @return String - a string value of the total cost
	 */
	private String getTotalCost(String count) {
		try {
			int newCount = Integer.parseInt(count);
			float newText = newCount * item.getPrice();
			
			return String.valueOf(newText);
		}
		catch(NumberFormatException e) {
			return "";
		}
	}
	
	/**
	 * This method is used to bind the sell button with his action
	 */
	private void setSellBtnAction() {
		sellBtn.setOnAction((event) -> {
			int quantity = 0;
			try {
				quantity = Integer.parseInt(sellField.getText());
			}
			catch(NumberFormatException e) {
				System.out.println(e.getMessage());
				return;
			}
			
			StoreController.sellItem(item, quantity);
		});
	}
	
	/**
	 * This method is used to bind the cancel button with his action
	 */
	private void setCancelBtnAction() {
		cancelBtn.setOnAction((event) -> {
			StoreController.setStorePage();
		});
	}
}