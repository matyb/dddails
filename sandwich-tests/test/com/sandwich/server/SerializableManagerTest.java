package com.sandwich.server;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.serialiazable.transferobject.TransferObject;
import com.sandwich.shared.serialiazable.util.HasKey;
import com.sandwich.shared.serialiazable.util.MethodConstants;
import com.sandwich.shared.serialiazable.util.MethodSuitability;
import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.shared.serialiazable.util.map.E;
import com.sandwich.test.TestUtils;
import com.sandwich.testclasses.BusinessPOJO;
import com.sandwich.testclasses.BusinessPOJOEntity;
import com.sandwich.testclasses.BusinessPOJOImpl;
import com.sandwich.testclasses.DynamicPOJO;
import com.sandwich.testclasses.NestedBusObjPOJOImpl;
import com.sandwich.testclasses.RandomBusObj;
import com.sandwich.testclasses.RandomBusObjTO;
import com.sandwich.testclasses.SerializablePOJO;
import com.sandwich.testclasses.SomeRandomBusObjChild;
import com.sandwich.testclasses.SomeRandomBusObjImpl;
import com.sandwich.testclasses.SomeRandomMutableBusObj;
import com.sandwich.testclasses.SomeRandomMutableBusObjChild;

@SuppressWarnings({"unchecked","unused"})
public class SerializableManagerTest extends TestCase {

	@Test
	public void testTOCreation() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		SomeRandomBusObjImpl bl = new SomeRandomBusObjImpl(number, name, list);
		DTO to = SerializableManager.createTransferObject(bl);
		assertEquals(SomeRandomBusObjImpl.class.getName(), to.getBusObjClassName());
		Method[] m = RandomBusObj.class.getDeclaredMethods();
		int three = MethodConstants.GET.length();
		assertEquals(name, to.get(m[0].getName().substring(three)));
		assertEquals(number, to.get(m[1].getName().substring(three)));
		assertEquals(list, to.get(m[2].getName().substring(three)));
	}
	
	@Test
	public void testTOCreation_actualSerialization() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		SomeRandomBusObjImpl bl = new SomeRandomBusObjImpl(number, name, list);
		DTO to = SerializableManager.createTransferObject(bl);
		DTO postSerialization = TestUtils.serialize(to);
		assertEquals(to, postSerialization);
		assertTrue(to != postSerialization);
	}
	
	@Test
	public void testBusObjNotSerializable() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		SomeRandomBusObjImpl bl = new SerializableBusObj(number, name, list);
		try{
			TestUtils.serialize((Serializable)bl);
			Assert.fail();
		}catch(RuntimeException re){
			assertEquals(NotSerializableException.class, re.getCause().getClass());
		}
	}
	
	class SerializableBusObj extends SomeRandomBusObjImpl implements Serializable { //invalid @see testBusObjNotSerializable

		private static final long serialVersionUID = -5161365596671308910L;
		public SerializableBusObj(Long number, String name, List<?> list) {
			super(number,name,list);
		} 
	}
	
	@Test
	public void testTOCreationWhenExtending() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		String extraField = "randomtext";
		SomeRandomBusObjImpl bl = new SomeRandomBusObjChild(number, name, list, extraField);
		DTO to = SerializableManager.createTransferObject(bl);
		Method[] m = SomeRandomBusObjChild.class.getMethods();
		assertEquals(extraField, to.get(m[0].getName().substring(MethodConstants.GET.length())));
		assertEquals(name, to.get(m[1].getName().substring(MethodConstants.GET.length())));
		assertEquals(number, to.get(m[2].getName().substring(MethodConstants.GET.length())));
		assertEquals(list, to.get(m[3].getName().substring(MethodConstants.GET.length())));
		assertEquals(SomeRandomBusObjChild.class.getName(),to.getBusObjClassName());
		assertEquals(5, to.getAll().size());
	}
	
	@Test
	public void testTOCreationWhenExtending_getAllIterationOrder() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		String extraField = "randomtext";
		String busObjClassName = SomeRandomBusObjChild.class.getName();
		SomeRandomBusObjImpl bl = new SomeRandomBusObjChild(number, name, list, extraField);
		DTO to = SerializableManager.createTransferObject(bl);
		Iterator<Entry<String, Serializable>> fields = to.getAll().iterator();
		// 1st entry
		Entry<String, Serializable> e = fields.next();
		assertEquals("ExtraField", e.getKey());
		assertEquals(extraField, ((ReadOnly<?>)e.getValue()).getValue());
		
		e = fields.next();
		assertEquals("Name", e.getKey());
		assertEquals(name, ((ReadOnly<?>)e.getValue()).getValue());
		
		e = fields.next();
		assertEquals("Number", e.getKey());
		assertEquals(number, ((ReadOnly<?>)e.getValue()).getValue());
		
		e = fields.next();
		assertEquals("List", e.getKey());
		assertEquals(list, ((ReadOnly<?>)e.getValue()).getValue());
		
		e = fields.next();
		assertEquals("busObjClassName", e.getKey());
		assertEquals(busObjClassName, e.getValue());
		assertFalse(fields.hasNext());
		// does not contain child field - not referenced via interface
	}
	
	@Test
	public void testBusObjCreationFromImmutableBusObjTransferObjectConversion() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(
				SerializableManager.createTransferObject(new SomeRandomBusObjImpl(number, name, list)));
		// values are not mutable - thus, not safe to set from values
		// crossing into service layer from client - should be uninitialized
		assertEquals(0l,   TestUtils.getValue("number",bo));
		assertEquals(null, TestUtils.getValue("name",bo));
		assertEquals(null, TestUtils.getValue("list",bo));
	}

	@Test
	public void testBusObjCreationFromMutableBusObjTransferObjectConversion() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(
				SerializableManager.createTransferObject(new SomeRandomMutableBusObj(number, name, list)));
		// BusObj implements a Mutable interface - values are set from TO
		assertEquals(number, TestUtils.getValue("number",bo));
		assertEquals(name,   TestUtils.getValue("name",bo));
		assertEquals(list,   TestUtils.getValue("list",bo));
	}
	
	@Test
	public void testBusObjCreationFromTO_childClass() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		String extraField = "extraField";
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(
				SerializableManager.createTransferObject(
						new SomeRandomMutableBusObjChild(number, name, list, extraField)));
		assertEquals(number, TestUtils.getValue("number",bo));
		assertEquals(name, TestUtils.getValue("name",bo));
		assertEquals(list, TestUtils.getValue("list",bo));
		assertEquals(extraField, TestUtils.getValue("extraField",bo));
	}
	
	@Test
	public void testBusObjCreationFromTO_childClassSerializability() throws Exception {
		DTO to = SerializableManager.createTransferObject(
				new SomeRandomMutableBusObjChild(-251l, "meh", Arrays.asList("meh",123), "extraField"));
		assertEquals(to, TestUtils.serialize(to));
	}
	
	@Test
	public void testModifyingTOAndConvertingToBusObj_mutable() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(new SomeRandomMutableBusObj(number, name, list));
		String newName = "newName";
		to.set("Name", newName);
		Map<String, Serializable> map = (Map<String,Serializable>)TestUtils.getValue("map", to);
		assertEquals(new ReadOnly<Serializable>(newName), map.get("Name"));
		
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(to);
		assertEquals(number,  bo.getNumber());
		assertEquals(newName, bo.getName()); 
		assertEquals(list, 	  bo.getList()); 
	}
	
	@Test
	public void testModifyingTOAndConvertingToBusObj_immutable_noChanges() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(new SomeRandomBusObjImpl(number, name, list));
		
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(to);
		// no mutator methods - so bus obj gets defaults, nothing from client is set as they are read only
		assertEquals(0l, (long)bo.getNumber());
		assertNull(bo.getName()); 
		assertNull(bo.getList()); 
	}
	
	@Test
	public void testModifyingTOAndConvertingToBusObj_immutable_throwRuntime() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(new SomeRandomBusObjImpl(number, name, list));
		try{
			to.set("Name", "newName");
			fail();
		}catch(RuntimeException rte){
			assertEquals(new ReadOnly<Serializable>(name).toString()+" is ReadOnly",rte.getMessage());
		}
	}
	
	@Test
	public void testModifyingTOAndConvertingToBusObj_conversionToNull() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(new SomeRandomBusObjImpl(number, name, list));
		Map<String, Serializable> map = (Map<String,Serializable>)TestUtils.getValue("map", to);
		
		assertEquals(new ReadOnly<Serializable>(number), 	map.get("Number"));
		assertEquals(new ReadOnly<Serializable>(name),		map.get("Name")); 
		assertEquals(new ReadOnly<Serializable>((Serializable)list),
															map.get("List"));
	}
	
	@Test
	public void testNullValueClassOfFieldIsSubstituted() throws Exception {
		Long number = -251l;
		String name = null;
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(
				new SomeRandomBusObjImpl(number, name, list));
		assertNull(to.get("Name"));
		assertEquals(String.class, to.getValueClass("Name"));
	}
	
	@Test
	public void testNullValueIsNullWhenConvertedToBusinessObject() throws Exception {
		Long number = -251l;
		String name = null;
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(
				new SomeRandomBusObjImpl(number, name, list));
		SomeRandomBusObjImpl bo = SerializableManager.createBusObj(to);
		assertNull(TestUtils.getValue("name",bo));
	}
	
	@Test
	public void testModifiabilityOfGetAll() throws Exception {
		Long number = -251l;
		String name = "meh";
		List<?> list = Arrays.asList("meh",123);
		DTO to = SerializableManager.createTransferObject(new SomeRandomBusObjImpl(number, name, list));
		Iterator<Entry<String, Serializable>> values = to.getAll().iterator();
		
		values.next().setValue("stillMutable");
		values.next().setValue(100l);
		values.next().setValue((Serializable)Arrays.asList(1,"random"));
		
		assertEquals(100l, to.get("Number"));
		assertEquals("stillMutable", to.get("Name"));
		assertEquals(Arrays.asList(1,"random"), to.get("List"));
	}
	
	@Test
	public void testModifiabilityOfGetAll_throwsException() throws Exception {
		Set<Entry<String, Serializable>> toGetAllSet = SerializableManager
				.createTransferObject(new SomeRandomBusObjImpl(0l, null, null)).getAll();
		E element = new E("name",null);
		try{
			toGetAllSet.add(element);
			fail();
		}catch(Throwable t){
			assertTrue(t instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testAnonymousInnerClass_evenMutableValuesAreReadOnly() throws Exception {
		final int number = 255;
		final String string = "meh";
		DTO to = SerializableManager.createTransferObject(new BusObj(){
			public Integer getInteger(){return number;}
			public void setString(String s){}
			public String getString(){return string;}
		});
		assertEquals(BusObj.WILL_NOT_RETURN_TO_BUS_OBJ, to.getBusObjClassName());
		Iterator<Entry<String,Serializable>> entries = to.getAll().iterator();
		assertEquals(new ReadOnly<Integer>(number), entries.next().getValue());
		assertEquals(new ReadOnly<String>(string), entries.next().getValue());
		assertEquals(BusObj.WILL_NOT_RETURN_TO_BUS_OBJ, entries.next().getValue());
		assertFalse(entries.hasNext());
	}
	
	@Test
	public void testAnonymousInnerClass_refuseReturnToBusObj() throws Exception {
		final int number = 255;
		final String string = "meh";
		try{
			SerializableManager.createBusObj(SerializableManager.createTransferObject(new BusObj(){
				public Integer getInteger(){return number;}
				public void setString(String s){}
				public String getString(){return string;}
			}));
			fail();
		}catch(IllegalArgumentException x){
			
		}
	}
	
	@Test
	public void testDynamicProxyGeneration() throws Exception {
		RandomBusObj busObj = new SomeRandomBusObjImpl();
		RandomBusObjTO to = SerializableManager.createDynamicProxyTO(
				(BusObj)busObj, RandomBusObjTO.class);
		assertEquals(busObj.getClass().getName(),	((TransferObject)to).getBusObjClassName());
		assertEquals(busObj.getName(),				to.getName());
		assertEquals(busObj.getList(),				to.getList());
		assertEquals(busObj.getNumber(),			to.getNumber());
	}
	
	
	@Test
	public void testDynamicPojo_isNotABusObj_busObj() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				BusinessPOJO.class, SerializablePOJO.class);
		assertFalse(BusinessPOJO.class.isAssignableFrom(pojo.getClass()));
		assertFalse(Entity.class.isAssignableFrom(pojo.getClass()));
		assertFalse(BusObj.class.isAssignableFrom(pojo.getClass()));
	}
	
	@Test
	public void testDynamicPojo_isSerializeable_busObj() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				BusinessPOJO.class, SerializablePOJO.class);
		assertTrue(DynamicPOJO.class.isAssignableFrom(pojo.getClass()));
		assertTrue(DTO.class.isAssignableFrom(pojo.getClass()));
		assertFalse(HasKey.class.isAssignableFrom(pojo.getClass()));
		assertTrue(Serializable.class.isAssignableFrom(pojo.getClass()));
	}
	
	@Test
	public void testDynamicPojo_behaviorInference_busObj() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				BusinessPOJO.class, SerializablePOJO.class);
		pojo.setColorOfOranges("orange");
		assertEquals("orange",pojo.getColorOfOranges());
		assertNull(pojo.getNoOfApples());
		pojo.setNoOfApples(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), pojo.getNoOfApples());
	}
	
	@Test
	public void testDynamicPojo_busObj_instanceTransformation() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		assertEquals("orange",pojo.getColorOfOranges());
		assertEquals(Integer.valueOf(5), pojo.getNoOfApples());
	}
	
	@Test
	public void testDynamicPojo_busObj_transformBackToBusObj() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		BusinessPOJOImpl businessPojo = SerializableManager.createBusObj((DTO)pojo);
		assertEquals(businessPojo.getColorOfOranges(),  pojo.getColorOfOranges());
		assertEquals(businessPojo.getNoOfApples(), 		pojo.getNoOfApples());
		assertFalse(businessPojo.getClass() == pojo.getClass());
	}
	
	@Test
	public void testDynamicPojoActualSerialization() throws Exception {
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		assertEquals(pojo, TestUtils.serialize((Serializable)pojo));
	}
	
	@Test
	public void testDynamicPojo_methodSuitabilityUsage() throws Exception {
		testDynamicPojo_methodSuitabilityUsage(24, BusinessPOJO.class, new Class[]{
				Proxy.class, DTO.class, SerializablePOJO.class, Cloneable.class
		});
	}
	
	@Test
	public void testDynamicPojo_methodSuitabilityUsageWithEntity_extendsHasKey() throws Exception {
		testDynamicPojo_methodSuitabilityUsage(25, BusinessPOJOEntity.class, new Class[]{
			Proxy.class, DTO.class, SerializablePOJO.class, HasKey.class, Cloneable.class	
		});
	}
	
	private void testDynamicPojo_methodSuitabilityUsage(int totalNumberOfMethods, 
			Class<? extends BusinessPOJO> pojoClass, Class<?>[] classInheritedFrom){
		DynamicPOJO pojo = SerializableManager.createDynamicProxyTO(pojoClass, SerializablePOJO.class);
		Method[] methods = pojo.getClass().getMethods();
		
		assertEquals(totalNumberOfMethods, methods.length);
	
		List<Method> pojoMethods = new ArrayList<Method>(Arrays.asList(methods));
		Set<MethodSuitability> suitabilities = new HashSet<MethodSuitability>(); 
		// convert the pojoMethods to MethodSuitability 
		for(Method m : pojoMethods){
			suitabilities.add(new MethodSuitability(m));
		}
		
		assertEquals(totalNumberOfMethods, suitabilities.size());
		for(Class<?> c : classInheritedFrom){
			// two assertions appear contradictory - these show the methods
			// asserted inherited from are assigned when proxy is formed, and
			// not inherited from the pojoClass
			assertFalse(c.isAssignableFrom(pojoClass));
			assertTrue(c.isInstance(pojo));
			totalNumberOfMethods -= c.getMethods().length;
		}
		assertEquals(0, totalNumberOfMethods);
	}
	
	@Test
	public void testNestedBusObj_createDTO_nullNestedBusObjReference() throws Exception {
		DTO to = SerializableManager.createTransferObject(NestedBusObjPOJOImpl.class);
		assertNull(to.get("BusinessPOJO"));
		ReadOnly<Serializable> ro = (ReadOnly<Serializable>)
			((Map<String,Serializable>)TestUtils.getValue("map", to)).get("BusinessPOJO");
		assertEquals(
				new ReadOnly<Serializable>(new Null("com.sandwich.testclasses.DynamicPOJO")), ro);
	}
	
	@Test
	public void testNestedBusObj_createDTO_populateNestedBusObjReference_throwsException() throws Exception {
		DTO to = SerializableManager.createTransferObject(NestedBusObjPOJOImpl.class);
		try{
			to.set("BusinessPOJO", SerializableManager.createTransferObject(BusinessPOJOImpl.class));
			fail(); // interface has no setter... so should not be able to set
		}catch(RuntimeException rte){
			assertEquals(new ReadOnly<Serializable>(new Null(DynamicPOJO.class.getName())) + " is ReadOnly", rte.getMessage());
		}
	}
	
	@Test
	public void testNestedBusObj_createDTO_populateNestedBusObjReference() throws Exception {
		DTO to = SerializableManager.createTransferObject(NestedBusObjPOJOImpl.class);
		((Map<String, Serializable>)TestUtils.getValue("map", to))
			.put("getBusinessPOJO",SerializableManager.createTransferObject(BusinessPOJOImpl.class));
		assertEquals(SerializableManager.createTransferObject(BusinessPOJOImpl.class), to.get("getBusinessPOJO"));
		NestedBusObjPOJOImpl bp = SerializableManager.createBusObj(to);
		assertNull(bp.getBusinessPOJO());
	}
	
	@Test
	public void testNestedBusObj_createDTO_serialization() throws Exception {
		DTO to0 = SerializableManager.createTransferObject(NestedBusObjPOJOImpl.class);
		((Map<String, Serializable>)TestUtils.getValue("map", to0))
			.put("getBusinessPOJO",SerializableManager.createTransferObject(BusinessPOJOImpl.class));
		DTO to1 = SerializableManager.createTransferObject(NestedBusObjPOJOImpl.class);
		((Map<String, Serializable>)TestUtils.getValue("map", to1))
			.put("getBusinessPOJO",SerializableManager.createTransferObject(BusinessPOJOImpl.class));
		assertEquals(TestUtils.serialize(to0),to1);
		assertEquals(TestUtils.serialize(to1).get("getBusinessPOJO"),to0.get("getBusinessPOJO"));
		assertFalse(to0 == to1);
	}
	
	@Test
	public void testEqualsContractBetweenTOs() throws Exception {
		DTO to0 = SerializableManager.createTransferObject(new BusObj(){
			public Integer getValue(){
				return 1;
			}
		});
		DTO to1 = SerializableManager.createTransferObject(new BusObj(){
			public Integer getValue(){
				return 1;
			}
		});
		DTO to2 = SerializableManager.createTransferObject(new BusObj(){
			public Integer getValue(){
				return 1;
			}
		});
		TestUtils.assertEqualsContractEnforcement(to0, to1, to2);
	}
}


