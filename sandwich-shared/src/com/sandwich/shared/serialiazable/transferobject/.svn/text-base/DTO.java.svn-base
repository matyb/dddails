package com.sandwich.shared.serialiazable.transferobject;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;

public interface DTO extends TransferObject, Cloneable{
	public static final String BUSOBJCLASSNAME_KEY = "busObjClassName";
	<T extends Serializable> T get(String s);
	<T extends Serializable> T set(String s, T o);
	Set<Entry<String, Serializable>> getAll();
	boolean isValueReadOnly(String s);
	Class<?> getValueClass(String string) throws ClassNotFoundException;
	Object clone() throws CloneNotSupportedException;
}
