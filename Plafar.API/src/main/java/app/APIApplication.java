package app;

import static spark.Spark.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.BillController;
import controllers.ShopController;

/**
 * APIApplication is the backbone of the API.
 * The class is responsible for putting application's pieces together like:
 * Finding a free port, loading the plugins, setting the Injector, creating the controllers and listening to commands
 */
public class APIApplication {
	/**
	 * This is the port on which the API will listen
	 */
	private int _port = 0;
	
	/**
	 * The "main" method of the application, here are the modules are initialized and called sequentially
	 */
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
	
	/**
	 * This method is responsible of waiting for input from other processes in the System.in
	 * identifying the input and passing it for analyzing.
	 * Note: this is a loop which is stopped only by the "shutdown" command
	 */
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
	
	/**
	 * This method is responsible for command analyzing and executing command's associated functionality/response
	 * @param command - the command being analyzed
	 * @return boolean - whether or not the program should stop
	 */
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