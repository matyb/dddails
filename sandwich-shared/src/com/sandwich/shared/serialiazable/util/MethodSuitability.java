package com.sandwich.shared.serialiazable.util;

import java.io.Serializable;
import java.lang.reflect.Method;

public class MethodSuitability implements Serializable{
	private static final long serialVersionUID = -1670706715472648138L;
	final String name;
	final Class<?>[] parameterTypes;
	public MethodSuitability(Method m){
		name = m.getName();
		parameterTypes = m.getParameterTypes();
	}
	public MethodSuitability(String name, Class<?>[] parameterTypes){
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	public int hashCode(){
		int meh = 31;
		meh += meh * name.hashCode();
		for(Class<?> paramType : parameterTypes){
			meh += paramType.hashCode();
		}
		return meh;
	}
	public boolean equals(Object o){
		if(!(o instanceof MethodSuitability)){
			return false;
		}
		MethodSuitability suitability = (MethodSuitability)o;
		if(!name.equals(suitability.getName())){
			return false;
		}
		if(!isParameterTypesEqual(parameterTypes, suitability.parameterTypes)){
			return false;
		}
		return true;
	}
	public static boolean isParameterTypesEqual(Class<?>[] parameterTypes,
			Class<?>[] parameterTypes2) {
		if(!parameterTypes.equals(parameterTypes2)){
			if(parameterTypes2 == null){
				return false;
			}
			if(parameterTypes.length != parameterTypes2.length){
				return false;
			}
			for(int i = 0; i < parameterTypes.length; i++){
				if(!parameterTypes[i].isAssignableFrom(parameterTypes2[i])){
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String space = " ";
		sb.append(name + space);
		if(parameterTypes != null){
			for(Class<?> param : parameterTypes){
				sb.append(param + space);
			}
		}
		return sb.toString().trim();
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
}
