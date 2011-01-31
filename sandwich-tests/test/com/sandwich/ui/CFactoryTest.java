package com.sandwich.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Container;
import java.io.Serializable;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.easymock.EasyMock;
import org.junit.Test;

import com.sandwich.server.SerializableManager;
import com.sandwich.shared.serialiazable.sandwichobject.ReadOnly;
import com.sandwich.shared.serialiazable.sandwichobject.SandwichWrapper;
import com.sandwich.shared.serialiazable.transferobject.DTO;
import com.sandwich.test.TestUtils;
import com.sandwich.testclasses.BusinessPOJOImpl;
import com.sandwich.testclasses.DynamicPOJO;
import com.sandwich.testclasses.SerializablePOJO;
import com.sandwich.ui.components.DTOPanel;
import com.sandwich.ui.components.LabeledComponent;
import com.sandwich.ui.components.MutableComponent;
import com.sandwich.ui.components.SandwichLabel;
import com.sandwich.ui.components.SandwichTextBox;
import com.sandwich.ui.components.factory.CFactory;


public class CFactoryTest {

	@Test
	public void testCreateComponent_string_noKey() throws Exception {
		LabeledComponent<String> lc = CFactory.createComponent("","");
		assertEquals("description-+.null", lc.getText()); // adds the . symbol to seperate key from class
		SandwichLabel label = lc.getComponent();
		assertEquals("", label.getText());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateComponent_dto_noKey() throws Exception {
		String expectedLabelSuffix = "busObjClassName";
		DTO to = EasyMock.createMock(DTO.class);
		EasyMock.expect(to.getBusObjClassName()).andReturn(expectedLabelSuffix);
		EasyMock.expect(to.getAll()).andReturn(Collections.EMPTY_SET);
		EasyMock.replay(to);
		
		LabeledComponent<DTO> lc = CFactory.createComponent("",to);
		assertEquals("description-+"+to+'.'+expectedLabelSuffix, lc.getText()); // adds the . symbol to separate key from class
		DTOPanel panel = lc.getComponent();
		assertEquals(0, ((Container)panel).getComponentCount());
		EasyMock.verify(to);
	}
	
	@Test
	public void testCreateComponent_string_withKey() throws Exception {
		LabeledComponent<String> lc = CFactory.createComponent("key", " meh");
		assertEquals("description-+ meh.key"+null,lc.getText());
		assertOnComponentCreatedWithStringArg(lc.getComponent());
	}
	
	@Test
	public void testLabelCreation_readOnly() throws Exception {
		MutableComponent<String> label = CFactory.createComponent(new ReadOnly<String>(" meh"));
		assertOnComponentCreatedWithStringArg(label);
	}
	
	@Test
	public void testLabelCreation_string() throws Exception {
		MutableComponent<String> label = CFactory.createComponent(" meh");
		assertOnComponentCreatedWithStringArg(label);
	}

	private void assertOnComponentCreatedWithStringArg(
			MutableComponent<String> label) {
		assertTrue(label instanceof SandwichLabel);
		assertTrue(label instanceof JLabel);
		assertEquals(" meh", label.getValue());
	}
	
	
	
	@Test
	public void testTextBoxCreation_hasValue() throws Exception {
		MutableComponent<String> textField = CFactory.createComponent(new SandwichWrapper<String>(" meh"));
		assertTrue(textField instanceof SandwichTextBox);
		assertEquals(" meh", textField.getValue());
		assertEquals(false, ((JTextField)textField).isEnabled());
	}
	
	@Test
	public void testDTOToPanelAndBackAgain() throws Exception {
		DynamicPOJO originalPojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		BusinessPOJOImpl businessPojo = SerializableManager.createBusObj((DTO)originalPojo);

		// adds unnecessary length - just showing no tricks up sleeve
		assertEquals(businessPojo.getColorOfOranges(),  originalPojo.getColorOfOranges());
		assertEquals("orange",  						originalPojo.getColorOfOranges());
		assertEquals(businessPojo.getNoOfApples(), 		originalPojo.getNoOfApples());
		assertEquals(Integer.valueOf(5),		 		originalPojo.getNoOfApples());
		assertFalse(Serializable.class.isAssignableFrom(businessPojo.getClass()));
		assertTrue(	Serializable.class.isAssignableFrom(originalPojo.getClass()));
		
		DTOPanel panel = new DTOPanel((DTO)originalPojo);
		assertEquals(originalPojo, panel.getValue());
		assertFalse(originalPojo == panel.getValue());
		assertEquals("orange",  						originalPojo.getColorOfOranges());
		assertEquals(originalPojo.getColorOfOranges(),	panel.getValue().get("ColorOfOranges"));
		
		originalPojo.setColorOfOranges("red");
		assertEquals("red",		originalPojo.getColorOfOranges());
		assertEquals("orange",	panel.getValue().get("ColorOfOranges"));
	}
	
	@Test
	public void testDTOToPanelAndBackToSerializedFormAgain() throws Exception {
		DynamicPOJO originalPojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		DTOPanel panel = new DTOPanel((DTO)originalPojo);
		assertEquals(originalPojo, TestUtils.serialize(panel.getValue()));
		assertFalse(originalPojo == panel.getValue());
	}
	
	@Test
	public void testDTOToPanelAndBackToBOAgain() throws Exception {
		DynamicPOJO originalPojo = SerializableManager.createDynamicProxyTO(
				new BusinessPOJOImpl(), SerializablePOJO.class);
		BusinessPOJOImpl businessPojo = SerializableManager.createBusObj((DTO)originalPojo);
		DTOPanel panel = new DTOPanel((DTO)originalPojo);
		BusinessPOJOImpl fromPanel = SerializableManager.createBusObj(panel.getValue());
		assertEquals(businessPojo, fromPanel);
		assertFalse(businessPojo == fromPanel);
	}
}
