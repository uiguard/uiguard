package com.uiguard.action.imp;

import java.awt.AWTException;
import java.awt.Robot;

import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.action.IUiGuardAction;

public class RobotMouseMove implements IUiGuardAction {

	@Override
	public Object action(String uiGuardActionStr) {
		try {
			String [] positions = uiGuardActionStr.split("###");
			Robot rb = new Robot();
			rb.mouseMove(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		} catch (AWTException e) {
			throw new SeleniumException("RobotMouseMove Error",e);
		}
		
		return null;
	}

}
