package com.sandwich.ui.components.factory.compfactories;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.ui.components.DTOPanel;
import com.sandwich.ui.components.SandwichComponent;

public class DTOPanelFactory implements ComponentFactory{

	@Override
	public SandwichComponent<?> createComponent(String s, Serializable source, Serializable value) {
		return new DTOPanel((DTO)source);
	}
	
}
