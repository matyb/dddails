package com.sandwich.server;

import java.lang.reflect.InvocationTargetException;

import com.sandwich.shared.validation.ValidatableImpl;
import com.sandwich.shared.validation.Validation;

public abstract class EntityImpl extends ValidatableImpl implements Entity{

	/**
	 * Override this method to return custom validation objects to be
	 * cached by the ValidatableImpl for use in public getValidation() method.
	 * 
	 * Overriding the public method will bypass access to this method from
	 * members outside pkg, or class hierarchy. It is wise to override the
	 * protected version to chain to the public version when overridden.
	 * 
	 * @return A validation object cached by ValidatableImpl class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	protected Validation<?> getValidationProtected()
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		String className = getClass().getName();
		return (Validation<?>) Class.forName(className.substring(0, className.lastIndexOf(Entity.CLASS_SUFFIX)) + 
				Validation.CLASS_SUFFIX).getConstructor(new Class[] {}).newInstance();
	}
}
