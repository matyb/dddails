package com.sandwich.shared.validation;

import java.lang.reflect.InvocationTargetException;

import com.sandwich.shared.serialiazable.transferobject.TransferObject;

public class ValidatableImpl implements Validatable {
	
	/**
	 * Generally creates validation instance with same name and package as
	 * Validatable implementation that it validates except it has Validation at
	 * end of class name. This occurs by convention and is cached to mitigate
	 * reflection expenses. The reflective instantiation of an instance is
	 * performed in the getValidationProtected() method, and can be overriden by
	 * subclass looking to maximize caching of validation objects.
	 * 
	 * If this method is overridden access to parent cache is avoided and thus
	 * called directly for any subsequent fetches of validation objects. This
	 * can be used to apply a factory style creation of validation for objects
	 * whose state varies enough to warrant differing validation routines but no
	 * distinct subclasses.
	 * 
	 * @return Validation appropriate for an instanceof Validatable
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Validatable> Validation<T> getValidation() {
		try {
			return (Validation<T>)getValidationPrivate();
		} catch (Throwable ae) {
			throw new RuntimeException(ae);
		}
	}

	private Validation<?> getValidationPrivate()
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		Class<? extends ValidatableImpl> clazz = getClass();
		Validation<?> validation = Validatable.VALIDATION_BY_CLASS_MAP.get(clazz);
		if (validation == null) {
			Validatable.VALIDATION_BY_CLASS_MAP.put(clazz, validation = getValidationProtected());
		}
		return validation;
	}

	/**
	 * Override this method to return custom validation objects to be cached by
	 * the ValidatableImpl for use in public getValidation() method.
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
		return (Validation<?>) Class.forName(className.substring(0, className.lastIndexOf(TransferObject.CLASS_SUFFIX)) + 
				Validation.CLASS_SUFFIX).getConstructor(new Class[] {}).newInstance();
	}

}