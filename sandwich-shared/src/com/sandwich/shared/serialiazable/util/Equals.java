package com.sandwich.shared.serialiazable.util;

import com.sandwich.shared.serialiazable.sandwichobject.equals.EmptyChecker;
import com.sandwich.shared.serialiazable.sandwichobject.equals.LooseEquals;
import com.sandwich.shared.serialiazable.sandwichobject.equals.NullOrEmpty;
import com.sandwich.shared.serialiazable.sandwichobject.equals.NullSafeEquals;
import com.sandwich.shared.serialiazable.sandwichobject.equals.TwoObjectEquals;

public class Equals {

	private static TwoObjectEquals STRICT = getDefaultStrictImpl();
	private static TwoObjectEquals LOOSE = getDefaultLooseImpl();
	private static EmptyChecker NULL_OR_EMPTY = getDefaultEmptyCheckerImpl();

	private Equals() {
	}
	
	public static boolean equals(Object o0, Object o1){
		return STRICT.equals(o0, o1);
	}
	
	public static boolean equalsLoosely(Object o0, Object o1){
		return LOOSE.equals(o0, o1);
	}
	
	public static boolean isNullOrEmpty(boolean treatNullObjectAs_null, Object...o){
		return NULL_OR_EMPTY.isEmpty(o, treatNullObjectAs_null); 
	}
	
	public static boolean equals(Object...o){
		return numerousObjectsEqual(STRICT, o);
	}

	public static boolean equalsLoosely(Object...o){
		return numerousObjectsEqual(LOOSE, o);
	}
	
	private static boolean numerousObjectsEqual(TwoObjectEquals equals, Object[] o) {
		if(o == null || o.length < 2){
			return false;
		}
		for(int i = 0; i < o.length - 1; i++){
			if(!equals.equals(o[i], o[i+1])){
				return false;
			}
		}
		return true;
	}
	
	private static TwoObjectEquals getDefaultStrictImpl() {
		return new NullSafeEquals();
	}
	
	private static TwoObjectEquals getDefaultLooseImpl() {
		return new LooseEquals();
	}
	
	private static EmptyChecker getDefaultEmptyCheckerImpl() {
		return new NullOrEmpty();
	}
}
