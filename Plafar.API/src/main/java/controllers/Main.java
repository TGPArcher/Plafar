package controllers;

import static spark.Spark.*;

import plafar.logic.BillServices;
import plafar.persistence.BillRepository;

public class Main {
	public static void main(String[] args) {
		port(5000);
		
		new BillController(new BillServices(new BillRepository()));
	}
}