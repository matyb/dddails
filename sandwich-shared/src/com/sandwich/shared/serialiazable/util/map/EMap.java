package com.sandwich.shared.serialiazable.util.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A LinkedHashMap implementation that essentially has constructors
 * for quick explicit instantiation. Generally used for E's
 * which are Map.Entry<String,Serializable> implementations. Commonly used to
 * represent field name, value pairs.
 *
 * @param <V>
 */
public class EMap extends LinkedHashMap<String,Serializable> {

	private static final long serialVersionUID = -5764814249009114710L;

	public EMap(Map.Entry<String,Serializable>...es){
		if(es != null){
			for(Map.Entry<String,Serializable> e : es){
				add(e);
			}
		}
	}
	
	public EMap(Collection<Map.Entry<String,Serializable>> es){
		if(es != null){
			for(Map.Entry<String,Serializable> e : es){
				add(e);
			}
		}
	}
	
	public Object add(Map.Entry<String, Serializable> e){
		return put(e.getKey(), e.getValue());
	}
}
