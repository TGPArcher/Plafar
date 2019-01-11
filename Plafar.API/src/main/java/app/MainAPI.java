package app;

import static spark.Spark.*;
import com.google.inject.*;

import controllers.BillController;
import controllers.ShopController;

public class MainAPI {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// setting api's port
		port(5000);
		
		// loading the dependencies
		PluginManager.loadConfiguration("config.json");
		PluginManager.loadPlugins();
		
		// setting the dependency injector
		Injector injector = Guice.createInjector(new CoreModule());
		
		// creating api's controllers
		BillController billController = injector.getInstance(BillController.class);
		ShopController shopController = injector.getInstance(ShopController.class);
	}
}