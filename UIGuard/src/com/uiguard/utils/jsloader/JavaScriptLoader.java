package com.uiguard.utils.jsloader;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;

public class JavaScriptLoader {
	
	private static String package2dir(Class<?> c){
		return "/" + c.getPackage().getName().replace(".", "/") + "/js/";	
	}
	
	public static String getJavaScriptStr(Class<?> c, String methodName){
		return getJavaScriptStr(package2dir(c),c.getSimpleName()+ "_" + methodName +".js");
	}
	
	public static String getJavaScriptStr(Class<?> c){
		return getJavaScriptStr(package2dir(c),c.getSimpleName()+".js");
	}
	
	public static String getJavaScriptStr(final String prefix, final String name) {
		final String scriptPath = prefix + name;
		URL url = JavaScriptLoader.class.getResource(scriptPath);
		if (url == null) {
			throw new RuntimeException("Cannot locate " + scriptPath);
		}

		try {
			return Resources.toString(url, Charsets.UTF_8);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

}
