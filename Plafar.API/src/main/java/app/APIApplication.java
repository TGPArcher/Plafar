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
		
		// listening to commands
		listening();
		
		// stoping the program
		stop();
		System.exit(0);
	}
	
	private void listening() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("listening...");
		while(true) {
			String command = scanner.nextLine();
			
			if (analyzeCommand(command)) {
				break;
			}
		}
		
		scanner.close();
	}
	
	private boolean analyzeCommand(String command) {
		if(command.equals("port")) {
			System.out.println(_port);
		}
		if(command.equals("shutdown")) {
			System.out.println("Shutting down!");
			return true;
		}
		
		return false;
	}
}
