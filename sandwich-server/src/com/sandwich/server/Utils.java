package com.sandwich.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sandwich.shared.serialiazable.util.Predicate;

public class Utils {
	
	/**
	 * Class cannot be instantiated.
	 */
	private Utils(){}

	/**
	 * Always returns true.
	 */
	private static Predicate<?> alwaysMatchesPredicate = new Predicate<Object>(){
		private static final long serialVersionUID = 1L;
		public boolean evaluate(Object t){
			return true;
		}
	};
	
	/**
	 * If not null.
	 */
	private static Predicate<?> nonNullPredicate = new Predicate<Object>(){
		private static final long serialVersionUID = 1L;
		public boolean evaluate(Object t){
			return t != null;
		}
	};
	
	/**
	 * Copy any number of passed in arrays into a List<T> and return.
	 * @param <T>
	 * @param ts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> copyArrays(T[]...ts){
		return copyArrays((Predicate<T>)alwaysMatchesPredicate, ts);
	}	
	
	/**
	 * Copy any number of passed in arrays into a List<T> and return.
	 * @param <T>
	 * @param ts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> copyArraysNoNullElements(T[]...ts){
		return copyArrays((Predicate<T>)nonNullPredicate, ts);
	}	
	
	/**
	 *  Copy any number of passed in arrays into a List<T> and return.
	 * 
	 * @param <T>
	 * @param predicate chance to evaluate prior to addition to List<T>
	 * @param tas
	 * @return
	 */
	public static <T> List<T> copyArrays(Predicate<T> predicate, T[]...tas){
		List<T> ts = new ArrayList<T>();
		if(tas != null){
			for(T[] ta : tas){
				if(ta != null){
					for(T t : ta){
						if(predicate.evaluate(t)){
							ts.add(t);
						}
					}
				}
			}
		}
		return ts;
	}

	/**
	 * Converts a string (file path + filename) into an XML Document. 
	 * @param xmlLocation
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Document stringToXML(String xmlLocation) throws FileNotFoundException {
		File configXml = new File(xmlLocation);
		if(configXml == null || !configXml.exists()){
			throw new FileNotFoundException("Specified XML file location "
					+xmlLocation+" does not exist.");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		try {
			return db.parse(configXml);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
