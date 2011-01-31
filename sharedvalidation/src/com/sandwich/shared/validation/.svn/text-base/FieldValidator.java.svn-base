package com.sandwich.shared.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class FieldValidator {

	private FieldValidator(){}
	
	public static void validate(Validatable...validatables) throws ValidationException{
		validate(Arrays.asList(validatables));
	}
	
	public static void validate(Collection<Validatable> businessObjects) throws ValidationException{
		List<ValidationResponse<Validatable>> responses = new ArrayList<ValidationResponse<Validatable>>();
		for(Validatable validatable : businessObjects){
			Validation<Validatable> validation = validatable.getValidation();
			responses.addAll(validation.validate(validatable));
		}
		if(!responses.isEmpty()){
			throw new ValidationException(responses);
		}
	}
}