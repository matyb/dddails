package com.sandwich.server.oneholder;

import java.util.List;

import com.sandwich.server.DAOFactory;
import com.sandwich.shared.oneholder.OneHolder;
import com.sandwich.shared.oneholder.OneHolderErrorCodes;
import com.sandwich.shared.validation.ValidationResponse;
import com.sandwich.shared.validation.ValidationResponseImpl;

public class OneHolderValidation extends com.sandwich.shared.oneholder.OneHolderValidation{
	
	public List<ValidationResponse<OneHolder>> validate(
			OneHolder object) {
		List<ValidationResponse<OneHolder>> responses = super.validate(object);
		// same as client side validation - but want to check that oneholder is unique by number
		if(!DAOFactory.getOneHolderDAO().isUnique(String.valueOf(object.getOne()))){
			responses.add(new ValidationResponseImpl<OneHolder>(OneHolderErrorCodes.NOT_UNIQUE, object));
		}
		return responses;
	}
	
}
