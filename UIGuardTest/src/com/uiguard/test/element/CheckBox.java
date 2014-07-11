package com.uiguard.test.element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;
import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.logger.IUiGuardLogger;

public class CheckBox extends UGElement{

	public CheckBox(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}
	
	public CheckBox type(String item){
		return type(new String[]{item});		
	}
	
	public CheckBox type(String ... items){
		Set<String> checkedItems = new HashSet<String>(Lists.newArrayList(items));
		List<WebElement> elements = driver.findElements(By.name(helper().getLocatorValue())); 
		for(WebElement element : elements){
			if(checkedItems.contains(element.getAttribute("value"))){
				if(!element.isSelected()){
					element.click();
				}
				continue;
			}
			if(element.isSelected()){
				element.click();
			}
		}
		return this;
	}

}
