package app;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * APIHandler is a utility class which makes the communication with the core of the application, the api.
 * It starts the api and communicates with it via commands.
 */
public final class APIHandler {
	private APIHandler() {
		
	}
	/**
	 * This represents api's process
	 */
	private static Process process = null;
	/**
	 * This is the OutputStream of the api and the InputStream for the presentation app.
	 * From this stream the app reads api's responses to commands.
	 */
	private static InputStream in = null;
	/**
	 * This is the InputStream of the api and OutputStream for the presentation app.
	 * From this stream the api takes app's commands and responds accordingly
	 */
	private static OutputStream out = null;
	/**
	 * The port on which the api listens to requests
	 */
	private static int port = 0;
	
	/**
	 * If the api is ready to receive requests
	 * @return boolean - true - the api is initialized, false - the api is not started or not initialized
	 */
	public static boolean isReady() {
		return port == 0 ? false : true;
	}
	
	/**
	 * This method is used to retrieve the port on which the api listens
	 * @return int - api's port
	 */
	public static int getPort() {
		return port;
	}
	
	/**
	 * This method is used to get the full route of the api
	 * @return String - api route
	 */
	public static String getApiRoute() {
		return "http://localhost:" + getPort() + "/api";
	}
	
	/**
	 * This method is used to start the api and wait for it to fully initialize
	 */
	public static void start() {
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", "./api/Plafar_API.jar");
			pb.redirectErrorStream(true);
			process = pb.start();
			
			in = process.getInputStream();
			out = process.getOutputStream();
			
			while(!exec("ready").equals("true")) {
				
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Retrieving port");
		retrievePort();
	}
	
	/**
	 * This method is used to execute the shutdown command on the api
	 */
	public static void shutdown() {
		exec("shutdown");
	}
	
	/**
	 * This method is used to execute the port command on the api to get its port
	 */
	private static void retrievePort() {
		try {
			port = Integer.parseInt(exec("port"));
		}
		catch(Exception e) {
			// we are not interested in logging errors here
		}
	}
	
	/**
	 * This method is used to execute a command on the api
	 * @param command - the command being executed
	 * @return String - api's response to the command
	 */
	private static String exec(String command) {
		String result = "";
		
		byte[] buffer = new byte[500];
		boolean stop = false;
	    while (process.isAlive()) {
	    	try {
	    		int no = in.available();
	    		if (no > 0) {
	    			in.read(buffer, 0, Math.min(no, buffer.length));
	    			String raw = new String(buffer, 0, Math.min(no, buffer.length));
	    			System.out.println(raw);
	    			
	    			if(stop) {
	    				result = raw;
	    				break;
	    			}
			    }
	    		else {
	    			out.write(command.getBytes(), 0, command.length());
	    			out.flush();
	    			stop = true;
	    		}
	    	}
	    	catch(Exception e) {
	    		System.out.println(e);
	    	}
	    }
		
		return result;
	}
}