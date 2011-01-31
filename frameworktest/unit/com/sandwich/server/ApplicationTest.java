package com.sandwich.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.IllegalComponentStateException;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Test;

import com.sandwich.client.view.View;
import com.sandwich.server.controller.Controller;
import com.sandwich.server.domain.Domain;
import com.sandwich.server.domain.subpkg.ReadWriteExample;
import com.sandwich.server.domain.subpkg.ReadWriteServerDynImpl;
import com.sandwich.server.domain.subpkg.ReadWriteServerImpl;
import com.sandwich.server.domain.subpkg.SerializableRWExample;
import com.sandwich.shared.StrippedName;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.test.TestUtils;


public class ApplicationTest extends ApplicationBootstrappingTestCase{
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetDomainClassesIsImmutable_remove() throws Exception {
		assertNull(Application.getDomainClasses());
		bootstrapDotMain();
		Map<String,Class<?>> domainClasses = Application.getDomainClasses();
		domainClasses.remove(StrippedName.stripName(Domain.class));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetViewClassesIsImmutable_remove() throws Exception {
		assertNull(Application.getViewClasses());
		bootstrapDotMain();
		Map<String,Class<?>> viewClasses = Application.getViewClasses();
		viewClasses.remove(StrippedName.stripName(View.class));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetControllerClassesIsImmutable_remove() throws Exception {
		assertNull(Application.getControllerClasses());
		bootstrapDotMain();
		Map<String,Class<?>> controllerClasses = Application.getViewClasses();
		controllerClasses.remove(StrippedName.stripName(Controller.class));
	}
	
	@Test
	public void testApplicationInitialized() throws Exception {
		assertNull(TestUtils.getValue("settings", Application.class));
		assertNull(TestUtils.getValue("domainClasses", Application.class));
		assertNull(TestUtils.getValue("controllerClasses", Application.class));
		assertNull(TestUtils.getValue("viewClasses", Application.class));
		bootstrapDotMain();
		assertNotNull(TestUtils.getValue("settings", Application.class));
		assertNotNull(TestUtils.getValue("domainClasses", Application.class));
		assertNotNull(TestUtils.getValue("controllerClasses", Application.class));
		assertNotNull(TestUtils.getValue("viewClasses", Application.class));
	}
	
	@Test(expected=IllegalComponentStateException.class)
	public void testApplicationInitialized2x() throws Exception {
		bootstrapDotMain();
		bootstrapDotMain();
	}
	
	@Test
	public void testGetDTOs_dynamicProxyInstance() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		assertEquals(2, dtos.size());
		
		DTO to = dtos.get("ReadWriteExample");
		Set<Entry<String, Serializable>> allFields = to.getAll();
		assertEquals(5, allFields.size());
		assertEquals(ReadWriteServerDynImpl.class.getName(), to.getBusObjClassName());
		assertEquals("read",  to.get("Read"));
		assertEquals("write", to.get("Write"));
		assertEquals(SerializableRWExample.class, to.get("DTOInterface"));
		assertEquals(ReadWriteExample.class, to.get("BaseInterface"));
		assertTrue(to instanceof SerializableRWExample);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetDTOs_toImpl() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		assertEquals(2, dtos.size());
		
		String busObjClassName = ReadWriteServerImpl.class.getName();
		DTO to = dtos.get(busObjClassName);
		Set<Entry<String, Serializable>> allFields = to.getAll();
		assertEquals(3, allFields.size());
		assertEquals(busObjClassName, to.getBusObjClassName());
		assertNull(to.get("Read"));
		assertNull(to.get("Write"));
		assertFalse(to instanceof SerializableRWExample);
		to.set("Read", "");
	}
	
	@Test
	public void testWriteUpdatable() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get("ReadWriteExample");
		((ReadWriteExample)to).setWrite("ha");
		assertEquals("ha", ((ReadWriteExample)to).getWrite());
	}
	
	@Test
	public void testWriteUpdatable_viaTOImpl() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get("ReadWriteExample");
		to.set("Write","new");
		assertEquals("new", ((ReadWriteExample)to).getWrite());
	}
	
	@Test(expected=UndeclaredThrowableException.class)
	public void testReadReadOnly() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get("ReadWriteExample");
		to.set("Read","new");
	}
	
	@Test(expected=UndeclaredThrowableException.class)
	public void testDTOInterfaceReadOnly() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get("ReadWriteExample");
		to.set("read","");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVanillaDTOReadOnly() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get(ReadWriteServerImpl.class.getName());
		to.set("read","");
	}
	

	@Test
	public void testGetDTOsLogging() throws Exception {
		final Map<Class<?>,LogRecord> logRecords = new HashMap<Class<?>,LogRecord>();
		bootstrapDotMain();
		
		Logger logger = Logger.getLogger(Application.class.getName());
		for(Handler h : logger.getHandlers()){
			logger.removeHandler(h);
		}
		logger.addHandler(new Handler(){
			@Override
			public void publish(LogRecord logrecord) {
				String msg = logrecord.getMessage();
				if(msg.contains(Domain.class.getName())){
					logRecords.put(Domain.class, logrecord);
				}
				else if(msg.contains(SerializableRWExample.class.getName())){
					logRecords.put(SerializableRWExample.class, logrecord);
				}
				else if(msg.contains(ReadWriteExample.class.getName())){
					logRecords.put(ReadWriteExample.class, logrecord);
				}
				else{
					fail("unancticipated domain class: "+msg);
				}
			}
			@Override
			public void close() throws SecurityException {}
			@Override
			public void flush() {}
		});
		assertEquals(0, logRecords.size());
		Application.getDTOs();
		for(Handler h : logger.getHandlers()){
			logger.removeHandler(h);
		}
		assertEquals(3, logRecords.size());
		
		for(Class<?> c : new Class[]{
				Domain.class, ReadWriteExample.class, SerializableRWExample.class}){
			LogRecord record = logRecords.get(c);
			assertEquals(Level.INFO, record.getLevel());
			assertEquals(record.getMessage(),c+Application.NOT_A_BUSOBJ_WONT_BE_IN_UI_MSG);
		}
	}

	@Test
	public void testGetDTOsSerialize() throws Exception {
		bootstrapDotMain();
		Map<String, DTO> dtos = Application.getDTOs();
		DTO to = dtos.get("ReadWriteServerImpl");
		assertEquals(to, TestUtils.serialize(to));
	}
}
