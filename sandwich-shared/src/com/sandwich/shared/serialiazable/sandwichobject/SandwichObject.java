package com.sandwich.shared.serialiazable.sandwichobject;

import java.io.Serializable;

import com.sandwich.shared.serialiazable.sandwichobject.equals.TwoObjectEquals;
import com.sandwich.shared.serialiazable.util.HasValue;


public interface SandwichObject<T> extends HasValue<T>, Serializable, TwoObjectEquals{

}
