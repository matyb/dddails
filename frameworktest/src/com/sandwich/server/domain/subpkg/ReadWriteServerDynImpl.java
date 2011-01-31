package com.sandwich.server.domain.subpkg;

import java.io.Serializable;

import com.sandwich.server.HasDynamicDTOInterface;

public class ReadWriteServerDynImpl extends ReadWriteServerImpl implements HasDynamicDTOInterface{

	@Override
	public Class<? extends Serializable> getDTOInterface() {
		return SerializableRWExample.class;
	}
	
	@Override
	public Class<?> getBaseInterface() {
		return ReadWriteExample.class;
	}
}
