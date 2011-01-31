package com.sandwich.shared.oneholder;

import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.shared.serialiazable.util.map.E;
import com.sandwich.shared.serialiazable.util.map.EMap;
import com.sandwich.shared.validation.ValidatableTO;

public class OneHolderTO extends ValidatableTO implements OneHolder {

	private static final long serialVersionUID = 9057801821477783751L;
	
	public OneHolderTO(Integer i){
		super("com.sandwich.server.oneholder.OneHolderEntity", new EMap(
				new E(NUMBER_KEY, i == null ? new Null(Integer.class.getName()) : i)));
	}
	
	@Override
	public Integer getOne(){
		return super.get(NUMBER_KEY);
	}
	
	public void assertOne(){
		if(!Integer.valueOf(1).equals(getOne())){
			throw new IllegalStateException("must be one");
		}
	}
}
