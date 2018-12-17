package plafar.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteHelper {
	
	private static Connection conn = null;
	
	private SQLiteHelper() {
		
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
			System.out.println(e.getMessage());
		}
	}
}
