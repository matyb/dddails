package com.sandwich.ui.components;

import javax.swing.JLabel;

import com.sandwich.shared.serialiazable.util.HasValue;

public class SandwichLabel extends JLabel implements MutableComponent<String> {

	private static final long serialVersionUID = 1948749918908105376L;
	private static String DEFAULT_VALUE = "";
	private String key;
	
	public SandwichLabel(){}
	
	public SandwichLabel(String s){
		super(s == null ? DEFAULT_VALUE : s);
	}
	
	public SandwichLabel(HasValue<String> h){
		super(h == null ? DEFAULT_VALUE : h.getValue());
		key = h.getKey();
	}
	
	@Override
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}

	@Override
	public void setValue(String o) {
		setText(o);
	}

	@Override
	public String getValue() {
		return getText();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
}
