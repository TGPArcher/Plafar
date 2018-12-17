package plafar.domain;

public class Item {
	private int id;
	private String name;
	private String description;
	
	public Item() {
		id = 0;
		name = new String();
		description = new String();
	}
	
	public Item(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Item(Item item) {
		id = item.id;
		name = item.name;
		description = item.description;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if(name == null) {
			this.name = new String();
			return;
		}
		
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		if(description == null) {
			this.description = new String();
			return;
		}
		
		this.description = description;
	}
}