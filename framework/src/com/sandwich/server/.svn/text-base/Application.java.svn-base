package com.sandwich.server;

import java.awt.IllegalComponentStateException;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.sandwich.shared.ApplicationSettings;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.util.Equals;

public final class Application {

	static final String NOT_A_BUSOBJ_WONT_BE_IN_UI_MSG = " was not a BusObj implementation and thus it will not appear outside the service layer.";
	
	private static ApplicationSettings  settings;
	private static Map<String,Class<?>> domainClasses;
	private static Map<String,Class<?>> viewClasses;
	private static Map<String,Class<?>> controllerClasses;
	private static Map<String,DTO> dtos;

	public static void initialize(
			ApplicationSettings settings2,
			Map<String, Class<?>> controllerClasses2,
			Map<String, Class<?>> domainClasses2,
			Map<String, Class<?>> viewClasses2) throws InstantiationException, IllegalAccessException {
		if(settings == null && controllerClasses == null && domainClasses == null && viewClasses == null){
			settings = 				checkNull(settings2);
			controllerClasses = 	Collections.unmodifiableMap(checkNull(controllerClasses2));
			domainClasses =			Collections.unmodifiableMap(checkNull(domainClasses2));
			viewClasses =			Collections.unmodifiableMap(checkNull(viewClasses2));
		}else{
			throw new IllegalComponentStateException("Do not initialize Application more than once.");
		}
	}

	public static ApplicationSettings getSettings() {
		return settings;
	}

	public static Map<String, Class<?>> getDomainClasses() {
		return domainClasses;
	}

	public static Map<String, Class<?>> getViewClasses() {
		return viewClasses;
	}

	public static Map<String, Class<?>> getControllerClasses() {
		return controllerClasses;
	}
	
	public static Map<String, DTO> getDTOs() throws InstantiationException, IllegalAccessException {
		if(dtos == null){
			dtos = new LinkedHashMap<String, DTO>();
			for(Entry<String, Class<?>> e : getDomainClasses().entrySet()){
				Class<?> domainClass = e.getValue();
				if(BusObj.class.isAssignableFrom(domainClass)){
					if(HasDynamicDTOInterface.class.isAssignableFrom(domainClass)){
						HasDynamicDTOInterface b = ((HasDynamicDTOInterface)domainClass.newInstance());
						Class<? extends Serializable> toClass = b.getDTOInterface();
						dtos.put(b.getBaseInterface().getSimpleName(), 
								(DTO)SerializableManager.createDynamicProxyTO(b, toClass));
					}else{
						@SuppressWarnings("unchecked")
						DTO dto = SerializableManager.createTransferObject((Class<? extends BusObj>)domainClass);
						dtos.put(dto.getBusObjClassName(), dto);
					} 
				}else{
					Logger.getLogger(Application.class.getName()).info(domainClass+NOT_A_BUSOBJ_WONT_BE_IN_UI_MSG);
				}
			}
		}
		return dtos;
	}
	
	public static <T> T checkNull(T t){
		if(Equals.isNullOrEmpty(true, t)){
			throw new IllegalArgumentException();
		}
		return t;
	}
	
}
