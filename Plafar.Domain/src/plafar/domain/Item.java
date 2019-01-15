package plafar.domain;

/**
 * This is the Item class containing data about an physical item like name and description
 */
public class Item {
	/**
	 * The unique identification number of an item
	 */
	private int id;
	/**
	 * The name of the item
	 */
	private String name;
	/**
	 * The description of the item
	 */
	private String description;
	
	/**
	 * Initializes an empty item
	 */
	public Item() {
		id = 0;
		name = new String();
		description = new String();
	}
	
	/**
	 * Initializes an item with id, name and description
	 * @param id - the ID of the item
	 * @param name - the name of the item
	 * @param description - the description of the item
	 */
	public Item(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Initializes an item based on an existing item
	 * @param item - the item to reference for initializing
	 */
	public Item(Item item) {
		id = item.id;
		name = item.name;
		description = item.description;
	}
	
	/**
	 * This method is used to retrieve the id of the item
	 * @return int - the item id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * This method is used to retrieve the name of the item
	 * @return String - the item name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is used to set the name of the item
	 * @param name - the new name of the item
	 */
	public void setName(String name) {
		if(name == null) {
			this.name = new String();
			return;
		}
		
		this.name = name;
	}
	
	/**
	 * This method is used to retrieve the description of the item
	 * @return String - the item description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method is used to set a new description for the item
	 * @param description - the new description of the item
	 */
	public void setDescription(String description) {
		if(description == null) {
			this.description = new String();
			return;
		}
		
		this.description = description;
	}
}