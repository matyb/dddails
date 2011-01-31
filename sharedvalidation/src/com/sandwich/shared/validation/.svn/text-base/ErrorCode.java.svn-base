package com.sandwich.shared.validation;

public abstract class ErrorCode {
	
	private String code;
	
	protected ErrorCode(String code){
		this.code = code;
	}
	
	public String getDescription(){
		return getDescription(getClass(), code);
	}

	private String getDescription(Class<? extends ErrorCode> clazz, String string) {
		return "TODO: "+clazz+" : "+string;
	}
	
}