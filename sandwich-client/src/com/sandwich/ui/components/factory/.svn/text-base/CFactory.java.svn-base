package com.sandwich.ui.components.factory;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.util.HasValue;
import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.ui.components.LabeledComponent;
import com.sandwich.ui.components.MutableComponent;
import com.sandwich.ui.components.factory.compfactories.ComponentFactory;
import com.sandwich.ui.components.factory.compfactories.DTOPanelFactory;
import com.sandwich.ui.components.factory.compfactories.EntryComponentFactory;
import com.sandwich.ui.components.factory.compfactories.IntegerComponentFactory;
import com.sandwich.ui.components.factory.compfactories.StringComponentFactory;

public class CFactory extends KeyFactory{

	private static final long serialVersionUID = -838515745240707251L;

	private CFactory(){}
	
	@SuppressWarnings("unchecked")
	public static <E extends Serializable, C extends MutableComponent<?>> C createComponent(Object serializable) {
		E value = serializable instanceof HasValue ? 
				((HasValue<E>)serializable).getValue() : (E)serializable; 
		if(value == null){
			throw new NullPointerException("null values are not permitted - please wrap in " +
					Null.class.getName()+" object instead.");
		}
		
		String valueClass = value instanceof Null ? ((Null)value).getClassName() : value.getClass().getName();
		
		return (C)getFactory((Serializable)serializable, value).createComponent(
				valueClass, (Serializable)serializable, (Serializable)value);
	}
	
	public static <E extends Serializable> ComponentFactory getFactory(Serializable source, E e){
		Class<?> sourceClass;
		try {
			sourceClass = source == null ? 
				Null.class : 
			source instanceof String && ((String)source).contains(".") ? 
				Class.forName((String)source) :
			source instanceof Class<?> ? 
				((Class<?>)source) : 
			source.getClass();
		} catch (ClassNotFoundException e1) {
			throw new RuntimeException(e1);
		}
		if(Integer.class.isAssignableFrom(sourceClass)){
			return new IntegerComponentFactory();
		}else if(Map.Entry.class.isAssignableFrom(sourceClass)){
			return new EntryComponentFactory();
		}else if(DTO.class.isAssignableFrom(sourceClass)){
			return new DTOPanelFactory();
		}
		return new StringComponentFactory();
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Serializable> 
			LabeledComponent<E> createComponent(String label, E serializable) {
		return new LabeledComponent<E>(
				createKey(serializable,label), 
				(MutableComponent<E>)createComponent(serializable));
	}

	public static String getDescription(String className, String key){
		Logger.getAnonymousLogger().log(Level.INFO, "about time you got around to adding .properties support");
		return "description-+"+className+key;
	}

}
