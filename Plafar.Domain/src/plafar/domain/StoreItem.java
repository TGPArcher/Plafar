package plafar.domain;

public class StoreItem extends Item {
	private float price;
	private int cuantity;
	
	public StoreItem(){
		super();
		setPrice(0);
		setCuantity(0);
	}
	
	public StoreItem(int id, String name, String description, float price, int cuantity) {
		super(id, name, description);
		setPrice(price);
		setCuantity(cuantity);
	}
	
	public StoreItem(Item item, float price, int cuantity) {
		super(item);
		setPrice(price);
		setCuantity(cuantity);
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
	
	public int getCuantity() {
		return cuantity;
	}
	
	public void setCuantity(int cuantity) {
		if(cuantity < 0) {
			cuantity = 0;
		}
		
		this.cuantity = cuantity;
	}
}
