package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import app.APIHandler;
import plafar.domain.StoreItem;
import response.ResponseStatement;
import util.JsonUtil;
import views.*;

/**
 * ItemController is a controller class responsible for managing the view classes which operate with items.
 * Makes requests to the api. Gets data and sends it to views. Listens to views inputs an act accordingly. 
 */
public final class ItemController {
	/**
	 * This method is used to set AddItemPage as application's active page
	 */
	public static void setAddItemPage() {
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new ItemPage());
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to retrieve a specific item from the store
	 * @param id - the id of the item
	 * @return StoreItem - the item
	 */
	public static StoreItem getItem(int id) {
		StoreItem item = null;
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(APIHandler.getApiRoute() + "/items/{id}")
					.header("accept", "application/json")
					.routeParam("id", String.valueOf(id))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 200) {
				return item;
			}
			
			String responseData = JsonUtil.toJson(response.getData());
			item = JsonUtil.fromJson(responseData, StoreItem.class);
		}
		catch(UnirestException e) {
			System.out.println("ItemController/getItem/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("ItemController/getItem/Exception");
			System.out.println(e.getMessage());
		}
		
		return item;
	}
	
	/**
	 * This method is used to make a request at the api to add a new item to the store
	 * @param item - the new item to be added
	 */
	public static void addItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post(APIHandler.getApiRoute() + "/items/add")
					.header("accept", "application/json")
					.body(JsonUtil.toJson(item))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 201) {
				// should be an error message here, but since the page does not support error messages for now there is not
				return;
			}
		}
		catch(UnirestException e) {
			System.out.println("ItemController/addItem/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("ItemController/addItem/Exception");
			System.out.println(e.getMessage());
		}
		
		back();
	}
	
	/**
	 * This method is used to set EditItemPage as application's active page
	 * @param item - the item to be edited on the page
	 */
	public static void setEditItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new ItemPage(item));
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to edit an existent item from the store
	 * @param item - the edited item
	 */
	public static void editItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put(APIHandler.getApiRoute() + "/items/edit")
					.header("accept", "application/json")
					.body(JsonUtil.toJson(item))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 200) {
				// should be an error message here, but since the page does not support error messages for now there is not
				return;
			}
			
			String responseData = JsonUtil.toJson(response.getData());
			StoreItem editedItem = JsonUtil.fromJson(responseData, StoreItem.class);
			if(editedItem.getId() == item.getId()) {
				// should be an "edited" confirm message but since the page does not support this too there is not
			}
		}
		catch(UnirestException e) {
			System.out.println("ItemController/editItem/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("ItemController/editItem/Exception");
			System.out.println(e.getMessage());
		}
		
		back();
	}
	
	/**
	 * This method is used to set DeleteItemPage as application's active page
	 * @param item - item to be deleted
	 */
	public static void setDeleteItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new DeleteItemPage(item));
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to delete an existing item from the store
	 * @param item - the item to be deleted
	 */
	public static void deleteItem(StoreItem item) {
		if(item == null) {
			return;
		}
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.delete(APIHandler.getApiRoute() + "/items/delete/{id}")
					.header("accept", "application/json")
					.routeParam("id", String.valueOf(item.getId()))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 200) {
				// again there should be an error logger here
				return;
			}
			
			// and here should be an "deleted" confirmation, oh there are still critical UX things to implement
		}
		catch(UnirestException e) {
			System.out.println("ItemController/editItem/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("ItemController/editItem/Exception");
			System.out.println(e.getMessage());
		}
		
		back();
	}
	
	/**
	 * This method is used to go to the last page.
	 * Which in the context if ItemController means setting the StoreListPage as application's active page
	 */
	public static void back() {
		StoreController.setStorePage();
	}
}