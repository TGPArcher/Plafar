package plafar.persistence;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteHelper {
	private static String directoryPath = null;
	private static boolean externalDriver = false;
	private static String externalJarName = null;
	private static Connection conn = null;
	
	private SQLiteHelper() {
		
	}
	
	public static void setExternal(boolean value) {
		externalDriver = value;
	}
	
	public static void setExternalJarName(String name) {
		externalJarName = name;
	}
	
	public static void setDirectoryPath(String path) {
		directoryPath = path;
	}
	
	public static Connection getConnection() {
		if(conn == null) {
			connect();
		}
		
		return conn;
	}
	
	private static void connect() {
		try {
			String url = "jdbc:sqlite:plafar.db";
			conn = DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			if(externalDriver && externalJarName != null) {
				connectExternalDriver();
			}
		}
	}
	
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