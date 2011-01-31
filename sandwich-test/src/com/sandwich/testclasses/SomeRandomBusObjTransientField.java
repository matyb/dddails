package com.sandwich.testclasses;

import com.sandwich.server.BusObj;

@SuppressWarnings("unused")
public class SomeRandomBusObjTransientField implements BusObj{

	private int number;
	private String trans;
	private String name;
	public SomeRandomBusObjTransientField(){}
	public SomeRandomBusObjTransientField(int number, String trans, String name){
		this.number = number;
		this.trans = trans;
		this.name = name;
	}
}
