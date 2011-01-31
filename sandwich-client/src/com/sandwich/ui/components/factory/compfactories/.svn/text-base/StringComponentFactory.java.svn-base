package com.sandwich.ui.components.factory.compfactories;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.util.HasMutableValue;
import com.sandwich.shared.serialiazable.util.HasValue;
import com.sandwich.ui.components.SandwichComponent;
import com.sandwich.ui.components.SandwichLabel;
import com.sandwich.ui.components.SandwichTextBox;

public class StringComponentFactory implements ComponentFactory{

	@SuppressWarnings("unchecked")
	@Override
	public SandwichComponent<?> createComponent(String s, Serializable source, Serializable value) {
		if(source instanceof ReadOnly){
			return new SandwichLabel((ReadOnly<String>)source);
		}else if(source instanceof HasMutableValue){
			return new SandwichTextBox((HasValue<String>)source);
		}else if(source instanceof HasValue){
			SandwichTextBox returnValue = new SandwichTextBox((HasValue<String>)source);
			returnValue.setEnabled(false);
			return returnValue;
		}else{
			return new SandwichLabel((String)value);
		}
	}
	
}
