package com.sandwich.server;

import org.junit.After;
import org.junit.Before;

import com.sandwich.test.TestUtils;

public class ApplicationBootstrappingTestCase {

Bootstrap bs;
	
	@Before
	public void resetApplication() throws Exception{
		TestUtils.setValue("settings", null, Application.class);
		TestUtils.setValue("domainClasses", null, Application.class);
		TestUtils.setValue("controllerClasses", null, Application.class);
		TestUtils.setValue("viewClasses", null, Application.class);
		TestUtils.setValue("dtos", null, Application.class);

		// TODO: test this w/o being so hacky
		bs = new Bootstrap(){
			@Override
			String getConfigXMLLocation() {
				return BootstrapTest.getConfigXMLLocation();
			}
		};
	}
	
	@After
	public void returnApplicationToReset() throws Exception{
		resetApplication();
	}
	
	@SuppressWarnings("static-access")
	public void bootstrapDotMain() throws Exception {
		bs.main(new String[]{""});
	}
	
}
