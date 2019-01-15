package plafar.domain;

/**
 * This is an extension class of Item designed to be used for Plafar application because it expresses more the real properities of an item from the store.
 */
public class StoreItem extends Item {
	/**
	 * The price of the item in float. The desired format is to have only 2 decimals after the .
	 */
	private float price;
	/**
	 * The quantity of the item in the store. It should never be a negative number.
	 */
	private int quantity;
	
	/**
	 * Initializes an empty store item
	 */
	public StoreItem(){
		super();
		setPrice(0);
		setQuantity(0);
	}
	
	/**
	 * Initializes an store item with id, name, description, price and quantity
	 * @param id - the item id
	 * @param name - the item name
	 * @param description - the item description
	 * @param price - the item price
	 * @param quantity - the item quantity
	 */
	public StoreItem(int id, String name, String description, float price, int quantity) {
		super(id, name, description);
		setPrice(price);
		setQuantity(quantity);
	}
	
	/**
	 * Initializes an store item with an item object and his price and quantity
	 * @param item - the item from the store
	 * @param price - the item price
	 * @param quantity - the item description
	 */
	public StoreItem(Item item, float price, int quantity) {
		super(item);
		setPrice(price);
		setQuantity(quantity);
	}
	
	/**
	 * This method is used to retrieve the price of the item
	 * @return float - item's price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * This method is used to set a new price for the item
	 * @param price - the new item price
	 */
	public void setPrice(float price) {
		if(price < 0) {
			price = 0;
		}
		
		this.price = price;
	}
	
	/**
	 * This method is used to retrieve the quantity of items in store
	 * @return int - item quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * This method is used to set a new quantity of items in store
	 * @param quantity - the new quantity
	 */
	public void setQuantity(int quantity) {
		if(quantity < 0) {
			quantity = 0;
		}
		
		this.quantity = quantity;
	}
}