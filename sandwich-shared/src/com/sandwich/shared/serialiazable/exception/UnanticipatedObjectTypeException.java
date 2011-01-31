package com.sandwich.shared.serialiazable.exception;

public class UnanticipatedObjectTypeException extends RuntimeException {

	private static final long serialVersionUID = 7833989144509706831L;
	public UnanticipatedObjectTypeException(){}
	public UnanticipatedObjectTypeException(String msg){
		super(msg);
	}
	
}
