package com.uiguard.logger;

public interface IUiGuardLogger {
	
	void error(final String message);

	void error(final String message, final Throwable t);
	
	void info(final String message);

	void trace(final String message);
	
}