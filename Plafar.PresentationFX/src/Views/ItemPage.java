package Views;

import Formatters.UnsignedDecimalFormatter;
import Formatters.UnsignedIntegerFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

public class ItemPage extends Page{
	StoreItem item = null;
	
	public ItemPage() {
		item = new StoreItem();
		contents = doContents();
	}
	
	@Override
	protected Parent doContents() {
		Label nameLabel = new Label("Name:");
		TextField name = new TextField();
		Label descriptionLabel = new Label("Description:");
		TextArea description = new TextArea();
		
		VBox grid1 = new VBox(nameLabel, name, descriptionLabel, description);
		grid1.setPadding(new Insets(5, 2.5, 5, 5));
		
		Label priceLabel = new Label("Price:");
		TextField price = new TextField("");
		price.setTextFormatter(UnsignedDecimalFormatter.getFormatter());
		Label quantityLabel = new Label("Quantity:");
		TextField quantity = new TextField();
		quantity.setTextFormatter(UnsignedIntegerFormatter.getFormatter());
		
		VBox grid2 = new VBox(priceLabel, price, quantityLabel, quantity);
		grid2.setPadding(new Insets(5, 5, 5, 2.5));
		HBox grid = new HBox(grid1, grid2);
		
		Button addBtn = new Button("Add");
		Button cancelBtn = new Button("Cancel");
		HBox btns = new HBox(5, addBtn, cancelBtn);
		btns.setAlignment(Pos.CENTER);
		
		return new VBox(grid,
						btns);
	}
}
