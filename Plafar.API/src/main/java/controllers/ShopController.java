package controllers;

import static spark.Spark.*;
import static util.JsonUtilSpark.*;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import plafar.domain.*;
import plafar.logic.abstractions.ShopingServices;
import response.ResponseStatement;

public class ShopController {
	private ShopingServices shopService = null;
	
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
	
	private ResponseStatement getAllItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getAllItems()));
	}
	
	private ResponseStatement getAvailableItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getAvailableItems()));
	}
	
	private ResponseStatement getUnavailableItems() {
		return new ResponseStatement(200, toJsonTree(shopService.getUnavailableItems()));
	}
	
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
