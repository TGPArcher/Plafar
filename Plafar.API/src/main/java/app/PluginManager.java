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

public final class PluginManager {
	private static String directoryPath = null;
	private static Dictionary<String, String> configuration = null;
	private static PlugableLogic logicPlugin = null;
	private static PlugablePersistence persistencePlugin = null;
	
	private static void setDirectoryPath() {
		if(new File("./api/Plafar_API.jar").isFile()) {
			directoryPath = System.getProperty("user.dir") + "/api";
			return;
		}
		
		directoryPath = System.getProperty("user.dir");
	}
	
	public static String getDirectoryPath() {
		if(directoryPath == null) {
			setDirectoryPath();
		}
		
		return directoryPath;
	}
	
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
	
	public static String getConfigKeyValue(String key) {
		if(key == null) {
			return null;
		}
		
		return configuration.get(key);
	}
	
	public static void loadPlugins() {
		String pluginDirectoryPath = getDirectoryPath() + "\\plugins";
		
		logicPlugin = new PluginLoader<PlugableLogic>(PlugableLogic.class, pluginDirectoryPath).getPlugin();
		persistencePlugin = new PluginLoader<PlugablePersistence>(PlugablePersistence.class, pluginDirectoryPath).getPlugin();
		
		initPlugins();
	}
	
	private static void initPlugins() {
		logicPlugin.intitialize(new String[]{});
		persistencePlugin.intitialize(new String[]{getDirectoryPath()});
	}
	
	public static PlugableLogic getLogicPlugin() {
		return logicPlugin;
	}
	
	public static PlugablePersistence getPersistencePlugin() {
		return persistencePlugin;
	}
}