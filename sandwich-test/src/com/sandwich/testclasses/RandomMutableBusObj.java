package com.sandwich.testclasses;

import java.util.List;

public interface RandomMutableBusObj extends RandomBusObj {

	public void setList(List<?> l);

	public void setNumber(Long number);

	public void setName(String name);
	
}
