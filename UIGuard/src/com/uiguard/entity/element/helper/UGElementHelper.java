package com.uiguard.entity.element.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public class UGElementHelper implements  IElementHelper {
	
	private IUGDriver driver;
	
	private IUiGuardLogger ugLogger;
	
	private ElementLocatorType locType;
	
	private String locValue;
	
	private String iframePath;

	public UGElementHelper(IUGDriver driver,IUiGuardLogger ugLogger, ElementLocatorType locType, String locValue){
		this.driver = driver;
		this.ugLogger = ugLogger;
		this.locType = locType;
		this.locValue = locValue;
	}	

	@Override
	public IUiGuardLogger getUiGuardLogger() {
		return ugLogger;
	}

	@Override
	public ElementLocatorType getLocatorType() {
		return locType;
	}

	@Override
	public String getLocatorValue() {
		return locValue;
	}

	@Override
	public String getIframePath() {
		return iframePath;
	}

	@Override
	public IElementHelper setIframePath(String iframePath) {
		this.iframePath = iframePath;
		return this;		
	}

	@Override
	public WebElement findWebElementBeforeAction() throws UiGuardException {
		if(ElementLocatorType.DOM.equals(getLocatorType())){
			return getWebElementByExecuteScript(getLocatorValue());
		}
		return getWebElementUsingBy(null,getBy(getLocatorType(), getLocatorValue()));
	}

	@Override
	public boolean isElementPresent(final WebElement fatherElement, final By by) {
		try{
			driver.helper().getWebDriverWait().until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver arg0) {
					return findElementUsingBy(fatherElement, by);
				}
			});
			 
			return true;
		}catch(Exception e){			
			return false;
		}
	}
	
	@Override
	public WebElement getWebElementUsingBy(final WebElement fatherElement, final By by){
		return driver.helper().getWebDriverWait().until(new ExpectedCondition<WebElement>() {

			@Override
			public WebElement apply(WebDriver arg0) {
				return findElementUsingBy(null,by);
			}
			
		});
	}
	
	@Override
	public IElementHelper scrollIntoView() throws UiGuardException {
		driver.helper().scrollIntoView(getLocatorType(),getLocatorValue());
		return this;
	}

	@Override
	public By getBy(ElementLocatorType locType, String locValue) throws UiGuardException {
		switch (locType){
			case ID :
				return By.id(locValue);
			case NAME :
				return By.name(locValue);
			case CSS :
				return By.cssSelector(locValue);
			case XPATH :
				return By.xpath(locValue);
			case CLASSNAME :
				return By.className(locValue);
			case LINKTEXT :
				return By.linkText(locValue);
			case TAGNAME :
				return By.tagName(locValue);
			case PARTIALLINKTEXT :
				return By.partialLinkText(locValue);
			default:
				throw new UiGuardException("[Error][Can not support input locType]" + locType);
		}
	}

	@Override
	public String getElementJsString() throws UiGuardException {
		return driver.helper().getElementJsString(getLocatorType());
	}

	@Override
	public WebElement getWebElementByExecuteScript(final String jsStr, final Object... args) {
		return driver.helper().getWebDriverWait().until(new ExpectedCondition<WebElement>() {

			@Override
			public WebElement apply(WebDriver arg0) {
				return (WebElement) driver.helper().executeScript(jsStr, args);
			}
			
		});
	}

	@Override
	public Boolean isPresent() throws UiGuardException {
		if(ElementLocatorType.DOM.equals(locType)){
			try{
				getWebElementByExecuteScript(getLocatorValue());
				return true;
			}catch (Exception e) {
				return false;
			}
		}
		return isElementPresent(null, getBy(getLocatorType(), getLocatorValue()));
	}
	
	private WebElement findElementUsingBy(final WebElement fatherElement, final By by){
		if(fatherElement == null){
			return driver.findElement(by);
		}
		return fatherElement.findElement(by);
	}
	
}
