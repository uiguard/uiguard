package com.uiguard.entity.proxy.driver;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.uiguard.entity.proxy.UGDefaultProxy;

public class DriverExtentionProxy  extends UGDefaultProxy{
	
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
		UnTraceMethods.add("getUiGuardLogger");

	}
	
	private static final Set<String> UnAddInfoMethods = Collections.synchronizedSet(new HashSet<String>());
	
	static{
		
		UnAddInfoMethods.add("getLocatorType");
		UnAddInfoMethods.add("getLocatorValue");
		UnAddInfoMethods.add("setIframePath");
		UnAddInfoMethods.add("getIframePath");
		UnAddInfoMethods.add("getElementJsString");
		UnAddInfoMethods.add("getBy");
			
	}
	
	@Override
	protected boolean canAddInfoLog(Method method){
		return ! UnAddInfoMethods.contains(method.getName());
	}
	
	@Override
	protected boolean canAddTraceLog(Method method){
		return ! UnTraceMethods.contains(method.getName());
	}

}