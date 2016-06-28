package com.smi.model;

import java.util.List;



public class Language {
	String languageName;
	static List<Language> availableLanguages;
	
	public Language() {
		// TODO Auto-generated constructor stub
	}
	
	public Language(String languageName) {
		setLanguageName(languageName);
	}
	
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	public static void addLanguage(String languageName){
		availableLanguages.add(new Language(languageName));
	}
	
}
