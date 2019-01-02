package Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class StoreListPage extends Page{
	
	public StoreListPage() {
		setTitle("Store");
	}
	
	private Pane drawTitle() {
		Label titleLabel = new Label(getTitle());
		Separator titleSeparator = new Separator();
		
		return new VBox(titleLabel, titleSeparator);
	}
	
	private Pane drawHeader() {
		CheckBox available = new CheckBox("Available");
		available.setSelected(true);
		CheckBox unavailable = new CheckBox("Unavailable");
		unavailable.setSelected(true);
		HBox headerPane = new HBox(10, available, unavailable);
		
		return new VBox(headerPane, new Separator());
	}
	
	private ListView<HBox> drawItemList() {
		ObservableList<HBox> items = FXCollections.observableArrayList();
		for(int i = 0; i < 5; i++) {
			items.add(drawItem());
		}
		
		ListView<HBox> list = new ListView<HBox>(items);
		list.setFixedCellSize(30);
		VBox.setVgrow(list, Priority.ALWAYS);
		
		return list;
	}
	
	private HBox drawItem() {
		HBox itemPane = new HBox(10);
		itemPane.setAlignment(Pos.CENTER);
		
		Label name = new Label("Patlagina");
		name.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(name);
		
		Label description = new Label("This is the description");
		itemPane.getChildren().add(description);
		
		Region btnRegion = new Region();
		HBox.setHgrow(btnRegion, Priority.ALWAYS);
		itemPane.getChildren().add(btnRegion);
		
		Label price = new Label("5.55$");
		price.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(price);
		
		Button sellBtn = new Button("Sell");
		itemPane.getChildren().add(sellBtn);
		
		return itemPane;
	}

	@Override
	public Parent doContents() {
		VBox pageContent = new VBox(drawTitle(), drawHeader(), drawItemList());
		HBox.setHgrow(pageContent, Priority.ALWAYS);
		
		return pageContent;
	}
}