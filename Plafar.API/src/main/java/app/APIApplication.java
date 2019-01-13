package app;

import static spark.Spark.*;
import java.util.Scanner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.BillController;
import controllers.ShopController;

public class APIApplication {
	
	private int _port = 0;
	
	public void run() {
		// starting the server on a free port
		port(0);
		
		// loading the dependencies
		PluginManager.loadConfiguration("config.json");
		PluginManager.loadPlugins();
		
		// setting the dependency injector
		Injector injector = Guice.createInjector(new CoreModule());
		
		// creating api's controllers
		injector.getInstance(BillController.class);
		injector.getInstance(ShopController.class);
		
		// retrieving port
		awaitInitialization();
		_port = port();
	}
}
