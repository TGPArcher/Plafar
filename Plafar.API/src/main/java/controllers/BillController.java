package controllers;

import static spark.Spark.*;
import static util.JsonUtil.*;

import java.util.List;
import plafar.domain.Bill;
import plafar.logic.abstractions.BillingService;

public class BillController {

	private BillingService billService = null;
	
	public BillController(BillingService billService) {
		this.billService = billService;
		
		path("api", () -> {
			path("/bills", () -> {
				get("", (req, res) -> {
					return getBills();
				}, json());
				
				get("/:id", (req, res) -> {
					return getBill(req.params(":id"));
				}, json());
				
				post("/add", (req, res) -> {
					return addBill(req.body());
				}, json());
				
				put("/edit", (req, res) -> {
					return editBill(req.body());
				}, json());
				
				delete("/delete/:id", (req, res) -> {
					return deleteBill(req.params(":id"));
				}, json());
			});
		});
		
		after((req, res) -> {
			res.type("application/json");
		});
	}
	
	public List<Bill> getBills() {
		return billService.getAllBills();
	}
	
	public Bill getBill(String id) {
		int billId = 0;
		try {
			billId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return null;
		}
		
		return billService.getBill(billId);
	}
	
	public boolean addBill(String body) {
		Bill newBill = null;
		try {
			newBill = fromJson(body, Bill.class);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return billService.registerBill(newBill);
	}
	
	public boolean editBill(String body) {
		Bill editedBill = null;
		try {
			editedBill = fromJson(body, Bill.class);
		}
		catch(Exception e) {
			return false;
		}
		
		return billService.editBill(editedBill);
	}
	
	public boolean deleteBill(String id) {
		int billId = 0;
		try{
			billId = Integer.parseInt(id);
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		return billService.deleteBill(billId);
	}
}