package Views;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.StoreItem;

public class StoreListPage extends Page{
	
	private List<StoreItem> items = null;
	
	private Button editBtn = null;
	private Button deleteBtn = null;
	
	private CheckBox available = null;
	private CheckBox unavailable = null;
	
	// prototype test only
	public StoreListPage() {
		items = new LinkedList<StoreItem>();
		items.add(new StoreItem(0, "Patlagina", "Cel mai lecuitor lucru ever", 3.56f, 9));
		items.add(new StoreItem(0, "Curcunea", "Cel mai lecuitor lucru ever", 3.6f, 2));
		items.add(new StoreItem(0, "Sires", "Ieftin bun si mult in kill", 1.99f, 99));
		items.add(new StoreItem(0, "Patlajeli", "Siz di multi patlajeli", 0.56f, 200));
		setTitle("Store");
		contents = doContents();
	}
	
	public StoreListPage(List<StoreItem> items) {
		if(items == null) {
			this.items = new LinkedList<StoreItem>();
		}
		else {
			this.items = items;
		}
		setTitle("Store");
		contents = doContents();
	}
	
	public StoreListPage(List<StoreItem> items, boolean available, boolean unavailable) {
		if(items == null) {
			this.items = new LinkedList<StoreItem>();
		}
		else {
			this.items = items;
		}
		setTitle("Store");
		contents = doContents();
	}
	
	private Pane drawTitle() {
		Label titleLabel = new Label(getTitle());
		Separator titleSeparator = new Separator();
		
		return new VBox(titleLabel, titleSeparator);
	}
	
	private Pane drawHeader() {
		available = new CheckBox("Available");
		available.setSelected(true);
		
		unavailable = new CheckBox("Unavailable");
		unavailable.setSelected(true);
		
		Region freeRegion = new Region();
		HBox.setHgrow(freeRegion, Priority.ALWAYS);
		
		Button addBtn = new Button("Add");
		addBtn.setAlignment(Pos.CENTER);
		editBtn = new Button("Edit");
		editBtn.setAlignment(Pos.CENTER);
		editBtn.setDisable(true);
		deleteBtn = new Button("Delete");
		deleteBtn.setAlignment(Pos.CENTER);
		deleteBtn.setDisable(true);
		ButtonBar btns = new ButtonBar();
		btns.getButtons().addAll(addBtn, editBtn, deleteBtn);
		
		HBox headerPane = new HBox(10, available, unavailable, freeRegion, btns);
		headerPane.setPadding(new Insets(0, 5, 0, 0));
		headerPane.setAlignment(Pos.CENTER);
		
		return new VBox(headerPane, new Separator());
	}
	
	private ListView<HBox> drawItemList() {
		ObservableList<HBox> itemsBox = FXCollections.observableArrayList();
		for(int i = 0; i < items.size(); i++) {
			itemsBox.add(drawItem(items.get(i)));
		}
		
		ListView<HBox> list = new ListView<HBox>(itemsBox);
		list.setFixedCellSize(30);
		list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<HBox>() {
	                public void changed(ObservableValue<? extends HBox> ov, HBox old_val, HBox new_val) {
	                	setDisableControllButtons(false);
	                }
	            });
		
		VBox.setVgrow(list, Priority.ALWAYS);
		return list;
	}
	
	private HBox drawItem(StoreItem item) {
		HBox itemPane = new HBox(10);
		itemPane.setAlignment(Pos.CENTER);
		
		Label name = new Label(item.getName());
		name.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(name);
		
		Label description = new Label(item.getDescription());
		itemPane.getChildren().add(description);
		
		Region btnRegion = new Region();
		HBox.setHgrow(btnRegion, Priority.ALWAYS);
		itemPane.getChildren().add(btnRegion);
		
		Label price = new Label(String.valueOf(item.getPrice()) + " $");
		price.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(price);
		
		Button sellBtn = new Button("Sell");
		itemPane.getChildren().add(sellBtn);
		
		return itemPane;
	}
	
	private void setDisableControllButtons(boolean val) {
		editBtn.setDisable(val);
        deleteBtn.setDisable(val);
	}

	@Override
	protected Parent doContents() {
		VBox pageContent = new VBox(drawTitle(), drawHeader(), drawItemList());
		HBox.setHgrow(pageContent, Priority.ALWAYS);
		
		return pageContent;
	}
}