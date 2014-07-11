package com.uiguard.logger.imp;

import org.apache.log4j.Logger;

import com.uiguard.logger.IUiGuardLogger;

public class UiGuardLogger implements IUiGuardLogger {
	
	public UiGuardLogger(final Class<?> c){
		simpleClassLogger = SpliteLoggerFactory.createSplitLogger(c);
	}
	
	private Logger simpleClassLogger;
	
	private static final Logger allInOneLogger;
	
	static{
		allInOneLogger = Logger.getLogger("com.uigurad");
	}

	@Override
	public void error(final String message) {
		simpleClassLogger.error(message);
		allInOneLogger.error(message);
	}

	@Override
	public void error(final String message, Throwable t) {
		simpleClassLogger.error(message, t);
		allInOneLogger.error(message, t);
	}

	@Override
	public void info(final String message) {
		simpleClassLogger.info(message);
		allInOneLogger.info(message);
	}

	@Override
	public void trace(final String message) {
		simpleClassLogger.trace(message);
		allInOneLogger.trace(message);
	}

}
