package controller;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import app.APIHandler;
import plafar.domain.Bill;
import plafar.domain.StoreItem;
import response.ResponseStatement;
import util.JsonUtil;
import views.*;

/**
 * BillController is a controller class responsible for managing the view classes which operate with bills.
 * Makes requests to the api. Gets data and sends it to views. Listens to views inputs an act accordingly. 
 */
public final class BillController {
	/**
	 * This method is used to set the BillListPage as the application's active page
	 */
	public static void setBillPage() {
		List<Bill> bills = getBills();
		
		MenuPage menu = new MenuPage();
		menu.setBillBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new BillListPage(bills));
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to add a new bill
	 * @param bill - the bill to be added
	 */
	public static void addBill(Bill bill) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post(APIHandler.getApiRoute() + "/bills/add")
					.header("accept", "application/json")
					.body(JsonUtil.toJson(bill))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 201) {
				throw new Exception("addBill request returned a response status of " + String.valueOf(response.getStatus()));
			}
		}
		catch(UnirestException e) {
			System.out.println("BillController/addBill/UnirestException");
			System.out.println(e.getMessage());
			setDeleteBillPage(bill);
		}
		catch(Exception e) {
			System.out.println("BillController/addBill/Exception");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * This method is used to set DeleteBillPage as application's active page
	 * @param bill - the bill to be deleted
	 */
	public static void setDeleteBillPage(Bill bill) {
		if(bill == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new DeleteBillPage(bill));
		PageHandler.show();
	}
	
	/**
	 * This method is used to make a request at the api to delete a existing bill.
	 * @param bill - the bill to be deleted
	 */
	public static void deleteBill(Bill bill) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.delete(APIHandler.getApiRoute() + "/bills/delete/{billId}")
					.header("accept", "application/json")
					.routeParam("billId", String.valueOf(bill.getId()))
					.asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			if(response.getStatus() != 200) {
				setDeleteBillPage(bill);
			}
		}
		catch(UnirestException e) {
			System.out.println("BillController/deleteBill/UnirestException");
			System.out.println(e.getMessage());
			setDeleteBillPage(bill);
		}
		catch(Exception e) {
			System.out.println("BillController/deleteBill/Exception");
			System.out.println(e.getMessage());
		}
		
		setBillPage();
	}
	
	/**
	 * This method is used to get the item and its data from a bill
	 * @param itemId - the id of the item
	 * @return StoreItem - the item of the bill
	 */
	public static StoreItem getBillItem(int itemId) {
		return ItemController.getItem(itemId);
	}
	
	/**
	 * This method is used to make a request at the api to retrieve all the bills from the store
	 * @return List< Bill > - a list containing all the bills from the store
	 */
	private static List<Bill> getBills(){
		List<Bill> bills = new LinkedList<Bill>();
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(APIHandler.getApiRoute() + "/bills")
					  .header("accept", "application/json")
					  .asJson();
			
			ResponseStatement response = JsonUtil.fromJson(jsonResponse.getBody().toString(), ResponseStatement.class);
			String responseData = JsonUtil.toJson(response.getData());
			
			Type listType = new TypeToken<List<Bill>>() {}.getType();
			bills = JsonUtil.fromJson(responseData, listType);
		}
		catch(UnirestException e) {
			System.out.println("BillController/getBills/UnirestException");
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println("BillController/getBills/Exception");
			System.out.println(e.getMessage());
		}
		
		return bills;
	}
}