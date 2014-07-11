package com.uiguard.entity.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.google.common.base.Joiner;
import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public abstract class UGProxy  implements MethodInterceptor {

	protected IUGDriver driver;
	
	//The Object be proxyed
	private Object obj;
	
	public Object getObj(){
		return obj;
	}
	
	public Object createProxy(IUGDriver driver, Object obj, Class<?>[] argumentTypes, Object ... arguments) {
		this.driver = driver;
		this.obj = obj;
		Enhancer enhancer = createEnhence();
		if(arguments.length == 0){
			return enhancer.create();
		}
		return enhancer.create(argumentTypes,arguments);
	}
	
	private Enhancer createEnhence() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(obj.getClass());
		enhancer.setCallback(this);
		return enhancer;
	}
	
	protected abstract void actionBeforeAllMethodInvoke(Method method, Object[] args) throws UiGuardException;
	
	protected abstract void actionBeforePublicMethodInvoke(Method method, Object[] args) throws UiGuardException;
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		actionBeforeAllMethodInvoke(method, args);
		if(Modifier.PUBLIC == method.getModifiers()%2 ){	
			actionBeforePublicMethodInvoke(method, args);
			return invokeMthod(method, args);	
		}
		return invokeMthod(method, args);
	}
	
	protected Object invokeMthod(Method method, Object[] args) throws UiGuardException{
		try {
			return invokeMethodWithoutCatchException(method, args);
		} catch (Exception e) {
			driver.helper().handleFailure("[Method invoke error]"+getMessage(method, args), e);
			return null;
		}
		
	}

	protected Object invokeMethodWithoutCatchException(Method method,
			Object[] args) throws UiGuardException {
		method.setAccessible(true);
		try {
			return method.invoke(obj, args);
		} catch (IllegalArgumentException e) {
			throw new UiGuardException("IllegalArgumentException", e);
		} catch (IllegalAccessException e) {
			throw new UiGuardException("IllegalAccessException", e);
		} catch (InvocationTargetException e) {
			throw new UiGuardException("InvocationTargetException", e);
		}
	}
	
	protected void addTraceLog(Method method, Object[] args) {
		if(canAddTraceLog(method))
			getLogger(driver).trace(getTraceMessage(method,args));
	}
	
	private String getTraceMessage(Method method, Object[] args){
		 return getObj().getClass().getSimpleName()+ "->" + 
		 		method.getName() +"("+getParameterStringFromArgs(args)+")";
	}
	
	protected String getMessage(Method method, Object[] args){
		return 	"["+obj.getClass().getSimpleName()+"]"+
				"["+method.getName()+"]"+
				"["+getParameterStringFromArgs(args)+"]";
	}
	
	private String getParameterStringFromArgs(Object[] args){
		return Joiner.on(",").join(args);
	}
	
	protected static final IUiGuardLogger getLogger(IUGDriver driver){
		return driver.helper().getUiGuardLogger();
	}
	
	protected boolean canAddInfoAndTraceLog(Method method) {
		return canAddInfoLog(method) && canAddTraceLog(method);
	}
	
	protected abstract boolean canAddInfoLog(Method method);

	protected abstract boolean canAddTraceLog(Method method);

}
