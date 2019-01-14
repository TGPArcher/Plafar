package app;

import java.io.InputStream;
import java.io.OutputStream;

public final class APIHandler {
	private APIHandler() {
		
	}
	private static Process process = null;
	private static InputStream in = null;
	private static OutputStream out = null;
	private static int port = 0;
	
	public static boolean isReady() {
		return port == 0 ? false : true;
	}
	
	public static int getPort() {
		return port;
	}
	
	public static String getApiRoute() {
		return "http://localhost:" + getPort() + "/api";
	}
	
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
	
	public static void shutdown() {
		exec("shutdown");
	}
	
	private static void retrievePort() {
		try {
			port = Integer.parseInt(exec("port"));
		}
		catch(Exception e) {
			// we are not interested in logging errors here
		}
	}
	
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