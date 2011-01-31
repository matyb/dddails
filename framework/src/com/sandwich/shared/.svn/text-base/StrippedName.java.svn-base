package com.sandwich.shared;

import java.util.Arrays;

import com.sandwich.server.Entity;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.util.MethodConstants;
import com.sandwich.shared.validation.Validation;


public class StrippedName {
	
	private static final String[] SUBSTRINGS = {
		Validation.CLASS_SUFFIX,
		DTO.CLASS_SUFFIX,
		Entity.CLASS_SUFFIX,
		MethodConstants.GET,
		MethodConstants.SET
	}; 
	
	String fullName;
	String strippedName;
	
	public StrippedName(String fullName){
		this.fullName = fullName;
		this.strippedName = stripName(fullName);
	}
	
	public StrippedName(Class<?> clazz){
		this.fullName = clazz == null ? null : clazz.getName();
		this.strippedName = stripName(clazz);
	}

	public static String stripName(String fullName) {
		if(fullName == null){
			return "";
		}
		StringBuffer sb = new StringBuffer(fullName);
		for(String subString : SUBSTRINGS){
			int indexOfSubString = sb.indexOf(subString);
			if(indexOfSubString > -1){
				sb.replace(indexOfSubString, indexOfSubString + subString.length(), "");
			}
		}
		return sb.toString();
	}
	
	public static String stripName(Class<?> clazz) {
		if(clazz == null){
			return "";
		}
		return stripName(clazz.getSimpleName());
	}
	
	public static String[] getSubStrings(){
		return Arrays.copyOf(SUBSTRINGS, SUBSTRINGS.length);
	}
	
}
