package plafar.domain;

public class Bill {
	private int id;
	private int itemId;
	private int cuantity;
	private float price;
	
	public Bill() {
		id = 0;
		itemId = 0;
		cuantity = 0;
		price = 0;
	}
	
	public Bill(int itemId, int cuantity, float price) {
		this.itemId = itemId;
		this.cuantity = cuantity;
		this.price = price;
	}
	
	public Bill(int id, int itemId, int cuantity, float price) {
		this.id = id;
		this.itemId = itemId;
		this.cuantity = cuantity;
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
	
	public int getCuantity() {
		return cuantity;
	}
	
	public void setCuantity(int cuantity) {
		if(cuantity < 0) {
			cuantity = 0;
		}
		
		this.cuantity = cuantity;
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
