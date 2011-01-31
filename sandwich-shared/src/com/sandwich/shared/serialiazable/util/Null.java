package com.sandwich.shared.serialiazable.util;

import java.io.Serializable;

public class Null implements Serializable{

	private static final long serialVersionUID = -2570394655414899674L;
	public static final String NULL = "Null->";
	private final String className;
	
	public Null(String className){
		if(className == null){
			throw new IllegalArgumentException("Argument may not be null.");
		}
		this.className = className;
	}
	
	public String getClassName(){
		return className;
	}
	
	@Override
	public boolean equals(Object o){
		return o instanceof Null && ((Null)o).className.equals(className);
	}
	
	@Override
	public int hashCode(){
		return className.hashCode();
	}
	
	@Override
	public String toString(){
		return NULL+className;
	}
}
