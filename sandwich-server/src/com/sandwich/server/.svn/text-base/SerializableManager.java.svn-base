package com.sandwich.server;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.DTOHandler;
import com.sandwich.shared.serialiazable.transferobject.TOImpl;
import com.sandwich.shared.serialiazable.util.HasKey;
import com.sandwich.shared.serialiazable.util.MethodConstants;
import com.sandwich.shared.serialiazable.util.Null;

public class SerializableManager {

	/**
	 * Prevent instantiation.
	 */
	private SerializableManager(){}
	
	/**
	 * Creates a DTO implementation from a BusObj instance.
	 * @param busObj
	 * @return
	 */
	public static DTO createTransferObject(BusObj busObj) {
		Class<? extends BusObj> clazz = busObj.getClass();
		return createTransferObject(busObj, clazz);
	}

	/**
	 * Creates a DTO implementation from a BusObj class.
	 * @param clazz
	 * @return
	 */
	public static DTO createTransferObject(Class<? extends BusObj> clazz) {
		// TODO: speedup - cache to make to generation fast
		Map<String,Method> getters = getGetterMethodsByAssumedFieldName(clazz);
		Map<String,Method> setters = getSetterMethodsByAssumedFieldName(clazz);
		Map<String, Serializable> backing = new LinkedHashMap<String,Serializable>();
		for (Entry<String,Method> entry : getters.entrySet()) {
			Method method = entry.getValue();
			String key = entry.getKey();
			Serializable methodValue = new Null(method.getReturnType().getName());
			if(!setters.containsKey(key) && !(methodValue instanceof ReadOnly)){
				methodValue = new ReadOnly<Serializable>(methodValue);
			}
			backing.put(key, methodValue);
		}
		return new ServerOriginatedTO(clazz.getName(), backing);
	}
	
	/**
	 * Creates a Proxy instance that implements the passed in interface. Generally an
	 * interface with the getter's and setters used to manage the object's state.
	 * @param <B>
	 * @param <T>
	 * @param busObj
	 * @param toClass
	 * @return
	 */
	public static <B extends BusObj, T extends Serializable> T createDynamicProxyTO(
			B busObj, Class<T> toClass){
		checkBusObj(toClass);
		return createDynamicProxyTO(busObj.getClass(), toClass, createTransferObject(busObj));
	}
	
	/**
	 * Creates a Proxy instance from the passed in BusObj Class and Serializable Class
	 * @param <B>
	 * @param <T>
	 * @param busObjClass
	 * @param toClass
	 * @return
	 */
	public static <B extends BusObj, T extends Serializable> T createDynamicProxyTO(
			Class<B> busObjClass, Class<T> toClass){
		checkBusObj(toClass);
		return createDynamicProxyTO(busObjClass, toClass, createTransferObject(busObjClass));
	}

	/**
	 * Converts a DTO into an instance of the Class produced from 
	 * Class.forName(to.getBusObjClassName()) and populates it from
	 * the passed in DTO's backing map. 
	 * 
	 * Keys in the DTO's backing map correspond to method names in
	 * the created BusObj.
	 * 
	 * @param <T>
	 * @param to
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BusObj> T createBusObj(DTO to) {
		Class<T> c = null;
		if(BusObj.WILL_NOT_RETURN_TO_BUS_OBJ.equals(to.getBusObjClassName())){
			throw new IllegalArgumentException("Cannot pair up anonymous BusObj class w/ DTO.");
		}
		try {
			c = (Class<T>) Class.forName(to.getBusObjClassName());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e+" must be a valid subclass of BusObj");
		}
		return createBusObj(c, to.getAll());
	}
	
	public static <T extends BusObj> T populateBusObj(T t, Set<Entry<String,Serializable>> es){
		Map<String,Method> methods = getSetterMethodsByAssumedFieldName(t.getClass());
		for (Entry<String, Serializable> e : es) {
			String key = e.getKey();
			Object value = e.getValue();
			if(value instanceof ReadOnly<?> || DTO.BUSOBJCLASSNAME_KEY.equals(key)){
				continue;
			}
			try {
				if(key != null){
					Method method = methods.get(key);
					// check method is transferable in case client tries to set
					// transient method - might be malicious
					if (method != null && Modifier.isPublic(method.getModifiers())) {
						if (value instanceof Null) {
							value = null;
						}
						method.invoke(t, value);
					}
				}
			} catch (Throwable th) {
				throw new RuntimeException(th);
			}
		}
		return t;
	}
	
	private static DTO createTransferObject(BusObj busObj,
			Class<? extends BusObj> clazz) {
		boolean isAnonymous = clazz.isAnonymousClass();
		// TODO: speedup - cache to make to generation fast
		Map<String,Method> getters = getGetterMethodsByAssumedFieldName(clazz);
		Map<String,Method> setters = getSetterMethodsByAssumedFieldName(clazz);
		Serializable[] values = getValues(getters, setters, busObj);
		Map<String, Serializable> backing = new LinkedHashMap<String, Serializable>();
		int i = 0;
		for (Entry<String,Method> e : getters.entrySet()) {
			String fieldName = e.getKey();
			if(isAnonymous || !setters.containsKey(fieldName) && !(values[i] instanceof ReadOnly<?>)){
				values[i] = new ReadOnly<Serializable>(values[i]);
			}
			backing.put(fieldName, values[i]);
			i++;
		}
		String className = null;
		if(isAnonymous){
			className = BusObj.WILL_NOT_RETURN_TO_BUS_OBJ;
		}else{
			className = clazz.getName();
		}
		return new ServerOriginatedTO(className, backing);
	}
	
	private static <T> void checkBusObj(Class<T> toClass) {
		if(BusObj.class.isAssignableFrom(toClass)){
			throw new IllegalArgumentException("no busobj's are permitted: "+toClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <B extends BusObj, T extends Serializable> T createDynamicProxyTO(
			Class<B> busObjClass, Class<T> toClass, final DTO to){
		Class<?>[] interfaces = busObjClass.getInterfaces();
		boolean entityToHasKeyConversionNecessary = Entity.class.isAssignableFrom(busObjClass);
		int numberOfExplicitlyAddedInterfaces = entityToHasKeyConversionNecessary
			? 3 : 2;
		Collection<Class<?>> allInterfaces = new HashSet<Class<?>>(interfaces.length + numberOfExplicitlyAddedInterfaces);
		allInterfaces.add(toClass);
		allInterfaces.add(DTO.class);
		if(entityToHasKeyConversionNecessary){
			allInterfaces.add(HasKey.class);
		}
		int i = numberOfExplicitlyAddedInterfaces;
		for(Class<?> interfaze : interfaces){
			if(Serializable.class.isAssignableFrom(interfaze)){
				allInterfaces.add(interfaze);
			}
		}
		i = 0;
		interfaces = new Class<?>[allInterfaces.size()];
		for(Class<?> interfaze : allInterfaces){
			interfaces[i++] = interfaze;
		}
		Object p = Proxy.newProxyInstance(busObjClass.getClassLoader(), 
				interfaces, 
				new DTOHandler(to, interfaces));
		return (T)p;
	}

	private static Map<String,Method> getGetterMethodsByAssumedFieldName(Class<?> clazz) {
		return getAccessorOrMutatorMethodsByAssumedFieldName(clazz, MethodConstants.GET);
	}

	private static Map<String,Method> getSetterMethodsByAssumedFieldName(Class<?> clazz) {
		return getAccessorOrMutatorMethodsByAssumedFieldName(clazz, MethodConstants.SET);
	}
	
	private static Map<String, Method> getAccessorOrMutatorMethodsByAssumedFieldName(
			Class<?> clazz, String methodPrefix) {
		Method[] allMethods = clazz.getMethods();
		Map<String,Method> methods = new LinkedHashMap<String,Method>();
		for(int i = 0; i < allMethods.length; i++){
			Method m = allMethods[i];
			if(Modifier.isPublic(m.getModifiers())
					&& m.getName().startsWith(methodPrefix)){
				String methodName = m.getName();
				String assumedFieldName = methodName.replace(methodPrefix, "");
				if(methods.containsKey(assumedFieldName) 
						|| Object.class == m.getDeclaringClass()){
					continue; // don't copy in Object getters - ie getClass() or dupes
				}
				char firstLetter = assumedFieldName.charAt(0);
				assumedFieldName = assumedFieldName.replace(
						firstLetter, 
						Character.toUpperCase(firstLetter));
				methods.put(assumedFieldName,m);
			}
		}
		return methods;
	}
	
	private static Serializable[] getValues(Map<String,Method> getters, Map<String,Method> setters, BusObj busObj) {
		Serializable[] values = new Serializable[getters.size()];
		int i = 0;
		for (Entry<String,Method> e : getters.entrySet()) {
			try {
				Method method = e.getValue();
				Object methodValue = method.invoke(busObj, (Object[])null);
				Class<?> methodType = method.getReturnType();
				if (BusObj.class.isAssignableFrom(methodType)) {
					methodValue = createTransferObject((BusObj) methodValue);
				} else if (methodValue == null || !(methodValue instanceof Serializable)) {
					methodValue = new Null(methodType.getName());
				}
				values[i++] = (Serializable)ifNecessaryMarkReadOnly(setters, e.getKey(), (Serializable)methodValue);
			} catch (IllegalArgumentException e1) {
				throw new RuntimeException(e1);
			} catch (IllegalAccessException e1) {
				throw new RuntimeException(e1);
			} catch (InvocationTargetException e1) {
				throw new RuntimeException(e1);
			}
		}
		return values;
	}

	private static Serializable ifNecessaryMarkReadOnly(Map<String, Method> setters,
			String key, Serializable value) {
		if(!markValueReadOnly(setters,key,value)){
			value = new ReadOnly<Serializable>(value);
		}
		return value;
	}

	private static boolean markValueReadOnly(Map<String, Method> setters,
			String key, Serializable value) {
		return setters.containsKey(key) && !(value instanceof ReadOnly<?>);
	}

	private static <T extends BusObj> T createBusObj(Class<T> clazz,
			Set<Entry<String, Serializable>> entries) {
		T t = createBusObj(clazz);
		return populateBusObj(t, entries);
	}

	private static <T> T createBusObj(Class<T> clazz) {
		T t;
		try {
			Class<?>[] emtpyClassArray = new Class<?>[] {};
			Constructor<T> cons = clazz.getConstructor(emtpyClassArray);
			boolean wasAccessible = cons.isAccessible();
			cons.setAccessible(true);
			t = cons.newInstance((Object[]) null);
			cons.setAccessible(wasAccessible);
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
		return t;
	}

	private static class ServerOriginatedTO extends TOImpl {

		private static final long serialVersionUID = -5916923460428180290L;

		private ServerOriginatedTO(String className, Map<String, Serializable> map) {
			super(className, map);
		}

	}

}
