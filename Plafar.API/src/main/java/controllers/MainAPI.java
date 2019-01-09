package controllers;

import static spark.Spark.*;

import plafar.logic.BillServices;
import plafar.logic.ShopServices;
import plafar.persistence.BillRepository;
import plafar.persistence.ItemRepository;

public class MainAPI {
	public static void main(String[] args) {
		port(5000);
		new BillController(new BillServices(new BillRepository()));
		new ShopController(new ShopServices(new ItemRepository()));
	}
}