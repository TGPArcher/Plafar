package plafar.domain;

public class Bill {
	private int id;
	private int itemId;
	private int quantity;
	private float price;
	
	public Bill() {
		id = 0;
		itemId = 0;
		quantity = 0;
		price = 0;
	}
	
	public Bill(int itemId, int quantity, float price) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}
	
	public Bill(int id, int itemId, int quantity, float price) {
		this.id = id;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	
	public void setItemId(int itemId) {
		if(itemId < 0) {
			itemId = 0;
		}
		
		this.itemId = itemId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		if(quantity < 0) {
			quantity = 0;
		}
		
		this.quantity = quantity;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		if(price < 0) {
			price = 0;
		}
		
		this.price = price;
	}
}
