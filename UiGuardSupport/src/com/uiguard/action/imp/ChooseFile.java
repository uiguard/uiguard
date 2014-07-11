package com.uiguard.action.imp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.action.IUiGuardAction;


public class ChooseFile implements IUiGuardAction {
	
	public static final String TmpFiles = "tmpFiles";

	@Override
	public Object action(String uiGuardActionStr) {
		final int startIndex = uiGuardActionStr.indexOf("###");
		final String fileName = "tmpFiles"+File.separatorChar+uiGuardActionStr.substring(0, startIndex+1);
		final String content = uiGuardActionStr.substring(startIndex + 3);
		File file = new File(fileName);
		if(!file.exists()){
			file.mkdirs();
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			IOUtils.copy(bis,fos);
			bis.close();
		} catch (Exception e) {
			throw new SeleniumException("ChooseFile Error",e);
		}finally{
			try {
				fos.close();
				bis.close();
			} catch (IOException e) {
				throw new SeleniumException("ChooseFile Error",e);
			}
		}
		
		return null;
	}

}
