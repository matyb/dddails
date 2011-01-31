package com.sandwich.shared.oneholder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sandwich.server.DAOFactory;
import com.sandwich.server.SerializableManager;
import com.sandwich.server.oneholder.MutableOneHolderEntity;
import com.sandwich.server.oneholder.OneHolderEntity;
import com.sandwich.server.oneholder.dao.OneHolderDAO;
import com.sandwich.server.oneholder.dao.OneHolderDAOImpl;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.shared.validation.ErrorCode;
import com.sandwich.shared.validation.FieldValidator;
import com.sandwich.shared.validation.Validatable;
import com.sandwich.shared.validation.ValidationResponse;
import com.sandwich.test.TestUtils;

public class ReallySimpleValidatableTOImplTest {
	
	@Before
	public void setUp(){
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl());
	}
	
	@Test
	public void testNoErrors_to() throws Exception{
		FieldValidator.validate(new OneHolderTO(1));
	}
	
	@Test
	public void testNoErrors_entity() throws Exception{
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		FieldValidator.validate(new OneHolderEntity(1));
	}
	
	@Test
	public void testNoMutation_to() throws Exception{
		Integer ref = 1;
		checkOneHolder_noMutation(ref, new OneHolderTO(ref));
	}
	
	@Test
	public void testNoMutation_entity() throws Exception{
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		Integer ref = 1;
		checkOneHolder_noMutation(ref, new OneHolderEntity(ref));
	}
	
	public void checkOneHolder_noMutation(Integer ref, OneHolder re) throws Exception{
		FieldValidator.validate(re);
		assertEquals(ref, re.getOne());
		ref++;
		assertFalse(ref.equals(re.getOne()));
		assertEquals((int)1, (int)re.getOne());
	}
	
	@Test
	public void testNullArgumentConstruction_to() throws Exception{
		assertOffenderAndCodes(new OneHolderTO(null),OneHolderErrorCodes.NULL_NUMBER);
	}
	
	static void assertOffenderAndCodes(Validatable validatable, ErrorCode... codes) {
		List<ValidationResponse<Validatable>> validationResponses = validatable.getValidation().validate(validatable);
		assertEquals(codes.length, validationResponses.size());
		for(ValidationResponse<Validatable> validationResponse : validationResponses){
			assertEquals(
					validationResponse.getErrorCode(), 
					codes[validationResponses.indexOf(validationResponse)]);
		}
	}

	@Test
	public void testNullArgumentConstruction_entity() throws Exception{
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		assertOffenderAndCodes(new OneHolderEntity(null),OneHolderErrorCodes.NULL_NUMBER);
	}
	
	@Test
	public void testGetIsntOne_to() throws Exception{
		assertOffenderAndCodes(new OneHolderTO(0),OneHolderErrorCodes.NOT_ONE);
	}

	@Test
	public void testGetIsntOne_entity() throws Exception{
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		assertOffenderAndCodes(new OneHolderEntity(0),OneHolderErrorCodes.NOT_ONE);
	}
	
	@Test
	public void testCreationWNonOneValueNoException_to() throws Exception {
		new OneHolderTO(Integer.MAX_VALUE);
	}
	
	@Test
	public void testCreationWNonOneValueNoException_entity() throws Exception {
		new OneHolderEntity(Integer.MAX_VALUE);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAssertOneFailure_to() throws Exception {
		OneHolder re = new OneHolderTO(0);
		re.assertOne();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAssertOneFailure_entity() throws Exception {
		OneHolder re = new OneHolderEntity(0);
		re.assertOne();
	}
	
	@Test
	public void testServerSideValidation_entity(){
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return false;
			}
		});
		assertOffenderAndCodes(new OneHolderEntity(1),OneHolderErrorCodes.NOT_UNIQUE);
	}
	
	@Test
	public void testDynamicProxyOneHolder_immutableInterface() throws Exception {
		SerializableOneHolder oh = SerializableManager.createDynamicProxyTO(
				new OneHolderEntity(1), SerializableOneHolder.class);
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		oh = TestUtils.serialize(oh);
		assertEquals(Integer.valueOf(1),oh.getOne());
		// no setter in interface, value will be null as it's implied it was 
		// read only
		OneHolderEntity busObj = SerializableManager.createBusObj((DTO)oh);
		assertNull(busObj.getOne());
		assertOffenderAndCodes(busObj, OneHolderErrorCodes.NULL_NUMBER);
	}
	
	@Test
	public void testDynamicProxyOneHolder_mutableInterface() throws Exception {
		MutableSerializableOneHolder oh = SerializableManager.createDynamicProxyTO(
				new MutableOneHolderEntity(1), MutableSerializableOneHolder.class);
		DAOFactory.setAlternateImpl(OneHolderDAO.class, new OneHolderDAOImpl(){
			@Override
			public boolean isUnique(String key){
				return true;
			}
		});
		oh = TestUtils.serialize(oh);
		assertEquals(Integer.valueOf(1),oh.getOne());
		// there's a setter in the interface - so it's writeable, value will be
		// Integer.valueOf(1) after converstion to BusObj
		OneHolderEntity busObj = SerializableManager.createBusObj((DTO)oh);
		assertEquals(Integer.valueOf(1), busObj.getOne());
		assertOffenderAndCodes(busObj);
	}
	
	private static interface SerializableOneHolder extends Serializable,OneHolder {
		
	}	
	
	private static interface MutableSerializableOneHolder extends Serializable, MutableOneHolder {
		
	}
	
	final static boolean[] called = {false};
	@Test
	public void testMethodsOfCustomTOsAreUtilized() throws Exception {
		SerializableOneHolder to = TestUtils.serialize(SerializableManager.createDynamicProxyTO(
				OneHolderEntity.class, 
				SerializableOneHolder.class,
				new OneHolderTOChildClass()));
		assertTrue(Proxy.isProxyClass(to.getClass()));
		assertTrue(to instanceof DTO);
		
		assertFalse(called[0]);
		to.assertOne();
		assertTrue(called[0]);
	}
	
	static class OneHolderTOChildClass extends OneHolderTO {
		public OneHolderTOChildClass() {
			super(1);
		}
		private static final long serialVersionUID = 6831918652388137723L;
		@Override
		public void assertOne() {
			called[0] = true;
		}
	}
}