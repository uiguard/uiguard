package com.uiguard.action.imp;

import java.awt.AWTException;
import java.awt.Robot;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.action.IUiGuardAction;

public class RobotPressKey implements IUiGuardAction {

	@Override
	public Object action(String uiGuardActionStr) {
		if (StringUtils.isEmpty(uiGuardActionStr)) {
			throw new SeleniumException("uiGuardActionStr can not be empty!");
		}
		String[] args = uiGuardActionStr.split("###");
		try {
			Robot rb = new Robot();
			if (args.length == 2) {
				rb.keyPress(Integer.parseInt(args[1]));
				rb.keyPress(Integer.parseInt(args[0]));
				rb.delay(100);
				rb.keyRelease(Integer.parseInt(args[0]));
				rb.keyRelease(Integer.parseInt(args[1]));
			} else {
				rb.keyPress(Integer.parseInt(args[0]));
				rb.delay(100);
				rb.keyRelease(Integer.parseInt(args[0]));
			}
			rb.delay(100);
		} catch (AWTException e) {
			throw new SeleniumException("RobotPressKey Error", e);
		}
		return null;
	}

}
