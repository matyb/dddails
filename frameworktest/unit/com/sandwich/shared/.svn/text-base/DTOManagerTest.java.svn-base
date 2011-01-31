package com.sandwich.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.sandwich.server.ApplicationBootstrappingTestCase;
import com.sandwich.server.domain.subpkg.ReadWriteExample;
import com.sandwich.server.domain.subpkg.ReadWriteServerImpl;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TOImpl;
import com.sandwich.test.TestUtils;

public class DTOManagerTest extends ApplicationBootstrappingTestCase{

	@Before
	public void resetApplication() throws Exception{
		super.resetApplication();
		bootstrapDotMain();
	}
	
	@Test
	public void testGetBusObjNames() throws Exception {
		// Set returned from getBusObjNames is currently backed by LinkedHashMap
		// - this'll need updated if ordering is lost
		Collection<String> busObjNames = DTOManager.getBusObjNames();
		// standard DTO/BusObj tandem, fully qualified BusObj classname is used as key
		assertTrue(busObjNames.contains(ReadWriteServerImpl.class.getName()));
		// utilizes dynamic DTO facade, known externally by DTO interfaces bas
		assertTrue(busObjNames.contains(StrippedName.stripName(ReadWriteExample.class)));
	}
	
	@Test
	public void testGetObjectClone_standardDTO() throws Exception {
		DTO stdDTO = DTOManager.getDTOClone(ReadWriteServerImpl.class.getName());
		assertTrue(stdDTO instanceof DTO);
		assertTrue(stdDTO instanceof TOImpl);
		assertFalse(stdDTO instanceof ReadWriteExample);
		assertFalse(stdDTO instanceof ReadWriteServerImpl);
	}
	
	@Test
	public void testGetObjectClone_dtoFacade() throws Exception {
		DTO stdDTO = DTOManager.getDTOClone(StrippedName.stripName(ReadWriteExample.class));
		assertTrue(stdDTO instanceof DTO);
		assertTrue(stdDTO instanceof ReadWriteExample);
		assertFalse(stdDTO instanceof ReadWriteServerImpl);
	}
	
	@Test
	public void testGetObjectClone() throws Exception {
		for(String name : DTOManager.getBusObjNames()){
			DTO clone 	= DTOManager.getDTOClone(name);
			DTO clone1 	= DTOManager.getDTOClone(name);
			DTO clone2 	= (DTO)clone.clone();
			
			assertEquals(clone, clone1);
			assertEquals(clone2, clone1);
			assertEquals(clone, clone2);
			
			assertNotSame(clone,  clone1);
			assertNotSame(clone1, clone2);
			assertNotSame(clone2, clone);
		}
	}
	
	@Test
	public void testGetObjectClone_serializability() throws Exception {
		for(String name : DTOManager.getBusObjNames()){
			DTO clone 	= DTOManager.getDTOClone(name);
			DTO clone1 	= DTOManager.getDTOClone(name);
			DTO clone2 	= (DTO)clone.clone();
			DTO serialized = TestUtils.serialize(clone);
			
			assertEquals(clone, serialized);
			assertEquals(clone1, serialized);
			assertEquals(clone2, serialized);
			
			assertNotSame(clone,  serialized);
			assertNotSame(clone1, serialized);
			assertNotSame(clone2, serialized);
		}
	}
	
}
