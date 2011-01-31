package com.sandwich.ui.components.factory.compfactories;

import java.io.Serializable;
import java.util.Map.Entry;

import com.sandwich.ui.components.SandwichComponent;
import com.sandwich.ui.components.factory.CFactory;

public class EntryComponentFactory implements ComponentFactory{

	@Override
	public SandwichComponent<?> createComponent(String s, Serializable source, Serializable value) {
		@SuppressWarnings("unchecked")
		Entry<String, Serializable> e = (Entry<String,Serializable>)source;
		return CFactory.createComponent(e.getKey(), e.getValue());
	}
	
}
