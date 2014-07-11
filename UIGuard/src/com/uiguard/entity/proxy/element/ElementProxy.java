package com.uiguard.entity.proxy.element;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.proxy.UGProxy;
import com.uiguard.exception.UiGuardException;

public class ElementProxy  extends UGProxy{
	
	private static final Set<String> UnAddInfoMethods = Collections.synchronizedSet(new HashSet<String>());
	
	static{
		
		UnAddInfoMethods.add("getDriver");
		UnAddInfoMethods.add("getUiGuardLogger");
		UnAddInfoMethods.add("helper");
		UnAddInfoMethods.add("setHelper");
		
		UnAddInfoMethods.add("getNativeWebElement");
		UnAddInfoMethods.add("isPresent");
		UnAddInfoMethods.add("setNativeElement");
				
	}
	
	private static final Set<String> UnTraceMethods = Collections.synchronizedSet(new HashSet<String>());
	
	static{
		
		/********The method extended from Object*********/
		UnTraceMethods.add("finalize");
		UnTraceMethods.add("wait");
		UnTraceMethods.add("wait");
		UnTraceMethods.add("wait");
		UnTraceMethods.add("hashCode");
		UnTraceMethods.add("getClass");
		UnTraceMethods.add("clone");
		UnTraceMethods.add("equals");
		UnTraceMethods.add("registerNatives");
		UnTraceMethods.add("toString");
		UnTraceMethods.add("notify");
		UnTraceMethods.add("notifyAll");
		
		/********Logger may be not usable*********/
		UnTraceMethods.add("setHelper");
		UnTraceMethods.add("setNativeElement");

	}
	
	public IUGElement getElement(){
		return (IUGElement) getObj();
	}

	private void addInfoLogAndFindWebElement(Method method, Object[] args)
			throws UiGuardException {
		if(canAddInfoAndTraceLog(method)){
			getLogger(driver).info(getMessage(method, args));		
			try{
				getElement().setNativeElement(getElement().helper().findWebElementBeforeAction());
			} catch (Exception e) {
				driver.helper().handleFailure("[Method invoke error]"+getMessage(method, args), e);
			} 
		}
	}
	
	@Override
	protected boolean canAddInfoLog(Method method){
		return ! UnAddInfoMethods.contains(method.getName());
	}
	
	@Override
	protected boolean canAddTraceLog(Method method){
		return ! UnTraceMethods.contains(method.getName());
	}

	@Override
	protected void actionBeforeAllMethodInvoke(Method method, Object[] args)
			throws UiGuardException {
		addTraceLog(method, args);
	}

	@Override
	protected void actionBeforePublicMethodInvoke(Method method, Object[] args)
			throws UiGuardException {
		addInfoLogAndFindWebElement(method, args);
	}

}
