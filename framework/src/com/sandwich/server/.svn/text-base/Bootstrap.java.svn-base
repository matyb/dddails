package com.sandwich.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sandwich.shared.ApplicationSettings;
import com.sandwich.shared.StrippedName;

public abstract class Bootstrap {
	
	Map<String,Class<?>> viewClasses;
	Map<String,Class<?>> controllerClasses;
	
	ApplicationSettings applicationSettings = 
		new DefaultApplicationSettings(getConfigXMLLocation());
	
	abstract String getConfigXMLLocation();
	
	public Map<String,Class<?>> getDomainClasses() throws ClassNotFoundException, IOException{
		Map<String,Class<?>> domainClasses = new LinkedHashMap<String,Class<?>>();
		loadClasses(domainClasses, applicationSettings.getDomainPackage());
		return domainClasses;
	}
	
	public Map<String,Class<?>> getViewClasses() throws ClassNotFoundException, IOException{
		Map<String,Class<?>> viewClasses = new HashMap<String,Class<?>>();
		loadClasses(viewClasses, applicationSettings.getViewPackage());
		return viewClasses;
	}
	
	public Map<String,Class<?>> getControllerClasses() throws ClassNotFoundException, IOException{
		Map<String,Class<?>> controllerClasses = new HashMap<String,Class<?>>();
		loadClasses(controllerClasses, applicationSettings.getControllerPackage());
		return controllerClasses;
	}

	private void loadClasses(Map<String, Class<?>> classByStrippedName, String pkg) 
			throws ClassNotFoundException, IOException {
		for (Class<?> c : getClasses(pkg)) {
			classByStrippedName.put(StrippedName.stripName(c), c);
		}
	}
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class<?>[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static void main(final String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
    	if(args == null || args.length == 0 || args[0] == null){
    		throw new IllegalArgumentException(
    				"\nExpecting relative path to config.xml file. Example:\n"
    				+"Bootstrap ./\n"
    				+"or\n"
    				+"Bootstrap ../../xmlfiles/");
    	}
		Bootstrap bs = new Bootstrap(){
			@Override
			String getConfigXMLLocation() {
				URL url = Bootstrap.class.getResource(".");
				String path = url.toExternalForm().replace("file:/", "") + (args[0]);
				return System.getProperty("file.separator") + path + "config.xml";
			}
		};
		Application.initialize(	bs.applicationSettings, 
								bs.getControllerClasses(), 
								bs.getDomainClasses(),
								bs.getViewClasses()); 
	}
    
}
