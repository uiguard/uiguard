package com.uiguard.logger.imp;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;

import com.uiguard.utils.setting.UiGuardSettings;

class SpliteLoggerFactory {

	public static Logger createSplitLogger(final Class<?> c) {
		Logger logger = null;
		try {
			logger = Logger.getLogger(c.getName());
			logger.removeAllAppenders();
			PatternLayout layout = new PatternLayout("%d [%t] %p %X{operationName} %X{userId} - %m%n");
			logger.setLevel(Level.TRACE);
			String infoFileName = UiGuardSettings.LogPath+File.separator+"infoSplit"+File.separator+c.getName()+".log";
			String traceFileName = UiGuardSettings.LogPath+File.separator+"traceSplit"+File.separator+c.getName()+".log";
			logger.setAdditivity(false);
			logger.addAppender(getFileAppender(layout,infoFileName,Level.INFO));
			logger.addAppender(getFileAppender(layout,traceFileName,Level.TRACE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logger;
		
	}
	
	private static FileAppender getFileAppender(PatternLayout layout,
			String fileName, Level minLevel) throws IOException{
		FileAppender infoAppender;
		infoAppender = new FileAppender(layout, fileName);
		LevelRangeFilter filter = new LevelRangeFilter();
		filter.setLevelMin(minLevel);
		filter.setLevelMax(Level.ERROR);
		filter.setAcceptOnMatch(true);
		infoAppender.addFilter(filter);
		infoAppender.setAppend(false);
		infoAppender.activateOptions();
		return infoAppender;
	}
	
}
