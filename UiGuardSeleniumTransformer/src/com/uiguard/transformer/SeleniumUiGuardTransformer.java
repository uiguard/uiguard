package com.uiguard.transformer;


import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;


public class SeleniumUiGuardTransformer implements ClassFileTransformer {
	
	private static CtClass getCtClassByName(final String className) throws NotFoundException{
		return ClassPool.getDefault().get(className);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {		
		if (className.indexOf("/") != -1) {
			className = className.replaceAll("/", ".");
		}
		try {
			if ("org.openqa.selenium.remote.HttpCommandExecutor".equals(className)) {
				return getHttpCommandExecutorBytecode(className);
			}
			if("org.openqa.selenium.remote.server.JsonHttpRemoteConfig".equals(className)){
				return getJsonHttpRemoteConfigBytecode(className);
			}
			if("org.openqa.selenium.remote.RemoteWebDriver".equals(className)){
				return getRemoteWebDriverBytecode(className);
			}
			
			return null;
			
		} catch (CannotCompileException e) {
			throw new IllegalClassFormatException("");
		} catch (NotFoundException e) {
			throw new IllegalClassFormatException("");
		} catch (IOException e) {
			throw new IllegalClassFormatException("");
		}

	}

	private byte[] getRemoteWebDriverBytecode(String className) throws NotFoundException, CannotCompileException, IOException {
		CtClass cc = getCtClassByName(className);
		
		cc.addInterface(getCtClassByName("org.openqa.selenium.TakesScreenshot"));
		cc.addInterface(getCtClassByName("com.uiguard.IUGCommand"));
		
		CtMethod getScreenshotAsMethod = CtNewMethod.make("@Override "+
										"public <X> X getScreenshotAs(OutputType<X> target) "+
										"		throws WebDriverException { "+
										"	if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) { "+
										"		String base64Str = execute(DriverCommand.SCREENSHOT).getValue().toString(); "+
										"		return target.convertFromBase64Png(base64Str); "+
										"	} "+
										"	return null; "+
										"}"
									, cc); 
		cc.addMethod(getScreenshotAsMethod);
		
		CtMethod getExecuteUIGuardCommandMethod = CtNewMethod.make(" public Object executeUIGuardCommand(String className, String params) { "+
																	  	"Map<String, Object> uiguardParams = new HashMap<String, Object>(); "+
																	  	"uiguardParams.put(\"className\", className); "+
																	  	"uiguardParams.put(\"params\", params); "+																	  	
																	    "return execute(\"uiguardcommand\", watchmanParams).getValue(); "+
																    "}"
																   , cc);
		cc.addMethod(getExecuteUIGuardCommandMethod);
		return cc.toBytecode();
	}

	private byte[] getHttpCommandExecutorBytecode(String className)
			throws NotFoundException, CannotCompileException, IOException {
		CtClass cc = getCtClassByName(className);
		CtClass[] params = new CtClass[]{getCtClassByName("java.util.Map"),getCtClassByName("java.net.URL")};
		CtConstructor ctc = cc.getDeclaredConstructor(params);
		ctc.insertAfter("builder.put(\"uiguardcommand\",post(\"/session/:sessionId/uiguardcommand\"));nameToUrl = builder.build();");
		return cc.toBytecode();
	}
	
	private byte[] getJsonHttpRemoteConfigBytecode(String className) throws NotFoundException, CannotCompileException, IOException{
		CtClass cc = getCtClassByName(className);
		
		CtMethod ctm = cc.getDeclaredMethod("setUpMappings");
		ctm.insertAfter("getMapper.bind(\"/session/:sessionId/uiguardcommand\", org.openqa.selenium.remote.server.handler.UiGuardCommand.class).on(ResultType.SUCCESS, jsonResponse);"+
		" postMapper.bind(\"/session/:sessionId/uiguardcommand\", org.openqa.selenium.remote.server.handler.UiGuardCommand.class).on(ResultType.SUCCESS, jsonResponse);");
		
		return cc.toBytecode();
	}
	
	

}
