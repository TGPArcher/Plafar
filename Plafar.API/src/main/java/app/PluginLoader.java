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

public class PluginLoader<T extends Plugable> {
	private final Class<T> type;
	private T plugin = null;
	
	public PluginLoader(Class<T> type, String directory) {
		this.type = type;
		searchForPlugins(directory);
	}
	
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
	
	public T getPlugin() {
		return plugin;
	}
}