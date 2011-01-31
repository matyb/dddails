package com.sandwich.shared;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.sandwich.server.Application;
import com.sandwich.shared.serialiazable.transferobject.DTO;

public final class DTOManager {

	private DTOManager(){}
	
	private static final Map<String, DTO> 	dtos;
	
	static{
		Map<String, DTO> dtosL = null;
		try {
			Logger.getAnonymousLogger().severe("Application Object is not reachable from client context! Add middleware for access");
			dtosL = Application.getDTOs();
		} catch (Exception e) {
			throw new RuntimeException("Problem fetching DTOs - cannot continue",e);
		}finally{
			dtos = Collections.unmodifiableMap(dtosL);
		}
	}
	
	public static Set<String> getBusObjNames(){
		return dtos.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends DTO> T getDTOClone(String busObjName) throws CloneNotSupportedException{
		return (T)dtos.get(busObjName).clone();
	}
	
}
