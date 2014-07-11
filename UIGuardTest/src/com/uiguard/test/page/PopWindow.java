package com.uiguard.test.page;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.entity.page.imp.UGPage;
import com.uiguard.entity.page.support.UiGuardFindBy;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public class PopWindow extends UGPage{

	public PopWindow(IUGDriver driver, IUiGuardLogger ugLogger)
			throws UiGuardException {
		super(driver, ugLogger);
	}

	public PopWindow(IUGTest tb) throws UiGuardException {
		super(tb);
	}
	
	@UiGuardFindBy(id="randomNumber")
	private UGElement randomNumber;	
	@UiGuardFindBy(id="ok")
	private UGElement ok;
	
	public String getRandomNum(){
		ok.waitUtilDisplayed().waitUtilEnabled();
		final String randomnum = randomNumber.getText();
		ok.click();
		return randomnum;		
	}
	

}
