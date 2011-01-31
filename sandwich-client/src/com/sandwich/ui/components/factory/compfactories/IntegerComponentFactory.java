package com.sandwich.ui.components.factory.compfactories;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.util.HasMutableValue;
import com.sandwich.shared.serialiazable.util.HasValue;
import com.sandwich.ui.components.IntegerTextBox;
import com.sandwich.ui.components.SandwichComponent;

public class IntegerComponentFactory implements ComponentFactory {

	@SuppressWarnings("unchecked")
	@Override
	public SandwichComponent<?> createComponent(String s, Serializable source, Serializable value) {
		if(source instanceof ReadOnly){
			IntegerTextBox returnValue = new IntegerTextBox((ReadOnly<Integer>)source);
			returnValue.setEnabled(false);
			return returnValue;
		}else if(source instanceof HasMutableValue){
			return new IntegerTextBox((HasValue<Integer>)source);
		}else if(source instanceof HasValue){
			IntegerTextBox returnValue = new IntegerTextBox((HasValue<Integer>)source);
			returnValue.setEnabled(false);
			return returnValue;
		}else{
			IntegerTextBox returnValue = new IntegerTextBox((Integer)value);
			returnValue.setEnabled(false);
			return returnValue;
		}
	}
	
}
