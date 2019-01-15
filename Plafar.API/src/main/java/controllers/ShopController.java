package controllers;

import static spark.Spark.*;
import static util.JsonUtilSpark.*;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import plafar.domain.*;
import plafar.logic.abstractions.ShopingServices;
import response.ResponseStatement;

/**
 * This class is responsible of creating an controlling api's "\items" routes using Spark server
 */
public class ShopController {
	/**
	 * The access point to logic services of the application
	 */
	private ShopingServices shopService = null;
	
	/**
	 * Initializes the controller by assigning the logic access point, creating and monitoring the routes
	 * @param shopService - a class implementing the ShopingServices interface
	 */
	@Inject
	public ShopController(ShopingServices shopService) {
		this.shopService = shopService;
		
		path("api", () -> {
			path("/items", () -> {
				get("", (req, res) -> {
					ResponseStatement result = getAllItems();
					res.status(result.getStatus());
					return result;
				}, json());
				
				get("/available", (req, res) -> {
					ResponseStatement result = getAvailableItems();
					res.status(result.getStatus());
					return result;
				}, json());
				
				get("/unavailable", (req, res) -> {
					ResponseStatement result = getUnavailableItems();
					res.status(result.getStatus());
					return result;
				}, json());
				
				get("/:id", (req, res) -> {
					ResponseStatement result = getItem(req.params(":id"));
					res.status(result.getStatus());
					return result;
				}, json());
				
				put("/sell/:quantity", (req, res) -> {
					ResponseStatement result = sellItem(req.body(), req.params(":quantity"));
					res.status(result.getStatus());
					return result;
				}, json());
				
				post("/add", (req, res) -> {
					ResponseStatement result = registerItem(req.body());
					res.status(result.getStatus());
					return result;
				}, json());
				
				put("/edit", (req, res) -> {
					ResponseStatement result = editItem(req.body());
					res.status(result.getStatus());
					return result;
				}, json());
				
				delete("/delete/:id", (req, res) -> {
					ResponseStatement result = deleteItem(req.params(":id"));
					res.status(result.getStatus());
					return result;
				}, json());
			});
		});
	}
	
	/**
	 * This method is the handler for the route GET "api/items".
	 * On success returns all items from the store
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement getAllItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getAllItems()));
	}
	
	/**
	 * This method is the handler for the route GET "api/items/available".
	 * On success returns all in stock items from the store
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement getAvailableItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getAvailableItems()));
	}
	
	/**
	 * This method is the handler for the route GET "api/items/unavailable".
	 * On success returns all out of stock items from the store.
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement getUnavailableItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getUnavailableItems()));
	}
	
	/**
	 * This method is the handler for the route GET "api/items/{id}".
	 * On success returns the item with the id equal of the route parameter {id}
	 * @param id - the id of the item
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement getItem(String id) {
		int itemId = 0;
		try {
			itemId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return new ResponseStatement(400, "Wrong id format");
		}
		
		StoreItem item = shopService.getItem(itemId);
		if(item == null) {
			return new ResponseStatement(404, "There is no item with such id");
		}
		
		return new ResponseStatement(200, toJsonTree(item));
	}
	
	/**
	 * This method is the handler for the route PUT "api/items/sell/{quantity}.
	 * On success return a new Bill containing sale information.
	 * @param body - string in JSON format containing a StoreItem
	 * @param quantity - the number of sold items
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement sellItem(String body, String quantity) {
		int quant = 0;
		StoreItem item = null;
		try {
			quant = Integer.parseInt(quantity);
			item = fromJson(body, StoreItem.class);
		}
		catch(NumberFormatException e) {
			return new ResponseStatement(400, "Wrong id format");
		}
		catch(JsonSyntaxException ex) {
			return new ResponseStatement(400, "Could not bind json to object");
		}
		
		Bill bill = shopService.sellItem(item, quant);
		if(bill == null) {
			return new ResponseStatement(400, "Cannot sell! Invalid parameters");
		}
		
		return new ResponseStatement(200, toJsonTree(bill));
	}
	
	/**
	 * This is the handler method for the route POST "api/items/add".
	 * On success returns just the status of the operation (success status[201 - Created])
	 * @param body - string in JSON format containing a StoreItem
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement registerItem(String body) {
		StoreItem item = null;
		try {
			item = fromJson(body, StoreItem.class);
		}
		catch(JsonSyntaxException e) {
			return new ResponseStatement(400, "Could not bind json to object");
		}
		
		boolean result = shopService.registerItem(item);
		if(result) {
			return new ResponseStatement(201);
		}
		
		return new ResponseStatement(400, "Could not register item");
	}
	
	/**
	 * This method is the handler for the route PUT "api/items/edit".
	 * On success returns the edited item with changes and status code 200
	 * @param body - string in JSON format containing a StoreItem
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement editItem(String body) {
		StoreItem item = null;
		try {
			item = fromJson(body, StoreItem.class);
		}
		catch(JsonSyntaxException e) {
			return new ResponseStatement(400, "Could not bind json to object");
		}
		
		boolean result = shopService.editItem(item);
		if(result) {
			return new ResponseStatement(200, toJsonTree(shopService.getItem(item.getId())));
		}
		
		return new ResponseStatement(400, "Could not edit item");
	}
	
	/**
	 * This method is the handler for the route DELETE "api/items/delete/{id}".
	 * On success this method returns a response with status code 200
	 * @param id - the id of the item to be deleted
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement deleteItem(String id) {
		int itemId = 0;
		try {
			itemId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return new ResponseStatement(400, "Wrong id format");
		}
		
		boolean result = shopService.deleteItem(itemId);
		if(result) {
			return new ResponseStatement(200);
		}
		
		return new ResponseStatement(400, "Could not delete item");
	}
}