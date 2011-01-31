package com.sandwich.server.oneholder;

import com.sandwich.server.BusObj;
import com.sandwich.server.EntityImpl;
import com.sandwich.shared.oneholder.OneHolder;

public class OneHolderEntity extends EntityImpl implements OneHolder, BusObj{

	protected Integer one;
	
	public OneHolderEntity(){
		
	}
	
	public OneHolderEntity(Integer number){
		this.one = number;
	}
	
	public Integer getOne(){
		return one;
	}
	
	public void assertOne(){
		if(!Integer.valueOf(1).equals(one)){
			throw new IllegalStateException("must be one");
		}
	}

	@Override
	public String getKey(){
		return String.valueOf(getOne());
	}
}
