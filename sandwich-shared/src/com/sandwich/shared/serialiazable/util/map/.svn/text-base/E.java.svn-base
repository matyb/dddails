package com.sandwich.shared.serialiazable.util.map;

import java.io.Serializable;
import java.util.Map;

import com.sandwich.shared.serialiazable.sandwichobject.equals.WrapperSensitiveEquals;
import com.sandwich.shared.serialiazable.util.HasValue;

/**
 * Shorthand for Map.Entry<String, Object> implementation which generally represents
 * a field name and value. Must never actually set key to a real null keyword, 
 * the Null object should stand in to provide access to the underlying entity field's
 * class type - thus the checkNull call in the constructor.
 */
public final class E extends WrapperSensitiveEquals 
	implements Serializable, Map.Entry<String, Serializable>, HasValue<Serializable>{

	private static final long serialVersionUID = 9093769952484081287L;
	final String name;
	Serializable value;
	
	@SuppressWarnings("unused")
	private E(){name = null;}
	
	public E(String name, Serializable value){
		this.name = checkNull(name);
		this.value = value;
	}
	
	public E(Map.Entry<String, Serializable> e){
		this(e.getKey(), e.getValue());
	}
	
	private String checkNull(String o){
		if(o == null || o.trim().length() < 1){
			throw new RuntimeException("Name may not be null");
		}
		return o;
	}
	
	@Override
	public Serializable getValue(){
		return value;
	}
	
	public String getKey(){
		return name;
	}
	
	@Override
	public Serializable setValue(Serializable value) {
		Serializable old = this.value;
		this.value = value;
		return old;
	}
	
	public boolean equals(Object o){
		return this == o ||
			o instanceof E && 
			super.equals(name,((E)o).name) && 
			super.equals(value,((E)o).value);
	}
	
	public int hashCode(){
		final int prime = 31;
		int result = prime;
		result += prime * (name != null ? name.hashCode() : 0);
		result += prime * (value != null ? value.hashCode() : 0);
		return result;
	}
}
