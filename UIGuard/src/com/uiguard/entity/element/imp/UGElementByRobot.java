package com.uiguard.entity.element.imp;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;

public class UGElementByRobot extends UGElement {
	
	public UGElementByRobot(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}

	@Override
	public void click(){
		try {
			driver.extension().robotClick(getAbsoluteCenterLocation());
		} catch (UiGuardException e) {
			driver.helper().handleFailure("[Error][UGElementByRobot][click]", e);
		}
	}
	
	@Override
	public IUGElement type(String text) {
		try{
			driver.extension().robotClick(getAbsoluteCenterLocation());
			inputCharactorsByRobot(text.toCharArray());
		}catch(Exception e){
			driver.helper().handleFailure("[Error][UGElementByRobot][type]", e);
		}
		return this;
	}
	
	private void inputCharactorsByRobot(char ... text ) throws UiGuardException{
		driver.helper().pause(50);
		for(Character c : text){
			if(Character.isUpperCase(c)){
				driver.extension().robotPressKeyboard(getKeyEventByChar(c.toString().toLowerCase()), KeyEvent.VK_SHIFT);
			}else{
				driver.extension().robotPressKeyboard(getKeyEventByChar(c.toString()));
			}
		}
	}
	
	private Integer getKeyEventByChar(String c){
		return RobotKeyEventMapping.KeyEventMap.get(c);
	}
	
	private static class RobotKeyEventMapping {
		private static final Map<String, Integer> KeyEventMap = new ConcurrentHashMap<String, Integer>();
		static {
			KeyEventMap.put("0", KeyEvent.VK_0);
			KeyEventMap.put("1", KeyEvent.VK_1);
			KeyEventMap.put("2", KeyEvent.VK_2);
			KeyEventMap.put("3", KeyEvent.VK_3);
			KeyEventMap.put("4", KeyEvent.VK_4);
			KeyEventMap.put("5", KeyEvent.VK_5);
			KeyEventMap.put("6", KeyEvent.VK_6);
			KeyEventMap.put("7", KeyEvent.VK_7);
			KeyEventMap.put("8", KeyEvent.VK_8);
			KeyEventMap.put("9", KeyEvent.VK_9);
			KeyEventMap.put("a", KeyEvent.VK_A);
			KeyEventMap.put("b", KeyEvent.VK_B);
			KeyEventMap.put("c", KeyEvent.VK_C);
			KeyEventMap.put("d", KeyEvent.VK_D);
			KeyEventMap.put("e", KeyEvent.VK_E);
			KeyEventMap.put("f", KeyEvent.VK_F);
			KeyEventMap.put("g", KeyEvent.VK_G);
			KeyEventMap.put("h", KeyEvent.VK_H);
			KeyEventMap.put("i", KeyEvent.VK_I);
			KeyEventMap.put("j", KeyEvent.VK_J);
			KeyEventMap.put("k", KeyEvent.VK_K);
			KeyEventMap.put("l", KeyEvent.VK_L);
			KeyEventMap.put("m", KeyEvent.VK_M);
			KeyEventMap.put("n", KeyEvent.VK_N);
			KeyEventMap.put("o", KeyEvent.VK_O);
			KeyEventMap.put("p", KeyEvent.VK_P);
			KeyEventMap.put("q", KeyEvent.VK_Q);
			KeyEventMap.put("r", KeyEvent.VK_R);
			KeyEventMap.put("s", KeyEvent.VK_S);
			KeyEventMap.put("t", KeyEvent.VK_T);
			KeyEventMap.put("u", KeyEvent.VK_U);
			KeyEventMap.put("v", KeyEvent.VK_V);
			KeyEventMap.put("w", KeyEvent.VK_W);
			KeyEventMap.put("x", KeyEvent.VK_X);
			KeyEventMap.put("y", KeyEvent.VK_Y);
			KeyEventMap.put("z", KeyEvent.VK_Z);
			KeyEventMap.put("Tab", KeyEvent.VK_TAB);
			KeyEventMap.put("Space", KeyEvent.VK_SPACE);
			KeyEventMap.put("Shift", KeyEvent.VK_SHIFT);
			KeyEventMap.put("Ctrl", KeyEvent.VK_CONTROL);
			KeyEventMap.put("Alt", KeyEvent.VK_ALT);
			KeyEventMap.put("F1", KeyEvent.VK_F1);
			KeyEventMap.put("F2", KeyEvent.VK_F2);
			KeyEventMap.put("F3", KeyEvent.VK_F3);
			KeyEventMap.put("F4", KeyEvent.VK_F4);
			KeyEventMap.put("F5", KeyEvent.VK_F5);
			KeyEventMap.put("F6", KeyEvent.VK_F6);
			KeyEventMap.put("F7", KeyEvent.VK_F7);
			KeyEventMap.put("F8", KeyEvent.VK_F8);
			KeyEventMap.put("F9", KeyEvent.VK_F9);
			KeyEventMap.put("F10", KeyEvent.VK_F10);
			KeyEventMap.put("F11", KeyEvent.VK_F11);
			KeyEventMap.put("F12", KeyEvent.VK_F12);
		}
	}
}
