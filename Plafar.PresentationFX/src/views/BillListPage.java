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

public class BillListPage extends Page{
	
	private List<Bill> bills = null;
	private ListView<HBox> list = null;
	
	private Button deleteBtn = null;
	
	public BillListPage(List<Bill> bills) {
		if(bills == null) {
			bills = new LinkedList<Bill>();
		}
		
		setTitle("Bill History");
		this.bills = bills;
		contents = doContents();
		setControllButtonsAction();
	}
	
	@Override
	protected Parent doContents() {
		// delete button
		deleteBtn = new Button("Delete");
		deleteBtn.setAlignment(Pos.CENTER_LEFT);
		setDisableControllButtons(true);
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
	                	setDisableControllButtons(false);
	                }
	            });
		VBox.setVgrow(list, Priority.ALWAYS);		
		
		VBox pageContent = new VBox(drawTitle(),
									btnBox,
									list);
		HBox.setHgrow(pageContent, Priority.ALWAYS);
		
		return pageContent;
	}
	
	private HBox getBillBox(Bill bill) {
		HBox billBox = new HBox(10);
		billBox.setAlignment(Pos.CENTER_LEFT);
		
		try {
			StoreItem item = BillController.getBillItem(bill.getItemId());
			Label nameLabel = new Label("Name:");
			Label name = new Label(item.getName());
			
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
	
	private void setDisableControllButtons(boolean val) {
		deleteBtn.setDisable(val);
	}
	
	private void setControllButtonsAction() {
		deleteBtn.setOnAction((event) -> {
			int index = list.getSelectionModel().getSelectedIndex();
			if(index != -1) {
				BillController.setDeleteBillPage(bills.get(index));
			}
		});
	}
}
