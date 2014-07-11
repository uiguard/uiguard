package com.uiguard.entity.driver;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.uiguard.IUGCommand;
import com.uiguard.entity.driver.extension.IDriverExtension;
import com.uiguard.entity.driver.helper.IDriverHelper;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.exception.UiGuardException;

public interface IUGDriver extends WebDriver,IUGCommand,TakesScreenshot {

	IDriverExtension extension();
	
	IUGDriver setExtension(IDriverExtension extension);
	
	IDriverHelper helper();
	
	IUGDriver setHelper(IDriverHelper helper);

	IUGDriver navigateUrl(String url);

	IUGDriver refresh();
	
	/***************iframe*****************/
	IUGDriver selectIframe(String frameNameOrId);

	IUGDriver selectIframe(int index);

	IUGDriver selectIframeByPath(String iframePath) throws UiGuardException;

	IUGDriver selectIframeByPath(String iframePath, boolean forceSwitch) throws UiGuardException;

	IUGDriver swithToDefaultContent();
	
	/***************alert*****************/
	boolean isAlertPresent();

	String acceptAlert();

	String dismissAlert();
	
	String acceptAlertIfPresent();

	String dismissAlertIfPresent();
	
	
	/***************contains*****************/
	boolean pageContains(CharSequence s);

	/***************wait*****************/
	IUGDriver waitForPageLoadUtilStrExist(final CharSequence s);

	IUGDriver waitForPageLoadUtilElementExist(final IUGElement element);
	
	
	/***************window*****************/
	IUGDriver selectWindow(String nameOrHandler) throws UiGuardException;

	WebDriver getNativeWebDriver();

	String triggerSwitchToNewWindowBackOldWindowHandler(UGElement element, String triggerMethodName,
			 Object... args) throws UiGuardException;
	
	String clickElementSwitchToNewWindowBackOldWindowHandler(UGElement element)throws UiGuardException;

	IUGDriver selectWindowByTitle(String title) throws UiGuardException;

}