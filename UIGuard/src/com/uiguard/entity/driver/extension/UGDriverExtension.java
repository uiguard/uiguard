package com.uiguard.entity.driver.extension;

import org.openqa.selenium.Point;

import com.google.common.base.Joiner;
import com.uiguard.entity.driver.IUGDriver;

public class UGDriverExtension implements IDriverExtension{
	
	private static final String PRESSKEYCLASSNAME = PREFIX + "RobotPressKeyCommand";
	private static final String ROBOTCLICKCOMMAND = PREFIX + "RobotClickCommand";
	private static final String ROBOTMOUSEMOVECOMMAND = PREFIX + "RobotMouseMoveCommand";	
	private static final String AUTOITDOWNLOADCLASSNAME = PREFIX + "AutoItDownLoadFileCommand";
	private static final String AUTOITUNIFORMCERTIFICATIONCLASSNAME = PREFIX + "AutoItUniformCertificationCommand";
	private static final String EXECUTETEMINALCOMMAND = PREFIX + "ExecuteTeminalCommand";
	
	protected IUGDriver driver;
	
	private UGDriverExtension(){}
	
	public UGDriverExtension(IUGDriver driver){
		this();
		this.driver = driver;
	}

	@Override
	public void robotPressKeyboard(int keyCode) {
		driver.executeUIGuardCommand(PRESSKEYCLASSNAME, keyCode+"");
	}

	@Override
	public void robotPressKeyboard(int keyCode, int funKeyCode) {
		driver.executeUIGuardCommand(PRESSKEYCLASSNAME, keyCode+"###"+funKeyCode);		
	}

	@Override
	public void robotClick(Point p) {
		driver.executeUIGuardCommand(ROBOTCLICKCOMMAND, p.x+"###"+p.y);
	}

	@Override
	public void robotMouseMove(int x, int y) {
		driver.executeUIGuardCommand(ROBOTMOUSEMOVECOMMAND, x+"###"+y);
	}

	@Override
	public void autoItDownloadFile(String command) {
		driver.executeUIGuardCommand(AUTOITDOWNLOADCLASSNAME, command);
	}

	@Override
	public void autoItTypeUniformPassword(String winTitle, String password) {
		driver.executeUIGuardCommand(AUTOITUNIFORMCERTIFICATIONCLASSNAME, winTitle + "###" + password);
	}

	@Override
	public void executeTeminalCommand(String progremName, String... params) {
		driver.executeUIGuardCommand(EXECUTETEMINALCOMMAND, Joiner.on("#").join(params));
	}
	

}
