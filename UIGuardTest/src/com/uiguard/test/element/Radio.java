package com.uiguard.test.element;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.entity.element.IUGElement;
import com.uiguard.entity.element.imp.UGElement;
import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;


public class Radio extends UGElement{

	public Radio(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}
	
	public IUGElement type(String value) throws UiGuardException{
		WebElement element = findRadioElement(value);
		if(!element.isSelected()){
			element.click();
		}
		return this;
	}
	
	private WebElement findRadioElement(String value) throws UiGuardException{
		List<WebElement> elements = driver.findElements(By.name(helper().getLocatorValue())); 
		for(WebElement element : elements){
			if(element.getAttribute("value").equals(value)){
				return element;
			}
		}
		throw new UiGuardException("Radio not find. "+value);
	}

}
