package com.sandwich.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sandwich.server.ApplicationBootstrappingTestCase;
import com.sandwich.server.Entity;
import com.sandwich.server.oneholder.OneHolderEntity;
import com.sandwich.shared.DTOManager;
import com.sandwich.shared.DoTimes;
import com.sandwich.shared.oneholder.OneHolder;
import com.sandwich.shared.oneholder.OneHolderTO;
import com.sandwich.shared.serialiazable.sandwichobject.MutableWrapper;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TransferObject;
import com.sandwich.shared.validation.Validation;

public class PerformanceTests extends ApplicationBootstrappingTestCase{

	@Before
	public void resetApplication() throws Exception{
		super.resetApplication();
		bootstrapDotMain();
	}
	
	@Test(timeout=300)
	public void testGetObjectClone_performance() throws Exception {
		for(String name : DTOManager.getBusObjNames()){
			final DTO clone = DTOManager.getDTOClone(name);
			Field[] fields 	= clone.getClass().getFields();
			
			assertEquals(2, fields.length);
			assertEquals("BUSOBJCLASSNAME_KEY", 	fields[0].getName());
			assertEquals(DTO.BUSOBJCLASSNAME_KEY, 	fields[0].get(DTO.class));
			assertEquals("CLASS_SUFFIX",			fields[1].getName());
			assertEquals(DTO.CLASS_SUFFIX,			fields[1].get(DTO.class));
			
			final List<DTO> clones = new ArrayList<DTO>();
			int oneThousand = 1000;
			TestUtils.chewUpVM();
			long time = TestUtils.time(new DoTimes(oneThousand, new Runnable(){
				public void run() {
						try {
							clones.add((DTO)clone.clone());
						} catch (CloneNotSupportedException e) {
							fail(e.getMessage());
						}
				}
			}));
			// platform dependent assertion - adjust accordingly
			assertTrue("Object cloning performance may have changed to an unnacceptable level: "+time+"ms", 
					200 > time);
			for(int i = 0; i < (oneThousand - 1); i ++){
				DTO clone1 = clones.get(i);
				DTO clone2 = clones.get(i+1);
				assertEquals(clone1, clone2);
				assertNotSame(clone1, clone2);
			}
		}
	}
	
	@Test(timeout=1500)
	public void testTimeDifferenceInstantiationVsCaching_to() throws Exception {
		TestUtils.chewUpVM(); // help ensure lagging vm initialization does not interfere w/ results
		final int times = 5000;
		final OneHolder defaultOneHolder = new OneHolderTO(0);
		@SuppressWarnings("serial")
		final OneHolder doctoredOneHolder = new OneHolderTO(0){
			@SuppressWarnings("unchecked")
			@Override
			public Validation<?> getValidation(){
				try {
					String className = OneHolderTO.class.getName();
					return (Validation<?>) Class.forName(className.substring(0, className.lastIndexOf(TransferObject.CLASS_SUFFIX)) + 
							Validation.CLASS_SUFFIX).getConstructor(new Class[] {}).newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};  
		
		long timeDefaultCached = TestUtils.time(new Runnable(){@Override public void run() { 
			int i = 0;
			do{
				defaultOneHolder.getValidation();
			}while(++i < times);}});
		long timeReflectively = TestUtils.time(new Runnable(){@Override public void run() { 
			int i = 0;
			do{
				doctoredOneHolder.getValidation();
			}while(++i < times);}});
		assertTrue(timeDefaultCached < timeReflectively); // generally 75 vs 250 or so ms
	}
	
	@Test(timeout=150)
	public void testTimeDifferenceInstantiationVsCaching_entity() throws Exception {
		TestUtils.chewUpVM(60); // help ensure lagging vm initialization does not interfere w/ results
		final int times = 500;
		final OneHolder defaultOneHolder = new OneHolderEntity(0);
		final OneHolder doctoredOneHolder = new OneHolderEntity(0){
			@SuppressWarnings("unchecked")
			@Override
			public Validation<?> getValidation(){
				try {
					String className = OneHolderEntity.class.getName();
					return (Validation<?>) Class.forName(className.substring(0, className.lastIndexOf(Entity.CLASS_SUFFIX)) + 
							Validation.CLASS_SUFFIX).getConstructor(new Class[] {}).newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};  
		
		long timeDefaultCached = TestUtils.time(new Runnable(){@Override public void run() { 
			int i = 0;
			do{
				defaultOneHolder.getValidation();
			}while(++i < times);}});
		long timeReflectively = TestUtils.time(new Runnable(){@Override public void run() { 
			int i = 0;
			do{
				doctoredOneHolder.getValidation();
			}while(++i < times);}});
		assertTrue(timeDefaultCached+"ms caching was slower than reflectively "+timeReflectively+"ms",
				timeDefaultCached <= timeReflectively); // generally 75 vs 250 or so ms
	}
		
	@Test(timeout=100)
	public void testMutableWrapperPerformance_creation() throws Exception {
		int numberOfIterations = 1000;
		final List<Object> objects = new ArrayList<Object>(1000);
		final Serializable s = "";
		TestUtils.chewUpVM(60);
		Long oneThousandInstantiations_object = TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			@Override
			public void run() {
				objects.add(new Object());
			}
		}));
		assertEquals(numberOfIterations, objects.size());
		Long oneThousandInstantiations_wrapper = TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			@Override
			public void run() {
				objects.add(new MutableWrapper<Serializable>(s));
			}
		}));
		assertEquals(numberOfIterations * 2, objects.size());
		assertTrue(oneThousandInstantiations_wrapper <= (oneThousandInstantiations_object + 50));
	}
	
	@Test(timeout=100)
	public void testMutableWrapperPerformance_equals() throws Exception {
		int numberOfIterations = 1000;
		final long[] calls = {0};
		TestUtils.chewUpVM(60);
		final Serializable s = "";
		Long oneThousandEquals_object = TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			@Override
			public void run() {
				calls[0]++;
				// depends on creation time, refactor into tests for each timing factor and integration test for all
				new Object().equals(new Object());
			}
		}));
		assertEquals(numberOfIterations, calls[0]);
		Long oneThousandEquals_wrapper = TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			@Override
			public void run() {
				calls[0]++;
				new MutableWrapper<Serializable>(s).equals(new MutableWrapper<Serializable>(s));
			}
		}));
		assertEquals(numberOfIterations * 2, calls[0]);
		assertTrue(oneThousandEquals_wrapper <= (oneThousandEquals_object + 50));
	}
	
	@Test//(timeout=100)
	public void testMutableWrapperPerformance_hashCollectionAccess() throws Exception {
		int numberOfIterations = 1000;
		TestUtils.chewUpVM(60);
		
		final char[] serializables = Arrays.copyOf(
				TestUtils.getRandomText(TestUtils.MAX_UNIQUE_CHARS,true).toCharArray(), 
				numberOfIterations);
		for(int i = 0; i < serializables.length - TestUtils.MAX_UNIQUE_CHARS; i++){
			serializables[i + TestUtils.MAX_UNIQUE_CHARS] = serializables[i];
		}
		final MutableWrapper<?>[] wrappers = new MutableWrapper[numberOfIterations];
		for(int j = 0; j < serializables.length; j++){
			wrappers[j] = new MutableWrapper<Character>(serializables[j]);
		}
		Long oneThousandEquals_wrapper = TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			int counter = 0;
			@Override
			public void run() {
				Object obj = wrappers[counter];
				assertTrue(obj.equals(serializables[counter++]));
			}
		}));
		Long oneThousandEquals_object = getOneThousandObjectEqualsInvocationsTime(
				numberOfIterations, serializables, wrappers);
		long maximumAcceptableTimeForWrapperEquals = Math.round(oneThousandEquals_object + 30);
		String timeExceededString = 
			numberOfIterations + " object equals invocations took: "+oneThousandEquals_object+"ms"
			+ " the same amount of wrapper equals invocations took: "+oneThousandEquals_wrapper+"ms";
		assertTrue(
				timeExceededString,
				oneThousandEquals_wrapper <= maximumAcceptableTimeForWrapperEquals);
	}

	private Long getOneThousandObjectEqualsInvocationsTime(
			final int numberOfIterations, 
			final char[] serializables, final MutableWrapper<?>[] wrappers) {
		return TestUtils.time(new DoTimes(numberOfIterations, new Runnable(){
			int counter = 0;
			@Override
			public void run() {
				Character obj = serializables[counter];
				assertFalse(obj.equals(wrappers[counter++]));
			}
		}));
	}
	
}
