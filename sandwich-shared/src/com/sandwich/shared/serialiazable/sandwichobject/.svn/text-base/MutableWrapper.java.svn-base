package com.sandwich.shared.serialiazable.sandwichobject;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.util.HasMutableValue;

public class MutableWrapper<T extends Serializable> extends SandwichWrapper<T> implements HasMutableValue<T>{
	
	public MutableWrapper(T t) {
		super(t);
	}

	private static final long serialVersionUID = -6802223184218304968L;

	@Override
	public void setValue(T o) {
		this.o = o;
	}
}
