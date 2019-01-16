package views;

import java.util.LinkedList;
import java.util.List;

import controller.BillController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plafar.domain.Bill;
import plafar.domain.StoreItem;

/**
 * BillListPage is a view class responsible for creating the page where all the bills are listed
 */
public class BillListPage extends Page{
	
	/**
	 * List of bills to show
	 */
	private List<Bill> bills = null;
	/**
	 * List of containers containing displayed bills
	 */
	private ListView<HBox> list = null;
	
	/**
	 * The button for deleting bills
	 */
	private Button deleteBtn = null;
	
	/**
	 * Initializes the BillListPage with the list of bills provided
	 * @param bills - bills to show on page
	 */
	public BillListPage(List<Bill> bills) {
		if(bills == null) {
			bills = new LinkedList<Bill>();
		}
		
		setTitle("Bill History");
		this.bills = bills;
		contents = doContents();
		setControlButtonsAction();
	}
	
	@Override
	protected Parent doContents() {
		// delete button
		deleteBtn = new Button("Delete");
		deleteBtn.setAlignment(Pos.CENTER_LEFT);
		setDisableControlButtons(true);
		Region btnRegion = new Region();
		HBox.setHgrow(btnRegion, Priority.ALWAYS);
		HBox btnBox = new HBox(btnRegion, deleteBtn);
		
		// setting the list view
		ObservableList<HBox> billsBox = FXCollections.observableArrayList();
		for(int i = 0; i < bills.size(); i++) {
			billsBox.add(getBillBox(bills.get(i)));
		}
		
		list = new ListView<HBox>(billsBox);
		list.setFixedCellSize(30);
		list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<HBox>() {
	                public void changed(ObservableValue<? extends HBox> ov, HBox old_val, HBox new_val) {
	                	setDisableControlButtons(false);
	                }
	            });
		VBox.setVgrow(list, Priority.ALWAYS);		
		
		VBox pageContent = new VBox(drawTitle(),
									btnBox,
									list);
		HBox.setHgrow(pageContent, Priority.ALWAYS);
		
		return pageContent;
	}
	
	/**
	 * This method is creating containers which display bills
	 * @param bill - the bill to be displayed
	 * @return HBox - a container with all the nodes displaying bill data
	 */
	private HBox getBillBox(Bill bill) {
		HBox billBox = new HBox(10);
		billBox.setAlignment(Pos.CENTER_LEFT);
		
		try {
			StoreItem item = BillController.getBillItem(bill.getItemId());
			String itemName = item == null ? "Item unavailable" : item.getName();
			Label nameLabel = new Label("Name:");
			Label name = new Label(itemName);
			
			Region reg1 = new Region();
			HBox.setHgrow(reg1, Priority.ALWAYS);
			
			Label priceLabel = new Label("Price:");
			Label price = new Label(String.valueOf(bill.getPrice()));
			Label quantityLabel = new Label("Quantity:");
			Label quantity = new Label(String.valueOf(bill.getQuantity()));
			
			Region reg2 = new Region();
			HBox.setHgrow(reg2, Priority.ALWAYS);
			
			Label costLabel = new Label("Total:");
			Label cost = new Label(String.valueOf(bill.getPrice() * bill.getQuantity()));
			
			billBox.getChildren().addAll(nameLabel,
										 name,
										 reg1,
										 priceLabel,
										 price,
										 quantityLabel,
										 quantity,
										 reg2,
										 costLabel,
										 cost);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return billBox;
	}
	
	/**
	 * This method is used to make control buttons from the page active or inactive
	 * @param val - true - all control buttons are disabled, false - all control buttons are enabled
	 */
	private void setDisableControlButtons(boolean val) {
		deleteBtn.setDisable(val);
	}
	
	/**
	 * This method is used to set action to control buttons
	 */
	private void setControlButtonsAction() {
		deleteBtn.setOnAction((event) -> {
			int index = list.getSelectionModel().getSelectedIndex();
			if(index != -1) {
				BillController.setDeleteBillPage(bills.get(index));
			}
		});
	}
}