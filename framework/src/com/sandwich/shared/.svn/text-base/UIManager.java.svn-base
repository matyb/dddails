package com.sandwich.shared;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class UIManager {

	private static final ApplicationSettings  	settings 		= fetchSettings();
	private static final Map<String,Class<?>> 	viewClasses		= Collections.unmodifiableMap(fetchViewClasses());
	
	private UIManager(){}
	
	public static ApplicationSettings getSettings(){
		return settings;
	}

	public static Map<String, Class<?>> getViewClasses(){
		return viewClasses;
	}
	
	static Map<String, Class<?>> fetchViewClasses() {
		Map<String, Class<?>> viewClasses = new HashMap<String, Class<?>>();
		return viewClasses;
	}
	
	static ApplicationSettings fetchSettings() {
		return null;
	}
}
