package controllers;

import static spark.Spark.*;
import static util.JsonUtilSpark.*;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import plafar.domain.Bill;
import plafar.logic.abstractions.BillingService;
import response.ResponseStatement;

/**
 * This class is responsible of creating an controlling api's "\bills" routes using Spark server
 */
public class BillController {
	/**
	 * The access point to logic services of the application
	 */
	private BillingService billService = null;
	
	/**
	 * Initializes the controller by assigning the logic access point, creating and monitoring the routes
	 * @param billService - a class implementing the BillingService interface
	 */
	@Inject
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
	
	/**
	 * This method is the handler for route GET "api/bills".
	 * On success returns a response statement containing a list of all bills from the store
	 * @return ResponseStatement - the operation result
	 */
	private ResponseStatement getBills() {
		return new ResponseStatement(200, toJsonTree(billService.getAllBills()));
	}
	
	/**
	 * This method is the handler for route GET"api/bills/{id}".
	 * On success returns a response statement containing the bill with id equal to route parameter {id}
	 * @param id - the id of the item
	 * @return ResponseStatement - the operation result
	 */
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
	
	/**
	 * This method is the handler for route POST "api/bills/add".
	 * On success returns a response statement containing status code 201
	 * @param body - string in JSON format containing a StoreItem
	 * @return ResponseStatement - the operation result
	 */
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
	
	/**
	 * This method is the handler for route PUT "api/bills/edit".
	 * On success returns a response statement containing status code 200 and the item with modifications
	 * @param body - string in JSON format containing a StoreItem
	 * @return ResponseStatement - the operation result
	 */
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
	
	/**
	 * This is the handler method for route DELETE "api/bills/delete/{id}".
	 * On success returns a response statement with status code 200
	 * @param id - the id of the item to be deleted
	 * @return ResponseStatement - the operation result
	 */
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