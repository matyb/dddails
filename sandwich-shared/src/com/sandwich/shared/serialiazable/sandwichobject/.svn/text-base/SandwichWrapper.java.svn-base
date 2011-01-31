package com.sandwich.shared.serialiazable.sandwichobject;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.sandwichobject.equals.TwoObjectEquals;
import com.sandwich.shared.serialiazable.sandwichobject.equals.WrapperSensitiveEquals;

public class SandwichWrapper<T extends Serializable> implements SandwichObject<T> {

	private static final long serialVersionUID = -4610641732585518652L;
	protected T o;
	protected TwoObjectEquals p;
	
	public SandwichWrapper(T t){
		this(t,null);
	}
	
	public SandwichWrapper(T o, TwoObjectEquals p){
		this.o = o;
		this.p = p == null ? new WrapperSensitiveEquals() : p;
	}
	
	public T getValue(){
		return o;
	}
	
	@Override
	public boolean equals(Object o){
		return p.equals(this.o, o);
	}
	
	@Override
	public int hashCode(){
		return o == this ? super.hashCode() : o.hashCode();
	}
	
	@Override
	public String toString(){
		return o == this ? super.toString() : String.valueOf(o);
	}
	
	@Override
	public String getKey() {
		return getClass().getName();
	}

	@Override
	public boolean equals(Object o0, Object o1) {
		return p.equals(o0, o1);
	}
}
