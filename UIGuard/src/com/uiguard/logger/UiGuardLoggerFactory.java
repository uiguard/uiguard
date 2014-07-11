package com.uiguard.logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.uiguard.exception.UiGuardException;

public class UiGuardLoggerFactory {
	
	@SuppressWarnings("unchecked")
	public static IUiGuardLogger createUiGuardLogger(final String ugLoggerClassName, final Class<?> c) throws UiGuardException{
		Constructor<IUiGuardLogger> con;
		try {
			con = (Constructor<IUiGuardLogger>) Class.forName(ugLoggerClassName).getConstructor(Class.class);
			return con.newInstance(c);
		} catch (SecurityException e) {
			throw new UiGuardException("SecurityException", e);
		} catch (NoSuchMethodException e) {
			throw new UiGuardException("NoSuchMethodException", e);
		} catch (ClassNotFoundException e) {
			throw new UiGuardException("ClassNotFoundException", e);
		} catch (IllegalArgumentException e) {
			throw new UiGuardException("IllegalArgumentException", e);
		} catch (InstantiationException e) {
			throw new UiGuardException("InstantiationException", e);
		} catch (IllegalAccessException e) {
			throw new UiGuardException("IllegalAccessException", e);
		} catch (InvocationTargetException e) {
			throw new UiGuardException("InvocationTargetException", e);
		}
	}

}
