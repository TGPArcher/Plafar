package plafar.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import plafar.domain.Bill;
import plafar.persistence.abstractions.Persistent;

public class BillRepository implements Persistent<Bill> {
	
	private Connection connection = null;
	
	public BillRepository() {
		connection = SQLiteHelper.getConnection();
		initTable(connection);
	}
	
	@Override
	public boolean addObject(Bill object) {
		if(object.getId() != 0) {
			System.out.println("Trying to add a new bill with existing id(" + object.getId() + "), ignoring the call!");
			return false;
		}
		
		String sql = "INSERT INTO bills(itemId, quantity, price) VALUES(?,?,?)";
		int status = 0;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, object.getItemId());
			pstmt.setInt(2, object.getQuantity());
			pstmt.setFloat(3, object.getPrice());
			status = pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = 0;
		}
		
		return status != 0 ? true : false;
	}

	@Override
	public Bill getObject(int objectId) {
		Bill bill = null;
		String sql = "SELECT id, itemId, quantity, price FROM bills WHERE id = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, objectId);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				bill = new Bill(result.getInt("id"), result.getInt("itemId"), result.getInt("quantity"), result.getFloat("price"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return bill;
	}

	@Override
	public boolean editObject(Bill object) {
		String sql = "UPDATE bills SET itemId = ? , quantity = ? , price = ? WHERE id = ?;";
		int status = 0;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, object.getItemId());
			pstmt.setInt(2, object.getQuantity());
			pstmt.setFloat(3, object.getPrice());
			pstmt.setInt(4, object.getId());
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
		String sql = "DELETE FROM bills WHERE id = ?;";
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
	public List<Bill> getAllObjects() {
		List<Bill> bills = new LinkedList<Bill>();
		String sql = "SELECT id, itemId, quantity, price FROM bills";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				bills.add(new Bill(
					result.getInt("id"),
					result.getInt("itemId"),
					result.getInt("quantity"),
					result.getFloat("price")));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return bills;
	}
	
	private static void initTable(Connection conn) {
		String sql = "CREATE TABLE IF NOT EXISTS bills (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
				+ " itemId integer NOT NULL,\n"
                + "	quantity integer NOT NULL,\n"
                + "	price real NOT NULL\n"
                + ");";
		
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void finalize() {
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
