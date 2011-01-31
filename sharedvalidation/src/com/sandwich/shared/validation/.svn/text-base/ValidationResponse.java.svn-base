package com.sandwich.shared.validation;



public interface ValidationResponse<T extends Validatable> {

	public String getMessage();
	public Level getLevel();
	public T getOffender();
	public ErrorCode getErrorCode();
	
}

enum Level{ERROR,WARN,INFO};