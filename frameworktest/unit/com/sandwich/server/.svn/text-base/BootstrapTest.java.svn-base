package com.sandwich.server;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Map;

import org.junit.Test;

import com.sandwich.client.view.View;
import com.sandwich.client.view.subpkg.ViewFromSubPkg;
import com.sandwich.server.controller.Controller;
import com.sandwich.server.controller.subpkg.ControllerFromSubPkg;
import com.sandwich.server.domain.Domain;
import com.sandwich.server.domain.subpkg.ReadWriteExample;
import com.sandwich.server.domain.subpkg.ReadWriteServerDynImpl;
import com.sandwich.server.domain.subpkg.ReadWriteServerImpl;
import com.sandwich.server.domain.subpkg.SerializableRWExample;
import com.sandwich.shared.StrippedName;


public class BootstrapTest{

	@Test
	public void testConstuction() throws Exception {
		URL url = BootstrapTest.class.getResource(".");
		final String path = url.toExternalForm().replace("file:/", "");
		new Bootstrap(){
			@Override
			String getConfigXMLLocation() {
				return System.getProperty("file.separator") + path + "config.xml";
			}
		};
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstuction_fileNotFound() throws Exception {
		new Bootstrap(){
			@Override
			String getConfigXMLLocation() {
				return null;
			}
		};
	}
	
	@Test
	public void testGetters() throws Exception { 
		Bootstrap bs = new Bootstrap(){
			@Override
			String getConfigXMLLocation() {
				return BootstrapTest.getConfigXMLLocation();
			}
		};
		assertEquals("testApp", bs.applicationSettings.getApplicationName());
		assertEquals("0.1",		bs.applicationSettings.getApplicationVersion());
	}

	@Test
	public void test_getDomainClasses() throws Exception {
		Map<String,Class<?>> domainClasses = new Bootstrap() {
			@Override
			String getConfigXMLLocation() {
				return BootstrapTest.getConfigXMLLocation();
			}
		}.getDomainClasses();
		assertEquals(5, domainClasses.size());
		assertEquals(Domain.class, domainClasses.get(StrippedName.stripName(Domain.class)));
		assertEquals(ReadWriteServerImpl.class, domainClasses.get(StrippedName.stripName(ReadWriteServerImpl.class)));
		assertEquals(ReadWriteServerDynImpl.class, domainClasses.get(StrippedName.stripName(ReadWriteServerDynImpl.class)));
		assertEquals(SerializableRWExample.class, domainClasses.get(StrippedName.stripName(SerializableRWExample.class)));
		assertEquals(ReadWriteExample.class, domainClasses.get(StrippedName.stripName(ReadWriteExample.class)));
	}
	
	@Test
	public void test_getViewClasses() throws Exception {
		Map<String,Class<?>> viewClasses = new Bootstrap() {
			@Override
			String getConfigXMLLocation() {
				return BootstrapTest.getConfigXMLLocation();
			}
		}.getViewClasses();
		assertEquals(2, viewClasses.size());
		assertEquals(View.class, viewClasses.get(StrippedName.stripName(View.class)));
		assertEquals(ViewFromSubPkg.class, viewClasses.get(StrippedName.stripName(ViewFromSubPkg.class)));
	}
	
	@Test
	public void test_getControllerClasses() throws Exception {
		Map<String,Class<?>> controllerClasses = new Bootstrap() {
			@Override
			String getConfigXMLLocation() {
				return BootstrapTest.getConfigXMLLocation();
			}
		}.getControllerClasses();
		assertEquals(2, controllerClasses.size());
		assertEquals(Controller.class, controllerClasses.get(StrippedName.stripName(Controller.class)));
		assertEquals(ControllerFromSubPkg.class, controllerClasses.get(StrippedName.stripName(ControllerFromSubPkg.class)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMain_throwExceptionIfNoArgs_null() throws Exception {
		Bootstrap.main(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMain_throwExceptionIfNoArgs_emptyArray() throws Exception {
		Bootstrap.main(new String[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMain_throwExceptionIfNoArgs_arrayNullElement() throws Exception {
		Bootstrap.main(new String[]{null});
	}
	
	protected static String getConfigXMLLocation(){
		URL url = BootstrapTest.class.getResource("");
		final String path = url.toExternalForm().replace("file:/", "");
		String fileSeparator = System.getProperty("file.separator");
		String pathPrefix = path.indexOf(":/") > 0 ? "" : fileSeparator;
		String resultingPath = pathPrefix + path + "config.xml";
		return resultingPath;
	}
}
