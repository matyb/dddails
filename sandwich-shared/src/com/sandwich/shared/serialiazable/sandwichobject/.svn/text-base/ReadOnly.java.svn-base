package com.sandwich.shared.serialiazable.sandwichobject;

import java.io.Serializable;
import java.util.ConcurrentModificationException;

import com.sandwich.shared.serialiazable.sandwichobject.equals.TwoObjectEquals;
import com.sandwich.shared.serialiazable.sandwichobject.equals.WrapperSensitiveEquals;

public class ReadOnly<T extends Serializable> extends SandwichWrapper<T>{

	private static final long serialVersionUID = 1085954729370771824L;
	public static String READONLY = "ReadOnly->";
	final private int hashCode;
	
	public ReadOnly(T o) {
		super(o, new WrapperSensitiveEquals());
		hashCode = getHashCode(o);
	}
	
	public ReadOnly(T o, TwoObjectEquals p) {
		super(o,p);
		hashCode = getHashCode(o);
	}
	
	@Override
	public T getValue(){
		T value = super.getValue();
		if(hashCode != getHashCode(value)){
			throw new ConcurrentModificationException(getClass().getCanonicalName()+" has been altered and was read only.");
		}
		return value;
	}
	
	private int getHashCode(T o) {
		int hashCode = 0;
		if(o != null){
			hashCode = o.hashCode();
		}
		return hashCode;
	}
	
	@Override 
	public String toString(){
		return READONLY+super.toString();
	}
}
