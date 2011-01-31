package com.sandwich.ui.components;

public interface SandwichComponent<T> {

	/**
	 * @return default value when HasValue is null (<b>NOT</b> when
	 *         hasValue.getValue() is null).
	 */
	public T getDefaultValue();
	/**
	 * The key for the matching DTO map entry
	 * 
	 * @return key
	 */
	public String getKey();
	void setEnabled(boolean enabled);
	
}
