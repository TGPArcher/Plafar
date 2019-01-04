package controller;

import java.util.LinkedList;
import java.util.List;

import plafar.domain.Bill;
import plafar.domain.StoreItem;
import views.*;

public final class BillController {
	
	public static void setBillPage() {
		List<Bill> bills = new LinkedList<Bill>();
		bills.add(new Bill(1, 5, 3.5f));
		bills.add(new Bill(2, 3, 0.5f));
		bills.add(new Bill(3, 51, 0.1f));
		
		MenuPage menu = new MenuPage();
		menu.setBillBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new BillListPage(bills));
		PageHandler.show();
	}
	
	public static void addBill(Bill bill) {
		// call the api to add the new bill
	}
	
	public static void setDeleteBillPage(Bill bill) {
		if(bill == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new DeleteBillPage(bill));
		PageHandler.show();
	}
	
	public static void deleteBill(Bill bill) {
		// call the api to delete the bill
		setBillPage();
	}
	
	public static StoreItem getBillItem(int itemId) {
		return ItemController.getItem(itemId);
	}
}
