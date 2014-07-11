package com.uiguard.entity.testcase.imp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.driver.extension.IDriverExtension;
import com.uiguard.entity.driver.extension.UGDriverExtension;
import com.uiguard.entity.driver.helper.IDriverHelper;
import com.uiguard.entity.driver.helper.UGDriverHelper;
import com.uiguard.entity.driver.imp.UGDriver;
import com.uiguard.entity.proxy.driver.DriverExtentionProxy;
import com.uiguard.entity.proxy.driver.DriverHelperProxy;
import com.uiguard.entity.proxy.driver.DriverProxy;
import com.uiguard.entity.testcase.IAssert;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.entity.testcase.support.DataProviderHelper;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.logger.UiGuardLoggerFactory;
import com.uiguard.nativedriverfactory.NativeDriverFactoryControler;
import com.uiguard.utils.CommandExecutor;
import com.uiguard.utils.setting.UiGuardSettings;

public class UGTest implements IUGTest {

	protected IAssert uiAssert;
	
	protected IUGDriver driver;
	
	private IUiGuardLogger ugLogger;
	
	@Override
	public IUGDriver getDriver() throws UiGuardException {
		if(null==driver){
			throw new UiGuardException("You should set driver first!");
		}
		return driver;
	}

	@Override
	public void setDriver(IUGDriver driver) {
		this.driver = driver;
	}
	
	protected Object[][] getDataProvidorFromXls() throws UiGuardException {
		return new DataProviderHelper().getDataProvidorFromXls(this,UiGuardSettings.Language);
	}
	
	protected Object[][] getDataProvidorFromXlsBySheetName(String sheetName) throws UiGuardException {
		return new DataProviderHelper().getDataProvidorFromXlsBySheetName(this,UiGuardSettings.Language,
				sheetName);
	}

	protected String getSysUrl() {
		return UiGuardSettings.WebUrl;
	}
	
	@BeforeMethod(alwaysRun = true)
	protected  void ugBeforeMethod() throws UiGuardException {
		info("[##Start Method Execute##]");
		autoKillProcesses();
		final String hubUrl = UiGuardSettings.HubIpAddr;
		WebDriver nativeDriver = NativeDriverFactoryControler.getNativeWebDriver(UiGuardSettings.NativeDriverFactory);		
		
		initDriver(nativeDriver);
		driver.helper().setHubUrl(hubUrl);
		uiAssert = new UGAssert(driver);
		driver.helper().setLanguage(UiGuardSettings.Language);
		autoSetWindowSize();
		driver.navigateUrl(getSysUrl());
	}

	/**
	 * You can override this method to do what you want.
	 * This Method is very powerful.
	 * */
	protected void initDriver(WebDriver nativeDriver) {
		//Proxy for driver
		driver = new UGDriver(nativeDriver, ugLogger);
		IUGDriver originalDriver = new UGDriver(nativeDriver, ugLogger);
		driver = (IUGDriver) new DriverProxy().createProxy(originalDriver, originalDriver, 
				new Class[]{ WebDriver.class,  IUiGuardLogger.class}, 
				nativeDriver, ugLogger);
		driver.setHelper(new UGDriverHelper(driver, ugLogger));
		
		driver.setExtension(new UGDriverExtension(driver));
		
		//Proxy for driver helper
		IDriverHelper helper = new UGDriverHelper(driver, ugLogger);
		IDriverHelper helperProxy = (IDriverHelper) new DriverHelperProxy().createProxy(driver, helper, 
				new Class[]{IUGDriver.class,  IUiGuardLogger.class}, 
				driver, ugLogger);		
		driver.setHelper(helperProxy);
		
		//Proxy for driver extension
		IDriverExtension extension = new UGDriverExtension(driver);
		IDriverExtension extensionProxy = (IDriverExtension) new DriverExtentionProxy().createProxy(driver, extension, 
				new Class[]{IUGDriver.class}, 
				driver);
		driver.setExtension(extensionProxy);
		
		
	}
	
	@AfterMethod(alwaysRun = true)
	protected  void ugAfterMethod() throws UiGuardException {
		autoQuitAfterMethodDone();
		info("[##End Method Execute##]");
	}

	@BeforeClass(alwaysRun = true)
	protected void ugBeforeTest() throws UiGuardException {
		ugLogger = UiGuardLoggerFactory.createUiGuardLogger(UiGuardSettings.UiGuardLoggerClassName, this.getClass());		
		info("[Start Class Execute][" + this.getClass().getName() + "]");
	}

	@AfterClass(alwaysRun = true)
	protected void ugAfterTest() throws UiGuardException {
		info("[End Class Execute][" + this.getClass().getName() + "]");
	}

	protected void info(final String message){
		ugLogger.info(message);
	}
	
	protected static void autoKillProcesses() {
		final String hubIpAddr = UiGuardSettings.HubIpAddr;
		if (	!"".equals(UiGuardSettings.KillProcesses) && 
				(hubIpAddr==null ||	"".equals(hubIpAddr) || hubIpAddr.toUpperCase().contains("HTTP://LOCALHOST"))
		) {
			CommandExecutor ce = CommandExecutor.getInstance();
			for (String processName : UiGuardSettings.KillProcesses.split(",")) {
				ce.killProcess(processName);
			}
		}
	}

	private void autoSetWindowSize() {
		if (UiGuardSettings.MaxsizeWindowWhenTest) {
			driver.manage().window().maximize();
		}
	}

	private void autoQuitAfterMethodDone() {
		if (UiGuardSettings.QuitAfterMethodDone && driver!=null) {
			driver.helper().waitForAjaxResponse();
			driver.quit();
		}
	}

	@Override
	public IUiGuardLogger getUiGuardLogger() {
		return ugLogger;
	}

}
