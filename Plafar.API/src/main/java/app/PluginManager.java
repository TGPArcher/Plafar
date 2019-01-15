package app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import plafar.logic.abstractions.plugin.PlugableLogic;
import plafar.persistence.abstractions.plugin.PlugablePersistence;
import util.JsonUtil;

/**
 * PluginManager is a utility class responsible of application's plugin management.
 */
public final class PluginManager {
	/**
	 * Application's executing directory path
	 * Note: this is critical as the loading process is based on this path
	 */
	private static String directoryPath = null;
	/**
	 * A dictionary containing the plugin configuration the application should load
	 */
	private static Dictionary<String, String> configuration = null;
	/**
	 * The plugin containing the logic functionality of the application
	 */
	private static PlugableLogic logicPlugin = null;
	/**
	 * The plugin containing the logic functionality of the application
	 */
	private static PlugablePersistence persistencePlugin = null;
	
	/**
	 * This method is used to set application's executing directory path
	 */
	private static void setDirectoryPath() {
		if(new File("./api/Plafar_API.jar").isFile()) {
			directoryPath = System.getProperty("user.dir") + "/api";
			return;
		}
		
		directoryPath = System.getProperty("user.dir");
	}
	
	/**
	 * This method is used to retrieve application's executing directory path
	 * @return
	 */
	public static String getDirectoryPath() {
		if(directoryPath == null) {
			setDirectoryPath();
		}
		
		return directoryPath;
	}
	
	/**
	 * This method is used to load the plugin configuration from file
	 * @param fileName - the name of the configuration file
	 */
	@SuppressWarnings("unchecked")
	public static void loadConfiguration(String fileName) {
		configuration = new Hashtable<String, String>();
		
		try {
			String configPath = getDirectoryPath() + "\\config\\" + fileName;
			
			byte[] encoded = Files.readAllBytes(Paths.get(configPath));
			String configContents = new String(encoded, StandardCharsets.UTF_8);
			configuration = JsonUtil.fromJson(configContents, Hashtable.class);
		}
		catch(IOException e) {
			System.out.println("Could not load configuration!");
			configuration = null;
		}
	}
	
	/**
	 * This method is used to retrieve a configuration value based on a key
	 * @param key - the key associated with the value desired
	 * @return String - the value associated with the key specified
	 */
	public static String getConfigKeyValue(String key) {
		if(key == null) {
			return null;
		}
		
		return configuration.get(key);
	}
	
	/**
	 * This method is used to load the plugins into the application
	 */
	public static void loadPlugins() {
		String pluginDirectoryPath = getDirectoryPath() + "\\plugins";
		
		logicPlugin = new PluginLoader<PlugableLogic>(PlugableLogic.class, pluginDirectoryPath).getPlugin();
		persistencePlugin = new PluginLoader<PlugablePersistence>(PlugablePersistence.class, pluginDirectoryPath).getPlugin();
		
		initPlugins();
	}
	
	/**
	 * This method is calling plugins "initialize" method, making sure that every loaded plugin is ready to be used
	 */
	private static void initPlugins() {
		logicPlugin.intitialize(new String[]{});
		persistencePlugin.intitialize(new String[]{getDirectoryPath()});
	}
	
	/**
	 * This method is used to retrieve the currently loaded logic plugin
	 * @return PlugableLogic - a class implementing the PlugableLogic interface
	 */
	public static PlugableLogic getLogicPlugin() {
		return logicPlugin;
	}
	
	/**
	 * This method is used to retrieve the currently loaded persistence plugin
	 * @return PlugablePersistence - a class implementing the PlugablePersistence interface
	 */
	public static PlugablePersistence getPersistencePlugin() {
		return persistencePlugin;
	}
}