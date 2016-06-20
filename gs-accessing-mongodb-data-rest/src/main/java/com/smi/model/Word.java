package com.smi.model;

import java.util.HashMap;
import java.util.Map;

public class Word {
	int id;
	int level;
	int timesAnsCorr;
	int timesAnsWrong;
	String wordInMainLanguage;
	String wordInEnglish;
	Language lang;
	Map<String, Boolean> word;
	
	public Word() {
		// TODO Auto-generated constructor stub
	}
	public Word(int level, int timesAnsCorr, int timesAnsWrong, String wordInEnglish) {
		setLevel(level);
		setTimesAnsCorr(0);
		setTimesAnsWrong(0);
		setWordInEnglish(wordInEnglish);
		word = new HashMap<String, Boolean>();
	}
	
	public Word(int level, Map<String, Boolean> wordInLang, String wordInEnglish){
		this.setLevel(level);
		this.setWord(wordInLang);
		this.setWordInEnglish(wordInEnglish);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTimesAnsCorr() {
		return timesAnsCorr;
	}
	public void setTimesAnsCorr(int timesAnsCorr) {
		this.timesAnsCorr = timesAnsCorr;
	}
	public int getTimesAnsWrong() {
		return timesAnsWrong;
	}
	public void setTimesAnsWrong(int timesAnsWrong) {
		this.timesAnsWrong = timesAnsWrong;
	}
	public Language getLang() {
		return lang;
	}
	public void setLang(Language lang) {
		this.lang = lang;
	}
	@Override
	public String toString() {
		return "Word [id=" + id + ", level=" + level + ", timesAnsCorr="
				+ timesAnsCorr + ", timesAnsWrong=" + timesAnsWrong + ", lang="
				+ lang + ", word=" + word + "]";
	}

	public void setWord(Map<String, Boolean> word) {
		this.word = word;
	}
	
	
	public String getWordInMainLanguage() {
		return wordInMainLanguage;
	}

	public void setWordInMainLanguage(String wordInMainLanguage) {
		this.wordInMainLanguage = wordInMainLanguage;
	}

	public String getWordInEnglish() {
		return wordInEnglish;
	}

	public void setWordInEnglish(String wordInEnglish) {
		this.wordInEnglish = wordInEnglish;
	}

	public Map<String, Boolean> getWord() {
		return word;
	}
}
