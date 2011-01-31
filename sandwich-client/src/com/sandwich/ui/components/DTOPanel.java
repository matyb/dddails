package com.sandwich.ui.components;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TOImpl;
import com.sandwich.ui.components.factory.CFactory;

public class DTOPanel extends JPanel implements MutableComponent<DTO> {
	
	private static final long serialVersionUID = 1532550544881578861L;
	private final static String UNDEFINED = "UNDEFINED";
	String busObjClassName = UNDEFINED;
	Map<String, MutableComponent<Serializable>> componentsByKeyMap = 
		new LinkedHashMap<String, MutableComponent<Serializable>>();

	public DTOPanel(){
		this(new MutableTO());
	}
	
	public DTOPanel(DTO to){
		busObjClassName = to.getBusObjClassName();
		for(Entry<String, Serializable> entry : to.getAll()){
			String key = entry.getKey();
			LabeledComponent<Serializable> component = CFactory.createComponent(key, entry.getValue());
			componentsByKeyMap.put(key, component);
			add(component);
		}
	}
	
	@Override
	public String getKey() {
		return busObjClassName;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for(SandwichComponent<?> sc : componentsByKeyMap.values()){
			sc.setEnabled(enabled);
		}
	}
	
	@Override
	public DTO getValue() {
		Map<String, Serializable> backingMap = new LinkedHashMap<String, Serializable>();
		for(Entry<String, MutableComponent<Serializable>> entry : componentsByKeyMap.entrySet()){
			backingMap.put(entry.getKey(), entry.getValue().getValue());
		}
		return new MutableTO(busObjClassName, backingMap);
	}
	
	@Override
	public void setValue(DTO o) {
		for(Entry<String, MutableComponent<Serializable>> entry : componentsByKeyMap.entrySet()){
			entry.getValue().setValue(o.get(entry.getKey()));
		}
	}
	
	@Override
	public DTO getDefaultValue() {
		return new MutableTO();
	}
	
	static class MutableTO extends TOImpl {
		private static final long serialVersionUID = 3726934290436862432L;
		protected MutableTO() {
			this(UNDEFINED, new LinkedHashMap<String,Serializable>());
		}
		protected MutableTO(String busObjClassName, Map<String,Serializable> map) {
			super(busObjClassName, map);
		}
		public void setBusObjClassName(String busObjClassName){
			map.put(BUSOBJCLASSNAME_KEY,busObjClassName);
		}
		@SuppressWarnings("unchecked") // the backing map is parameterized w/ value as Serializable
		@Override
		public Serializable get(String key){
			return map.get(key);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Serializable set(String key, Serializable value){
			if(key == null){
				throw new NullPointerException("key may never be null in DTO implementations");
			}
			return map.put(key, value);
		}
	}
	
}
