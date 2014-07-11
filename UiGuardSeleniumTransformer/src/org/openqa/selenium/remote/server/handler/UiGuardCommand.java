package org.openqa.selenium.remote.server.handler;

import java.util.Map;

import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.Session;
import org.openqa.selenium.remote.server.rest.ResultType;

import com.uiguard.action.IUiGuardAction;

public class UiGuardCommand  extends ResponseAwareWebDriverHandler implements
		JsonParametersAware {

	private String params;
	private String className;

	public UiGuardCommand(Session session) {
		super(session);
	}

	public void setJsonParameters(Map<String, Object> allParameters)
			throws Exception {
		className = (String) allParameters.get("className");
		params = (String) allParameters.get("params");
	}

	public ResultType call() throws Exception {
		Object uga = Class.forName(className).newInstance();
		if(uga instanceof IUiGuardAction){
			response.setValue(((IUiGuardAction)uga).action(params));
		}else{
			throw new SecurityException("WatchmanCommand must get a parameter "
					+ "which is a instance of WatchmanAction!");
		}
		return ResultType.SUCCESS;
	}

	@Override
	public String toString() {
		return String.format("[run watchman action: %s; command: %s]", className, params);
	}

}
