package controller;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import plafar.domain.Bill;
import plafar.domain.StoreItem;
import util.JsonUtil;
import response.ResponseStatement;
import views.*;

public final class StoreController {
	public static void setStorePage() {
		List<StoreItem> items = getItemList();
		
		MenuPage menu = new MenuPage();
		menu.setStoreBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new StoreListPage(items));
		PageHandler.show();
	}
	
	public static void sellItemPage(StoreItem item) {
		if(item == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new SellPage(item));
		PageHandler.show();
	}
	
	public static void sellItem(StoreItem item, int quantity) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:5000/api/items/sell/{quantity}")
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
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:5000/api/items" + itemPath)
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