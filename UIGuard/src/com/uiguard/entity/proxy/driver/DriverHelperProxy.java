package com.uiguard.entity.proxy.driver;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.uiguard.entity.proxy.UGDefaultProxy;
import com.uiguard.exception.UiGuardException;

public class DriverHelperProxy  extends UGDefaultProxy{
	
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
		
		UnTraceMethods.add("error");
		UnTraceMethods.add("getUiGuardLogger");
		UnTraceMethods.add("handleFailure");
		UnTraceMethods.add("screenShot");
		
	}
	
	private static final Set<String> UnAddInfoMethods = Collections.synchronizedSet(new HashSet<String>());
	
	static{
		UnAddInfoMethods.add("waitForAjaxResponse");
		UnAddInfoMethods.add("getElementJsString");		
		UnAddInfoMethods.add("getLanguage");
		UnAddInfoMethods.add("setHubUrl");
		UnAddInfoMethods.add("setLanguage");
		UnAddInfoMethods.add("getCurrentIframePath");
		UnAddInfoMethods.add("getHubUrl");
		UnAddInfoMethods.add("getWebDriverWait");
		UnAddInfoMethods.add("setCurrentIframePath");
		UnAddInfoMethods.add("waitForDefaultTime");
	}
	
	@Override
	protected boolean canAddInfoLog(Method method){
		return ! UnAddInfoMethods.contains(method.getName());
	}
	
	@Override
	protected boolean canAddTraceLog(Method method){
		return ! UnTraceMethods.contains(method.getName());
	}
	
	private static final Set<String> UnHandleFailure = Collections.synchronizedSet(new HashSet<String>());
	
	static{
		UnHandleFailure.add("handleFailure");
	}
	
	private boolean canHandleFailure(Method method){
		return !UnHandleFailure.contains(method.getName());
	}
	
	@Override
	protected Object invokeMthod(Method method, Object[] args) throws UiGuardException{
		if(canHandleFailure(method)){
			return super.invokeMthod(method, args);
		}
		return invokeMethodWithoutCatchException(method, args);
	}

}
