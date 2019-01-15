package views;

import controller.BillController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import plafar.domain.Bill;
import plafar.domain.StoreItem;

/**
 * DeleteBillPage is a view class responsible for creating the confirmation page for deleting bills 
 */
public class DeleteBillPage extends Page{
	/**
	 * The bill to be deleted
	 */
	private Bill bill = null;
	/**
	 * The item from the bill
	 */
	private StoreItem billItem = null;
	
	/**
	 * Initializes DeleteBillPage with the bill to delete
	 * @param bill - bill to be deleted
	 */
	public DeleteBillPage(Bill bill) {
		if(bill == null) {
			return;
		}
		
		this.bill = bill;
		billItem = BillController.getBillItem(bill.getItemId());
		setTitle("Delete Bill");
		contents = doContents();
	}
	
	@Override
	protected Parent doContents() {
		Label message = new Label("Are you sure you want to delete this bill?");
		message.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label("Name:");
		Label name = new Label(billItem.getName());
		Label priceLabel = new Label("Price:");
		Label price = new Label(String.valueOf(bill.getPrice()));
		Label quantityLabel = new Label("Quantity:");
		Label quantity = new Label(String.valueOf(bill.getQuantity()));
		Label costLabel = new Label("Total:");
		Label cost = new Label(String.valueOf(bill.getPrice() * bill.getQuantity()));
		HBox billBox = new HBox(10, nameLabel, name, priceLabel, price, quantityLabel, quantity, costLabel, cost);
		billBox.setAlignment(Pos.CENTER);
		
		Button yesBtn = new Button("Yes");
		yesBtn.setOnAction((event) -> {
			BillController.deleteBill(bill);
		});
		Button noBtn = new Button("No");
		noBtn.setOnAction((event) -> {
			BillController.setBillPage();
		});
		HBox btnBox = new HBox(10, yesBtn, noBtn);
		btnBox.setAlignment(Pos.CENTER);
		
		VBox content = new VBox(10,
								drawTitle(),
								message,
								billBox,
								btnBox);
		content.setAlignment(Pos.TOP_CENTER);
		HBox.setHgrow(content, Priority.ALWAYS);
		return content;
	}
}