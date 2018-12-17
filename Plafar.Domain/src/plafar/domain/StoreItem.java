package plafar.domain;

public class StoreItem extends Item {
	private float price;
	private int quantity;
	
	public StoreItem(){
		super();
		setPrice(0);
		setQuantity(0);
	}
	
	public StoreItem(int id, String name, String description, float price, int quantity) {
		super(id, name, description);
		setPrice(price);
		setQuantity(quantity);
	}
	
	public StoreItem(Item item, float price, int quantity) {
		super(item);
		setPrice(price);
		setQuantity(quantity);
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int cuantity) {
		if(cuantity < 0) {
			cuantity = 0;
		}
		
		this.quantity = cuantity;
	}
}
