package com.sandwich.server;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sandwich.shared.ApplicationSettings;


public class DefaultApplicationSettings implements ApplicationSettings{
	
	private String appName;
	private String appVersion;
	private ViewFramework viewFramework;
	private String domainPkg;
	private String controllerPkg;
	private String viewPkg;
	private PersistenceFramework persistenceFramework;
	
	public DefaultApplicationSettings(String xmlLocation) {
		try {
			Document xml = Utils.stringToXML(xmlLocation);
			Element element = xml.getDocumentElement();
			element.normalize();
			if("application".equalsIgnoreCase(element.getNodeName())){
				appName = element.getAttribute("name");
				NodeList nodes = element.getChildNodes();
				for(int i = 0; i < nodes.getLength(); i++){
					Node node = nodes.item(i);
					if("version".equalsIgnoreCase(node.getNodeName())){
						setAppVersion(node);
					}else if("view-framework".equalsIgnoreCase(node.getNodeName())){
						setViewFrameWork(node);
					}else if("orm-framework".equalsIgnoreCase(node.getNodeName())){
						setORMFrameWork(node);
					}else if("domain-pkg".equalsIgnoreCase(node.getNodeName())){
						setDomainPackage(node);
					}else if("view-pkg".equalsIgnoreCase(node.getNodeName())){
						setViewPackage(node);
					}else if("controller-pkg".equalsIgnoreCase(node.getNodeName())){
						setControllerPackage(node);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(xmlLocation+" was not found.",e);
		}
	}

	private void setControllerPackage(Node node) {
		controllerPkg = node.getFirstChild().getNodeValue();
	}
	
	private void setViewPackage(Node node) {
		viewPkg = node.getFirstChild().getNodeValue();
	}
	
	private void setDomainPackage(Node node) {
		domainPkg = node.getFirstChild().getNodeValue();
	}

	private void setAppVersion(Node node) {
		appVersion = node.getFirstChild().getNodeValue();
	}

	private void setViewFrameWork(Node node) {
		String vfw = node.getFirstChild().getNodeValue();
		if("swing".equalsIgnoreCase(vfw)){
			viewFramework = ViewFramework.SWING;
		}
		if(viewFramework != ViewFramework.SWING){
			Logger.getAnonymousLogger().log(Level.SEVERE, "No support for the" +
					" view framework "+vfw+" currently.");
		}
	}

	private void setORMFrameWork(Node node) {
		String pfw = node.getFirstChild().getNodeValue();
		if("hibernate".equalsIgnoreCase(pfw)){
			persistenceFramework = PersistenceFramework.HIBERNATE;
		}
		if(persistenceFramework != PersistenceFramework.HIBERNATE){
			Logger.getAnonymousLogger().log(Level.SEVERE, "No support for the" +
					" orm framework "+pfw+" currently.");
		}
	}
	
	@Override
	public String getApplicationName() {
		return appName;
	}

	@Override
	public String getApplicationVersion() {
		return appVersion;
	}

	@Override
	public ViewFramework getViewFramework() {
		return viewFramework;
	}

	@Override
	public String getDomainPackage() {
		return domainPkg;
	}

	@Override
	public String getControllerPackage() {
		return controllerPkg;
	}

	@Override
	public String getViewPackage() {
		return viewPkg;
	}

	@Override
	public PersistenceFramework getPersistenceFramework() {
		return persistenceFramework;
	}

}
