package com.smi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sun.jersey.core.util.Base64;

public class User {
	int id;
	String name;
	String username;
	String email;
	String password;
	Language mainLanguage;
	Language langToLearn;
	Map<Language, ArrayList<Integer>> answCorrWords;
	Map<Language, ArrayList<Integer>> answWrongWords;
	Boolean repeatWronWords;
	double score;
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String name, String username, String email,
			String password, double score) {
		setName(name);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setRepeatWronWords(false);
		setScore(score);
//		answCorrWords = new HashMap<Language, ArrayList<Integer>>();
//		answWrongWords = new HashMap<Language, ArrayList<Integer>>();
//		answCorrWords.keySet().addAll(Language.availableLanguages);
//		answWrongWords.keySet().addAll(Language.availableLanguages);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassword() {
		byte[] decoded = Base64.decode(this.password.getBytes().toString());
		return Arrays.toString(decoded);
	}
	public void setPassword(String password) {
		byte[] encoded = Base64.encode(password.getBytes().toString());
		this.password = Arrays.toString(encoded);
	}
	public Language getMainLanguage() {
		return mainLanguage;
	}
	public void setMainLanguage(Language mainLanguage) {
		this.mainLanguage = mainLanguage;
	}
	public Language getLangToLearn() {
		return langToLearn;
	}
	public void setLangToLearn(Language langToLearn) {
		this.langToLearn = langToLearn;
	}
	public Boolean getRepeatWronWords() {
		return repeatWronWords;
	}
	public void setRepeatWronWords(Boolean repeatWronWords) {
		if (repeatWronWords == null){
			this.repeatWronWords = false;
		} else {
			this.repeatWronWords = repeatWronWords;
		}
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		if (score < 0){
			this.score = 0;
		} else {
			this.score = score;
		}
	}
	
	public void addCorrectWord(int wordId, Language lang){
		answCorrWords.get(lang).add(wordId);
	}
	
	public void addWrongWord(int wordId, Language lang){
		answWrongWords.get(lang).add(wordId);
	}
	
	public void removeWrongWord(int wordId, Language lang){
		answWrongWords.get(lang).remove(wordId);
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username
				+ ", email=" + email + ", password=" + password;
//				+ ", mainLanguage=" + mainLanguage + ", langToLearn="
//				+ langToLearn + ", answCorrWords=" + answCorrWords
//				+ ", answWrongWords=" + answWrongWords + ", repeatWronWords="
//				+ repeatWronWords + ", score=" + score + "]";
	}
	
	
}
