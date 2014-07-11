package com.uiguard.test.page;

import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.entity.page.imp.UGPage;
import com.uiguard.entity.page.support.UiGuardFindBy;
import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;
import com.uiguard.test.element.CheckBox;
import com.uiguard.test.element.Radio;

public class Index extends UGPage{

	public Index(IUGTest tb) throws UiGuardException {
		super(tb);
	}
	
	@UiGuardFindBy(name="name")
	private UGElement user;
	@UiGuardFindBy(name="sex")
	private Radio sex;	
	@UiGuardFindBy(name="age")
	private UGElement age;
	@UiGuardFindBy(name="nationality")
	private UGElement nationality;
	@UiGuardFindBy(name="habbies")
	private CheckBox habbies;	
	@UiGuardFindBy(name="pic")
	private UGElement pic;
	@UiGuardFindBy(xpath="//input[@value='获取随机数']")
	private UGElement randomButton;
	@UiGuardFindBy(name="randNum")
	private UGElement randNum;	
	@UiGuardFindBy(xpath="//input[@value='提交']")
	private UGElement submit;
	
	public void submitForm() throws UiGuardException{
		user.type("xxx");
		sex.type("女");
		age.selectOptionByText("46~65");
		nationality.type("美国");
		habbies.type("运动","影视","音乐");
		pic.chooseFile("upload\\xxx.txt");
		randomButton.click();
		randNum.type(getRandomNum());
		submit.click();
	}


	private String getRandomNum() throws UiGuardException {
		final String oldWindowHandler = driver.getWindowHandle();
		driver.selectWindowByTitle("弹出窗口");
		final String randomNum = new PopWindow(driver, getUiGuardLogger()).getRandomNum();
		driver.selectWindow(oldWindowHandler);
		return randomNum;
	}
	

}
