package com.sandwich.ui.components;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sandwich.ui.components.factory.CFactory;

public class LabeledComponent<T extends Serializable> extends JPanel implements MutableComponent<T>{

	private static final long serialVersionUID = -4239873771872033762L;
	private JLabel label = null;
	private MutableComponent<T> comp = null;  
	private String key;
	
	public LabeledComponent(String s, MutableComponent<T> comp){
		this.comp = comp;
		key = s;
		this.label = new JLabel(CFactory.getDescription(s, comp.getKey()));
		add(label);
		add((Component)comp);
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public String getText(){
		return label.getText();
	}

	@Override
	public T getDefaultValue() {
		return comp.getDefaultValue();
	}

	@Override
	public void setValue(T o) {
		comp.setValue(o);
	}

	@Override
	public T getValue() {
		return comp.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public <C extends MutableComponent<T>> C getComponent(){
		return (C)comp;
	}
	
	public void setComponent(MutableComponent<T> comp){
		remove((Component)this.comp);
		this.comp = comp;
		add((Component)comp);
	}

	@Override
	public String getKey() {
		return key;
	}
}