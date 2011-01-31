package com.sandwich.shared.oneholder;

import java.util.ArrayList;
import java.util.List;

import com.sandwich.shared.validation.Validation;
import com.sandwich.shared.validation.ValidationResponse;
import com.sandwich.shared.validation.ValidationResponseImpl;


public class OneHolderValidation implements Validation<OneHolder> {

	@Override
	public List<ValidationResponse<OneHolder>> validate(
			OneHolder object) {
		List<ValidationResponse<OneHolder>> offenders = 
			new ArrayList<ValidationResponse<OneHolder>>();
		if(object == null || object.getOne() == null){
			offenders.add(new ValidationResponseImpl<OneHolder>(
					OneHolderErrorCodes.NULL_NUMBER, object));
		}else{
			if(object.getOne() != 1){
				offenders.add(new ValidationResponseImpl<OneHolder>(
						OneHolderErrorCodes.NOT_ONE, object));
			}
		}
		return offenders;
	}
	
}
