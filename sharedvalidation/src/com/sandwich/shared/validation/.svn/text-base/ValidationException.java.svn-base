package com.sandwich.shared.validation;

import java.util.Collections;
import java.util.List;


public class ValidationException extends Exception {

	private static final long serialVersionUID = 7272005102525707349L;
	private List<ValidationResponse<Validatable>> responses;

	public ValidationException(List<ValidationResponse<Validatable>> responses) {
		this.responses = Collections.unmodifiableList(responses);
	}

	public List<ValidationResponse<Validatable>> getResponses() {
		return responses;
	}
}
