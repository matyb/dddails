package com.sandwich.shared.validation;

import java.util.HashMap;
import java.util.Map;

import com.sandwich.shared.validation.Validation;


public interface Validatable {
	
	static final Map<Class<? extends Validatable>, Validation<?>> VALIDATION_BY_CLASS_MAP = 
		new HashMap<Class<? extends Validatable>, Validation<?>>();
	<T extends Validatable> Validation<T> getValidation();
	
}
