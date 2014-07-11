package com.uiguard.entity.element.imp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.element.helper.IElementHelper;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.jsloader.JavaScriptLoader;
import com.uiguard.utils.setting.UiGuardSettings;

public class UGElement extends WebElementProxy implements IUGElement {
	
	protected IUGDriver driver = null;
	
	protected Actions builder = null;
	
	private IElementHelper helper = null;
	
	private IUiGuardLogger ugLogger;
	
	public UGElement(IUGDriver driver, IUiGuardLogger ugLogger, ElementLocatorType locType, String locValue){
		super();
		this.driver = driver;
		this.ugLogger = ugLogger;
		this.builder = new Actions(driver.getNativeWebDriver());
	}
		
	@Override
	public IUiGuardLogger getUiGuardLogger() {
		return ugLogger;
	}

	@Override
	public IUGElement setHelper(IElementHelper helper) {
		this.helper = helper;
		return this;
	}

	@Override
	public IElementHelper helper() {
		return helper;
	}

	@Override
	public IUGDriver getDriver() {
		return driver;
	}

	@Override
	public IUGElement type(String text) throws UiGuardException {
		element.clear();
		element.sendKeys(text);
		return this;
	}

	@Override
	public IUGElement dbClick() {
		builder.doubleClick(element).build().perform();
		return this;
	}

	@Override
	public IUGElement dragAndDrop(UGElement targetElement) {
		builder.dragAndDrop(element, targetElement.getNativeWebElement()).build().perform();
		return this;
	}

	@Override
	public IUGElement mouseOver() {
		// Only support chrome by this time
		driver.extension().robotMouseMove(0,0);
		builder.moveToElement(element).build().perform();
		return this;
	}

	@Override
	public IUGElement selectOptionByText(String optionText) {
		getSelectOptionByValue(element, optionText).click();
		return this;
	}

	@Override
	public IUGElement selectOptionByValue(String optionValue) {
		getSelectOptionByValue(element, optionValue).click();
		return this;
	}

	protected WebElement getSelectOptionByText(WebElement selectElement,String text){
		return selectElement.findElement(By.xpath(".//option[text()='"+text+"']"));
	}
	
	protected WebElement getSelectOptionByValue(WebElement selectElement,String value){
		return selectElement.findElement(By.xpath(".//option[@value='"+value+"']"));
	}

	@Override
	public IUGElement selectOptionByIndex(int index) throws UiGuardException {
		List<WebElement> options = element.findElements(By.tagName("option"));
		if(options.size() <= index){
			throw new UiGuardException("[Error][Index out of size]" + index + "," + options.size());
		}
		options.get(index).click();
		return this;
	}

	@Override
	public IUGElement chooseFile(String filePath) throws UiGuardException {
		File file = new File(filePath);
		if(!file.exists()){
			throw new UiGuardException("File not exist:"+filePath);
		}
		
		try {
			element.sendKeys(file.getCanonicalPath());
		} catch (IOException e) {
			throw new UiGuardException("Get file path error:"+filePath, e);
		}
		return this;
	}

	@Override
	public Boolean isPresent() throws UiGuardException {
		return helper().isPresent();
	}

	@Override
	public WebElement getNativeWebElement() {
		return element;
	}

	@Override
	public IUGElement setNativeElement(WebElement element) {
		this.element = element;
		return this;
	}

	@Override
	public Point getAbsoluteCenterLocation() throws UiGuardException {
		final int elementY = this.getSize().getHeight(); 
		final int elementX = this.getSize().getWidth(); 
		final Point p = this.getAbsoluteLocation();
		return new Point(p.x + elementX / 2, p.y + elementY / 2);
	}
	
	@Override
	public IUGElement waitUtilEnabled(){
		driver.helper().getWebDriverWait().until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				// TODO Auto-generated method stub
				return element.isEnabled();
			}
		});
		return this;
	}
	
	@Override
	public IUGElement waitUtilDisplayed(){
		driver.helper().getWebDriverWait().until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				// TODO Auto-generated method stub
				return element.isDisplayed();
			}
		});
		return this;
	}
	
	private Point getAbsoluteLocation() throws UiGuardException {
		if(UiGuardSettings.isIECore()){
			return getIECoreAbsoluteLocation();
		}else if(UiGuardSettings.isChromeCore()){
			return getChromeCoreAbsoluteLocation();
		}else if(UiGuardSettings.isFirefoxCore()){
			return getFireFoxCoreAbsoluteLocation();
		}else{
			//TODO more browser should be supported
			throw new UiGuardException("[Error][This function only support IE,CHROME,FIREFOX by this time]");
		}
	}
	
	private Point getIECoreAbsoluteLocation(){
		final Point elementPoint = element.getLocation();
		final String posStr = getIEPosString();
		return new Point(elementPoint.getX() + Integer.parseInt(posStr.split("###")[0]),
						elementPoint.getY() + Integer.parseInt(posStr.split("###")[1]) );
	}
	
	private Point getChromeCoreAbsoluteLocation() throws UiGuardException{
		final String posStr = getPosString();
		return new Point(Integer.parseInt(posStr.split("###")[0]),
						UiGuardSettings.ChromeToolHeight + Integer.parseInt(posStr.split("###")[1]) );
	}
	
	private Point getFireFoxCoreAbsoluteLocation() throws UiGuardException{
		final String posStr = getPosString();
		return new Point( Integer.parseInt(posStr.split("###")[0]),
						UiGuardSettings.FireFoxToolHeight + Integer.parseInt(posStr.split("###")[1]) );
	}
	
	private String getIEPosString(){
		return getStringFromExecuteScript(JavaScriptLoader.getJavaScriptStr(UGElement.class, "getIEPosString"));
	}
	
	private String getStringFromExecuteScript(String jsStr, Object ... args){
		return (String) driver.helper().executeScript(jsStr, args);
	}
	
	private String getPosString() throws UiGuardException{
		return getStringFromExecuteScript(getPosStringFromFile());
	}
	
	private String getPosStringFromFile() throws UiGuardException{
		return JavaScriptLoader.getJavaScriptStr(UGElement.class, "getPosString")	+
				"function getElementByJsString() { " +
							"return "+ helper().getElementJsString() + 
				"}; " +
				"var abspos = getPageAbslutePosExceptIE(window, getElementByJsString()); " +
				"var scrpos = getWinScrolloffset(window); " +
				"return (abspos.left - scrpos.left) + '###' + (abspos.top - scrpos.top);";
	}
	
}
