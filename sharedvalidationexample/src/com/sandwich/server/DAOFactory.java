package com.sandwich.server;

import java.util.HashMap;
import java.util.Map;

import com.sandwich.server.oneholder.dao.OneHolderDAO;
import com.sandwich.server.oneholder.dao.OneHolderDAOImpl;

public final class DAOFactory {

	private static final Map<Class<? extends DAO<?>>, DAO<?>> implementations;
	static{
		implementations = new HashMap<Class<? extends DAO<?>>,DAO<?>>();
		implementations.put(OneHolderDAO.class, new OneHolderDAOImpl());
	}
	
	private DAOFactory(){}
	
	public static OneHolderDAO getOneHolderDAO(){
		return get(OneHolderDAO.class);
	}
	
	public static <T extends DAO<?>> void setAlternateImpl(Class<T> clazz, T t){
		implementations.put(clazz, t);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends DAO<?>> T get(Class<T> t){
		return (T) implementations.get(t);
	}
}
