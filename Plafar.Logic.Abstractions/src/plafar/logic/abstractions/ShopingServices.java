package plafar.logic.abstractions;
import java.util.List;
import plafar.domain.*;

/**
 * A interface describing the logic of StoreItem usage in the application
 */
public interface ShopingServices {
	/**
	 * This method is used to retrieve a item from the shop
	 * @param itemId - The ID of the desired item
	 * @return StoreItem - the desired item
	 */
	StoreItem getItem(int itemId);
	
	/**
	 * This method is used to perform a item sale
	 * @param item - the item being sold
	 * @param quantity - how many items are being sold
	 * @return Bill - a bill containing information about this sale
	 */
	Bill sellItem(StoreItem item, int quantity);
	
	/**
	 * This method is used to register a new item in the shop
	 * @param item - the item being registered
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean registerItem(StoreItem item);
	
	/**
	 * This method is used to edit an existing item from the store
	 * @param item - the edited item
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean editItem(StoreItem item);
	
	/**
	 * This method is used to remove an existing item from the store
	 * @param itemId - the ID of the item desired to be removed
	 * @return boolean - the status of the operation (true - success, false - failure)
	 */
	boolean deleteItem(int itemId);
	
	/**
	 * This method is used to retrieve all the items from the store
	 * @return List< StoreItem > - a list containing all the items from store
	 */
	List<StoreItem> getAllItems();
	
	/**
	 * This method is used to retrieve all the items which are in stock from the store
	 * @return List< StoreItem > - a list containing all the items from store which are in stock
	 */
	List<StoreItem> getAvailableItems();
	
	/**
	 * This method is used to retrieve all the items which are out of stock from the store
	 * @return List< StoreItem > - a list containing all the items from store which are out of stock
	 */
	List<StoreItem> getUnavailableItems();
}
