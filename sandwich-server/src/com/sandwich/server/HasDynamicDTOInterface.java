package com.sandwich.server;

import java.io.Serializable;

public interface HasDynamicDTOInterface extends BusObj{

	Class<? extends Serializable> getDTOInterface();
	Class<?> getBaseInterface();
	
}
