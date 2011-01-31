package com.sandwich.ui.components;

import javax.swing.JTextField;

import com.sandwich.shared.serialiazable.util.HasValue;

public class SandwichTextBox extends JTextField implements MutableComponent<String> {

	private static final long serialVersionUID = 1L;
	private static String DEFAULT_VALUE = "";
	private String key;
	
	public SandwichTextBox(){}
	
	public SandwichTextBox(String s){
		super(s == null ? DEFAULT_VALUE : s);
	}
	
	public SandwichTextBox(HasValue<String> h){
		super(h == null ? DEFAULT_VALUE : h.getValue());
		this.key = h.getKey();
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