package com.uiguard.test.page;

import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.entity.page.imp.UGPage;
import com.uiguard.entity.page.support.UiGuardFindBy;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;

public class Iframe extends UGPage {

	public Iframe(IUGTest tb) throws UiGuardException {
		super(tb);
	}

	@UiGuardFindBy(xpath="//button[text()='显示']")
	private UGElement showButton;
	@UiGuardFindBy(xpath="//a[text()='返回']")
	private UGElement back;
	
	public void showAndBack() throws UiGuardException{
		driver.selectIframeByPath("/frame1");
		showButton.click();
		driver.swithToDefaultContent();
		back.click();
	}
	
}
