package com.sandwich.ui.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.sandwich.shared.serialiazable.util.HasValue;

public class IntegerTextBox extends JTextField implements MutableComponent<Integer> {

	private static final long serialVersionUID = 1L;
	private static Integer DEFAULT_VALUE = 0;
	private String key;
	
	public IntegerTextBox(){}
	
	public IntegerTextBox(Integer s){
		setDocument(new PlainDocument(){
			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offset, String string, AttributeSet attribs){
				if(string != null){
					StringBuilder sb = new StringBuilder();
					for(char c : string.toCharArray()){
						if(Character.isDigit(c)){
							sb.append(c);
						}
					}
					try {
						super.insertString(offset, sb.toString(), attribs);
					} catch (BadLocationException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
		setValue(s);
	}
	
	public IntegerTextBox(HasValue<Integer> h){
		this(h == null ? DEFAULT_VALUE : h.getValue());
		this.key = h.getKey();
	}
	
	@Override
	public Integer getDefaultValue() {
		return DEFAULT_VALUE;
	}

	@Override
	public void setValue(Integer o) {
		setText(String.valueOf(o));
	}
	
	@Override
	public Integer getValue() {
		return Integer.valueOf(getText());
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
}
