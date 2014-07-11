package com.uiguard.action.imp;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.action.IUiGuardAction;

public class RobotClick implements IUiGuardAction {

	private static final Robot robot;

	static {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new SeleniumException("RobotClick Error", e);
		}
	}
	
	@Override
	public Object action(String uiGuardActionStr) {
		String[] positions = uiGuardActionStr.split("###");
		robot.delay(5);
		robot.mouseMove(Integer.parseInt(positions[0]),
				Integer.parseInt(positions[1]));
		robot.delay(5);
		robot.setAutoDelay(0);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(5);
		return null;
	}

}