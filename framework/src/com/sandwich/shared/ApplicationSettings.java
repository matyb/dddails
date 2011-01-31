package com.sandwich.shared;


public interface ApplicationSettings {

	enum ViewFramework {
		SWING
	}
	
	enum PersistenceFramework {
		HIBERNATE
	}

	String getDomainPackage();

	String getViewPackage();

	String getControllerPackage();
	
	String getApplicationName();

	String getApplicationVersion();
	
	ViewFramework getViewFramework();

	PersistenceFramework getPersistenceFramework();
	
}
