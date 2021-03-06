package controller;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import app.APIHandler;
import plafar.domain.Bill;
import plafar.domain.StoreItem;
import util.JsonUtil;
import response.ResponseStatement;
import views.*;

/**
 * StoreController is a controller class responsible for managing the view classes which operate with item and item related things.
 * Makes requests to the api. Gets data and sends it to views. Listens to views inputs an act accordingly. 
 */
public final class StoreController {
	/**
	 * This method is used to set StoreListPage as application's active page
	 */
	public static void setStorePage() {
		List<StoreItem> items = getItemList();
		
		MenuPage menu = new MenuPage();
		menu.setStoreBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new StoreListPage(items));
		PageHandler.show();
	}
	
	/**
	 * This method is used to set SellItemPage as application's active page
	 * @param item - the item being sold
	 */
	public static void sellItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new SellPage(item));
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to sell a item from the store
	 * @param item - the item being sold
	 * @param quantity - the number of items being sold
	 */
	public static void sellItem(StoreItem item, int quantity) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put(APIHandler.getApiRoute() + "/items/sell/{quantity}")
					.header("accept", "application/json")
					.routeParam("quantity", String.valueOf(quantity))
					.body(JsonUtil.toJson(item))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 200) {
				sellItemPage(item);
				return;
			}
			
			String responseData = JsonUtil.toJson(response.getData());
			Bill bill = JsonUtil.fromJson(responseData, Bill.class);
			BillController.addBill(bill);
		}
		catch (UnirestException e) {
			System.out.println("StoreController/sellItem/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("StoreController/sellItem/Exception");
			System.out.println(e.getMessage());
		}
		setStorePage();
	}
	
	/**
	 * This method is used to make a request at the api to retrieve the number of items from the store depending on what checkboxes are selected
	 * @return List< StoreItem > - a list containing items from the store
	 */
	private static List<StoreItem> getItemList(){
		List<StoreItem> items = new LinkedList<StoreItem>();
		String itemPath = "";
		if(StoreListPage.prefAvailable && !StoreListPage.prefUnavailable) {
			itemPath = "/available";
		}
		else if(!StoreListPage.prefAvailable && StoreListPage.prefUnavailable) {
			itemPath = "/unavailable";
		}
		else if(!StoreListPage.prefAvailable && !StoreListPage.prefUnavailable) {
			return items;
		}
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(APIHandler.getApiRoute() + "/items" + itemPath)
					  .header("accept", "application/json")
					  .asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			String responseData = JsonUtil.toJson(response.getData());
			
			Type listType = new TypeToken<List<StoreItem>>() {}.getType();
			items = JsonUtil.fromJson(responseData, listType);
		} 
		catch (UnirestException e) {
			System.out.println("StoreController/getItemList/UnirestException");
		}
		catch(ClassCastException e) {
			System.out.println("StoreController/getItemList/ClassCastException");
		}
		catch(Exception e) {
			System.out.println("StoreController/getItemList/Exception");
		}
		
		return items;
	}
}