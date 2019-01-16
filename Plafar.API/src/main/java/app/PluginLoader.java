package app;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import interfaces.Plugable;

/**
 * PluginLoader is responsible for searching, identifying and loading of a plugin
 * @param <T> - any extension of the Plugable interface can be loaded as a plugin
 */
public class PluginLoader<T extends Plugable> {
	/**
	 * The class type of the desired plugin
	 */
	private final Class<T> type;
	/**
	 * The loaded plugin
	 */
	private T plugin = null;
	
	/**
	 * Initializes the loader
	 * @param type - the class type of the desired plugin
	 * @param directory - directory path where to look for that plugin
	 */
	public PluginLoader(Class<T> type, String directory) {
		this.type = type;
		searchForPlugins(directory);
	}
	
	/**
	 * This method is used to filter all the files from the directory and look at every jar if it is a plugin meeting all the requirements
	 * @param directory - directory where it looks for jars
	 */
	private void searchForPlugins(String directory) {
		File pluginsDir = new File(directory);
		FilenameFilter fileFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".jar")) {
					return true;
				}
				
				return false;
			}
		};
		
		for(File file : pluginsDir.listFiles(fileFilter)) {
			plugin = loadPlugin(file);
			if(plugin != null) {
				return;
			}
		}
		
		if(plugin == null) {
			System.err.println("ERROR: Could not find or load plugin!");
		}
	}
	
	/**
	 * This method is used to load a jar and verify its contents if it is a plugin and fits all criteria required to load and initialize it further 
	 * @param jar - file path to tha jar
	 * @return If the jar from the path is the plugin which fits the configuration then the plugin is returned.
	 * If not it returns null
	 */
	@SuppressWarnings({ "unchecked" })
	private T loadPlugin(File jar) {
		T _plugin = null;
		try {
			URI jarUri = jar.toURI();
			ClassLoader loader = URLClassLoader.newInstance(new URL[] {jarUri.toURL()}, getClass().getClassLoader());
			List<String> classesInJar = getJarClasses(jar.getAbsolutePath());
			
			for(String className : classesInJar) {
				Class<?> currentClass = Class.forName(className, true, loader);
				
				if(type.isAssignableFrom(currentClass)) {
					T currentPlugin = (T) currentClass.getConstructor().newInstance();
					
					// now search the configuration to match the condition
					if(currentPlugin.getName().equals(PluginManager.getConfigKeyValue(currentPlugin.getKey()))) {
						_plugin = currentPlugin;
						break;
					}
				}
			}
		}
		catch(Exception e) {
			_plugin = null;
		}
		
		return _plugin;
	}
	
	/**
	 * This method is used to list all classes inside a jar
	 * @param jarPath - path to the jar file
	 * @return List<String> - on success returns a list of classes inside the jar, on failure returns an empty list
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private List<String> getJarClasses(String jarPath) throws FileNotFoundException, IOException {
		List<String> classNames = new ArrayList<String>();
		
		ZipInputStream zip = new ZipInputStream(new FileInputStream(jarPath));
		for(ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
				String className = entry.getName().replace("/", ".");
				classNames.add(className.substring(0, className.length() - ".class".length()));
			}
		}
		if(zip != null) {
			zip.close();
		}
		
		return classNames;
	}
	
	/**
	 * This method is used to retrieve the loaded plugin
	 * @return T - the loaded plugin, null if no plugin is loaded
	 */
	public T getPlugin() {
		return plugin;
	}
}