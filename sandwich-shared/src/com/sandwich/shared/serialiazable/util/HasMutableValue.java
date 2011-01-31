package com.sandwich.shared.serialiazable.util;

public interface HasMutableValue<T> extends HasValue<T>{

	void setValue(T o);
	
}
