package com.sandwich.shared.serialiazable.sandwichobject.equals;


public class NullSafeEquals implements TwoObjectEquals {

	private static final long serialVersionUID = 8191769807666239932L;
	
	@Override
	public boolean equals(Object o0, Object o1) {
		if (o0 == o1) {
			return true;
		}
		if (o0 == null && o1 != null) {
			return false;
		}
		if (o1 == null && o0 != null) {
			return false;
		}
		return o0.equals(o1);
	}

}
