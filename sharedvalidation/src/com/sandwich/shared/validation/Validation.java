package com.sandwich.shared.validation;

import java.util.List;



public interface Validation<T extends Validatable> {

	public static final String CLASS_SUFFIX = "Validation";
	
	List<ValidationResponse<T>> validate(T object);
	
}
