package com.uiguard.entity.element.imp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.uiguard.entity.driver.IUGDriver;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.logger.IUiGuardLogger;

public class TableElement extends UGElement {

	public TableElement(IUGDriver driver, IUiGuardLogger ugLogger,
			ElementLocatorType locType, String locValue) {
		super(driver, ugLogger, locType, locValue);
	}

	public int getRowsCount() {
		int count = this.findElements(By.xpath(".//tr")).size();
		getUiGuardLogger().trace("count:"+count);
		return count;
	}
	
	/**
	 * <b>Des：</b> Get the count of columns for certain Row
	 *              Column tag is "td" for default, rowIndex should start from 1
	 */
	public int getColumnsCountOfRow(int rowIndex) {
		int columnCount = this.findElement(By.xpath(getXpathOfRow(rowIndex))).
			    findElements(By.xpath(".//td")).size();
		getUiGuardLogger().trace("columnCount:"+columnCount);
		return columnCount;
	}
	
	/**
	 * <b>Des：</b> Get the count of columns tag for certain Row, e.g. tagName=th
	 *              rowIndex/columnIndex should start from 1
	 */
	public int getColumnsCountOfRowByTagName(int rowIndex, String tagName) {
		int columnCount = this.findElement(By.xpath(getXpathOfRow(rowIndex))).
			    findElements(By.tagName(tagName)).size();
		getUiGuardLogger().trace("columnCount:"+columnCount);
		return columnCount;
	}
	
	/**
	 * <b>Des：</b> Get the text of cell, cell tagName should be "td", 
	 *              rowIndex/columnIndex should start from 1
	 */
	public String getTextOfCell(int rowIndex, int columnIndex) {
		String text = this.findElement(By.xpath(getXpathOfCell(rowIndex, columnIndex))).getText();
		getUiGuardLogger().trace("text:"+text);
		return text;
	}

	/**
	 * <b>Des：</b> Get the tagName of the elements in cell, cell should be "td"
	 *              rowIndex/columnIndex should start from 1
	 */
	public String getTagNameOfCell(int rowIndex, int columnIndex) {
		String tagName = "";
		WebElement webElement = this.getElementOfCell(rowIndex, columnIndex);
		List<WebElement> elements = webElement.findElements(By.xpath(".//*"));
		for (int i = 1; i <= elements.size(); i++) {
			tagName += "tagName" + i + ":" + elements.get(i-1).getTagName() + ",";
		}
		if (!tagName.trim().equals("")) {
			tagName = tagName.substring(0, tagName.lastIndexOf(","));
		}
		getUiGuardLogger().trace("tagName:"+tagName);
		return tagName;
	}
	
	/**
	 * <b>Des：</b> Get the text of cell, cell tagName should be "td"
	 *              rowIndex/columnIndex should start from 1
	 */
	public WebElement getElementOfCell(int rowIndex, int columnIndex) {
		WebElement webElement = this.findElement(By.xpath(getXpathOfCell(rowIndex, columnIndex)));
		getUiGuardLogger().trace("webElement:"+webElement);
		return webElement;
	}
	
	/**
	 * <b>Des：</b> Get the text of cell, cell tagName should be "td"
	 *              rowIndex/columnIndex should start from 1
	 * e.g. table.getSubElementOfCell(1,2, By.tagName("input")).click();
	 */
	public WebElement getSubElementOfCell(int rowIndex, int columnIndex, By by) {
		return this.findElement(By.xpath(getXpathOfCell(rowIndex, columnIndex))).findElement(by);
	}
	
	/**
	 * <b>Des：</b> Get the xpath of the Row, rowIndex should start from 1
	 */	
	protected String getXpathOfRow(int rowIndex) {
		getUiGuardLogger().trace("TableElement->getXpathOfRow("+rowIndex+")");
		return ".//tr[" + rowIndex + "]";
	}
	
	/**
	 * <b>Des：</b> Get the xpath of the Row, rowIndex/columnIndex should start from 1
	 */	
	protected String getXpathOfCell(int rowIndex, int columnIndex) {
		getUiGuardLogger().trace("TableElement->getXpathOfCell("+rowIndex+","+columnIndex+")");
		return ".//tr[" + rowIndex + "]/td[" + columnIndex + "]";
	}
}
