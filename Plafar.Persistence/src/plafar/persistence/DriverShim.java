package plafar.persistence;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * DriverShim is a extension class of Driver which does nothing new of his own.
 * The whole purpose of it is the fact that the DriverManager does not accept a driver not loaded by the same ClassLoader as it was loaded, but DriverShim is a wrapper over
 * an external loaded driver, and the fact that DriverShim is a class loaded by the main program it will share the same ClassLoader as the DriverManager thus being able to register
 * a external loaded driver with DriverManager.
 */
class DriverShim implements Driver {
	/**
	 * The jdbc driver
	 */
	private Driver driver;
	
	/**
	 * Initializing the DriverShim with a new driver
	 * @param d
	 */
	DriverShim(Driver d) {
		this.driver = d;
	}
	
	public boolean acceptsURL(String u) throws SQLException {
		return this.driver.acceptsURL(u);
	}
	
	public Connection connect(String u, Properties p) throws SQLException {
		return this.driver.connect(u, p);
	}
	
	public int getMajorVersion() {
		return this.driver.getMajorVersion();
	}
	
	public int getMinorVersion() {
		return this.driver.getMinorVersion();
	}
	
	public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
		return this.driver.getPropertyInfo(u, p);
	}
	
	public boolean jdbcCompliant() {
		return this.driver.jdbcCompliant();
	}
	
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.driver.getParentLogger();
	}
}