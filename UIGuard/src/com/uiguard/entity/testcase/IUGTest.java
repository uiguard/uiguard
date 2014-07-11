package com.uiguard.entity.testcase;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.ICanLogger;

public interface IUGTest extends ICanLogger{

	IUGDriver getDriver() throws UiGuardException;

	void setDriver(IUGDriver driver);

}