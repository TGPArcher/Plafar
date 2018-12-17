package test.persistence;

import java.util.List;

import plafar.domain.StoreItem;
import plafar.persistence.ItemRepository;
import plafar.persistence.abastractions.Persistent;

public class TestItemRepository {
	/*public static void main(String[] args) {
		add();
		getAll();
		edit();
		delete();
		getAll();
	}*/

	@SuppressWarnings("unused")
	private static void add() {
		Persistent<StoreItem> items = new ItemRepository();
		StoreItem item = new StoreItem(0, "Patlagina", "Ceva foarte bun, recomand", 5.5f, 50);
		items.addObject(item);
		
		item = new StoreItem(0, "Ricahrdasia", "Ceva straniu de tot da bun", 15f, 5);
		items.addObject(item);
		
		item = new StoreItem(0, "Amarolia", "Foarte amar, efectiv impotriva racelii", 5f, 26);
		items.addObject(item);
		
		item = new StoreItem(0, "Deleted", "To be deleted" , 0f, 0);
		items.addObject(item);
	}
	
	@SuppressWarnings("unused")
	private static void delete() {
		Persistent<StoreItem> items = new ItemRepository();
		items.deleteObject(4);
	}
	
	@SuppressWarnings("unused")
	private static void edit() {
		Persistent<StoreItem> items = new ItemRepository();
		StoreItem item = items.getObject(2);
		item.setPrice(15f);
		
		items.editObject(item);
	}
	
	@SuppressWarnings("unused")
	private static void getAll() {
		Persistent<StoreItem> items = new ItemRepository();
		
		List<StoreItem> itms = items.getAllObjects();
		for(int i = 0; i < itms.size(); i++) {
			StoreItem item = itms.get(i);
			System.out.println(item.getId() + " " + item.getName() + " " + item.getDescription() + " " + item.getPrice() + " " + item.getQuantity());
		}
	}
}
