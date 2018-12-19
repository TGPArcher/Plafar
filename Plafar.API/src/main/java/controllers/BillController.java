package controllers;

import static spark.Spark.*;
import static util.JsonUtil.*;

import com.google.gson.JsonSyntaxException;
import plafar.domain.Bill;
import plafar.logic.abstractions.BillingService;
import response.ResponseStatement;

public class BillController {

	private BillingService billService = null;
	
	public BillController(BillingService billService) {
		this.billService = billService;
		
		path("api", () -> {
			path("/bills", () -> {
				get("", (req, res) -> {
					ResponseStatement result = getBills();
					res.status(result.getStatus());
					return result;
				}, json());
				
				get("/:id", (req, res) -> {
					ResponseStatement result = getBill(req.params(":id"));
					res.status(result.getStatus());
					return result;
				}, json());
				
				post("/add", (req, res) -> {
					ResponseStatement result = addBill(req.body());
					res.status(result.getStatus());
					return result;
				}, json());
				
				put("/edit", (req, res) -> {
					ResponseStatement result = editBill(req.body());
					res.status(result.getStatus());
					return result;
				}, json());
				
				delete("/delete/:id", (req, res) -> {
					ResponseStatement result = deleteBill(req.params(":id"));
					res.status(result.getStatus());
					return result;
				}, json());
			});
		});
		
		after((req, res) -> {
			res.type("application/json");
		});
	}
	
	private ResponseStatement getBills() {
		return new ResponseStatement(200, toJsonTree(billService.getAllBills()));
	}
	
	private ResponseStatement getBill(String id) {
		int billId = 0;
		try {
			billId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return new ResponseStatement(400, "Wrong id format");
		}
		
		Bill bill = billService.getBill(billId);
		if(bill != null) {
			return new ResponseStatement(200, toJsonTree(bill));
		}
		
		return new ResponseStatement(404, "Could not find bill");
	}
	
	private ResponseStatement addBill(String body) {
		Bill newBill = null;
		try {
			newBill = fromJson(body, Bill.class);
		}
		catch(JsonSyntaxException e) {
			return new ResponseStatement(400, "Could not bind json to object");
		}
		
		boolean result = billService.registerBill(newBill);
		
		if(result) {
			return new ResponseStatement(201);
		}
		
		return new ResponseStatement(400, "Trying to add an existent bill");
	}
	
	private ResponseStatement editBill(String body) {
		Bill editedBill = null;
		try {
			editedBill = fromJson(body, Bill.class);
		}
		catch(JsonSyntaxException e) {
			return new ResponseStatement(400, "Could not bind json to object");
		}
		
		boolean result = billService.editBill(editedBill);
		if(result) {
			return new ResponseStatement(200, toJsonTree(billService.getBill(editedBill.getId())));
		}
		
		return new ResponseStatement(400, "Could not modify bill");
	}
	
	private ResponseStatement deleteBill(String id) {
		int billId = 0;
		try{
			billId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return new ResponseStatement(400, "Wrong id format");
		}
		
		boolean result = billService.deleteBill(billId);
		if(result) {
			return new ResponseStatement(200);
		}
		
		return new ResponseStatement(400, "Could not remove bill");
	}
}