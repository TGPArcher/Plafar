package plafar.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import plafar.domain.StoreItem;
import plafar.persistence.abastractions.Persistent;

public class ItemRepository implements Persistent<StoreItem> {
	
	Connection connection = null;
	
	public ItemRepository() {
		connection = SQLiteHelper.getConnection();
		initTable(connection);
	}
	
	@Override
	public boolean addObject(StoreItem object) {
		if(object.getId() != 0) {
			System.out.println("Trying to add a new item with existing id(" + object.getId() + "), ignoring the call!");
			return false;
		}
		
		String sql = "INSERT INTO items(name, description, price, quantity) VALUES(?,?,?,?)";
		int status = 0;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, object.getName());
			pstmt.setString(2, object.getDescription());
			pstmt.setFloat(3, object.getPrice());
			pstmt.setInt(4, object.getCuantity());
			status = pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = 0;
		}
		
		return status != 0 ? true : false;
	}

	@Override
	public StoreItem getObject(int objectId) {
		StoreItem item = null;
		String sql = "SELECT id, name, description, price, quantity FROM items WHERE id = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, objectId);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				item = new StoreItem(result.getInt("id"), result.getString("name"), result.getString("description"), result.getFloat("price"), result.getInt("quantity"));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	public boolean editObject(StoreItem object) {
		String sql = "UPDATE items SET name = ? , description = ? , price = ? , quantity = ? WHERE id = ?;";
		int status = 0;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, object.getName());
			pstmt.setString(2, object.getDescription());
			pstmt.setFloat(3, object.getPrice());
			pstmt.setInt(4, object.getCuantity());
			pstmt.setInt(5, object.getId());
			status = pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = 0;
		}
		
		return status != 0 ? true : false;
	}

	@Override
	public boolean deleteObject(int objectId) {
		String sql = "DELETE from items WHERE id = ?;";
		int status = 0;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, objectId);
			status = pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = 0;
		}
		
		return status != 0 ? true : false;
	}

	@Override
	public List<StoreItem> getAllObjects() {
		List<StoreItem> items = new LinkedList<StoreItem>();
		String sql = "SELECT id, name, description, price, quantity FROM items";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()) {
				StoreItem item = new StoreItem(result.getInt("id"), result.getString("name"), result.getString("description"), result.getFloat("price"), result.getInt("quantity"));
				items.add(item);
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return items;
	}
	
	private static void initTable(Connection connection) {
		String sql = "CREATE TABLE IF NOT EXISTS items (\n"
				+ " id integer PRIMARY KEY AUTOINCREMENT,\n"
				+ " name text(50) NOT NULL,\n"
				+ " description text(300),\n"
				+ " price real NOT NULL,\n"
				+ " quantity integer NOT NULL\n"
				+ ");";
		
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void finalize(){
		try {
			if(connection != null) {
				connection.close();
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
