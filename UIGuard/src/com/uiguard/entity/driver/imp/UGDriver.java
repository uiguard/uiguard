package com.uiguard.entity.driver.imp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Splitter;
import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.IUGCommand;
import com.uiguard.action.IUiGuardAction;
import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.driver.extension.IDriverExtension;
import com.uiguard.entity.driver.helper.IDriverHelper;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.jsloader.JavaScriptLoader;
import com.uiguard.utils.setting.UiGuardSettings;

public class UGDriver extends WebDriverProxy implements IUGDriver{
	
	private IDriverHelper helper;
	
	private IDriverExtension extension;
	
	private WebDriver nativeDriver;

	private static final String waitUntilFindIframeIndex;
	
	private Alert alert;

	private static final Pattern PATTERN = Pattern.compile("(?=\\S+)(([^/]+)?(?:/(?!/))?)+");
	
	private static final Pattern DIGIT = Pattern.compile("\\d+"); 

	public UGDriver(WebDriver nativeDriver, IUiGuardLogger ugLogger) {
		super(nativeDriver);
		this.nativeDriver = nativeDriver;
	}
	
	static{
		waitUntilFindIframeIndex = JavaScriptLoader.getJavaScriptStr(UGDriver.class, "waitUntilFindIframeIndex");
	}

	@Override
	public WebDriver getNativeWebDriver() {
		return nativeDriver;
	}

	@Override
	public IDriverExtension extension() {
		return extension;
	}

	@Override
	public IUGDriver setExtension(IDriverExtension extension) {
		this.extension = extension;
		return this;
	}

	@Override
	public IDriverHelper helper() {
		return helper;
	}

	@Override
	public IUGDriver setHelper(IDriverHelper helper) {
		this.helper = helper;
		return this;
	}

	@Override
	public IUGDriver navigateUrl(String url) {
		nativeDriver.navigate().to(url);
		return this;
	}

	@Override
	public IUGDriver refresh() {
		nativeDriver.navigate().refresh();
		return this;
	}

	/****************iframe****************/
	@Override
	public IUGDriver selectIframe(String iframeIdOrName) {
		selectFrame(iframeIdOrName, true);
		return this;
	}

	@Override
	public IUGDriver selectIframe(int iframeIndex) {
		selectIframeByIndex(iframeIndex, true);
		return this;
	}

	@Override
	public IUGDriver selectIframeByPath(String iframePath) throws UiGuardException {
		selectIframeByPath(iframePath, true);
		return this;
	}

	@Override
	public IUGDriver selectIframeByPath(final String iframePath, final boolean forceSwitch) throws UiGuardException {
		if(iframePathShouldNotBeSwitched(iframePath, forceSwitch)){
			return this;
		}
		Matcher m = PATTERN.matcher(iframePath.trim());
		if (!m.matches()) {
			final String errMsg = "DefaultDriver->selectFrameByPath: " + iframePath
									+ " :please check param,program cannot understand it!";
			helper().handleFailure(errMsg);
		}
		Iterable<String> tokens = Splitter.onPattern("(/(?!$))|((?!^)/)").
				trimResults().split(getOptimizeIframeQueryPath(iframePath));
		for (String token : tokens) {
			selectFrameByToken(token);
		}
		helper().setCurrentIframePath(iframePath);
		return this;
	}
	

	@Override
	public IUGDriver swithToDefaultContent() {
		getNativeWebDriver().switchTo().defaultContent();
		return this;
	}
	
	/****************alert****************/
	@Override
	public boolean isAlertPresent() {
		try{
			alert = switchTo().alert();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	public String acceptAlert() {
		waitUntilAlertPresent();
		final String text = alert.getText();
		alert.accept();
		return text;
	}

	@Override
	public String dismissAlert() {
		waitUntilAlertPresent();
		final String text = alert.getText();
		alert.dismiss();
		return text;
	}

	@Override
	public String acceptAlertIfPresent() {
		if(isAlertPresent()){
			final String text = alert.getText();
			alert.accept();
			return text;
		}
		return null; //if alert is not present, nothing to do!
	}

	@Override
	public String dismissAlertIfPresent() {
		if(isAlertPresent()){
			final String text = alert.getText();
			alert.accept();
			return text;
		}
		return null; //if alert is not present, nothing to do!
	}

	@Override
	public boolean pageContains(CharSequence s) {
		return nativeDriver.getPageSource().contains(s);
	}

	/****************wait until****************/
	@Override
	public IUGDriver waitForPageLoadUtilStrExist(final CharSequence s) {
		helper().getWebDriverWait().until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver arg0) {
				return pageContains(s);
			}
		});
		return this;
	}

	
	@Override
	public IUGDriver waitForPageLoadUtilElementExist(final IUGElement element) {
		helper().getWebDriverWait().until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					return element.isPresent();
				} catch (UiGuardException e) {
					throw new SeleniumException(e);
				}
			}
		});
		return this;
	}
	
	/****************window****************/
	@Override
	public IUGDriver selectWindow(final String nameOrHandler) throws UiGuardException {
		helper().getWebDriverWait().until(new ExpectedCondition<WebDriver>() {
			@Override
			public WebDriver apply(WebDriver driver) {
				return driver.switchTo().window(nameOrHandler);
			}
		});	
		helper.executeScript("window.opener=null;window.open('','_self');");
		return selectIframeByPath("/");
	}
	
	@Override
	public IUGDriver selectWindowByTitle(final String title) throws UiGuardException {
		helper().getWebDriverWait().until(new ExpectedCondition<WebDriver>() {
			@Override
			public WebDriver apply(WebDriver driver) {
				for(String handler :driver.getWindowHandles()){
					driver.switchTo().window(handler);
					if(driver.getTitle().equals(title)){
						return driver;
					}
				}
				throw new SeleniumException("Continue to select window");
			}
		});	
		helper.executeScript("window.opener=null;window.open('','_self');");
		return selectIframeByPath("/");
	}

	@Override
	public String triggerSwitchToNewWindowBackOldWindowHandler(
			UGElement element, String triggerMethodName, Object... args)
			throws UiGuardException {
		final String oldWindowHandler = nativeDriver.getWindowHandle();
		final Set<String> oldWindowHanders = nativeDriver.getWindowHandles();
		callbackObjectMethod(element,triggerMethodName,args);
		selectWindow(waitUntilGetNewWindowHander(oldWindowHanders));
		return oldWindowHandler;
	}
		
	@Override
	public String clickElementSwitchToNewWindowBackOldWindowHandler(
			UGElement element) throws UiGuardException {
		return triggerSwitchToNewWindowBackOldWindowHandler(element,"click");
		
	}
	
	/****************private select iframe****************/
	private void waitUntilAlertPresent() {
		helper().getWebDriverWait().until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				return isAlertPresent();
			}			
		});
	}
	
	private void selectFrame(final String iframeIdOrName,final boolean resetFlag) {
		if(iframeIdOrName.indexOf("_") == -1){			
			selectFrameByIdOrName(iframeIdOrName);
		}else{
			selectIframeByIndex(waitUntilFindIframeIndex(iframeIdOrName), resetFlag);
		}
		resetIframePathToNullWhenUserInvoke(resetFlag);
	}
	
	private int waitUntilFindIframeIndex(final String iframeIdOrName){
		return helper().getWebDriverWait().until(new ExpectedCondition<Integer>() {
			public Integer apply(WebDriver driver) {
				return (Integer) helper().executeScript(waitUntilFindIframeIndex, iframeIdOrName);
			}
		});
	}
	
	private void selectFrameByIdOrName(final String frameIdOrName){
		helper().getWebDriverWait().until(new ExpectedCondition<WebDriver>() {
			public WebDriver apply(WebDriver driver) {
				return driver.switchTo().frame(frameIdOrName);
			}
		});
	}
	
	private void selectIframeByIndex(final int iframeIndex, final boolean resetFlag){
		nativeDriver.switchTo().frame(iframeIndex);
		resetIframePathToNullWhenUserInvoke(resetFlag);
	}

	/****************private select iframe by path****************/
	private void resetIframePathToNullWhenUserInvoke(final boolean resetFlag){
		if(resetFlag)
			helper().setCurrentIframePath(null);
	}
	
	private void selectFrameByToken(String level) {
		if (DIGIT.matcher(level).matches()) {
			selectIframeByDigit(level);
		} else {
			if ("relative=parent".equals(level)) {
				selectIframeByParent();
			} else if ("relative=top".equals(level)) {
				swithToDefaultContent();
			} else {
				selectFrame(level,false);
			}
		}
	}
	
	private void selectIframeByDigit(String level) {
		final int frameIndex = Integer.valueOf(level);
		if (frameIndex < 10) {
			selectIframe(Integer.valueOf(level));
		} else {
			selectFrame(level,false);
		}
	}
	
	private void selectIframeByParent() {
		Stack<String> paths = getTopToParentIframePathStack();
		getNativeWebDriver().switchTo().defaultContent();
		while (!paths.isEmpty()) {
			selectFrame(paths.pop(),false);
		}
	}
	
	private Stack<String> getTopToParentIframePathStack() {
		Stack<String> paths = new Stack<String>();
		String parentLocator = "parent";
		String frameName = getIframeNameByExecuteJs(parentLocator);
		while (StringUtils.isNotEmpty(frameName)) {
			paths.push(frameName);
			parentLocator += ".parent";
			getIframeNameByExecuteJs(parentLocator);
		}
		return paths;
	}
	
	private String getIframeNameByExecuteJs(final String parentLocator){
		return getStringByExecuteJs("return " + parentLocator + ".name;");
	}
	
	private String getStringByExecuteJs(final String jsStr, Object ... args){
		return (String) helper().executeScript(jsStr, args);
	}
	
	private String getOptimizeIframeQueryPath(final String iframePath){
		return iframePath.trim().
		replaceAll("[^/]+/\\.{2}/", "").
		replaceFirst("(?<=^)/", "relative=top/").
		replaceAll("\\.{2}/", "relative=parent/").
		replaceFirst("(?!<^)/(?=$)", "");
	}
	
	private boolean iframePathShouldNotBeSwitched(final String iframePath, final boolean forceSwitch){
		return !forceSwitch && iframePathNotEmputyAndRepeat(iframePath);
	}
	
	private boolean iframePathNotEmputyAndRepeat(final String iframePath){
		return StringUtils.isEmpty(iframePath) || iframePath.equals(helper().getCurrentIframePath());
	}

	/****************private window****************/
	private Object callbackObjectMethod(UGElement element, String methodName, Object... args) throws UiGuardException {
		try {
			return getTriggerMethod(element,methodName,args).invoke(element, args);
		} catch (IllegalArgumentException e) {
			helper().handleFailure("[UGDriver->callbackObjectMethod][IllegalArgumentException]", e);
		} catch (SecurityException e) {
			helper().handleFailure("[UGDriver->callbackObjectMethod][SecurityException]", e);
		} catch (IllegalAccessException e) {
			helper().handleFailure("[UGDriver->callbackObjectMethod][IllegalAccessException]", e);
		} catch (InvocationTargetException e) {
			helper().handleFailure("[UGDriver->callbackObjectMethod][InvocationTargetException]", e);
		} catch (NoSuchMethodException e) {
			helper().handleFailure("[UGDriver->callbackObjectMethod][NoSuchMethodException]", e);
		}
		throw new UiGuardException("[UGDriver->callbackObjectMethod][You should not be here]");
	}
	
	@SuppressWarnings("rawtypes")
	private Method getTriggerMethod(Object obj , String triggerMethodName, Object... args ) 
		throws SecurityException, NoSuchMethodException{
		List<Class> methodParameterTypes = new ArrayList<Class>();
		for(Object arg : args){
			methodParameterTypes.add(arg.getClass());
		}
		return obj.getClass().getMethod(triggerMethodName, methodParameterTypes.toArray(new Class[0]));
	}

	private String waitUntilGetNewWindowHander(final Set<String> oldWindowHanders){
		return helper().getWebDriverWait().until(new ExpectedCondition<String>() {
			@Override
			public String apply(WebDriver driver) {
				return getNewWindowHander(oldWindowHanders);
			}
		});
	}
	
	private String getNewWindowHander(final Set<String> oldWindowHanders) throws SeleniumException{
		Set<String> newWindowHanders = getWindowHandles();
		for(String winHander : newWindowHanders){
			if(! oldWindowHanders.contains(winHander)){
				return winHander;
			}
		};
		throw new SeleniumException("Try to find new window handler!");
	}

	@Override
	public Object executeUIGuardCommand(String className, String params) {
		if(StringUtils.isEmpty(UiGuardSettings.HubIpAddr)){
			IUiGuardAction a;
			try {
				a = (IUiGuardAction)Class.forName(className).newInstance();
				return a.action(params);
			} catch (InstantiationException e) {
				helper().handleFailure("[UGDriver->executeUIGuardCommand][InstantiationException]", e);
			} catch (IllegalAccessException e) {
				helper().handleFailure("[UGDriver->executeUIGuardCommand][IllegalAccessException]", e);
			} catch (ClassNotFoundException e) {
				helper().handleFailure("[UGDriver->executeUIGuardCommand][ClassNotFoundException]", e);
			}
		}
		return ((IUGCommand) nativeDriver).executeUIGuardCommand(className, params);
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isEmpty(""));
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {		
		return ((TakesScreenshot) nativeDriver).getScreenshotAs(target);
	}



}
