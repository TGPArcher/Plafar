package views;

import java.util.LinkedList;
import java.util.List;

import controller.ItemController;
import controller.StoreController;
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

/**
 * StoreListPage is a view class responsible for creating and displaying the list of items from the store
 */
public class StoreListPage extends Page{
	/**
	 * Session persistent data about the available checkbox
	 */
	public static boolean prefAvailable = true;
	/**
	 * Session persistent data about the unavailable checkbox
	 */
	public static boolean prefUnavailable = true;
	
	/**
	 * The list of items to be displayed
	 */
	private List<StoreItem> items = null;
	/**
	 * The list of containers displaying items
	 */
	private ListView<HBox> list = null;
	
	/**
	 * The button used to add a new item to the store
	 */
	private Button addBtn = null;
	/**
	 * The button used to edit a selected item from the store
	 */
	private Button editBtn = null;
	/**
	 * The button used to delete a selecte item from teh store
	 */
	private Button deleteBtn = null;
	
	/**
	 * The checkbox handling the available items
	 */
	private CheckBox available = null;
	/**
	 * The checkbox handling the unavailable items
	 */
	private CheckBox unavailable = null;
	
	/**
	 * Initializes the page with the items to be displayed
	 * @param items - items to be displayed
	 */
	public StoreListPage(List<StoreItem> items) {
		if(items == null) {
			this.items = new LinkedList<StoreItem>();
		}
		else {
			this.items = items;
		}
		setTitle("Store");
		contents = doContents();
		setCheckBoxAction();
		setItemButtonsAction();
	}
	
	/**
	 * This method is used to create the upper part of the page.
	 * Containing the the control buttons and checkboxes
	 * @return Pane - the parent container of the upper part of the page
	 */
	private Pane drawHeader() {
		available = new CheckBox("Available");
		available.setSelected(true);
		
		unavailable = new CheckBox("Unavailable");
		unavailable.setSelected(true);
		
		Region freeRegion = new Region();
		HBox.setHgrow(freeRegion, Priority.ALWAYS);
		
		addBtn = new Button("Add");
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
	
	/**
	 * This method is used to create a ListView off all the items
	 * @return ListView - a list view containg cells with all the items
	 */
	private ListView<HBox> drawItemList() {
		ObservableList<HBox> itemsBox = FXCollections.observableArrayList();
		for(int i = 0; i < items.size(); i++) {
			itemsBox.add(drawItem(items.get(i)));
		}
		
		list = new ListView<HBox>(itemsBox);
		list.setFixedCellSize(30);
		list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<HBox>() {
	                public void changed(ObservableValue<? extends HBox> ov, HBox old_val, HBox new_val) {
	                	setDisableControlButtons(false);
	                }
	            });
		
		VBox.setVgrow(list, Priority.ALWAYS);
		return list;
	}
	
	/**
	 * This method is used to create the container which holds a item's data
	 * @param item - the item to be displayed
	 * @return HBox - the parent container of the item
	 */
	private HBox drawItem(StoreItem item) {
		HBox itemPane = new HBox(10);
		itemPane.setAlignment(Pos.CENTER);
		
		Label name = new Label(item.getName().substring(0, Math.min(25, item.getName().length())));
		name.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(name);
		
		Label description = new Label(item.getDescription().substring(0, Math.min(50, item.getDescription().length())));
		itemPane.getChildren().add(description);
		
		Region btnRegion = new Region();
		HBox.setHgrow(btnRegion, Priority.ALWAYS);
		itemPane.getChildren().add(btnRegion);
		
		Label price = new Label(String.valueOf(item.getPrice()) + " $");
		price.setAlignment(Pos.CENTER);
		itemPane.getChildren().add(price);
		
		Button sellBtn = new Button("Sell");
		sellBtn.setOnAction((event) -> {
			StoreController.sellItemPage(item);
		});
		itemPane.getChildren().add(sellBtn);
		
		return itemPane;
	}
	
	/**
	 * This method is used to bind checkboxes to their actions
	 */
	private void setCheckBoxAction() {
		available.setSelected(prefAvailable);
		unavailable.setSelected(prefUnavailable);
		
		available.setOnAction((event) -> {
			prefAvailable = !prefAvailable;
			StoreController.setStorePage();
		});
		
		unavailable.setOnAction((event) -> {
			prefUnavailable = !prefUnavailable;
			StoreController.setStorePage();
		});
	}
	
	/**
	 * This method is used to bind control buttons with their actions
	 */
	private void setItemButtonsAction() {
		addBtn.setOnAction((event) -> {
			ItemController.setAddItemPage();
		});
		
		editBtn.setOnAction((event) -> {
			StoreItem item = getSelectedItem();
			if(item == null) {
				return;
			}
			
			ItemController.setEditItemPage(item);
		});
		
		deleteBtn.setOnAction((event) -> {
			StoreItem item = getSelectedItem();
			if(item == null) {
				return;
			}
			
			ItemController.setDeleteItemPage(item);
		});
	}
	
	/**
	 * This method is used to enable/disable control buttons
	 * @param val - true - disables the buttons, false - enables the buttons
	 */
	private void setDisableControlButtons(boolean val) {
		editBtn.setDisable(val);
        deleteBtn.setDisable(val);
	}

	@Override
	protected Parent doContents() {
		VBox pageContent = new VBox(drawTitle(), drawHeader(), drawItemList());
		HBox.setHgrow(pageContent, Priority.ALWAYS);
		
		return pageContent;
	}
	
	/**
	 * This method is used to retrieve the currently selected item
	 * @return StoreItem - the selected item
	 */
	public StoreItem getSelectedItem() {
		int index = list.getSelectionModel().getSelectedIndex();
		if(index == -1) {
			return null;
		}
		
		return items.get(index);
	}
}