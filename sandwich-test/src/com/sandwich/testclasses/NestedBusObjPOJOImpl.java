package com.sandwich.testclasses;

import com.sandwich.server.BusObj;

public class NestedBusObjPOJOImpl implements NestedBusObj, BusObj {

	DynamicPOJO businessPOJO;
	public NestedBusObjPOJOImpl(){}
	
	public DynamicPOJO getBusinessPOJO(){
		return businessPOJO;
	}
	
}
