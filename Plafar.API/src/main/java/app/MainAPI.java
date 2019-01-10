package app;

import static spark.Spark.*;
import com.google.inject.*;

import controllers.BillController;
import controllers.ShopController;

public class MainAPI {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		port(5000);
		
		Injector injector = Guice.createInjector(new CoreModule());
		
		BillController billController = injector.getInstance(BillController.class);
		ShopController shopController = injector.getInstance(ShopController.class);
	}
}