package com.sandwich.shared.serialiazable.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sandwich.shared.serialiazable.exception.ReadOnlyModificationException;
import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.util.HasMutableValue;
import com.sandwich.shared.serialiazable.util.HasValue;
import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.shared.serialiazable.util.map.E;
import com.sandwich.shared.serialiazable.util.map.EMap;


public class TOImpl implements DTO {

	private static final long serialVersionUID = 9186812339384038055L;
	protected final Map<String, Serializable> map;
	
	protected TOImpl(String busObjClassName, Map<String, Serializable> map){
		this.map = map;
		this.map.put(BUSOBJCLASSNAME_KEY, busObjClassName);
	}
	
	protected TOImpl(String busObjClassName, E...es){
		this.map = new EMap(es);
		this.map.put(BUSOBJCLASSNAME_KEY, busObjClassName);
	}
	
	protected TOImpl(String busObjClassName, Collection<Entry<String,Serializable>> es){
		this.map = new EMap(es);
		this.map.put(BUSOBJCLASSNAME_KEY, busObjClassName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T get(String s) {
		Object o = map.get(s);
		while(o instanceof HasValue){
			o = ((HasValue<?>)o).getValue();
		}
		if(o instanceof Null){
			return null;
		}
		return (T) o;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T set(String s, T t) {
		// refactor set behavior into E objects
		if(!map.containsKey(s)){
			throw new IllegalArgumentException("Key "+s+" does not exist.");
		}
		Object old = map.get(s);
		if(old instanceof ReadOnly){
			throw new ReadOnlyModificationException(old);
		}
		else if(t == null){
			setNull(s, old);
		}
		return (T)map.put(s,t);
	}

	private Object setNull(String s, Object oldEntryValue) {
		// refer to oldValue we're replacing -- need to dupe
		// reference to oldValue to compensate for injecting
		// into HasMutable instance if necessary
		Object returnValue = null;
		if(oldEntryValue instanceof HasValue<?>){
			returnValue = ((HasValue<?>)oldEntryValue).getValue();
			if(returnValue == null){
				Logger.getAnonymousLogger().log(Level.WARNING, 
						oldEntryValue+" is a HasValue implementation extended!\n" +
								"may not have been intentional to set null...\n" +
								"Will have unforeseen side effects in client.");
				returnValue = new Null(oldEntryValue.getClass().getName());
			}
		}
		else if(oldEntryValue instanceof Null){
			returnValue = oldEntryValue;
		}
		else if(oldEntryValue instanceof TransferObject){
			returnValue = new Null(((TransferObject)oldEntryValue).getBusObjClassName());
		}
		else{
			// let NPE throw - return value may never be null in this method
			returnValue = new Null(oldEntryValue.getClass().getName());
		}
		
		// value may be mutable (ie SandwichWrapper)
		if(oldEntryValue instanceof HasMutableValue<?>){
			returnValue = returnValue == null ? null : returnValue.getClass().getName();
			if(returnValue == null){
				Logger.getAnonymousLogger().log(Level.WARNING, 
						oldEntryValue+" is a HasValue implementation extended!\n" +
								"may not have been intentional to set null...\n" +
								"Will have unforeseen side effects in client.");
				returnValue = new Null(oldEntryValue.getClass().getName());
			}
			
		}
		return returnValue;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof TOImpl){
			return map.equals(((TOImpl)o).map);
		}
		if(o != null && o instanceof DTO){
			for(String key : map.keySet()){
				Object value = map.get(key);
				Serializable thatValue = ((DTO)o).get(key);
				if(value == thatValue){
					continue;
				}else if(value != null && !value.equals(thatValue)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return map.hashCode();
	}

	/**
	 * Should only prevent addition or removal of set elements - the elements
	 * themselves are likely still modifiable. 
	 * 
	 * @return unmodifiable Set<Entry<String, Object>> representing BusObj's 
	 * externally visible attributes.
	 * @see {@link com.sandwich.TransferObjectTest.testModifiabilityOfGetAll}
	 */
	@Override
	public Set<Entry<String, Serializable>> getAll() {
		return Collections.unmodifiableSet(map.entrySet());
	}

	@Override
	public String getBusObjClassName() {
		return get(BUSOBJCLASSNAME_KEY);
	}
	
	@Override
	public Class<?> getValueClass(String key) throws ClassNotFoundException{
		Object o = map.get(key);
		if(o == null){
			o = get(key);
		}
		while(o instanceof HasValue<?>){
			o = ((HasValue<?>)o).getValue();
		}
		if(o instanceof Null){
			return Class.forName(((Null)o).getClassName());
		}
		return o.getClass();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
	@Override
	public boolean isValueReadOnly(String s) {
		return map.get(s) instanceof ReadOnly<?>;
	}
	
	@Override
	public String toString(){
		return String.valueOf(map);
	}
}
