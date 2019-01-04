package controller;

import plafar.domain.StoreItem;
import views.*;

public final class ItemController {
	public static void setAddItemPage() {
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new ItemPage());
		PageHandler.show();
	}
	
	public static StoreItem getItem(int id) {
		// call the api for the item
		StoreItem item = new StoreItem(1, "Patlagina", "Cea mai buna", 5.23f, 17);
		
		return item;
	}
	
	public static void addItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		// call the api and add the item
		back();
	}
	
	public static void setEditItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new ItemPage(item));
		PageHandler.show();
	}
	
	public static void editItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		// call the api and edit the item
		back();
	}
	
	public static void setDeleteItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new DeleteItemPage(item));
		PageHandler.show();
	}
	
	public static void deleteItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		// call the api to delete the item
		back();
	}
	
	public static void back() {
		StoreController.setStorePage();
	}
}
