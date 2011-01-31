package com.sandwich.testclasses;

import java.io.Serializable;
import java.util.List;

import com.sandwich.server.BusObj;
import com.sandwich.server.Entity;
import com.sandwich.server.SerializableManager;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TransferObject;

@SuppressWarnings("unused")
public class SomeRandomBusObjImpl implements RandomBusObj, BusObj {
	
	protected long number;
	protected String name;
	protected List<?> list;
	private String key =  "the key";
	public SomeRandomBusObjImpl(){}
	public SomeRandomBusObjImpl(Long l, String s, List<?> l1){
		this.number = l;
		this.name = s;
		this.list = l1;
	}
	
	/* (non-Javadoc)
	 * @see com.sandwich.testclasses.RandomBusObj#getList()
	 */
	@Override
	public List<?> getList(){
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.sandwich.testclasses.RandomBusObj#getNumber()
	 */
	@Override
	public Long getNumber(){
		return number;
	}
	
	/* (non-Javadoc)
	 * @see com.sandwich.testclasses.RandomBusObj#getName()
	 */
	@Override
	public String getName(){
		return name;
	}
}
