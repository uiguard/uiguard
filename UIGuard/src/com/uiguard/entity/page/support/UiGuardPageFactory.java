package com.uiguard.entity.page.support;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.element.helper.UGElementHelper;
import com.uiguard.entity.element.helper.IElementHelper;
import com.uiguard.entity.page.IUGPage;
import com.uiguard.entity.proxy.element.ElementHelperProxy;
import com.uiguard.entity.proxy.element.ElementProxy;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public class UiGuardPageFactory {
	
	public static void initElements(IUGDriver driver, IUGPage pageObj) throws UiGuardException{
		Class<?> aClass = getElementClass(pageObj);
		List<Field> fields = new ArrayList<Field>();		
		fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
		while(aClass.getSuperclass() != null){
			aClass = aClass.getSuperclass();
			fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
		};
		for(Field field:fields){
			initField(driver,pageObj,field);
		}
	}
	
	private static Class<?> getElementClass(Object obj) throws UiGuardException{
		final String className = obj.getClass().getName().split("\\${2}")[0];
		try {			
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new UiGuardException("[Error][Class not find]："+className,e);
		}
	} 
	
	private static void initField(IUGDriver driver, IUGPage pageObj, Field field) throws UiGuardException {
		try {			
			if (field.isAnnotationPresent(UiGuardFindBy.class)) {				
				conField(driver, pageObj, field);
			}
		}catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isStrValid(String value){
		if(StringUtils.isEmpty(value) || "".equals(value)){
			return false;
		}
		return true;
	}

	private static void conField(IUGDriver driver, IUGPage pageObj, Field field)
			throws UiGuardException {		
		UiGuardFindBy findBy = field.getAnnotation(UiGuardFindBy.class);
		final String iframePath = findBy.iframePath();
		final ElementLocatorType locType = getElementLocatorType(findBy);
		final String locValue = getElementLocatorValue(driver, pageObj, findBy, locType);
		
		field.setAccessible(true);
		
		try {
			Constructor<?> con = getFieldClass(field).getConstructor(IUGDriver.class,IUiGuardLogger.class,ElementLocatorType.class,String.class);
			IUGElement element = (IUGElement) con.newInstance(driver,pageObj.getUiGuardLogger(),locType,locValue);
			IUGElement elementProxy = (IUGElement)new ElementProxy().createProxy(driver, element, con.getParameterTypes(), 
					driver, pageObj.getUiGuardLogger(), locType,locValue);
			
			IElementHelper helper = new UGElementHelper(driver, pageObj.getUiGuardLogger(), locType, locValue);
			if(!iframePath.equals("")){
				helper.setIframePath(iframePath);
			}			
			IElementHelper helperProxy =(IElementHelper) new ElementHelperProxy().createProxy(driver, helper, con.getParameterTypes(), 
					driver, pageObj.getUiGuardLogger(), locType,locValue);
			
			elementProxy.setHelper(helperProxy);
			
			field.set(pageObj, elementProxy);
		} catch (IllegalArgumentException e) {
			throw new UiGuardException("[Error][field instance][IllegalArgumentException]"+field.getName(),e);
		} catch (IllegalAccessException e) {
			throw new UiGuardException("[Error][field instance][IllegalAccessException]"+field.getName(),e);
		} catch (InstantiationException e) {
			throw new UiGuardException("[Error][field instance][InstantiationException]"+field.getName(),e);
		} catch (InvocationTargetException e) {
			throw new UiGuardException("[Error][field instance][InvocationTargetException]"+field.getName(),e);
		} catch (SecurityException e) {
			throw new UiGuardException("[Error][field instance][SecurityException]"+field.getName(),e);
		} catch (NoSuchMethodException e) {
			throw new UiGuardException("[Error][field instance][NoSuchMethodException]"+field.getName(),e);
		} catch (UiGuardException e){
			throw new UiGuardException("[Error][field instance]"+field.getName(),e);
		}
	}

	private static Class<?> getFieldClass(Field field) throws UiGuardException {
		Class<?> fieldClass;
		final String className = getClassName(field.getType());
		try {
			fieldClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new UiGuardException("[Error][Class not find]"+className,e);
		}
		return fieldClass;
	}

	private static String getClassName(final Type type) {
		return type.toString().split(" ")[1];
	}
	
	private static ElementLocatorType getElementLocatorType(UiGuardFindBy findBy) throws UiGuardException{
		if(isStrValid(findBy.id())){
			return ElementLocatorType.ID;
		}else if(isStrValid(findBy.name())){
			return ElementLocatorType.NAME;
		}else if(isStrValid(findBy.xpath())){
			return ElementLocatorType.XPATH;
		}else if(isStrValid(findBy.dom())){
			return ElementLocatorType.DOM;
		}else if(isStrValid(findBy.linkText())){
			return ElementLocatorType.LINKTEXT;
		}else if(isStrValid(findBy.className())){
			return ElementLocatorType.CLASSNAME;
		}else if(isStrValid(findBy.css())){
			return ElementLocatorType.CSS;
		}else if(isStrValid(findBy.tagName())){
			return ElementLocatorType.TAGNAME;
		}else if(isStrValid(findBy.partialLinkText())){
			return ElementLocatorType.PARTIALLINKTEXT;
		}else{
			throw new UiGuardException("[Error][Can not support locator]"+
					"[Choose one of them: id、name、css、xpath、className、linkText、tagName、partialLinkText、dom]");
		}
	}
	
	private static String getElementLocatorValue(IUGDriver driver, IUGPage pageObj,UiGuardFindBy findBy,
			ElementLocatorType type) throws UiGuardException{	
		Map<ElementLocatorType, String> typeValueMap = new ConcurrentHashMap<ElementLocatorType, String>();
		typeValueMap.put(ElementLocatorType.ID, findBy.id());
		typeValueMap.put(ElementLocatorType.NAME, findBy.name());
		typeValueMap.put(ElementLocatorType.XPATH, findBy.xpath());
		typeValueMap.put(ElementLocatorType.DOM, findBy.dom());
		typeValueMap.put(ElementLocatorType.LINKTEXT, findBy.linkText());
		typeValueMap.put(ElementLocatorType.CLASSNAME, findBy.className());
		typeValueMap.put(ElementLocatorType.CSS, findBy.css());
		typeValueMap.put(ElementLocatorType.TAGNAME, findBy.tagName());
		typeValueMap.put(ElementLocatorType.PARTIALLINKTEXT, findBy.partialLinkText());
		return 	getRealAttributeValue(driver,pageObj,findBy,typeValueMap.get(type));
	} 
	
	private static String getRealAttributeValue(IUGDriver driver, IUGPage pageObj,
			UiGuardFindBy findBy, String property) throws UiGuardException{
		if(findBy.usePropertiesValue()){
			return getProperties(getLangPropertiesFile(driver, pageObj),property);
		}else{
			return property;
		}
	}
	
	private static String getProperties(URI langPropertiesFileUri,String property) {
		Properties prop = new Properties();
		try {			
			FileInputStream file = new FileInputStream(new File(langPropertiesFileUri));
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}
	
	private static URI getLangPropertiesFile(IUGDriver driver, IUGPage pageObj)
			throws UiGuardException {
		URI fileUri;
		try {
			fileUri = pageObj.getClass().getResource(pageObj.getClass().getSimpleName() + driver.helper().getLanguage()+ ".properties").toURI();
		} catch (Exception e1) {
			throw new UiGuardException("[Error][Get language properties file]" + pageObj.getClass().getName());
		}
		return fileUri;
	}
	
}
