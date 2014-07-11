package com.uiguard.entity.element.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Strings;
import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.jsloader.JavaScriptLoader;

public class UGElementByAutoIt extends UGElement {
	
	public UGElementByAutoIt(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}

	@Override
	public void click(){		
		Random rm = new Random();
		int myLuckNum = rm.nextInt(1000000);
		String id = element.getAttribute("id");
		final String tempTitle = "title_"+myLuckNum;
		final String oldWinTitle = setWinTitle(tempTitle);
		if(Strings.isNullOrEmpty(id)){
			id = "uiguard_"+myLuckNum;
			String jsStr;
			try {
				jsStr = helper().getElementJsString()+".setAttribute('id','"+id+"')";
				driver.helper().executeScript(jsStr);
			} catch (UiGuardException e) {
				driver.helper().handleFailure("Error][UGElementByAutoIt][click][Run Js]", e);
			}
		}
		driver.extension().executeTeminalCommand("ClickElementInIE.exe", "tempTitle", getFrameIndexPath(), "id");
		setWinTitle(oldWinTitle);
	}

	private String setWinTitle(String newTitle){
		final String oldFrame = driver.helper().getCurrentIframePath();
		try {
			driver.selectIframeByPath("/");
			final String oldTitle = driver.getTitle();
			driver.helper().executeScript("document.title='"+newTitle+"';");
			driver.selectIframeByPath(oldFrame);
			return oldTitle;
		} catch (UiGuardException e) {
			driver.helper().handleFailure("[Error][selectIframeByPath]", e);
			return null; // You can not be here.
		}
	}
	
	private String getFrameIndexPath(){
		String currentFramePath = driver.helper().getCurrentIframePath();
		if(Strings.isNullOrEmpty(currentFramePath)||currentFramePath.equals("/")){
			return "/";
		}
		return getFrameIndexPathFromIntList(getFrameIndexList(Arrays.asList(currentFramePath.split("/"))));
		
	}
	
	private String getFrameIndexPathFromIntList(List<Integer> indexList){
		if(indexList.size()==0)
			return "/";
		String frameIndexPath="";
		for(int i = indexList.size()-1;i>-1;i--){
			frameIndexPath += "/"+indexList.get(i);
		}
		return frameIndexPath;
	}

	private List<Integer> getFrameIndexList(List<String> frameList) {
		List<Integer> indexList = new ArrayList<Integer>();
		String parentLocator = "parent";
		for(int i = frameList.size()-1;i>-1;i--){
			String frameString = frameList.get(i);
			if(Strings.isNullOrEmpty(frameString))
				continue;
			if(frameString.matches("\\d+") && Integer.getInteger(frameString)<10){
				indexList.add(Integer.getInteger(frameString));
			}else{
				indexList.add(getIframeIndex(frameString,parentLocator+".document"));
			}
			parentLocator += ".parent";
		}
		return indexList;
	}
	
	private int getIframeIndex(String frameIdOrName,String document){		
		return waitUntilFindIframeIndex(JavaScriptLoader.getJavaScriptStr(UGElementByAutoIt.class, "getIframeIndex"));
	} 
	
	private int waitUntilFindIframeIndex(final String jsStr) {
		return driver.helper().getWebDriverWait().until(new ExpectedCondition<Integer>(){
			public Integer apply(WebDriver arg0) {
				return ((Long)driver.helper().executeScript(jsStr)).intValue();
			}
		});
	}

}
