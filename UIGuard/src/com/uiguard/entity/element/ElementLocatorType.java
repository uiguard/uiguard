package com.uiguard.entity.element;

public enum ElementLocatorType {
	
	ID("ID"),
	NAME("NAME"),
	CSS("CSS"),
	XPATH("XPATH"),
	CLASSNAME("CLASSNAME"),
	LINKTEXT("LINKTEXT"),
	TAGNAME("TAGNAME"),
	PARTIALLINKTEXT("PARTIALLINKTEXT"),
	DOM("DOM");
	
	public final String str;
	
	ElementLocatorType(String str){
		this.str = str.toUpperCase();
	}
	
	@Override
	public String toString(){
		return str;
	}
	
}
