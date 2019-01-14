package app;

import static spark.Spark.*;
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
		while(true) {
			try {
				int n = System.in.available();
				if(n > 0) {
					byte[] buffer = new byte[100];
					System.in.read(buffer, 0, Math.min(n, buffer.length));
					
					String command = new String(buffer, 0, n);
					if (analyzeCommand(command)) {
						break;
					}
				}
			}
			catch(Exception e) {
				// We have nothing to do with an exception here :(
			}
		}
	}
	
	private boolean analyzeCommand(String command) {
		if(command.equals("port")) {
			System.out.println(_port);
		}
		if(command.equals("shutdown")) {
			System.out.println("Shutting down!");
			return true;
		}
		if(command.equals("ready")) {
			System.out.println("true");
		}
		
		return false;
	}
}