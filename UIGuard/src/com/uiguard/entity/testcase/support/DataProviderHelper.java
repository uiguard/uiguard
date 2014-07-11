package com.uiguard.entity.testcase.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.uiguard.entity.testcase.IUGTest;
import com.uiguard.exception.UiGuardException;

public class DataProviderHelper {
	
	public Object[][] getDataProvidorFromXls(IUGTest tb, String langMode) throws UiGuardException {
		HSSFWorkbook wb = getHSSFWorkbook(tb,langMode);
		HSSFSheet sheet = wb.getSheetAt(0);		
		return getDataProviderFromXlsBySheet(sheet);
	}
	
	public Object[][] getDataProvidorFromXlsBySheetName(IUGTest tb, String langMode,String sheetName) throws UiGuardException {
		HSSFWorkbook wb = getHSSFWorkbook(tb,langMode);
		HSSFSheet sheet = wb.getSheet(sheetName);		
		return getDataProviderFromXlsBySheet(sheet);
	}
	
	private HSSFWorkbook getHSSFWorkbook(IUGTest tb,String langMode) throws UiGuardException {
		try {
			URI fileUri = tb.getClass().getResource(tb.getClass().getSimpleName()+langMode+".xls").toURI();
			return new HSSFWorkbook(new FileInputStream(new File(fileUri)));
		} catch (FileNotFoundException e) {
			throw new UiGuardException("FileNotFoundException",e);
		} catch (URISyntaxException e) {
			throw new UiGuardException("URISyntaxException",e);
		} catch (IOException e) {
			throw new UiGuardException("IOException",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Object[][] getDataProviderFromXlsBySheet(HSSFSheet sheet) {
		int colCount = 0;
		List<Object[]> dataProvidor = new ArrayList<Object[]>();
		for(Iterator<HSSFRow> rit = sheet.rowIterator(); rit.hasNext();){
			HSSFRow row  =rit.next();
			List<Object> cellData =new ArrayList<Object>();
			if(colCount == 0){
				for (Iterator<HSSFCell> cit = row.cellIterator(); cit.hasNext();) {
					HSSFCell cell = cit.next();
					cellData.add(getValueFromCell(cell));
				}
				colCount = cellData.size();
			}else{
				int validnum = row.getLastCellNum()>colCount?colCount:row.getLastCellNum();
				for(int i = 0;i<validnum;i++){
					cellData.add(getValueFromCell(row.getCell(i)));
				}
				for(int j = validnum;j<colCount;j++){
					cellData.add(getValueFromCell(null));
				}
			}
			for(Object cdo : cellData){
				if(!cdo.toString().equals("")){
					dataProvidor.add(cellData.toArray());	
					break;
				}
			}
		}
		if(dataProvidor.size()<2){
			return new Object[0][0];
		}
		return dataProvidor.subList(1, dataProvidor.size()).toArray(new Object[0][]);
	}
	
	/**
	 * <b>描述：</b> 从单元格中获取到的数据
	 * @param cell	单元格对应的HSSFCell对象
	 * @return Object 从单元格中获取到的数据
	 */
	private Object getValueFromCell(HSSFCell cell){
		if(cell==null)return "";
		switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			case HSSFCell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case HSSFCell.CELL_TYPE_FORMULA:
				return cell.getCellFormula();
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getRichStringCellValue().toString();
			default:
				return cell.getRichStringCellValue().toString();
		}
		
	}

}
