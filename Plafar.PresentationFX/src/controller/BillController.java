package controller;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import plafar.domain.Bill;
import plafar.domain.StoreItem;
import response.ResponseStatement;
import util.JsonUtil;
import views.*;

public final class BillController {
	
	public static void setBillPage() {
		List<Bill> bills = getBills();
		
		MenuPage menu = new MenuPage();
		menu.setBillBtnDisabled(true);
		
		PageHandler.setMenu(menu);
		PageHandler.setActivePage(new BillListPage(bills));
		PageHandler.show();
	}
	
	public static void addBill(Bill bill) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:5000/api/bills/add")
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
	
	public static void setDeleteBillPage(Bill bill) {
		if(bill == null) {
			return;
		}
		
		PageHandler.setMenu(new MenuPage());
		PageHandler.setActivePage(new DeleteBillPage(bill));
		PageHandler.show();
	}
	
	public static void deleteBill(Bill bill) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.delete("http://localhost:5000/api/bills/delete/{billId}")
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
	
	public static StoreItem getBillItem(int itemId) {
		return ItemController.getItem(itemId);
	}
	
	private static List<Bill> getBills(){
		List<Bill> bills = new LinkedList<Bill>();
		
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:5000/api/bills")
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