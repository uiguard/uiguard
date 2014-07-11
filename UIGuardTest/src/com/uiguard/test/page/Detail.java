package com.uiguard.test.page;

import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.entity.page.imp.UGPage;
import com.uiguard.entity.page.support.UiGuardFindBy;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;

public class Detail extends UGPage{

	public Detail(IUGTest tb) throws UiGuardException {
		super(tb);
	}
	
	@UiGuardFindBy(linkText="看看iframe吧")
	private UGElement link;
	
	public void openIframePage(){
		driver.swithToDefaultContent();
		link.click();
	}

}
