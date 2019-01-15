package plafar.domain;

/**
 * This is the Bill class containing data describing a real bill object
 */
public class Bill {
	/**
	 * The unique identification number of a bill
	 */
	private int id;
	/**
	 * The ID of the sold item
	 */
	private int itemId;
	/**
	 * The quantity of items sold
	 */
	private int quantity;
	/**
	 * The price of the item when it was sold
	 */
	private float price;
	
	/**
	 * Initializes a new empty bill
	 */
	public Bill() {
		id = 0;
		itemId = 0;
		quantity = 0;
		price = 0;
	}
	
	/**
	 * Initializes a new bill with item, quantity and price
	 * @param itemId - the sold item
	 * @param quantity - the quantity of sold items
	 * @param price - the price of the item
	 */
	public Bill(int itemId, int quantity, float price) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}
	
	/**
	 * Initializes a new bill with id, item, quantity and price
	 * @param id - the bill id
	 * @param itemId - the sold item
	 * @param quantity - the quantity of sold items
	 * @param price - the price of the item
	 */
	public Bill(int id, int itemId, int quantity, float price) {
		this.id = id;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}
	
	/**
	 * This method is used to get bill's id
	 * @return int - the bill id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * This is used to set a new item to the bill
	 * @param itemId - the item id
	 */
	public void setItemId(int itemId) {
		if(itemId < 0) {
			itemId = 0;
		}
		
		this.itemId = itemId;
	}
	
	/**
	 * This method is used to retrieve the item id of the bill
	 * @return int - the item id
	 */
	public int getItemId() {
		return itemId;
	}
	
	/**
	 * This method is used to retrieve the quantity of sold items from bill
	 * @return int - quantity of sold items
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * This method is used to set a new quantity of sold items to the bill
	 * @param quantity - the new quantity of sold items
	 */
	public void setQuantity(int quantity) {
		if(quantity < 0) {
			quantity = 0;
		}
		
		this.quantity = quantity;
	}
	
	/**
	 * This method is used to retrieve the price of the item from the bill
	 * @return int - item price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * This method is used to set a new price of the item from the bill
	 * @param price - the new item price
	 */
	public void setPrice(float price) {
		if(price < 0) {
			price = 0;
		}
		
		this.price = price;
	}
}