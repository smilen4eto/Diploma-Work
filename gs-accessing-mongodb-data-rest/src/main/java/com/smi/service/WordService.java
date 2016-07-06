package com.smi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.smi.model.Language;
import com.smi.model.User;
import com.smi.model.Word;
import com.smi.mongo.CRUDUser;
import com.smi.mongo.CRUDWord;

public class WordService {

	public static List<Word> findAllUsers() {
//		Word word = new Word();
//		word.setLang(new Language("ES"));
//		word.setLevel(1);
//		Map<String, Boolean> wordInEs = new HashMap<String, Boolean>();
//		wordInEs.put("appleso", false);
//		wordInEs.put("manzana", true);
//		wordInEs.put("apple", false);
//		wordInEs.put("balana", false);
//		word.setWord(wordInEs);
//		word.setWordInEnglish("apple");
//		CRUDWord.updateWordLanguageMeanings(word);
		return null;
	}
	
	public static void addNewWordDefinition(Word word){
		CRUDWord.updateWordLanguageMeanings(word);
	}

	public static DBObject findByLanguage(String language) {
		DBObject foundWord = CRUDWord.findWordByEnglishDefinition(language);
		return foundWord;
	}

	public static boolean isWordExist(Word word) {
		DBObject foundWord = CRUDWord.findWord(word.getWordInEnglish());
		if (foundWord !=null){
			return true;
		} else {
			return false;
		}
	}

	public static void saveWord(Word word) {
		CRUDWord.insertQueryWord(word);
		
	}
	
	public static Word returnWordForGame(String wordInEnglish, String username){
		DBObject user = CRUDUser.findUserByUsername(username);
		User foundUser = new User();
		foundUser.setMainLanguage(user.get("mainLanguage").toString());
		foundUser.setLangToLearn(user.get("langToLearn").toString());
		DBObject word = CRUDWord.findWordByEnglishDefinition(wordInEnglish);
		
		while((!(word.containsField("wordInEn"))||word.toString().length() == 0)){
			word = CRUDWord.findWordByEnglishDefinition(wordInEnglish);
		}
		
		System.out.println(word.toString() + "--- wooooord");
		DBObject wordMainLang = (DBObject) word.get("word_"+foundUser.getMainLanguage());
		System.out.println(wordMainLang);
		DBObject wordLangToLearn = (DBObject) word.get("word_"+foundUser.getLangToLearn());
		String wordInMainLang = wordMainLang.get(foundUser.getMainLanguage()).toString();
		Word neededWord = new Word();
		neededWord.setWordInMainLanguage(wordInMainLang);
		Map<String,Boolean> mapWords = new HashMap<String,Boolean>();
		mapWords.put(wordLangToLearn.get(foundUser.getLangToLearn()).toString(), true);
		ArrayList listW = (ArrayList) wordLangToLearn.get("word_wrong"+foundUser.getLangToLearn());
		System.out.println(listW);
		for (int i = 0; i < listW.size();i++) {
			mapWords.put(listW.get(i).toString(),false);
		}
		
		neededWord.setWord(mapWords);
		System.out.println(wordInMainLang);
		System.out.println(word.get("word_"+foundUser.getMainLanguage()).toString());
		System.out.println(word.get("word_"+foundUser.getLangToLearn()).toString());

		//neededWord.setWordInMainLanguage(word.get("word_"+foundUser.getMainLanguage()+"."+foundUser.getMainLanguage()).toString());
		//Map<String, Boolean> wordDefinition = new HashMap<String, Boolean>();
		//wordDefinition.put(word.get("word_"+foundUser.getLangToLearn()+"."+foundUser.getLangToLearn()).toString(), true);
		//wordDefinition.putAll();
		//neededWord.setWord(word.get("word_"+foundUser.getLangToLearn()));
		//System.out.println(neededWord.toString());
		JSONObject json = new JSONObject();
		json.put(foundUser.getMainLanguage(), wordInMainLang);
		json.put(foundUser.getLangToLearn(), word.get("word_"+foundUser.getLangToLearn()).toString());
		
		return neededWord;
	}

	public static void deleteWordByEn(String wordInEn) {
		CRUDWord.deleteWord(wordInEn);
		
	}

}
