package com.sandwich.shared.validation;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TOImpl;
import com.sandwich.shared.serialiazable.util.Equals;

public class ValidatableTO extends ValidatableImpl implements DTO   {
	
	private static final long serialVersionUID = -832756787359737285L;
	final DTO to;
	
	public ValidatableTO(String className, Map<String, Serializable> map){
		if(Equals.isNullOrEmpty(false, className)){
			throw new IllegalArgumentException("The className used to construct TO's cannot be null or empty");
		}
		if(Equals.isNullOrEmpty(false, map)){
			throw new IllegalArgumentException("The map used to construct TO's cannot be null or empty");
		}
		this.to = new InternalTOImpl(className,map);
	}
	
	public ValidatableTO(DTO to){
		if(Equals.isNullOrEmpty(true, to)){
			throw new IllegalArgumentException("The TO used to construct ValidatableTO's cannot be null or empty");
		}
		this.to = to;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T get(String s) {
		return (T)to.get(s);
	}
	@Override
	public Set<Entry<String, Serializable>> getAll() {
		return to.getAll();
	}
	@Override
	public String getBusObjClassName() {
		return to.getBusObjClassName();
	}
	public <T extends Serializable> T set(String s, T o) {
		return to.set(s, o);
	};
	
	static class InternalTOImpl extends TOImpl{
		private static final long serialVersionUID = 783840183238981093L;
		private InternalTOImpl(String className){
			super(className, new LinkedHashMap<String,Serializable>());
		}
		private InternalTOImpl(String className, Map<String,Serializable> map){
			super(className, map);
		}
	}

	@Override
	public Class<?> getValueClass(String string) throws ClassNotFoundException {
		return to.getValueClass(string);
	}

	@Override
	public boolean isValueReadOnly(String s) {
		return to.isValueReadOnly(s);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
