package com.uiguard.entity.page.imp;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.page.IUGPage;
import com.uiguard.entity.page.support.UiGuardPageFactory;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public  class UGPage implements IUGPage{
	
	protected IUGDriver driver;
	
	private IUiGuardLogger ugLogger;

	private UGPage() {}

	public UGPage(IUGTest tb) throws UiGuardException {
		this(tb.getDriver(),tb.getUiGuardLogger());		
	}
	
	public UGPage(IUGDriver driver, IUiGuardLogger ugLogger) throws UiGuardException{		
		this();
		this.driver = driver;
		this.ugLogger = ugLogger;
		initElements();
	}

	protected void initElements() throws UiGuardException {
		UiGuardPageFactory.initElements(driver, this);
	}

	protected void saveDownloadFile() {
		driver.extension().autoItDownloadFile("SAVE");	
	}

	protected void saveDownloadFile(String filePath) {
		driver.extension().autoItDownloadFile("SAVE###"+filePath);
	}

	protected void openDownloadFile() {
		driver.extension().autoItDownloadFile("OPEN");
	}

	protected void cancelDownloadFile() {
		driver.extension().autoItDownloadFile("CANCEL");
	}

	@Override
	public IUiGuardLogger getUiGuardLogger() {
		return ugLogger;
	}
		
}
