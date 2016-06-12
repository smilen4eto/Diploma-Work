package com.smi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalChart {
	Map<Language, String> theHardestWord;
	Map<Language, String> easyestWord;
	ArrayList<User> bestUsers;
	public GlobalChart() {
		this.theHardestWord = new HashMap<Language, String>();
		this.easyestWord = new HashMap<Language, String>();
		this.bestUsers = new ArrayList<User>();
	}
	
	
	@Override
	public String toString() {
		return "GlobalChart [theHardestWord=" + theHardestWord
				+ ", easyestWord=" + easyestWord + ", bestUsers=" + bestUsers
				+ "]";
	}
	
	
	
	
}
