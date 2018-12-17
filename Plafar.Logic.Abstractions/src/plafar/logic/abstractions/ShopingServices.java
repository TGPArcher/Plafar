package plafar.logic.abstractions;
import java.util.List;
import plafar.domain.*;

public interface ShopingServices {
	StoreItem getItem(int itemId);
	Bill sellItem(StoreItem item, int quantity);
	boolean registerItem(StoreItem item, int quantity, float price);
	boolean editItem(StoreItem item);
	
	List<StoreItem> getAllItems();
	List<StoreItem> getAvailableItems();
	List<StoreItem> getUnavailableItems();
}
