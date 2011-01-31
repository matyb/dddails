package com.sandwich.shared.serialiazable.transferobject;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.util.MethodConstants;
import com.sandwich.shared.serialiazable.util.MethodSuitability;
import com.sandwich.shared.serialiazable.util.Null;


public class DTOHandler implements InvocationHandler, Serializable, MethodConstants, Cloneable{

	private static final long serialVersionUID = -7716235768480606209L;
	private static final MethodSuitability cloneMethod = new MethodSuitability("clone", new Class[]{});
	
	private List<MethodSuitability> toMethods = new ArrayList<MethodSuitability>();
	private Class<?>[] interfaces;
	private final DTO to;
	public DTOHandler(DTO to, Class<?>[] interfaces){
		this.to = to;
		this.interfaces = interfaces;
		for(Method m : to.getClass().getMethods()){
			toMethods.add(getMethodSuitability(m));
		}
	}
	
	@Override
	public Object invoke(Object proxy, Method method,
			Object[] args) throws Throwable {
		MethodSuitability methodSuitability = getMethodSuitability(method);
		if(isCloneMethod(method)){
			return cloneAll();
		}
		if(toMethods.contains(methodSuitability)){
			return method.invoke(to, args);
		}
		String methodName = method.getName();
		Class<?> returnType = method.getReturnType();
		if(methodName.startsWith(GET) && args == null && methodName.trim().length() > GET.length()){
			return get(methodName, returnType);
		}
		else if(methodName.startsWith(SET)
				&& args != null && args.length == 1){
			return set(args, methodName, returnType);
		}
		throw new UnsupportedOperationException(method+" is not supported by default handler");
	}

	private boolean isCloneMethod(Method method) {
		return cloneMethod.getName().equals(method.getName())
			&& MethodSuitability.isParameterTypesEqual(cloneMethod.getParameterTypes(), method.getParameterTypes());
	}

	private Object set(Object[] args, String methodName, Class<?> returnType)
			throws ClassNotFoundException {
		String trimmedMethodName = methodName.replaceFirst(SET, "");
		Object returnValue = ((DTO)to).set(trimmedMethodName, (Serializable)args[0]);
		if(returnValue instanceof Null){
			returnType = Class.forName(((Null)returnValue).getClassName());
			returnValue = null;
		}
		if(returnType.isPrimitive() && returnValue == null){
			throw new RuntimeException("time to handle null primitive return values");
		}
		return returnValue;
	}

	private Object get(String methodName, Class<?> returnType)
			throws ClassNotFoundException {
		Object returnValue = ((DTO)to).get(methodName.substring(GET.length()));
		if(returnValue == null){
			return null;
		}
		if(returnValue instanceof ReadOnly<?>){
			returnValue = ((ReadOnly<?>)returnValue).getValue();
		}
		if(returnValue instanceof Null){
			returnType = Class.forName(((Null)returnValue).getClassName());
			returnValue = null;
		}
		if(returnType.isPrimitive() && returnValue == null){
			throw new RuntimeException("time to handle null primitive return values");
		}
		return returnValue;
	}

	private DTO cloneAll() throws CloneNotSupportedException{
		DTO clone = (DTO)to.clone();
		return (DTO) Proxy.newProxyInstance(clone.getClass().getClassLoader(),
				interfaces, (DTOHandler)clone());
	}
	
	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private MethodSuitability getMethodSuitability(Method method) {
		return new MethodSuitability(method);
	}
	
}
