package com.uiguard.entity.element;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.helper.IElementHelper;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public interface IUGElement extends WebElement{
	
	IUGElement setHelper(IElementHelper helper);

	IElementHelper helper();

	IUGDriver getDriver();

	/************method
	 * @throws UiGuardException **************/
	IUGElement type(String text) throws UiGuardException;

	IUGElement dbClick();

	IUGElement dragAndDrop(UGElement targetElement);

	IUGElement mouseOver();

	IUGElement selectOptionByText(String optionText);

	IUGElement selectOptionByValue(String optionValue);

	IUGElement selectOptionByIndex(int index) throws UiGuardException;

	IUGElement chooseFile(String filePath) throws UiGuardException;

	Point getAbsoluteCenterLocation() throws UiGuardException;
	
	Boolean isPresent() throws UiGuardException;
	
	/************webelement**************/
	WebElement getNativeWebElement();
	
	IUGElement setNativeElement(WebElement element);	

	IUiGuardLogger getUiGuardLogger();

	IUGElement waitUtilEnabled();

	IUGElement waitUtilDisplayed();

}