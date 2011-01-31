package com.sandwich.shared.serialiazable.sandwichobject.hashcode;

public class DefaultHashCode {

	public int hashCode(Object o){
		return o == null ? 0 : o.hashCode();
	}
	
}
