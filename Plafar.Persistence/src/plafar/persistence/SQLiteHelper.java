package plafar.persistence;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SQLiteHelper is a utility class with the purpose of initializing the SQLite database.
 * Initialization includes locating and loading the JDBC driver and getting the connection to the database
 */
public final class SQLiteHelper {
	/**
	 * Application executing directory path
	 */
	private static String directoryPath = null;
	/**
	 * If the application is connection of not to a external driver.
	 * External driver means a driver which is not included in the class path but loaded at runtime
	 */
	private static boolean externalDriver = false;
	/**
	 * The name of the external jdbc driver jar
	 */
	private static String externalJarName = null;
	/**
	 * The connection to the database.
	 * Only one connection to the database is opened during the execution of the program as opening a connection is resources costly
	 */
	private static Connection conn = null;
	
	/**
	 * This constructor assures that the SQLiteHelper can't be instantiated
	 */
	private SQLiteHelper() {
		
	}
	
	/**
	 * This method is used to specify to the SQLiteHelper if it uses or not a external connection
	 * @param value - the boolean value
	 */
	public static void setExternal(boolean value) {
		externalDriver = value;
	}
	
	/**
	 * This method is used to tell the SQLiteHelper what is the name of the external driver he should be looking for
	 * @param name - the name of the external JDBC Driver jar
	 */
	public static void setExternalJarName(String name) {
		externalJarName = name;
	}
	
	/**
	 * This method is used to tell the SQLiteHelper what is the path of program's executin directory
	 * This is used to make sure that the external driver is found every time and no other path interfere with the helper.
	 * @param path
	 */
	public static void setDirectoryPath(String path) {
		directoryPath = path;
	}
	
	/**
	 * This method is used to retrieve the connection to the database
	 * @return Connection - the connection instance to the database
	 * Note: if there is no connection the helper is trying to connect to a database based on the parameters available
	 */
	public static Connection getConnection() {
		if(conn == null) {
			connect();
		}
		
		return conn;
	}
	
	/**
	 * This method is used to connect to the database file
	 */
	private static void connect() {
		try {
			String url = "jdbc:sqlite:plafar.db";
			conn = DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			if(externalDriver && externalJarName != null) {
				connectExternalDriver();
			}
			else {
				System.out.println(e);
			}
		}
	}
	
	/**
	 * This method is used when no JDBC driver suitable for the SQLite database among the loaded classes to search and connect using an external driver
	 */
	private static void connectExternalDriver() {
		try {
			URL u = new URL("jar:file:" + directoryPath + "\\plugins\\libs\\" + externalJarName + "!/");
			String classname = "org.sqlite.JDBC";
			URLClassLoader ucl = new URLClassLoader(new URL[] { u });
			Driver d = (Driver)Class.forName(classname, true, ucl).newInstance();
			DriverManager.registerDriver(new DriverShim(d));
			conn = DriverManager.getConnection("jdbc:sqlite:plafar.db");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Can't find or connect to external jdbc driver!");
		}
	}
}