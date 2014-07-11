package com.uiguard.entity.element.imp;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.jsloader.JavaScriptLoader;

public class UGElementByJs extends UGElement{
	
	public UGElementByJs(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}

	@Override
	public void click(){
		try {
			final String jsStr = helper().getElementJsString() + ".click()";
			getUiGuardLogger().trace("click by js: " + jsStr);
			driver.helper().executeScriptUntilSuccessOrTimeOut(jsStr);
		} catch (UiGuardException e) {
			driver.helper().handleFailure("[Error][UGElementByJs][click]", e);
		}
	}

	@Override
	public IUGElement type(String value){
		try{
			final String jsStr = helper().getElementJsString() + ".value='"+value+"'";
			getUiGuardLogger().trace("type by js:" + jsStr);
			driver.helper().executeScriptUntilSuccessOrTimeOut(jsStr);
		}catch(Exception e){
			driver.helper().handleFailure("[Error][UGElementByJs][type]", e);
		}
		return this;
	}

	@Override
	public IUGElement selectOptionByText(String optionText){
		String jsStr;
		try {
			jsStr = JavaScriptLoader.getJavaScriptStr(UGElementByJs.class, "selectOptionByText") + 
								"; selectOptionByText(" + helper().getElementJsString() + ",arguments[0]).click()";
			driver.helper().executeScript(jsStr, optionText);
		} catch (UiGuardException e) {
			driver.helper().handleFailure("[Error][UGElementByJs][selectOptionByText]", e);
		}
		return this;
	}
	
	@Override
	public IUGElement selectOptionByValue(String optionValue){
		String jsStr;
		try {
			jsStr = JavaScriptLoader.getJavaScriptStr(UGElementByJs.class, "selectOptionByValue") + 
								"; selectOptionByValue(" + helper().getElementJsString() + ",arguments[0]).click()";
			driver.helper().executeScript(jsStr, optionValue);
		} catch (UiGuardException e) {
			driver.helper().handleFailure("[Error][UGElementByJs][selectOptionByValue]", e);
		}
		return this;
	}

}
