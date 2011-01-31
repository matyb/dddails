package com.sandwich.shared.serialiazable.sandwichobject.equals;


public class LooseEquals extends WrapperSensitiveEquals implements EmptyChecker {

	private static final long serialVersionUID = 1560271391552370573L;
	NullOrEmpty nullOrEmpty = new NullOrEmpty();
	
	@Override
	public boolean equals(Object o0, Object o1) {
		if ((o0 == o1) || 
			nullOrEmpty.isEmpty(o0, true) && nullOrEmpty.isEmpty(o1, true)) {
			return true;
		}
		return super.equals(o0, o1);
	}

	@Override
	public boolean isEmpty(Object o, boolean treatNullObjectAs_null) {
		return nullOrEmpty.isEmpty(o, true);
	}
	
}
