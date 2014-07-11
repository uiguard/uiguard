package com.uiguard.entity.driver.extension;


import org.openqa.selenium.Point;


public interface IDriverExtension {

	public final static String PREFIX = "org.openqa.selenium.remote.server.handler.watchman.";
	
	void robotPressKeyboard(int keyCode);

	void robotPressKeyboard(int keyCode, int funKeyCode);

	void robotClick(Point p);

	void robotMouseMove(int x, int y);

	void autoItDownloadFile(final String command);

	void autoItTypeUniformPassword(final String winTitle, final String password);
	
	void executeTeminalCommand(final String progremName, final String ... params);
	
}
