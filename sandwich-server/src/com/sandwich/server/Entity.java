package com.sandwich.server;

import java.io.Serializable;


public interface Entity {

	public final String CLASS_SUFFIX = "Entity";
	
	Serializable getKey();
	
}
