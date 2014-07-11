package com.uiguard.entity.driver.imp;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

abstract class WebDriverProxy implements WebDriver{	
	
	WebDriver nativeDriver;
	
	private WebDriverProxy(){}
	
	public WebDriverProxy(WebDriver nativeDriver){
		this();	
		this.nativeDriver = nativeDriver;
	}

	@Override
	public void get(String url) {
		nativeDriver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return nativeDriver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return nativeDriver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return nativeDriver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return nativeDriver.findElement(by);
	}

	@Override
	public String getPageSource() {
		return nativeDriver.getPageSource();
	}

	@Override
	public void close() {
		nativeDriver.close();
	}

	@Override
	public void quit() {
		nativeDriver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		return nativeDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		return nativeDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return nativeDriver.switchTo();
	}

	@Override
	public Navigation navigate() {
		return nativeDriver.navigate();
	}

	@Override
	public Options manage() {
		return nativeDriver.manage();
	}
	
}
