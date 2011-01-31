package com.sandwich.testclasses;

import java.io.Serializable;

public class LiteVO implements Serializable{

	private static final long serialVersionUID = 4287470123204576080L;
	String oid;
	String name;
	
	public LiteVO(String oid, String name){
		this.oid  = oid;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getOid(){
		return oid;
	}
	
	public String toString(){
		return getName();
	}
}
