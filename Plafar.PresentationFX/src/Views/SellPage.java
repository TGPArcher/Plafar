package Views;

import Formatters.UnsignedIntegerFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

public class SellPage extends Page {
	private StoreItem item = null;
	
	// testing only
	public SellPage() {
		this.item = new StoreItem(0, "Patlagina", "Cel mai bun", 3.56f, 8);
	}
	
	public SellPage(StoreItem item) {
		this.item = item;
	}
	
	@Override
	public Parent doContents() {
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
		TextField sell = new TextField("1");
		sell.setTextFormatter(UnsignedIntegerFormatter.getFormatter(1, item.getQuantity()));
		VBox buyBox = new VBox(sellLabel, sell);
		
		HBox grid1 = new HBox(10, availableBox, buyBox);
		grid1.setAlignment(Pos.CENTER);
		
		Label priceLabel = new Label("Price");
		TextField price = new TextField(String.valueOf(item.getPrice()));
		price.setDisable(true);
		VBox priceBox = new VBox(priceLabel, price);
		
		Label costLabel = new Label("Cost");
		TextField cost = new TextField(String.valueOf(item.getPrice()));
		cost.setDisable(true);
		sell.textProperty().addListener((obs, oldText, newText) -> {
		    cost.setText(getTotalCost(newText));
		});
		VBox costBox = new VBox(costLabel, cost);
		
		HBox grid2 = new HBox(10, priceBox, costBox);
		grid2.setAlignment(Pos.CENTER);
		
		Button sellBtn = new Button("Confirm");
		Button cancelBtn = new Button("Cancel");
		HBox grid3 = new HBox(10, sellBtn, cancelBtn);
		grid3.setAlignment(Pos.CENTER);
		
		VBox pageContents = new VBox(10, nameBox, grid1, grid2, grid3);
		
		return pageContents;
	}
	
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

}
