package com.sandwich.shared.serialiazable.sandwichobject.equals;

import com.sandwich.shared.serialiazable.util.HasValue;

public class WrapperSensitiveEquals extends NullSafeEquals{

	private static final long serialVersionUID = -1882249280856752624L;

	@Override
	public boolean equals(Object o0, Object o1) {
		while (o0 instanceof HasValue<?>) {
			o0 = ((HasValue<?>) o0).getValue();
		}
		while (o1 instanceof HasValue<?>) {
			o1 = ((HasValue<?>) o1).getValue();
		}
		return super.equals(o0, o1);
	}
}
