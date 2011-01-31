package com.sandwich.shared.oneholder;

import com.sandwich.shared.validation.Validatable;

public interface OneHolder extends Validatable {
	public static final String NUMBER_KEY = "one";
	Integer getOne();
	void assertOne();
	
}
