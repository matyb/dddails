package com.sandwich.shared.validation;



public class ValidationResponseImpl<T extends Validatable> implements ValidationResponse<T> {

	private ErrorCode code;
	private String message;
	private Level level;
	private T offender;
	
	public ValidationResponseImpl(ErrorCode code, T offender){
		this.code = code;
		this.message = code.getDescription();
		this.level = Level.ERROR;
		this.offender = offender;
	}
	
	public ValidationResponseImpl(ErrorCode code, Level level, T offender){
		this.code = code;
		this.message = code.getDescription();
		this.level = level;
		this.offender = offender;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public T getOffender() {
		return offender;
	}

	@Override
	public ErrorCode getErrorCode(){
		return code;
	}
	
}
