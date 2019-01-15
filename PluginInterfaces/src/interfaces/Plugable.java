package interfaces;

/**
 * This interface is used to retrieve data about a Plugin.
 */
public interface Plugable {
	/**
	 * This method returns the name of the plugin implementing the interface
	 * @return String This returns the name of the plugin
	 */
	String getName();
	/**
	 * This method is used to retrieve a key which tells where this plugin is intended to connect to
	 * @return String This returns the key
	 */
	String getKey();
	/**
	 * This method is used to retrieve the version of the plugin
	 * @return String This returns the version of the plugin
	 */
	String getVersion();
	/**
	 * This method is used to initialize plugin's contents after the plugin was loaded
	 * @param args This contains arguments passed to the plugin during initialization process
	 */
	void intitialize(String args[]);
}