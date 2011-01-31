package com.sandwich.shared.serialiazable.sandwichobject.equals;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.util.Null;

public class NullOrEmpty implements EmptyChecker {

	private static final long serialVersionUID = -9150252538808692056L;

	
	
	/**
	 * Returns true if o == null or o is an empty String or Collection/Map/Array
	 * or if any of the previously mentioned Iterables contains only null/empty
	 * objects.
	 * 
	 * @param o
	 * @param treatNullAs_null
	 *            should method treat com.sandwich.shared.serializable.Null as a
	 *            null/empty reference.
	 * @return
	 */
	public boolean isEmpty(Object o, boolean treatNullAs_null) {
		if(o == null){
			return true;
		}
		if(o instanceof String){
			return (((String)o).trim()).isEmpty();
		}
		if(treatNullAs_null && o instanceof Null){
			return true;
		}
		if(o instanceof DTO){
			o = ((DTO)o).getAll();
		}
		if(o instanceof Map){
			o = ((Map<?,?>)o).entrySet();
		}
		if(o instanceof Collection<?>){
			if(((Collection<?>)o).isEmpty()){
				return true;
			}
			for(Object ob : ((Collection<?>)o)){
				if(!isEmpty(ob, treatNullAs_null)){
					return false;
				}
			}
			return true;
		}
		if(o instanceof Entry<?,?>){
			Entry<?,?> e = (Entry<?,?>)o;
			if(!isEmpty(e.getValue(), treatNullAs_null)
					|| !isEmpty(e.getKey(), treatNullAs_null)){
				return false;
			}
			return true;
		}
		if(o.getClass().isArray()){
			if(((Object[])o).length == 0){
				return true;
			}
			for(Object ob : ((Object[])o)){
				if(!isEmpty(ob, treatNullAs_null)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
