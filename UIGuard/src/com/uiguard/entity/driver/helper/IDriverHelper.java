package com.uiguard.entity.driver.helper;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.uiguard.entity.IUGHelper;
import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public interface IDriverHelper extends IUGHelper {

	String getLanguage();

	IDriverHelper setLanguage(String language);
	
	IDriverHelper setCurrentIframePath(String iframePath);
	
	String getCurrentIframePath();
	
	IDriverHelper setHubUrl(String hubUrl);
	
	String getHubUrl();

	IDriverHelper scrollIntoView(ElementLocatorType locatorType, String locatorValue) throws UiGuardException;

	IDriverHelper scrollIntoView(IUGElement ugElement) throws UiGuardException;

	IDriverHelper waitForAjaxResponse();
	
	String screenShot(String screenShotName) throws UiGuardException;
	
	IDriverHelper pause(int time) throws UiGuardException;
	
	IDriverHelper waitForDefaultTime() throws UiGuardException;
	
	IUiGuardLogger getUiGuardLogger();
	
	WebDriverWait getWebDriverWait();

	String getElementJsString(ElementLocatorType locatorType) throws UiGuardException;

	IDriverHelper handleFailure(String message, Throwable ... realCause);
	
	Object executeScript(String jsStr, Object... args);
	
	IUGDriver executeScriptUntilSuccessOrTimeOut(String jsStr, Object... args)
	throws UiGuardException;

	
}