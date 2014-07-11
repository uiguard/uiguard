package com.uiguard.entity.driver.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.jsloader.JavaScriptLoader;
import com.uiguard.utils.setting.UiGuardSettings;


public class UGDriverHelper implements IDriverHelper{
	
	private String language = "";
	
	private String currentIframePath = "";
	
	private String hubUrl = "";
	
	private IUiGuardLogger ugLogger;
	
	private static final String wgxpath;
	
	private static final String domElementByPath;
	
	private final static String hasActiveAjax;

	protected IUGDriver driver;
	
	public UGDriverHelper(IUGDriver driver,IUiGuardLogger ugLogger){
		this.driver = driver;
		this.ugLogger = ugLogger;
	}
	
	static{
		wgxpath = JavaScriptLoader.getJavaScriptStr(UGDriverHelper.class,"wgxpath");
		domElementByPath = wgxpath + JavaScriptLoader.getJavaScriptStr(UGDriverHelper.class,"domElementByPath");
		hasActiveAjax = JavaScriptLoader.getJavaScriptStr(UGDriverHelper.class,"hasActiveAjax");
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public IDriverHelper setLanguage(String language) {
		this.language = language;
		return this;
	}

	@Override
	public String getCurrentIframePath() {
		return currentIframePath;
	}

	@Override
	public IDriverHelper scrollIntoView(ElementLocatorType locatorType, String locatorValue) throws UiGuardException {
		executeScript(getElementJsString(locatorType) + ".scrollIntoView();", locatorValue);
		return this;
	}

	@Override
	public IDriverHelper scrollIntoView(IUGElement ugElement) throws UiGuardException {
		scrollIntoView(ugElement.helper().getLocatorType(),ugElement.helper().getLocatorValue());
		return this;
	}
	
	@Override
	public String getElementJsString(ElementLocatorType locType) throws UiGuardException{
		switch (locType){
		case ID :
			return "document.getElementById(arguments[0])";
		case NAME :
			return "document.getElementsByName(arguments[0])[0]";
		case DOM :
			return "function getUnvisibleElement(){arguments[0]}; getUnvisibleElement()";
		case XPATH :
			return domElementByPath;
		default:
			throw new UiGuardException("[Error][Get elenent js str infomation]" + "[locType:"+locType+"]");
		}
	}
	
	@Override
	public Object executeScript(String jsStr, Object... args) {
		return ((JavascriptExecutor) driver.getNativeWebDriver()).executeScript(jsStr, args);
	}

	/*can not be invoke*/
	@Override
	public String screenShot(String screenShotName) throws UiGuardException {
		final String dir = UiGuardSettings.PngPath; 
		final String screenShotPath = dir + File.separator + screenShotName;
		try {
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File(screenShotPath));
		} catch (Exception e) {
			throw new UiGuardException("Screen shot error", e);
		}
		return screenShotName;
	}

	@Override
	public IDriverHelper setHubUrl(String hubUrl) {
		this.hubUrl = hubUrl;
		return this;
	}

	@Override
	public String getHubUrl() {
		return this.hubUrl;
	}

	@Override
	public IUiGuardLogger getUiGuardLogger() {
		return this.ugLogger;
	}

	@Override
	public WebDriverWait getWebDriverWait() {
		return new WebDriverWait(driver.getNativeWebDriver(),UiGuardSettings.Timeout / 1000);
	}

	@Override
	public IDriverHelper setCurrentIframePath(String iframePath) {
		this.currentIframePath = iframePath;
		return this;
	}

	/*can not be invoke*/
	@Override
	public IDriverHelper handleFailure(String message, Throwable ... realCause) {
		if(UiGuardSettings.OmitErrorContinueRun){
			error(message,realCause);
		}else {
			driver.switchTo().defaultContent();
			String png;
			try {
				png = screenShot();
			} catch (UiGuardException e) {
				error("[Screen shot error]");
				png = "error";
			}
			String logMsg = message + "\n [screenshot pic:" + png+"]";
			error(logMsg,realCause);
			Assert.fail(logMsg);
		}
		return this;
	}

	@Override
	public IDriverHelper waitForAjaxResponse() {
		
		getWebDriverWait().until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				Boolean isApply = false;
				try{
					pause(50);
					isApply = (Boolean) executeScript(hasActiveAjax);
				}catch(Exception e){
					return true;
				}
				return isApply;
			}
			
		});
		return this;
	}

	@Override
	public IDriverHelper pause(int time) throws UiGuardException {
		if (time <= 0) {
			throw new UiGuardException("Pause time should greater than 0!");
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new UiGuardException("Pause time InterruptedException!",e);
		}
		return this;
	}

	@Override
	public IDriverHelper waitForDefaultTime() throws UiGuardException {
		pause(UiGuardSettings.StepInterval);
		return this;
	}
	
	private void error(String message,Throwable ... realCause){
		if(realCause.length == 0){
			ugLogger.error(message);
		}else{
			ugLogger.error(message, realCause[0]);
		}
	}

	private String screenShot() throws UiGuardException{
		final String time = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
		final String screenShotName = "SS_"+time + "_" + (new Random().nextInt()) +".png";
		return screenShot(screenShotName);
	}

	@Override
	public IUGDriver executeScriptUntilSuccessOrTimeOut(final String jsStr,
			final Object... args) throws UiGuardException {
		getWebDriverWait().until(new ExpectedCondition<WebDriver>() {
			@Override
			public WebDriver apply(WebDriver driver) {
				 executeScript(jsStr, args);
				 return driver;
			}
		});
		return driver;
	}
	
}
