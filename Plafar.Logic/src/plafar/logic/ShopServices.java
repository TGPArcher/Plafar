package plafar.logic;

import java.util.LinkedList;
import java.util.List;
import com.google.inject.Inject;
import plafar.domain.Bill;
import plafar.domain.StoreItem;
import plafar.logic.abstractions.ShopingServices;
import plafar.persistence.abstractions.Persistent;

/**
 * ShopServices is a implementation of ShopingServices using the services architecture for the store.
 */
public class ShopServices implements ShopingServices {
	/**
	 * This is the access to the database layer where service data is persisted
	 */
	Persistent<StoreItem> itemRepository = null;
	
	/**
	 * Initializes the service with dependency injection
	 * @param itemRepository - database access class implementing the Persistent< StoreItem >
	 */
	@Inject
	public ShopServices(Persistent<StoreItem> itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Override
	public StoreItem getItem(int itemId) {
		return itemRepository.getObject(itemId);
	}

	@Override
	public Bill sellItem(StoreItem item, int quantity) {
		if(item == null) {
			System.out.println("Trying to sell a null item");
			return null;
		}
		if(quantity < 0) {
			System.out.println("Trying to sell negative quantity of items");
			return null;
		}
		
		if(item.getQuantity() < quantity) {
			System.out.println("Trying to sell more items than we have available");
			return null;
		}
		
		Bill bill = new Bill(item.getId(), quantity, item.getPrice());
		item.setQuantity(item.getQuantity() - quantity);
		itemRepository.editObject(item);
		return bill;
	}

	@Override
	public boolean registerItem(StoreItem item) {
		if(item == null) {
			System.out.println("Trying to register a null item");
			return false;
		}
		return itemRepository.addObject(item);
	}

	@Override
	public boolean editItem(StoreItem item) {
		if(item == null) {
			System.out.print("Trying to edit a null item");
			return false;
		}
		
		return itemRepository.editObject(item);
	}

	@Override
	public List<StoreItem> getAllItems() {
		return itemRepository.getAllObjects();
	}

	@Override
	public List<StoreItem> getAvailableItems() {
		List<StoreItem> allItems = getAllItems();
		List<StoreItem> availableItems = new LinkedList<StoreItem>();
		
		for(int i = 0; i < allItems.size(); i++) {
			StoreItem item = allItems.get(i);
			
			if(item.getQuantity() > 0) {
				availableItems.add(item);
			}
		}
		
		return availableItems;
	}

	@Override
	public List<StoreItem> getUnavailableItems() {
		List<StoreItem> allItems = getAllItems();
		List<StoreItem> unavailableItems = new LinkedList<StoreItem>();
		
		for(int i = 0; i < allItems.size(); i++) {
			StoreItem item = allItems.get(i);
			
			if(item.getQuantity() == 0) {
				unavailableItems.add(item);
			}
		}
		
		return unavailableItems;
	}

	@Override
	public boolean deleteItem(int itemId) {
		return itemRepository.deleteObject(itemId);
	}

}
