package com.sandwich.ui.components.factory;

import java.io.Serializable;

public class KeyFactory implements Serializable {
	
	private static final long serialVersionUID = -210189142162563714L;
	static KeyFactory km = new KeyFactory();
	
	protected KeyFactory(){
		
	}
	
	public static String createKey(Serializable obj, String key){
		return km.createKey(true,obj,key);
	}
	
	private String createKey(boolean fromWithin, Serializable obj, String key){
		return String.valueOf(obj) + '.' + key;
	}
	
	public String createKey(String key){
		return createKey(true, this, key);
	}
	
}
