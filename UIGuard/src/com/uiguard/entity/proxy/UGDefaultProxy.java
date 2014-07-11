package com.uiguard.entity.proxy;

import java.lang.reflect.Method;

import com.uiguard.exception.UiGuardException;

public abstract class  UGDefaultProxy extends UGProxy{

	@Override
	protected void actionBeforeAllMethodInvoke(Method method, Object[] args)
			throws UiGuardException {
		addTraceLog(method, args);
	}

	@Override
	protected void actionBeforePublicMethodInvoke(Method method, Object[] args)
			throws UiGuardException {
		if(canAddInfoAndTraceLog(method))
			getLogger(driver).info(getMessage(method, args));
	}

}
