package controller;

import java.util.LinkedList;
import java.util.List;

import plafar.domain.StoreItem;
import views.*;

public final class StoreController {
	public static void setStorePage() {
		// call the api to get list of items
		List<StoreItem> items = new LinkedList<StoreItem>();
		items.add(new StoreItem(0, "Patlagina", "Cel mai lecuitor lucru ever", 3.56f, 9));
		items.add(new StoreItem(0, "Curcunea", "Cel mai lecuitor lucru ever", 3.6f, 2));
		items.add(new StoreItem(0, "Sires", "Ieftin bun si mult in kill", 1.99f, 99));
		items.add(new StoreItem(0, "Patlajeli", "Siz di multi patlajeli", 0.56f, 200));
		
		MenuPage menu = new MenuPage();
		menu.setStoreBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new StoreListPage(items));
		PageHandler.show();
	}
	
	public static void sellItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new SellPage(item));
		PageHandler.show();
	}
	
	public static void sellItem(StoreItem item, int quantity) {
		// call the api to sell
		setStorePage();
	}
}
